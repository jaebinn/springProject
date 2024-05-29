package com.gl.givuluv.service;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.gl.givuluv.domain.dto.FBoardDTO;
import com.gl.givuluv.domain.dto.FileDTO;
import com.gl.givuluv.domain.dto.ProductDTO;
import com.gl.givuluv.mapper.BoardMapper;
import com.gl.givuluv.mapper.FileMapper;
import com.gl.givuluv.mapper.ProductMapper;


@Service
public class FBoardServiceImpl implements FBoardService{

	@Value("${file.dir}")
	private String saveFolder;
	
	@Autowired
	private BoardMapper bmapper;
	
	@Autowired
	private FileMapper fmapper;
	
	@Autowired
	private ProductMapper pmapper;
	
	public boolean regist(Model model, FBoardDTO fBoard, List<ProductDTO> products, String filenames, MultipartFile thumbnail) throws Exception {
		// s_num 찾기
		if(bmapper.insertFunding(fBoard)==1) {
			int fBoardnum = bmapper.getFundingLastBoardnumByOrgid(fBoard.getOrgid());
			System.out.println(fBoardnum);
			// content에 있는 파일이름 DB에 저장
			System.out.println("파일이름들 : "+filenames);
			String[] filenameList = filenames.split(",");
			if(!filenameList[0].isEmpty()){
				for (String systemname : filenameList) {
					FileDTO file = new FileDTO();
					file.setConnectionid(fBoardnum+"");
					file.setType('F');
					file.setSystemname(systemname);
					fmapper.insertFile(file);
				}
			}
			
			// 썸네일 저장
			String orgname = thumbnail.getOriginalFilename();
			int lastIdx = orgname.lastIndexOf(".");
			String ext = orgname.substring(lastIdx);
			System.out.println(ext);
			
			LocalDateTime now = LocalDateTime.now();
			String time = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
			
			String systemname = time+UUID.randomUUID().toString()+ext;
			
			String path = saveFolder+systemname;
			
			FileDTO thumbnailFile = new FileDTO();
			thumbnailFile.setConnectionid(fBoardnum+"");
			thumbnailFile.setType('F');
			thumbnailFile.setSystemname(systemname);
			System.out.println(thumbnailFile.getConnectionid());
			System.out.println(thumbnailFile.getType());
			System.out.println(thumbnailFile.getSystemname());
			
			if(fmapper.insertThumbnail(thumbnailFile) == 1) {
				thumbnail.transferTo(new File(path));
			}
			else {
				//content에 해당하는 파일들과 파일db 삭제, board삭제 등
				return false;
			}
			// product 넣기
			for (ProductDTO product : products) {
				product.setConnectid(fBoardnum+"");
				if(pmapper.insertFundingProduct(product)==1) {}
				else {
					//넣은 product들 삭제 / SBoard 삭제 ...
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public List<Map<String, Object>> getFundingList() {
	    List<FBoardDTO> fundingList = bmapper.getFundingList();
	    List<Map<String, Object>> result = new ArrayList<>();
	    String src = "/summernoteImage/";
	    LocalDate today = LocalDate.now(); // 현재 날짜

	    for(FBoardDTO fboard : fundingList) {
	        // 펀딩 썸네일 뽑아오기
	        List<String> files = fmapper.getFileByFBoardnum(fboard.getFBoardnum());
	        String systemname = src+files.get(0);
	        // 펀딩 종료일 뽑아오기
	        List<String> fundingEndDayList = bmapper.getFundingEndDay(fboard.getFBoardnum());
	        String fundingEndDayStr = fundingEndDayList.isEmpty() ? null : fundingEndDayList.get(0);
	        long fundingDDay = -1; // 기본값 -1로 설정 (종료일이 없는 경우)

	        if (fundingEndDayStr != null) {
	            LocalDate fundingEndDay = LocalDate.parse(fundingEndDayStr);
	            fundingDDay = ChronoUnit.DAYS.between(today, fundingEndDay); // 종료일까지 남은 일 수 계산
	        }
	        List<String> orgname = bmapper.getOrgnameByOrgId(fboard.getOrgid());
	        
	        List<Integer> targetMoneyList = bmapper.getTargetMoneyByFBoardnum(fboard.getFBoardnum());
	        List<Integer> saveMoneyList = bmapper.getSaveMoneyByFBoardnum(fboard.getFBoardnum());
	        List<Double> percentageList = new ArrayList<>();

	        for (int i = 0; i < targetMoneyList.size(); i++) {
	            int targetMoney = targetMoneyList.get(i);
	            int saveMoney = saveMoneyList.get(i);
	            double percentage = 0.0;

	            if (targetMoney > 0) {
	                percentage = (double) saveMoney / targetMoney * 100;
	            }

	            percentageList.add(percentage);
	        }
	        
	        
	        Map<String, Object> map = new HashMap<>();
	        map.put("fboard", fboard);
	        map.put("systemname", systemname);
	        map.put("fundingDDay", fundingDDay);
	        map.put("percentage", percentageList.get(0));
	        map.put("orgname",orgname.get(0));

	        result.add(map);
	    }
	    return result;
	}

	@Override
    public Map<String, Object> getFundingDetail(int fBoardnum) {
        List<Map<String, Object>> fundingList = getFundingList();
        List<ProductDTO> products = pmapper.getProductByConnectid(fBoardnum);
        String orgid = bmapper.getOrgIdByFBoardnum(fBoardnum);
        String orgProfile = fmapper.getOrgProfileByOrgid(orgid);
        String src = "/summernoteImage/";
        
        Map<String, Object> fundingDetail = new HashMap<>();
        for (Map<String, Object> funding : fundingList) {
            FBoardDTO fboard = (FBoardDTO) funding.get("fboard");
            if (fboard.getFBoardnum() == fBoardnum) {
                fundingDetail.putAll(funding);
                fundingDetail.put("products", products);
                fundingDetail.put("orgProfile", src+orgProfile);
                break;
            }
        }
        System.out.println(fundingDetail);
        return fundingDetail;
    }

	

}
