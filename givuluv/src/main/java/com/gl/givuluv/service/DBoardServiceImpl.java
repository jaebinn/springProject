package com.gl.givuluv.service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gl.givuluv.domain.dto.CategoryDTO;
import com.gl.givuluv.domain.dto.Criteria;
import com.gl.givuluv.domain.dto.DBoardDTO;
import com.gl.givuluv.domain.dto.FileDTO;
import com.gl.givuluv.mapper.BoardMapper;
import com.gl.givuluv.mapper.FileMapper;

@Service
public class DBoardServiceImpl implements DBoardService{
	
	@Value("${file.dir}")
	private String saveFolder;
	
	@Autowired
	private BoardMapper dbmapper;
	
	@Autowired
	private FileMapper fmapper;
	
	@Override
	public boolean regist(DBoardDTO dboard, String filenames, MultipartFile thumbnail) throws Exception{
		if(dbmapper.insertDonation(dboard) != 1) {
			return false;
		}
		else {
			//board 마지막 번호 찾기
			int dBoardnum = dbmapper.getDonationLastBoardnumByOrgid(dboard.getOrgid());
			
			// content에 있는 파일이름 DB에 저장
			System.out.println(filenames);
			String[] filenameList = filenames.split(",");
			for (String systemname : filenameList) {
				FileDTO file = new FileDTO();
				file.setConnectionid(dBoardnum+"");
				file.setType('D');
				file.setSystemname(systemname);
				fmapper.insertFile(file);
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
			thumbnailFile.setConnectionid(dBoardnum+"");
			thumbnailFile.setType('D');
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
			return true;
		}
	}


	@Override
	public DBoardDTO getDonation(int dBoardnum) {
		return dbmapper.getDonationByBoardnum(dBoardnum);
	}

	@Override
	//전체 
	public List<DBoardDTO> getList() {
		return dbmapper.getList();
	}

	@Override
	//카테고리순
	public List<DBoardDTO> getList(CategoryDTO category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DBoardDTO> getList(Criteria cri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DBoardDTO> getList(Criteria cri, String orgid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean modify(DBoardDTO dboard) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean changeOrgid(DBoardDTO dboard) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(int dBoardnum) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeByOrgid(String orgid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getOrgnameBynum(int dBoardnum) {
		return dbmapper.getOrgnameBynum(dBoardnum);
	}

	@Override
    public List<DBoardDTO> getItemsByCategory(String orgcategory) {
        List<DBoardDTO> items = new ArrayList<>();
        if ("전체".equals(orgcategory)) {
            items = getList();
        } else {
            items = dbmapper.getListByCategory(orgcategory);
        }
        return items;
    }

	@Override
	   public int getDonationLastBoardnumByOrgid(String orgid) {
	      return dbmapper.getDonationLastBoardnumByOrgid(orgid);
	   }


	@Override
	public String getEnddateByBoardnum(int dBoardnum) {
		return dbmapper.getEnddateByBoardnum(dBoardnum);
	}
}
