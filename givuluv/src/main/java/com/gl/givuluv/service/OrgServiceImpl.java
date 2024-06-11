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
		if(omapper.updateOrg(org) != 1) { 
			return false;
		}
		if(files == null ) {
			return true;
		}
		else {
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

}
