package com.lvmama.vst.hhs.stampDefinition.repositorySlave;

import java.util.List;

import com.lvmama.vst.hhs.model.admin.StampRequest;

public interface StampDefinitionRepositoryCustomSlave {

	List<String>  findByFileds(StampRequest stamps,int startrum,int num);
	
	long countFindByFileds(StampRequest stamps);
	
	String findStampMaxStampNo();
	
}
