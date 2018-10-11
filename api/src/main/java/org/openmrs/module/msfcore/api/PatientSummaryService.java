package org.openmrs.module.msfcore.api;

import org.openmrs.Patient;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.msfcore.patientSummary.PatientSummary;
import org.openmrs.module.msfcore.patientSummary.PatientSummary.Representation;

public interface PatientSummaryService extends OpenmrsService {
    public void setRepresentation(Representation representation);
    /**
     * may be used outside msfcore module
     */
    public PatientSummary generatePatientSummary(Patient patient);

    public PatientSummary requestPatientSummary(Patient patient);
}
