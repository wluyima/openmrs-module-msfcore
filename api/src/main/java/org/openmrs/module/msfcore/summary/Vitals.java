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
    @Builder.Default
    private Observation height = Observation.builder().name("Height").unit("cm").build();
    @Builder.Default
    private Observation weight = Observation.builder().name("Weight").unit("kg").build();
    @Builder.Default
    private Observation bmi = Observation.builder().name("BMI").build();
    @Builder.Default
    private Observation temperature = Observation.builder().name("Temperature").unit("°C").build();
    @Builder.Default
    private Observation pulse = Observation.builder().name("Pulse").unit("/min").build();
    @Builder.Default
    private Observation respiratoryRate = Observation.builder().name("Respiratory rate").unit("/min").build();
    private Observation sBloodPressure;
    private Observation dBloodPressure;
    @Builder.Default
    private Observation bloodPressure = Observation.builder().name("Blood Pressure").build();
    @Builder.Default
    private Observation bloodOxygenSaturation = Observation.builder().name("Blood oxygen saturation").unit("%").build();
    // values, use a GP to populate this
    @Builder.Default
    private List<Observation> otherObservations = new ArrayList<Observation>();
}
