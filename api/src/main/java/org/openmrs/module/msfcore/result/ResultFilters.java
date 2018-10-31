package org.openmrs.module.msfcore.result;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultFilters {
    private List<ResultStatus> statuses;
    private List<String> dates;
}
