package com.lucas.alps.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate3.Hibernate3Module;

public class HibernateAwareObjectMapper extends ObjectMapper {
	
		public HibernateAwareObjectMapper() {
			ObjectMapper mapper = new ObjectMapper();
			// for Hibernate 4.x:
			//mapper.registerModule(new Hibernate4Module());
			// or, for Hibernate 3.6
			mapper.registerModule(new Hibernate3Module());
		}
	

}
