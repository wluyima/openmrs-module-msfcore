/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * 
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.msfcore.api;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.openmrs.module.msfcore.api.dao.AuditDao;
import org.openmrs.module.msfcore.api.impl.AuditServiceImpl;
import org.openmrs.module.msfcore.audit.AuditLog;
import org.openmrs.test.BaseContextMockTest;

/**
 * This is a unit test, which verifies logic in AuditService.
 */
public class AuditServiceTest extends BaseContextMockTest {

  @InjectMocks
  AuditServiceImpl auditService;

  @Mock
  AuditDao auditDao;

  @Test
  public void deleteAuditLogsInLastNMonths_shouldCompletelyDeleteRangedLogs() {

    Date endDate = new Date();

    AuditLog auditLog1 = AuditLog.builder().id(1).build();
    AuditLog auditLog2 = AuditLog.builder().id(2).build();
    AuditLog auditLog3 = AuditLog.builder().id(3).build();

    when(auditDao.getAuditLogs(null, endDate, null, null, null, null, null, null))
        .thenReturn(Arrays.asList(auditLog1, auditLog2, auditLog3));

    auditService.deleteAuditLogsToDate(endDate);

    verify(auditDao).deleteAuditLog(auditLog1);
    verify(auditDao).deleteAuditLog(auditLog2);
    verify(auditDao).deleteAuditLog(auditLog3);
  }
}
