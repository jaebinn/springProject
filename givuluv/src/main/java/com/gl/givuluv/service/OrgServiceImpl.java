package com.gl.givuluv.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gl.givuluv.domain.dto.DBoardDTO;
import com.gl.givuluv.domain.dto.FileDTO;
import com.gl.givuluv.domain.dto.OrgDTO;
import com.gl.givuluv.mapper.BoardMapper;
import com.gl.givuluv.mapper.FileMapper;
import com.gl.givuluv.mapper.OrgMapper;

@Service
public class OrgServiceImpl implements OrgService {

	@Value("${file.dir}")
	private String saveFolder;
	
	@Autowired
	private OrgMapper omapper;
	
	@Autowired
	private FileMapper fmapper;
	
	@Override
    public boolean join(OrgDTO org, MultipartFile[] files) throws Exception {
		if(omapper.insertOrg(org) != 1) {
			return false;
		}
		if(files == null || files.length == 0) {
			return true;
		}
		else {
			//방금 등록한 게시글 번호
			boolean flag = false;
			System.out.println("파일 개수 : "+files.length);
			
			for(int i=0;i<files.length;i++) {
				System.out.println("for문 잘 들어옴.");
				MultipartFile file = files[i];
				System.out.println(file.getOriginalFilename());
				
				//apple.png
				String orgname = file.getOriginalFilename();
				//5
				int lastIdx = orgname.lastIndexOf(".");
				//.png
				String extension = orgname.substring(lastIdx);
				
				LocalDateTime now = LocalDateTime.now();
				String time = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));

				//20240502162130141랜덤문자열.png
				String systemname = time+UUID.randomUUID().toString()+extension;
				
				//실제 생성될 파일의 경로
				//D:/0900_GB_JDS/7_spring/file/20240502162130141랜덤문자열.png
				String path = saveFolder+systemname;
				
				//File DB 저장
				FileDTO fdto = new FileDTO();
				fdto.setSystemname(systemname);
				fdto.setConnectionid(org.getOrgid());
				fdto.setType('O');
				System.out.println("fdto 잘 포장함."+fdto);
				
				flag = fmapper.insertFile(fdto) == 1;
				System.out.println("DB에 fdto 잘 넣음.");
				
				//실제 파일 업로드
				file.transferTo(new File(path));
				System.out.println("실제 파일을 업로드 잘함.");
				
				if(!flag) {
					//업로드했던 파일 삭제, 게시글 데이터 삭제, 파일 data 삭제, ...
					System.out.println("flag false:업로드 실패");
					return false;
				}
			}
			System.out.println("flag ture:업로드 성공");
			return true;
		}
    }
	
    @Override
    public boolean login(String orgid, String orgpw) {
        OrgDTO org = omapper.getOrgById(orgid);
        if (org != null) {
            if (org.getOrgpw().equals(orgpw)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkId(String orgid) {
        return omapper.getOrgById(orgid) == null;
    }


	@Override
	public String getOrgnameByOrgid(String orgid) {
		return omapper.getOrgnameByOrgid(orgid);
	}


	@Override
	public String getOrgnameBynum(int dBoardnum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getOrgnameByCategory(String orgcategory) {
		return omapper.getOrgnameByCategory(orgcategory);
	}


	@Override
	public String getCategoryByOrgid(String orgid) {
		return omapper.getCategoryByOrgid(orgid);
	}

	@Override
	public String getOrgidByOrgname(String orgname) {
		return omapper.getOrgidByOrgname(orgname);
	}
	@Override
	public int getOrgByUnqnum(int orgunqnum) {
		return omapper.getOrgByUnqnum(orgunqnum);
	}

	@Override
	public List<Map<String, String>> getOrgProfile() {
	    List<FileDTO> fileList = fmapper.getOrgProfile();
	    String src = "/summernoteImage/";
	    List<Map<String, String>> files = new ArrayList<>();
	    
	    for(FileDTO file : fileList) {
	        List<String> orgnameList = omapper.getOrgnameListByOrgid(file.getConnectionid());
	        String orgname = orgnameList.isEmpty() ? "Unknown" : orgnameList.get(0); 
	        Map<String, String> fileMap = new HashMap<>();
	        fileMap.put("file", src + file.getSystemname());
	        fileMap.put("orgname", orgname);
	        files.add(fileMap);
	    }
	    return files;
	}

	@Override
	 public boolean checkUnqnumber(int orgunqnum) {
       return omapper.checkUnqnumber(orgunqnum);
    }

	@Override
    public boolean checkPw(String orgpw) {
        return omapper.checkPw(orgpw) == null;
    }

	@Override
	public char checkRegisterByOrgid(String loginOrg) {
		return omapper.checkRegisterByOrgid(loginOrg);
	}
	
	@Override
	public String getOrgPhoneByOrgid(String orgid) {
		
		return omapper.getOrgPhoneByOrgid(orgid);
	}

	@Override
	public String getCeoNameByOrgid(String orgid) {
		
		return omapper.getCeoNameByOrgid(orgid);
	}

	@Override
	public String getLogoByOrgid(String orgid) {
		
		return omapper.getLogoByOrgid(orgid);
	}

	@Override
	public OrgDTO getOrgInfo(String orgid) {
		 
		return omapper.getOrgInfo(orgid);
	}

	@Override
	public boolean modify(OrgDTO org, MultipartFile files) {
		System.out.println("service/org :: "+org);
		if(omapper.modify(org) != 1) { 
			System.out.println("Org User의 정보 수정 실패");
			return false;
		}
		if(files == null ) {
			System.out.println("파일을 불러오지 못했거나 없습니다.");
			return true;
		}
		else {
			System.out.println("org유저 정보 수정 완료 파일 수정 진행");
			//방금 등록한 게시글 번호
			boolean flag = false;
			
			
			
				System.out.println("for문 잘 들어옴.");
				MultipartFile file = files;
				System.out.println(file.getOriginalFilename());
				
				//apple.png
				String orgname = file.getOriginalFilename();
				//5
				int lastIdx = orgname.lastIndexOf(".");
				//.png
				String extension = orgname.substring(lastIdx);
				
				LocalDateTime now = LocalDateTime.now();
				String time = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));

				//20240502162130141랜덤문자열.png
				String systemname = time+UUID.randomUUID().toString()+extension;
				
				//실제 생성될 파일의 경로
				//D:/0900_GB_JDS/7_spring/file/20240502162130141랜덤문자열.png
				String path = saveFolder+systemname;
				System.out.println();
				//File DB 저장
				FileDTO fdto = new FileDTO();
				fdto.setSystemname(systemname);
				fdto.setConnectionid(org.getOrgid());
				fdto.setType('O');
				System.out.println("fdto 잘 포장함."+fdto);
				
				flag = fmapper.updateFile(fdto) == 1;
				System.out.println("DB에 fdto 잘 넣음.");
				
				//실제 파일 업로드
				try {
					file.transferTo(new File(path));
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("path :: "+path);
				System.out.println("실제 파일을 업로드 잘함.");
				
				if(!flag) {
					//업로드했던 파일 삭제, 게시글 데이터 삭제, 파일 data 삭제, ...
					System.out.println("flag false:업로드 실패");
					return false;
				}
			
			System.out.println("flag ture:업로드 성공");
			return true;
		}
	}

	@Override
	public String getOrgSystemname(String orgid) {
		
		return omapper.getOrgSystemname(orgid);
	}

	@Override
	public List<String> getD_board(String orgid) {
		
		return omapper.getD_board(orgid);
	}

	@Override
	public List<String> getF_board(String orgid) {
		
		return omapper.getF_board(orgid);
	}
	@Override
	public List<String> Dboard_info(String orgid) {
		
		return omapper.getDboard_info(orgid);
	}

	@Override
	public String getDboardtitle(String orgid) {
		return omapper.getDboardtitle(orgid);
	}

	@Override
	public String getTarget_amount(String orgid) {
		return omapper.getTarget_amount(orgid);
	}

	@Override
	public String getSave_money(String orgid) {
		return omapper.getSave_money(orgid);
	}

	@Override
	public List<String> getD_paymentinfo(String orgid) {
		return omapper.getD_paymentinfo(orgid);
	}

	@Override
	public List<String> getD_payment_id(String orgid) {
		return omapper.getD_payment_id(orgid);
	}

	@Override
	public List<Integer> getD_payment_cost(String orgid) {
		return omapper.get_D_payment_cost(orgid);
	}

	@Override
	public String getFboardtitle(String orgid) {
		return omapper.getFboardtitle(orgid);
	}

	@Override
	public String getf_Target_amount(String orgid) {
		return omapper.getf_Target_amount(orgid);
	}

	@Override
	public String getf_Save_money(String orgid) {
		return omapper.getf_Save_money(orgid);
	}

	@Override
	public String getFollow(String orgid) {
		return omapper.getFollow(orgid);
	}

	@Override
	public String getFollow_amount(String orgid) {
		return omapper.getFollow_amount(orgid);
	}

	@Override
	public String getD_boardnum(String orgid) {
		return omapper.getD_boardnum(orgid);
	}

	@Override
	public String getLike_id(String d_boardnum) {
		return omapper.getLike_id(d_boardnum);
	}

	@Override
	public String getLike_amount(String d_boardnum) {
		return omapper.getLike_amount(d_boardnum);
	}

	@Override
	public String getF_boardnum(String orgid) {
		return omapper.getF_boardnum(orgid);
	}

	@Override
	public String getProduct_name(String f_boardnum) {
		return omapper.getProduct_name(f_boardnum);
	}

	@Override
	public String getp_amount(String f_boardnum) {
		return omapper.getp_amount(f_boardnum);
	}

	@Override
	public String getp_cost(String f_boardnum) {
		return omapper.getp_cost(f_boardnum);
	}

	@Override
	public String getReview_id(String f_boardnum) {
		return omapper.getReview_id(f_boardnum);
	}

	@Override
	public String getReview_date(String f_boardnum) {
		return omapper.getReview_date(f_boardnum);
	}

	@Override
	public String getReview_star(String f_boardnum) {
		return omapper.getReview_star(f_boardnum);
	}
 
	 @Override
	   public boolean checkuserPw(String orgpw) {
	      return omapper.checkuserPw(orgpw)==1;
	   }


	@Override
	public boolean deleteOrg(String orgid) {
		return omapper.deleteOrg(orgid)==1;
	}

	

	@Override
	public boolean deleteFollow(String orgid) {
		return omapper.deleteFollow(orgid)==1;
	}

	@Override
	public boolean deleteD_Like(String d_boardnum) {
		return omapper.deleteD_Like(d_boardnum)==1;
	}
	@Override
	public boolean deleteF_Like(String f_boardnum) {
		return omapper.deleteF_Like(f_boardnum)==1;
	}

	@Override
	public boolean deleteD_Review(String d_boardnum) {
		return omapper.deleteD_Review(d_boardnum)==1;
	}
	@Override
	public boolean deleteF_Review(String f_boardnum) {
		return omapper.deleteF_Review(f_boardnum)==1;
	}

	@Override
	public boolean deleteD_payment(String orgid) {
		return omapper.deleteD_payment(orgid)==1;
	}


	@Override
	public boolean deleteF_payment(String orgid) {
		return omapper.deleteF_payment(orgid)==1;
	}

	@Override
	public String getuserPwById(String orgid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteO_approve(String orgid) {
		return omapper.deleteO_approve(orgid);
	}

	@Override
	public boolean deleteO_register(String orgid) {
		return omapper.deleteO_register(orgid);
	}

	@Override
	public boolean deleteD_board(String orgid) {
		return omapper.deleteD_board(orgid);
	}

	@Override
	public boolean deleteF_board(String orgid) {
		return omapper.deleteF_board(orgid);
	}

	@Override
	public List<Map<String, Object>> getD_boardinfo(String orgid) {
		return omapper.getD_boardinfo(orgid);
	}

	@Override
	public List<Map<String, Object>> getF_boardinfo(String orgid) {
		return omapper.getF_boardinfo(orgid);
	}

	@Override
	public List<Map<String, Object>> getReviewinfo(String f_boardnum) {
		return omapper.getReviewinfo(f_boardnum);
	}

	@Override
	public List<Map<String, Object>> getFollowinfo(String orgid) {
		return omapper.getFollowinfo(orgid);
	}

	@Override
	public List<Map<String, Object>> getLikeinfo(String d_boardnum) {
		return omapper.getLikeinfo(d_boardnum);
	}

	@Override
	public String getLike_cnt(String orgid) {
		return omapper.getLike_cnt(orgid);
	}

	@Override
	public String getReview_cnt(String orgid) {
		return omapper.getReview_cnt(orgid);
	}

	@Override
	public String getFollow_cnt(String orgid) {
		return omapper.getFollow_cnt(orgid);
	}

	

}
