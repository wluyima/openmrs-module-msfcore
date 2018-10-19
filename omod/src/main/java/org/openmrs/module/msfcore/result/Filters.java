package org.openmrs.module.msfcore.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Filters {
    private StatusFilter status;

    public enum StatusFilter {
        Pending, Cancelled, Completed
    }
}
