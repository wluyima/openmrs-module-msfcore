package org.openmrs.module.msfcore.page.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.Location;
import org.openmrs.LocationAttribute;
import org.openmrs.api.LocationService;
import org.openmrs.module.msfcore.SimplifiedLocation;
import org.openmrs.module.msfcore.api.MSFCoreService;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

public class ConfigurationPageController {

    public void controller(PageModel model, @RequestParam(value = "defaultLocationUuid", required = false) Location defaultLocation,
                    @RequestParam(value = "instanceId", required = false) String instanceId,
                    @SpringBean("locationService") LocationService locationService,
                    @SpringBean("msfCoreService") MSFCoreService msfCoreService, HttpServletRequest request) {
        if (defaultLocation != null) {
            msfCoreService.saveDefaultLocation(defaultLocation);
        }
        if (StringUtils.isNotBlank(instanceId)) {
            msfCoreService.saveInstanceId(instanceId);
        }
        saveLocationCodes(request, msfCoreService, locationService);
        model.addAttribute("defaultLocation", locationService.getDefaultLocation());
        model.addAttribute("allLocations", locationService.getAllLocations());
        model.addAttribute("msfLocations", getSimplifiedMSFLocations(msfCoreService));
        model.addAttribute("instanceId", msfCoreService.instanceId());
    }

    private List<SimplifiedLocation> getSimplifiedMSFLocations(MSFCoreService msfCoreService) {
        List<SimplifiedLocation> locations = new ArrayList<SimplifiedLocation>();
        for (Location loc : msfCoreService.getMSFLocations()) {
            locations.add(new SimplifiedLocation(loc.getName(), msfCoreService.getLocationCode(loc), loc.getUuid()));

        }
        return locations;
    }

    private void saveLocationCodes(HttpServletRequest request, MSFCoreService msfCoreService, LocationService locationService) {
        for (Location loc : msfCoreService.getMSFLocations()) {
            SimplifiedLocation simplifiedLocation = new SimplifiedLocation(loc.getName(), msfCoreService.getLocationCode(loc), loc
                            .getUuid());
            String code = request.getParameter(simplifiedLocation.getUuid());
            if (StringUtils.isNotBlank(code) && !code.equals(simplifiedLocation.getCode())) {
                Location location = locationService.getLocationByUuid(simplifiedLocation.getUuid());
                LocationAttribute locationAttribute = msfCoreService.getLocationCodeAttribute(location);
                if (locationAttribute != null) {
                    locationAttribute.setValue(code);
                    location.setAttribute(locationAttribute);
                    locationService.saveLocation(location);
                }
            }
        }
    }
}
