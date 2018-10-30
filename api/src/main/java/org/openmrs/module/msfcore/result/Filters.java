package org.openmrs.module.msfcore.result;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Filters {
    private List<StatusFilter> statuses;

    public enum StatusFilter {
        Pending, Cancelled, Completed
    }
}
