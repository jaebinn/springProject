package com.gl.givuluv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gl.givuluv.domain.dto.ManagerDTO;



@Mapper
public interface ManagerMapper {
	// Create (C)
    int insertManager(ManagerDTO manager);
    // Read (R)
    ManagerDTO getManagerById(String managerid);
    List<ManagerDTO> getAllManagers();
    // Update (U)
    int updateManager(ManagerDTO manager);
    // Delete (D)
    int deleteManagerById( String managerid);
    ManagerDTO checkManagername(@Param("managername") String managername);
}
