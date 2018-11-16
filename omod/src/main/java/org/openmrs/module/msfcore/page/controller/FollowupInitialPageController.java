package org.openmrs.module.msfcore.page.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.module.msfcore.NCDFollowUpLinks;
import org.openmrs.module.msfcore.fragment.controller.FollowupLeftMenuFragmentController;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

public class FollowupInitialPageController {

    public void controller(PageModel model, @RequestParam(value = "patientId", required = true) String patientId,
                    @RequestParam(value = "formUuid", required = true) String formUuid,
                    @RequestParam(value = "visitId", required = false) String visitId, HttpServletRequest request,
                    HttpServletResponse response) throws IOException {

        FollowupLeftMenuFragmentController controller = new FollowupLeftMenuFragmentController();
        NCDFollowUpLinks links = controller.getNCDFollowUpLinks(patientId);
        response.sendRedirect("../htmlformentryui/htmlform/" + links.getVisitDetailsLink());
    }
}
