package org.openmrs.module.msfcore.patientSummary;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClinicalHistory {
    @Builder.Default
    private List<Observation> medical = new ArrayList<Observation>();
    @Builder.Default
    private List<Observation> social = new ArrayList<Observation>();
    @Builder.Default
    private List<Observation> family = new ArrayList<Observation>();
    // assesment/conclusion
    private String assesment;
    @Builder.Default
    private List<Observation> complications = new ArrayList<Observation>();
    @Builder.Default
    private List<Observation> targetOrganDamages = new ArrayList<Observation>();
    private Observation cardiovascularCholesterolScore;
    private Observation bloodGlucose;
    private Observation analysisType;
    private Observation patientEducation;
}
