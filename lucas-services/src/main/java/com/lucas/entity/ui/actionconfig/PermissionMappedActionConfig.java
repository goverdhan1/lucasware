package com.lucas.entity.ui.actionconfig;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lucas.entity.user.Permission;
import com.lucas.services.util.CollectionsUtilService;

@SuppressWarnings("hiding")
public class PermissionMappedActionConfig implements
		MappedActionConfigurable<String, Map<Permission, Boolean>> {

	Map<String, Map<Permission, Boolean>> mappedActionConfig;

	public PermissionMappedActionConfig(Map<String, Map<Permission, Boolean>> mappedActionConfig) {
		this.mappedActionConfig = mappedActionConfig;
	}

	@Override
	public Map<String, Map<Permission, Boolean>> getActionConfig()
			throws JsonParseException, JsonMappingException, IOException {


		return mappedActionConfig;
	}


	@Override
	public void buildActionConfigurable(
			List<Permission> permissions) {
		
		Map<Permission, Boolean> widgetAccessConfigMap = mappedActionConfig.get("widget-access");
		if(widgetAccessConfigMap != null && widgetAccessConfigMap.size() > 0){
			
			Iterator<Entry<Permission, Boolean>> entrySetIterator = widgetAccessConfigMap.entrySet().iterator();
			Permission permission = (Permission)entrySetIterator.next().getKey();
			if(permissions.contains(permission)){
				widgetAccessConfigMap.put(permission, Boolean.TRUE);
			}
		}
		Map<Permission, Boolean> widgetActionConfigMap = mappedActionConfig.get("widget-actions");
		if (widgetActionConfigMap != null && widgetActionConfigMap.size() > 0) {
			for (Permission permission : CollectionsUtilService
					.nullGuard(permissions)) {
				if (widgetActionConfigMap.containsKey(permission)) {
					widgetActionConfigMap.put(permission, Boolean.TRUE);
				}
			}
		}
		
	}

}
