package com.lucas.services.location;

import com.lucas.entity.location.Location;
import com.lucas.entity.location.LocationPart;

public interface LocationService {
	Location newLocation();
	Location newLocation(LocationPart... locationPart) ;
	Location newLocation(String locationId);
}
