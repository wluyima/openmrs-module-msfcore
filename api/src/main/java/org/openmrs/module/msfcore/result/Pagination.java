package org.openmrs.module.msfcore.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagination {
    @Builder.Default
    private Integer fromResultNumber = 1;
    @Builder.Default
    private Integer toResultNumber = 25;
    @Builder.Default
    private Integer totalResultNumber = 0;
}
