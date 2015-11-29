package com.lucas.entity.ui.actionconfig;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lucas.entity.support.ActionConfigJsonDeserializer;
import com.lucas.entity.user.Permission;

@JsonDeserialize(using = ActionConfigJsonDeserializer.class, as = PermissionMappedActionConfig.class)
public interface MappedActionConfigurable<T, S> extends ActionConfigurable{

	Map<T, S> getActionConfig() throws JsonParseException, JsonMappingException, IOException;
	
	void buildActionConfigurable(List<Permission> permissionList);
}
