package com.lvmama.vst.hhs.stampDefinition.repositorySlave;

import java.util.List;

public interface PresaleStampDefinitionProductBindingRepositoryCustomSlave {
	
	List<String> getStampDefinitionProductBinding(List<String> categoryIds, int start,int end);
	
	long countStampDefinitionProductBinding(List<String> categoryIds);

	
}
