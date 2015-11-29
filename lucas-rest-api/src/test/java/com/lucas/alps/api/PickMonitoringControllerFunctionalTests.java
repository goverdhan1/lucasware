/**
 * 
 */
package com.lucas.alps.api;

/**
 * @author Prafull
 *
 */

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucas.alps.rest.ApiResponse;
import com.lucas.services.search.SearchAndSortCriteria;
import com.lucas.services.sort.SortType;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({ @ContextConfiguration("/META-INF/spring/api-bootstrap-context.xml") })
public class PickMonitoringControllerFunctionalTests extends
		AbstractControllerTests {

	private static final Logger LOG = LoggerFactory
			.getLogger(PickMonitoringControllerFunctionalTests.class);
	private static final String USERNAME_JACK = "jack123";

	private static final String PASSWORD_JACK = "secret";

	private static final String GET_PICKLINES_BY_WAVE_FILE_PATH = "json/GetPicklinesByWave.json";

	@Test
	public void testGetPicklinesByWave() throws Exception {

		final String token = super.getAuthenticatedToken(USERNAME_JACK,
				PASSWORD_JACK);

		final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
		final Map<String, Object> searchMap = new HashMap<String, Object>();

		searchAndSortCriteria.setSearchMap(searchMap);

		searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
			{
				put("wave", SortType.ASC);
			}
		});

		searchAndSortCriteria.setPageNumber(0);
		searchAndSortCriteria.setPageSize(0);

		final String url = "/waves/picklines";
		final ResultActions apiResultActions = this.mockMvc.perform(post(url)
				.header("Authentication-token", token)
				.content(super.getJsonString(searchAndSortCriteria))
				.contentType(MediaType.APPLICATION_JSON));

		LOG.debug("controller returns : {}", apiResultActions.andReturn()
				.getResponse().getContentAsString());
		String actualJsonString = apiResultActions.andReturn().getResponse()
				.getContentAsString();
		LOG.debug(actualJsonString);
		Assert.notNull(actualJsonString, "Resulted Json is Null");

		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);

		ApiResponse<MultiMap> apiResponse = mapper.readValue(actualJsonString,
				new TypeReference<ApiResponse<MultiValueMap>>() {
				});
		MultiMap picklineByWaveMap = apiResponse.getData();
		Assert.notNull(picklineByWaveMap);
		apiResultActions.andExpect(status().isOk());
		Assert.isTrue(
				compareJson(actualJsonString, GET_PICKLINES_BY_WAVE_FILE_PATH),
				"Actual json response not equal to the expected");

	}

}
