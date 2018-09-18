package org.openmrs.module.msfcore.dhis2;

import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
public class Parameters {
    private String orgUnit;
    private String programDate;
    private String eventDate;
}
