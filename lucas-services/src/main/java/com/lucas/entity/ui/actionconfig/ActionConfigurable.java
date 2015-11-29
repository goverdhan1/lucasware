package com.lucas.entity.ui.actionconfig;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface ActionConfigurable {

	Object getActionConfig() throws JsonParseException, JsonMappingException, IOException;
}
