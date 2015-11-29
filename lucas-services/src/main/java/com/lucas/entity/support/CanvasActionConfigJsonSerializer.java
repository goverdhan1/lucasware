/**
 * 
 */
package com.lucas.entity.support;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.lucas.entity.ui.actionconfig.MappedActionConfigurable;
import com.lucas.entity.ui.actionconfig.PermissionMappedCanvasActionConfig;
import com.lucas.entity.user.Permission;

/**
 * @author Prafull
 * The class is serializer for the canvas action config
 */
public class CanvasActionConfigJsonSerializer extends JsonSerializer<PermissionMappedCanvasActionConfig> {

	
	/* (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
	 */
	@Override
	public void serialize(PermissionMappedCanvasActionConfig value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		Map<Permission, Boolean> actionConfigMap = value.getActionConfig();
		jgen.writeStartObject();
		for (Map.Entry<Permission, Boolean> entry : actionConfigMap.entrySet()) {
			jgen.writeBooleanField(entry.getKey().getPermissionName(), entry.getValue().booleanValue());
		}
		
        jgen.writeEndObject();
		
	}

}
