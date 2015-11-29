package com.lucas.entity.support;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.lucas.entity.ui.actionconfig.MappedActionConfigurable;
import com.lucas.entity.user.Permission;
import com.lucas.services.util.CollectionsUtilService;

public class ActionConfigJsonSerializer extends JsonSerializer<MappedActionConfigurable<String, Map<Permission, Boolean>>> {

	@Override
	public void serialize(MappedActionConfigurable<String, Map<Permission, Boolean>> value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		Map<String, Map<Permission, Boolean>> actionConfigMap = value.getActionConfig();
		
		jgen.writeStartObject();
		Map<Permission, Boolean> widgetAccessConfigMap = actionConfigMap.get("widget-access");
		Entry<Permission, Boolean> widgetAccessEntry = null;
		if(widgetAccessConfigMap != null && widgetAccessConfigMap.size() > 0){
			
			Iterator<Entry<Permission, Boolean>> entrySetIterator = widgetAccessConfigMap.entrySet().iterator();
			widgetAccessEntry = entrySetIterator.next();
			jgen.writeFieldName("widget-access");
			jgen.writeStartObject();
			jgen.writeBooleanField(widgetAccessEntry.getKey().getPermissionName(), widgetAccessEntry.getValue().booleanValue());
			jgen.writeEndObject();
		}	
		Map<Permission, Boolean> widgetActionConfigMap = actionConfigMap.get("widget-actions");
		
		if (widgetActionConfigMap != null && widgetActionConfigMap.size() > 0) {
			jgen.writeFieldName("widget-actions");
			jgen.writeStartObject();
			for (Map.Entry<Permission, Boolean> entry : widgetActionConfigMap
					.entrySet()) {

				jgen.writeBooleanField(entry.getKey().getPermissionName(),
						entry.getValue().booleanValue());
			}
			jgen.writeEndObject();
		}
		jgen.writeEndObject();
		
	}

}
