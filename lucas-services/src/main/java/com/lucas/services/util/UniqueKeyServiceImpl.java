/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.services.util;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class UniqueKeyServiceImpl implements UniqueKeyService {

	public String generateUniqueId() {

		return UUID.randomUUID().toString().toUpperCase();

	}

}
