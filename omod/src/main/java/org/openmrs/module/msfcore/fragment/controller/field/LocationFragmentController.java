package org.openmrs.module.msfcore.fragment.controller.field;

import org.openmrs.api.context.Context;
import org.openmrs.ui.framework.fragment.FragmentModel;

public class LocationFragmentController {

    public void controller(FragmentModel model) {
        model.addAttribute("defaultLocation", Context.getLocationService().getDefaultLocation());
    }
}
