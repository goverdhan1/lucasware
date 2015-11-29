package com.lucas.alps.api;

import com.lucas.alps.rest.ApiResponse;
import com.lucas.alps.support.view.ResponseView;
import com.lucas.alps.view.PicklinesByWavesView;
import com.lucas.alps.viewtype.PicklinesByWavesDetailsView;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.pick.PickMonitoringService;
import com.lucas.services.search.SearchAndSortCriteria;
import org.apache.commons.collections.MultiMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

@Controller
public class PickMonitoringController {

	private static final Logger LOG = LoggerFactory
			.getLogger(PickMonitoringController.class);
	private final PickMonitoringService pickMonitoringService;

	@Inject
	public PickMonitoringController(PickMonitoringService pickMonitoringService) {
		this.pickMonitoringService = pickMonitoringService;
	}

	/**sample JSON expected as input to this method
	   {
		  "pageSize": 0,
		  "pageNumber": 0,
		  "searchMap": {
		   
		  },
		  "sortMap": {
		    "wave": "ASC"
		  }
		}
 response json is as per the following
 {
    "status": "success",
    "code": "200",
    "message": "Request processed successfully",
    "level": null,
    "uniqueKey": null,
    "token": null,
    "explicitDismissal": null,
    "data": {
        "chart": {
            "Completed": [
                {
                    "value": "26",
                    "label": "Wave1"
                },
                {
                    "value": "12",
                    "label": "Wave2"
                },
                {
                    "value": "24",
                    "label": "Wave3"
                },
                {
                    "value": "13",
                    "label": "Wave4"
                }
            ],
            "Total": [
                {
                    "value": "26",
                    "label": "Wave1"
                },
                {
                    "value": "24",
                    "label": "Wave2"
                },
                {
                    "value": "25",
                    "label": "Wave3"
                },
                {
                    "value": "25",
                    "label": "Wave4"
                }
            ]
        }
    }
}
*/
	@RequestMapping(method = RequestMethod.POST, value = "/waves/picklines")
	@ResponseView(PicklinesByWavesDetailsView.class)
	public @ResponseBody
	ApiResponse<PicklinesByWavesView> getPicklinesByWave(
			@RequestBody SearchAndSortCriteria searchAndSortCriteria) {

		final ApiResponse<PicklinesByWavesView> apiResponse = new ApiResponse<PicklinesByWavesView>();
		MultiMap pickLinesByWaveMap = null;
		try {
			pickLinesByWaveMap = pickMonitoringService
					.getPicklinesByWave(searchAndSortCriteria);
			PicklinesByWavesView picklinesByWavesView = new PicklinesByWavesView(
					pickLinesByWaveMap);
			apiResponse.setData(picklinesByWavesView);
		} catch (Exception e) {
			throw new LucasRuntimeException(
					LucasRuntimeException.INTERNAL_ERROR, e);
		}

		return apiResponse;
	}

}
