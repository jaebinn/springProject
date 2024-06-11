package com.gl.givuluv.service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.gl.givuluv.domain.dto.FileDTO;
import com.gl.givuluv.domain.dto.LikeDTO;
import com.gl.givuluv.domain.dto.ProductDTO;
import com.gl.givuluv.domain.dto.SBoardDTO;
import com.gl.givuluv.domain.dto.SBoardwithFileDTO;
import com.gl.givuluv.domain.dto.SRegisterDTO;
import com.gl.givuluv.domain.dto.SinfoDTO;
import com.gl.givuluv.domain.dto.StoreDTO;
import com.gl.givuluv.mapper.BoardMapper;
import com.gl.givuluv.mapper.FileMapper;
import com.gl.givuluv.mapper.LikeMapper;
import com.gl.givuluv.mapper.ProductMapper;
import com.gl.givuluv.mapper.SinfoMapper;
import com.gl.givuluv.mapper.StoreMapper;

@Service
public class StoreServiceImpl implements StoreService {

	@Value("${file.dir}")
	private String saveFolder;
	
	@Autowired
	private BoardMapper bmapper;
	
	@Autowired
	private StoreMapper smapper;
	
	@Autowired
	private ProductMapper pmapper;
	
	@Autowired 
	private FileMapper fmapper;
	
	@Autowired
	private LikeMapper lmapper;
	
	@Autowired
	private SinfoMapper simapper;

	

	@Override
	public StoreDTO getStoreList(int connectid) {
		return smapper.getStoreById(connectid);
	}
	

