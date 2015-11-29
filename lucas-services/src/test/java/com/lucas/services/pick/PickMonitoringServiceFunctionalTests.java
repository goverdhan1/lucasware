package com.lucas.services.pick;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.collections.MultiMap;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.lucas.services.search.SearchAndSortCriteria;
import com.lucas.services.sort.SortType;
import com.lucas.testsupport.AbstractSpringFunctionalTests;

@TransactionConfiguration(transactionManager = "resourceTransactionManager")
public class PickMonitoringServiceFunctionalTests extends
		AbstractSpringFunctionalTests {

	@Inject
	private PickMonitoringService pickMonitoringService;

	private SearchAndSortCriteria searchAndSortCriteria;

	@Before
	public void setup() {

		searchAndSortCriteria = new SearchAndSortCriteria();
		final Map<String, Object> searchMap = new HashMap<String, Object>();

		searchAndSortCriteria.setSearchMap(searchMap);

		searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
			{
				put("wave", SortType.ASC);
			}
		});

		searchAndSortCriteria.setPageNumber(0);
		searchAndSortCriteria.setPageSize(0);
	}

	@Transactional(readOnly = true)
	@Test
	public void testGetPicklineByWave() throws ParseException {

		MultiMap picklinesByWave = pickMonitoringService
				.getPicklinesByWave(searchAndSortCriteria);

		Assert.isTrue(picklinesByWave.size() == 2,
				"picklinesByWave multimap does not have size equal to 2");
		Assert.isTrue(((List) picklinesByWave.get("Completed")).size() == 4,
				"picklinesByWave Completed list does not have size equal to 4");
	}

}
