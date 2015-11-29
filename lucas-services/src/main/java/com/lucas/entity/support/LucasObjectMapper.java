package com.lucas.entity.support;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.lucas.entity.ui.actionconfig.MappedActionConfigurable;
import com.lucas.entity.ui.actionconfig.PermissionMappedCanvasActionConfig;
import com.lucas.entity.user.Permission;

public class LucasObjectMapper extends ObjectMapper {
	
	private static final long serialVersionUID = -1280251949989784166L;

	@SuppressWarnings("unchecked")
	public LucasObjectMapper(){
		
		SimpleModule module = new SimpleModule("JSONModule", new Version(2, 3, 3, null, null, null));
		module.addSerializer((Class<MappedActionConfigurable<String, Map<Permission, Boolean>>>)(Class<?>)MappedActionConfigurable.class, new ActionConfigJsonSerializer());
		module.addDeserializer((Class<MappedActionConfigurable<String, Map<Permission, Boolean>>>)(Class<?>)MappedActionConfigurable.class, new ActionConfigJsonDeserializer());
		// Add the serializer and deserializer for the canvas action config to the object mapper
		module.addSerializer(PermissionMappedCanvasActionConfig.class, new CanvasActionConfigJsonSerializer());
		module.addDeserializer(PermissionMappedCanvasActionConfig.class, new CanvasActionConfigJsonDeserializer());

		module.addSerializer(Date.class,new JsonDateSerializer());
		this.registerModule(module);
	}
}
