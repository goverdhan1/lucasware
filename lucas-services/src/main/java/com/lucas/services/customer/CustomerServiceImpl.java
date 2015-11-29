/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.services.customer;

import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

	public boolean isPrivilegedCustomer() {
		return false;
	}

}
