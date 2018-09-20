package org.openmrs.module.msfcore;

import lombok.AllArgsConstructor;

@lombok.Data
@AllArgsConstructor
public class SimplifiedLocation {
    private String display;
    private String code;
    private String uuid;
    // DHIS2 uid
    private String uid;
}
