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
public class Vitals {
    private Observation height;
    private Observation weight;
    private Observation bmi;
    private Observation temperature;
    private Observation pulse;
    private Observation respiratoryRate;
    private Observation bloodPressure;
    private Observation bloodOxygenRate;
    // values, use a GP to populate this
    @Builder.Default
    private List<Observation> otherObservations = new ArrayList<Observation>();
}
