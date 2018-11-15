package org.openmrs.module.msfcore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagination {
    @Builder.Default
    private Integer fromItemNumber = 1;
    @Builder.Default
    private Integer toItemNumber = 25;
    @Builder.Default
    private Integer totalItemsNumber = 0;
}
