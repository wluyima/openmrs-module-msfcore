package org.openmrs.module.msfcore;

import lombok.AllArgsConstructor;

@lombok.Data
@AllArgsConstructor
public class CurrentLocationIdentity {
    // TODO MSF should define their login/transfer/admission(locationTag)
    // locations whose parent should be a clinic(locationTag)
    private String label;
    private String configUrl;
}
