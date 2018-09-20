package org.openmrs.module.msfcore.page.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.Location;
import org.openmrs.LocationAttribute;
import org.openmrs.LocationTag;
import org.openmrs.api.LocationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.MSFCoreConfig;
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
                    HttpServletRequest request, HttpServletResponse response, UiUtils ui,
                    @RequestParam(value = "localFeedUrl", required = false) String localFeedUrl,
                    @RequestParam(value = "parentFeedUrl", required = false) String parentFeedUrl) throws IOException {
        boolean isPostRequest = false;
        if (defaultLocation != null) {
            isPostRequest = true;
            msfCoreService.saveDefaultLocation(defaultLocation);
        }
        if (StringUtils.isNotBlank(instanceId)) {
            isPostRequest = true;
            msfCoreService.saveInstanceId(StringUtils.deleteWhitespace(instanceId).toUpperCase());
        }
        if (StringUtils.isNotBlank(localFeedUrl)) {
            isPostRequest = true;
            Context.getAdministrationService().setGlobalProperty(MSFCoreConfig.GP_SYNC_LOCAL_FEED_URL, localFeedUrl);
        }
        if (parentFeedUrl != null) {
            isPostRequest = true;
            Context.getAdministrationService().setGlobalProperty(MSFCoreConfig.GP_SYNC_PARENT_FEED_URL, parentFeedUrl);
        }
        String localFeedUrlString = Context.getAdministrationService().getGlobalProperty(MSFCoreConfig.GP_SYNC_LOCAL_FEED_URL);
        String parentFeedUrlString = Context.getAdministrationService().getGlobalProperty(MSFCoreConfig.GP_SYNC_PARENT_FEED_URL);
        saveLocationCodes(request, msfCoreService, dhisService, locationService, isPostRequest);
        model.addAttribute("defaultLocation", locationService.getDefaultLocation());
        model.addAttribute("allLocations", locationService.getAllLocations());
        model.addAttribute("msfLocations", getSimplifiedMSFLocations(msfCoreService, dhisService));
        model.addAttribute("instanceId", msfCoreService.instanceId());
        model.addAttribute("localFeedUrl", StringUtils.isBlank(localFeedUrlString) ? "" : localFeedUrlString);
        model.addAttribute("parentFeedUrl", StringUtils.isBlank(parentFeedUrlString) ? "" : parentFeedUrlString);
        model.addAttribute("isClinic", isClinic(locationService));
        if (isPostRequest && msfCoreService.configured()) {
            // reload msfIDgenerator installation
            msfCoreService.msfIdentifierGeneratorInstallation();
            // reload dhis2 metadata
            dhisService.installDHIS2Metadata();
            // overwriteSync2 configurations
            msfCoreService.overwriteSync2Configuration();
            response.sendRedirect(ui.pageLink("referenceapplication", "home"));
        }
    }

    private boolean isClinic(LocationService locationService) {
        Location def = locationService.getDefaultLocation();
        if (def != null && !def.getTags().isEmpty()) {
            for (LocationTag tag : def.getTags()) {
                if (tag.getUuid().equals(MSFCoreConfig.LOCATION_TAG_UUID_CLINIC)) {
                    return true;
                }
            }
        }
        return false;
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
