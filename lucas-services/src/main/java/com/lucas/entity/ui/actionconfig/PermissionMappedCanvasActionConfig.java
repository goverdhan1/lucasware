/**
 * 
 */
package com.lucas.entity.ui.actionconfig;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lucas.entity.user.Permission;
import com.lucas.services.util.CollectionsUtilService;

/**
 * @author Prafull
 * The class provides the implementation for providing the canvas action config
 */
public class PermissionMappedCanvasActionConfig implements
MappedActionConfigurable<Permission, Boolean> {

Map<Permission, Boolean> mappedActionConfig;

public PermissionMappedCanvasActionConfig(Map<Permission, Boolean> mappedActionConfig) {
this.mappedActionConfig = mappedActionConfig;
}

@Override
public Map<Permission, Boolean> getActionConfig()
	throws JsonParseException, JsonMappingException, IOException {


return mappedActionConfig;
}


/* (non-Javadoc)
 * @see com.lucas.entity.ui.actionconfig.MappedActionConfigurable#buildActionConfigurable(java.util.List)
 */
@Override
public void buildActionConfigurable(
	List<Permission> permissions) {

for (Permission permission : CollectionsUtilService.nullGuard(permissions)) {
	if (mappedActionConfig.containsKey(permission)) {
		mappedActionConfig.put(permission, Boolean.TRUE);
	}
}

}

}
