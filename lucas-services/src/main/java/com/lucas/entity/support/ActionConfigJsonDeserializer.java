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
import com.lucas.entity.ui.actionconfig.PermissionMappedActionConfig;
import com.lucas.entity.user.Permission;

public class ActionConfigJsonDeserializer extends JsonDeserializer<MappedActionConfigurable<String, Map<Permission, Boolean>>> {

	@Override
	public MappedActionConfigurable<String, Map<Permission, Boolean>> deserialize(
			JsonParser jp, DeserializationContext ctxt) throws IOException,
			JsonProcessingException {

		Map<String, Map<Permission, Boolean>> map = new HashMap<String, Map<Permission, Boolean>>();
		JsonNode node = jp.getCodec().readTree(jp);
		Iterator<Map.Entry<String, JsonNode>> nodesItr = node.fields();
		while (nodesItr.hasNext()) {
			Map<Permission, Boolean> configMap = new HashMap<Permission, Boolean>();
			Entry<String, JsonNode> etr = nodesItr.next();

			JsonNode valueNode = etr.getValue();
			Iterator<Map.Entry<String, JsonNode>> valueNodesItr = valueNode
					.fields();
			while (valueNodesItr.hasNext()) {
				Entry<String, JsonNode> valueEtr = valueNodesItr.next();
				Permission permission = new Permission(valueEtr.getKey());
				configMap.put(permission, valueEtr.getValue().booleanValue());
			}

			map.put(etr.getKey(), configMap);
		}
		return new PermissionMappedActionConfig(map);
	}

	

}
