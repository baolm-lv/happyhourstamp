package com.lvmama.vst.hhs.downgrade;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface DownGradeService {
	
	List<String> getDownDradeList(String serviceName);
	
	Map<String, Map<String, String>> getDownGradeMap(String sericeName);
	
	void downGradeByMethods(List<String> methods, String sericeName, boolean local);
	
	void downGradeByIds(List<String> ids, boolean local);
	
	void recoverByIds(List<String> ids, boolean local);
	
	void recoverByService(String serviceName);
	
}
