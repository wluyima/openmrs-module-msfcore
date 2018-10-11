package org.openmrs.module.msfcore.summary;

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
    private List<String> medical;
    private List<String> social;
    private List<String> family;
    // assesment/conclusion
    private String assesment;
    @Builder.Default
    private List<String> complications = new ArrayList<String>();
    @Builder.Default
    private List<String> targetOrganDamages = new ArrayList<String>();
    private String cardiovascularCholesterolScore;
    private String bloodGlucose;
    private String analysisType;
    private String patientEducation;
}
