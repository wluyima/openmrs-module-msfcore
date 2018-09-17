package org.openmrs.module.msfcore.page.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.Location;
import org.openmrs.LocationAttribute;
import org.openmrs.api.LocationService;
import org.openmrs.module.msfcore.SimplifiedLocation;
import org.openmrs.module.msfcore.api.DHISService;
import org.openmrs.module.msfcore.api.MSFCoreService;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

public class ConfigurationPageController {

    public void controller(PageModel model, @RequestParam(value = "defaultLocationUuid", required = false) Location defaultLocation,
                    @RequestParam(value = "instanceId", required = false) String instanceId,
                    @SpringBean("locationService") LocationService locationService,
                    @SpringBean("msfCoreService") MSFCoreService msfCoreService, @SpringBean("dhisService") DHISService dhisService,
                    HttpServletRequest request, HttpServletResponse response, UiUtils ui) throws IOException {
        boolean isPostRequest = false;
        if (defaultLocation != null) {
            isPostRequest = true;
            msfCoreService.saveDefaultLocation(defaultLocation);
        }
        if (StringUtils.isNotBlank(instanceId)) {
            isPostRequest = true;
            msfCoreService.saveInstanceId(StringUtils.deleteWhitespace(instanceId).toUpperCase());
        }
        saveLocationCodes(request, msfCoreService, dhisService, locationService, isPostRequest);
        model.addAttribute("defaultLocation", locationService.getDefaultLocation());
        model.addAttribute("allLocations", locationService.getAllLocations());
        model.addAttribute("msfLocations", getSimplifiedMSFLocations(msfCoreService, dhisService));
        model.addAttribute("instanceId", msfCoreService.instanceId());
        if (isPostRequest && msfCoreService.configured()) {
            // reload msfIDgenerator installation
            msfCoreService.msfIdentifierGeneratorInstallation();
            response.sendRedirect(ui.pageLink("referenceapplication", "home"));
        }
    }

    private List<SimplifiedLocation> getSimplifiedMSFLocations(MSFCoreService msfCoreService, DHISService dhisService) {
        List<SimplifiedLocation> locations = new ArrayList<SimplifiedLocation>();
        for (Location loc : msfCoreService.getMSFLocations()) {
            locations.add(new SimplifiedLocation(loc.getName(), msfCoreService.getLocationCode(loc), loc.getUuid(), dhisService
                            .getLocationDHISUid(loc)));
        }
        return locations;
    }

    private void saveLocationCodes(HttpServletRequest request, MSFCoreService msfCoreService, DHISService dhisService,
                    LocationService locationService, boolean isPostRequest) {
        for (Location loc : msfCoreService.getMSFLocations()) {
            SimplifiedLocation simplifiedLocation = new SimplifiedLocation(loc.getName(), msfCoreService.getLocationCode(loc), loc
                            .getUuid(), dhisService.getLocationDHISUid(loc));
            Location location = locationService.getLocationByUuid(simplifiedLocation.getUuid());
            String code = request.getParameter(simplifiedLocation.getUuid());
            String uid = request.getParameter(simplifiedLocation.getUuid() + "_uid");
            if (StringUtils.isNotBlank(code) && !StringUtils.deleteWhitespace(code).toUpperCase().equals(simplifiedLocation.getCode())) {
                LocationAttribute locationAttribute = msfCoreService.getLocationCodeAttribute(location);
                saveLocationAttribute(isPostRequest, msfCoreService, locationService, simplifiedLocation, code.toUpperCase(),
                                locationAttribute, location);
            }
            if (StringUtils.isNotBlank(uid) && !StringUtils.deleteWhitespace(uid).equals(simplifiedLocation.getUid())) {
                LocationAttribute locationAttribute = dhisService.getLocationUidAttribute(location);
                saveLocationAttribute(isPostRequest, msfCoreService, locationService, simplifiedLocation, uid, locationAttribute, location);
            }
        }
    }

    private void saveLocationAttribute(boolean isPostRequest, MSFCoreService msfCoreService, LocationService locationService,
                    SimplifiedLocation simplifiedLocation, String value, LocationAttribute locationAttribute, Location location) {
        if (locationAttribute != null) {
            locationAttribute.setValue(StringUtils.deleteWhitespace(value));
            location.setAttribute(locationAttribute);
            locationService.saveLocation(location);
            isPostRequest = true;
        }
    }

}