	@Override
	   public boolean regist(Model model, SBoardDTO sBoard, String sellerId, List<ProductDTO> products, String filenames, MultipartFile thumbnail) throws Exception {
	      // s_num 찾기
	      StoreDTO store = smapper.getStoreBySellerId(sellerId);
	      int sNum = store.getSNum();
	      sBoard.setSNum(sNum);
	      System.out.println(sNum);
	      
	      if(bmapper.insertStoreBoard(sBoard)==1) {
	         int sBoardnum = bmapper.getStoreBoardLastNumBySNum(sNum);
	         System.out.println(sBoardnum);
	         // content에 있는 파일이름 DB에 저장
	         System.out.println(filenames);
	         String[] filenameList = filenames.split(",");
	         if(!filenameList[0].isEmpty()){
	            for (String systemname : filenameList) {
	               FileDTO file = new FileDTO();
	               file.setConnectionid(sBoardnum+"");
	               file.setType('M');
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
	         thumbnailFile.setConnectionid(sBoardnum+"");
	         thumbnailFile.setType('M');
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
	            product.setConnectid(sBoardnum+"");
	            if(pmapper.insertSBoardProduct(product)==1) {}
	            else {
	               //넣은 product들 삭제 / SBoard 삭제 ...
	               return false;
	            }
	         }
	      }
	      return true;
	   }
	@Override
	public String getStoreName(int snum) {
		return smapper.getStoreName(snum);
	}
	
	@Override
	public boolean insertLikeSBoard(LikeDTO likedto) {
		return lmapper.insertLike(likedto) == 1;
	}
	
	@Override
	public boolean deleteLikeSBoard(int sboardnum, String userid) {
		return lmapper.deleteSLike(sboardnum, userid);
	}
	
	@Override
	public LikeDTO getSBoardLike(int connectid, String loginUser) {
		return lmapper.getSBoardLike(connectid, loginUser);
	}
	
	//MDM
	@Override
	public boolean checkRegnum(String regnum) {
		return smapper.checkRegnum(regnum) == 1;
	}
	//MDM
	@Override
	public boolean insertStoreSignup(SRegisterDTO srdto) {
		return smapper.insertStoreSignup(srdto) == 1;
	}
	//MDM
	@Override
	public boolean checkStorename(String storename) {
		return smapper.checkStorename(storename) == 1;
	}
	//MDM
	@Override
	public char checkStoreBySellerid(String loginSeller) {
		return smapper.checkStoreBySellerid(loginSeller);
	}
	//MDM
		@Override
		public List<SBoardwithFileDTO> getStoreViewProduct(int storenum, String loginUser) {
			List<SBoardwithFileDTO> resultList = new ArrayList<>();
			
			int[] s_boardnum = bmapper.getSBoardnumBySNum(storenum);
			
			for(int sbnum : s_boardnum) {
				SBoardDTO sdto = bmapper.getSBoard(sbnum);
				ProductDTO pdto = pmapper.getSList(sbnum);
				FileDTO fdto = fmapper.getSBoardFile(sbnum);
				LikeDTO ldto = lmapper.getSBoardLike(sbnum, loginUser);
				
				SBoardwithFileDTO svdto = new SBoardwithFileDTO();
				
				svdto.setProduct(pdto);
				svdto.setSBoard(sdto);
				svdto.setProductFile(fdto);
				svdto.setLike(ldto);
				
				resultList.add(svdto);
			}
			
			
			return resultList;
		}


		@Override
		public List<StoreDTO> getStore() {
			return smapper.getMStoreList();
		}
		//MDM
		@Override
		public SinfoDTO getSinfoByStorenum(int storenum) {
			return simapper.getSinfoByStorenum(storenum);
		}
		
		//MDM
		@Override
		public String getStoreMainImg(int storenum) {
			return fmapper.getStoreMainImg(storenum);
		}
		
		 @Override
		   public List<String> getStoreSubImg(int storenum) {
		      String[] systemnameList = fmapper.getStoreSubImg(storenum);

		      List<String> systemnames = new ArrayList<String>();
		      for (String systemname : systemnameList) {
		         systemnames.add("/summernoteImage/"+systemname);
		      }
		      
		      return systemnames;
		   }
		
		//MDM
		@Override
		public StoreDTO getStoreByStorenum(int storenum) {
			return smapper.getStoreByStorenum(storenum);
		}
		
		@Override
		   public SinfoDTO insertSinfo(SinfoDTO sinfo, List<MultipartFile> files, MultipartFile thumbnail) throws Exception {
		      System.out.println("insert" + sinfo);
		      if (simapper.insertSinfo(sinfo) == 1) {
		         // 썸네일 저장
		         String orgname = thumbnail.getOriginalFilename();
		         int lastIdx = orgname.lastIndexOf(".");
		         String ext = orgname.substring(lastIdx);
		         System.out.println(ext);

		         LocalDateTime now = LocalDateTime.now();
		         String time = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));

		         String systemname = time + UUID.randomUUID().toString() + ext;

		         String path = saveFolder + systemname;

		         FileDTO thumbnailFile = new FileDTO();
		         thumbnailFile.setConnectionid(sinfo.getSNum() + "");
		         thumbnailFile.setType('S');
		         thumbnailFile.setSystemname(systemname);
		         System.out.println(thumbnailFile.getConnectionid());
		         System.out.println(thumbnailFile.getType());
		         System.out.println(thumbnailFile.getSystemname());

		         if (fmapper.insertThumbnail(thumbnailFile) == 1) {
		            thumbnail.transferTo(new File(path));
		         } else {
		            // 해당하는 파일들과 파일db 삭제, board삭제 등
		            return null;
		         }
		         
		         // 파일 저장
		         int i=1;
		         System.out.println("files 사이즈 : "+files.size());
		         for (MultipartFile file_temp : files) {
		            if(i != files.size()) {
		               String orgname_file_temp = file_temp.getOriginalFilename();
		               int lastIdx_file_temp = orgname_file_temp.lastIndexOf(".");
		               String ext_file_temp = orgname_file_temp.substring(lastIdx_file_temp);
		               System.out.println("ext: "+ext_file_temp);
		               
		               LocalDateTime now_file_temp = LocalDateTime.now();
		               String time_file_temp = now_file_temp.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
		               
		               String systemname_file_temp = time_file_temp + UUID.randomUUID().toString() + ext_file_temp;
		               
		               String path_file_temp = saveFolder + systemname_file_temp;
		               
		               FileDTO file = new FileDTO();
		               file.setConnectionid(sinfo.getSNum() + "");
		               file.setType('S');
		               file.setSystemname(systemname_file_temp);
		               System.out.println(file.getConnectionid());
		               System.out.println(file.getType());
		               System.out.println(file.getSystemname());
		               if (fmapper.insertFile(file) == 1) {
		                  file_temp.transferTo(new File(path_file_temp));
		               } else {
		                  // 해당하는 파일들과 파일db 삭제, board삭제 등
		                  return null;
		               }
		               i++;
		            }
		         }

		         return simapper.getSinfoByStorenum(sinfo.getSNum());
		      }
		      return null;
		   }

		   @Override
		   public StoreDTO getStoreBySellerId(String sellerid) {
		      return smapper.getStoreBySellerId(sellerid);
		   }
}
