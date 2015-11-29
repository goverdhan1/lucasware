package com.lucas.services.pick;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lucas.dao.pick.PicklineDAO;
import com.lucas.entity.wave.Wave;
import com.lucas.services.search.SearchAndSortCriteria;

@RunWith(MockitoJUnitRunner.class)
public class PickMonitoringServiceUnitTests {

	@Mock
	private PicklineDAO picklineDAO;

	@InjectMocks
	private PickMonitoringServiceImpl pickMonitoringService;

	Map<String, List<Object>> picklineByWaveMap = new HashMap<String, List<Object>>();

	private SearchAndSortCriteria searchAndSortCriteria;

	@Before
	public void setup() throws JsonProcessingException {

		searchAndSortCriteria = new SearchAndSortCriteria();

		List<Object> completedList = new ArrayList<Object>();
		Wave wave1 = new Wave();
		wave1.setWaveName("Wave1");
		Object[] objArr1 = { wave1, "26" };

		Wave wave2 = new Wave();
		wave2.setWaveName("Wave2");
		Object[] objArr2 = { wave2, "12" };

		Wave wave3 = new Wave();
		wave3.setWaveName("Wave3");
		Object[] objArr3 = { wave3, "24" };

		Wave wave4 = new Wave();
		wave4.setWaveName("Wave4");
		Object[] objArr4 = { wave4, "13" };

		completedList.add(objArr1);
		completedList.add(objArr2);
		completedList.add(objArr3);
		completedList.add(objArr4);
		picklineByWaveMap.put("Completed", completedList);

		List<Object> totalList = new ArrayList<Object>();

		Wave wave11 = new Wave();
		wave11.setWaveName("Wave1");
		Object[] objArr11 = { wave11, "26" };

		Wave wave22 = new Wave();
		wave22.setWaveName("Wave2");
		Object[] objArr22 = { wave22, "24" };

		Wave wave33 = new Wave();
		wave33.setWaveName("Wave3");
		Object[] objArr33 = { wave33, "25" };

		Wave wave44 = new Wave();
		wave44.setWaveName("Wave4");
		Object[] objArr44 = { wave44, "25" };

		totalList.add(objArr11);
		totalList.add(objArr22);
		totalList.add(objArr33);
		totalList.add(objArr44);
		picklineByWaveMap.put("Total", totalList);

	}

	@Test
	public void testGetPicklinesByWave() {
		when(picklineDAO.getPicklinesByWave((SearchAndSortCriteria) any()))
				.thenReturn(picklineByWaveMap);
		pickMonitoringService.getPicklinesByWave(searchAndSortCriteria);
		verify(picklineDAO, times(1)).getPicklinesByWave(
				(SearchAndSortCriteria) any());
	}

}
