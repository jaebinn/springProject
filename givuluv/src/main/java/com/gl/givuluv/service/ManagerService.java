package com.gl.givuluv.service;

import java.util.List;
import java.util.Map;

import com.gl.givuluv.domain.dto.ManagerDTO;

public interface ManagerService {
	int insertManager(ManagerDTO manager);
    ManagerDTO getManagerById(String managerid);
    List<ManagerDTO> getAllManagers();
    int updateManager(ManagerDTO manager);
    int deleteManagerById(String managerid);
    boolean checkManagername(String managername); 
    boolean checkManagerId(String managerid);
	boolean login(String managerid, String managerpw);
	int getNotApproveCnt();
	List<Map<String, Object>> getApproveInfo();
}
