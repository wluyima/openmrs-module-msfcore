package org.openmrs.module.msfcore.summary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Observation {
    private String name;
    @Builder.Default
    private String unit = "";
    @Builder.Default
    private String value = "_";
}
