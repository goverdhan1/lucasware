package com.lucas.entity.support;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.lucas.services.util.DateUtils;

public class JsonDateSerializer extends JsonSerializer<Date>{


	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)
			throws IOException, JsonProcessingException {

		String formattedDate = DateUtils.formatDate(date);
		gen.writeString(formattedDate);;
	}
}