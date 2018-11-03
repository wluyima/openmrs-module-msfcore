package org.openmrs.module.msfcore.result;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultFilters {
    @Builder.Default
    private List<ResultStatus> statuses = new ArrayList<ResultStatus>();
    @Builder.Default
    private List<String> dates = new ArrayList<String>();
}
