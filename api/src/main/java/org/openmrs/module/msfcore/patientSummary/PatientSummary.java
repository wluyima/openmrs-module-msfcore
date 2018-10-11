package org.openmrs.module.msfcore.patientSummary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientSummary {
    @Builder.Default
    private Representation representation = Representation.SUMMARY;
    @Builder.Default
    private String facility = "";
    private Demographics demographics;
    @Builder.Default
    private List<Vitals> vitals = new ArrayList<Vitals>();
    @Builder.Default
    private List<Disease> diagnoses = new ArrayList<Disease>();
    @Builder.Default
    private List<Allergy> allergies = new ArrayList<Allergy>();
    private ClinicalHistory clinicalHistory;
    // test, result
    @Builder.Default
    private Map<String, String> recentLabTests = new HashMap<String, String>();
    @Builder.Default
    private List<Observation> currentMedications = new ArrayList<Observation>();
    @Builder.Default
    private List<Observation> clinicalNotes = new ArrayList<Observation>();

    private String provider;
    // TODO probably use complex obs or what?
    private Object signature;

    /**
     * Only set some properties on this object tree at Representation.FULL
     */
    public enum Representation {
        SUMMARY, FULL
    }
}
