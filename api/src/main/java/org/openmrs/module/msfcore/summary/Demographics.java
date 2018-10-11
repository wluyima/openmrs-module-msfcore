package org.openmrs.module.msfcore.summary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Demographics {
    private String name;
    private Age age;
}
