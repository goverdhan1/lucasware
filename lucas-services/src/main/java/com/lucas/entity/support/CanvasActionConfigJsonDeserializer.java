/**
 * 
 */
package com.lucas.entity.support;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.lucas.entity.ui.actionconfig.MappedActionConfigurable;
import com.lucas.entity.ui.actionconfig.PermissionMappedCanvasActionConfig;
import com.lucas.entity.user.Permission;

/**
 * @author Prafull
 * This class is a deserializer for the canvas action config.
 */
public class CanvasActionConfigJsonDeserializer extends JsonDeserializer<PermissionMappedCanvasActionConfig> {

	
	/* (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml.jackson.core.JsonParser, com.fasterxml.jackson.databind.DeserializationContext)
	 */
	@Override
	public PermissionMappedCanvasActionConfig deserialize(
			JsonParser jp, DeserializationContext ctxt) throws IOException,
			JsonProcessingException {

		Map<Permission, Boolean> map = new HashMap<Permission, Boolean>();
		JsonNode node = jp.getCodec().readTree(jp);
		Iterator<Map.Entry<String, JsonNode>> nodesItr = node.fields();
		while(nodesItr.hasNext()){
			Entry<String, JsonNode> etr = nodesItr.next();
			
			Permission permission = new Permission(etr.getKey());
			map.put(permission, etr.getValue().asBoolean());
		}
		return new PermissionMappedCanvasActionConfig(map);
	}

	

}
