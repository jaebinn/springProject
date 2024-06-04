package com.gl.givuluv.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gl.givuluv.domain.dto.ManagerDTO;
import com.gl.givuluv.domain.dto.OrgDTO;
import com.gl.givuluv.domain.dto.OrgapproveDTO;
import com.gl.givuluv.domain.dto.SRegisterDTO;
import com.gl.givuluv.domain.dto.SellerDTO;
import com.gl.givuluv.domain.dto.UserDTO;
import com.gl.givuluv.mapper.FileMapper;
import com.gl.givuluv.mapper.ManagerMapper;
import com.gl.givuluv.mapper.OrgMapper;
import com.gl.givuluv.mapper.OrgapproveMapper;
import com.gl.givuluv.mapper.SRegisterMapper;
import com.gl.givuluv.mapper.SellerMapper;

@Service
public class ManagerServicelmpl implements ManagerService{

	@Autowired
	private ManagerMapper mmapper;
	@Autowired
	private OrgapproveMapper oamapper;
	@Autowired
	private SRegisterMapper srmapper;
	@Autowired
	private OrgMapper omapper;
	@Autowired
	private FileMapper fmapper;
	@Autowired
	private SellerMapper smapper;
	
	@Override
	public int insertManager(ManagerDTO manager) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ManagerDTO getManagerById(String managerid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ManagerDTO> getAllManagers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateManager(ManagerDTO manager) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteManagerById(String managerid) {
		// TODO Auto-generated method stub
		return 0;
	}
	public boolean checkManagername(String managername) {
        try {
        	// 디버깅 로그 추가
            System.out.println("Checking manager name: " + managername);
            ManagerDTO result = mmapper.checkManagername(managername);
            return result == null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error checking manager name", e);
        }
    }
	@Override
	public boolean checkManagerId(String managerid){
		return mmapper.getManagerById(managerid) == null;
	}

	@Override
	public boolean login(String managerid, String managerpw) {
		ManagerDTO manager = mmapper.getManagerById(managerid);
		if(manager != null) {
			if(manager.getManagerpw().equals(managerpw)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int getNotApproveCnt() {
		int orgNotApproveCnt = oamapper.getNotApproveCnt();
		int SellerNotApproveCnt = srmapper.getNotApproveCnt();
		int totalNotApproveCnt = orgNotApproveCnt+SellerNotApproveCnt;
		return totalNotApproveCnt;
	}

	@Override
	public List<Map<String, Object>> getApproveInfo() {
	    List<Map<String, Object>> result = new ArrayList<>();
	    
	    //승인 못받은 판매자, 단체 수, 승인 받은 판매자, 단체 수 
	    int orgNotApproveCnt = oamapper.getNotApproveCnt();
	    int sellerNotApproveCnt = srmapper.getNotApproveCnt();
	    int orgApproveCnt = oamapper.getApproveCnt();
	    int sellerApproveCnt = srmapper.getApproveCnt();

	    //승인 못받은 판매자, 단체 리스트
	    List<OrgapproveDTO> oapproveIngList = oamapper.getNotApproveList();
	    List<SRegisterDTO> sregisterIngList = srmapper.getNotApproveList();
	    List<OrgapproveDTO> oapproveEndList = oamapper.getApproveList();
	    List<SRegisterDTO> sregisterEndList = srmapper.getApproveList();

	    Map<String, Object> sellerApproveInfo = new HashMap<>();
	    sellerApproveInfo.put("sellernotApproveCnt", sellerNotApproveCnt);
	    sellerApproveInfo.put("sellerApproveCnt", sellerApproveCnt);
	    sellerApproveInfo.put("sellernotApproveList", sregisterIngList);
	    sellerApproveInfo.put("sellerApproveList", sregisterEndList);
	    result.add(sellerApproveInfo);
	    
	    Map<String, Object> orgApproveInfo = new HashMap<>();
	    orgApproveInfo.put("orgnotApproveCnt", orgNotApproveCnt);
	    orgApproveInfo.put("orgApproveCnt", orgApproveCnt);
	    orgApproveInfo.put("orgnotApproveList", oapproveIngList);
	    orgApproveInfo.put("orgApproveList", oapproveEndList);
	    result.add(orgApproveInfo);

	    
	    System.out.println(result);
	    return result;
	}

	@Override
	public boolean sellerApprove(String sname) {
		return srmapper.updatesellerApprove(sname);
	}

	@Override
	public boolean orgApprove(int approvenum) {
		return oamapper.updateorgApprove(approvenum);
	}

	@Override
	public boolean sellerApproveCancel(String sname) {
		return srmapper.cancelsellerApprove(sname);
	}

	@Override
	public boolean orgApproveCancel(int approvenum) {
		return oamapper.cancelorgApprove(approvenum);
	}

	@Override
	public Map<String, Object> orgApproveProfile(int oapprovenum) {
		String src = "/summernoteImage/";
		OrgapproveDTO orgapproveProfile = oamapper.orgApproveProfile(oapprovenum);
		OrgDTO orgInfo = omapper.getOrgById(orgapproveProfile.getOrgid());
		String systemname = fmapper.getOrgProfileByOrgid(orgapproveProfile.getOrgid());
		
		Map<String, Object> result = new HashMap<>();
		
		result.put("orgapproveProfile", orgapproveProfile);
	    result.put("orgInfo", orgInfo);
	    result.put("systemname", src+systemname);
	    
	    return result;
	}
	@Override
	public Map<String, Object> sellerApproveProfile(String sName) {
		String src = "/summernoteImage/";
		SRegisterDTO sellerapproveProfile = srmapper.sellerApproveProfile(sName);
		SellerDTO sellerInfo = smapper.getSellerInfoById(sellerapproveProfile.getSellerid());
		String systemname = fmapper.getSellerProfileById(sellerapproveProfile.getSellerid());
		
		Map<String, Object> result = new HashMap<>();
		
		result.put("sellerapproveProfile", sellerapproveProfile);
		result.put("sellerInfo", sellerInfo);
		result.put("systemname", src+systemname);
		
		return result;
	}


}	
