package org.openmrs.module.msfcore.api;

import org.openmrs.Patient;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.msfcore.patientSummary.PatientSummary;
import org.openmrs.module.msfcore.patientSummary.PatientSummary.Representation;

public interface PatientSummaryService extends OpenmrsService {
    public void setRepresentation(Representation representation);
    /**
     * Can be used to only generate patient summary
     */
    public PatientSummary generatePatientSummary(Patient patient);
    /**
     * Genarates patient summary and adds request audit log
     */
    public PatientSummary requestPatientSummary(Patient patient);
}
