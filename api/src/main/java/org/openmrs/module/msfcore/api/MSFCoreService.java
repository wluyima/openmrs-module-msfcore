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

import java.util.List;
import java.util.Map;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.LocationAttribute;
import org.openmrs.Obs;
import org.openmrs.Order;
import org.openmrs.OrderType;
import org.openmrs.Patient;
import org.openmrs.PatientProgram;
import org.openmrs.Person;
import org.openmrs.ProgramWorkflowState;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.idgen.SequentialIdentifierGenerator;
import org.openmrs.module.msfcore.DropDownFieldOption;
import org.openmrs.module.msfcore.Pagination;

/**
 * The main service of this module, which is exposed for other modules. See
 * moduleApplicationContext.xml on how it is wired up.
 */
public interface MSFCoreService extends OpenmrsService {

    public List<Concept> getAllConceptAnswers(Concept question);

    public List<DropDownFieldOption> getAllConceptAnswerNames(String uuid);

    public boolean isConfigured();

    public String getInstanceId();

    public List<Location> getMSFLocations();

    public String getLocationCode(Location location);

    public void saveInstanceId(String instanceId);

    public LocationAttribute getLocationCodeAttribute(Location location);

    public void saveDefaultLocation(Location location);

    public void msfIdentifierGeneratorInstallation();

    public void saveSequencyPrefix(SequentialIdentifierGenerator generator);

    public void overwriteSync2Configuration();

    public String getCurrentLocationIdentity();

    /**
     * @param enrollment,
     *            is this an enrollment or not
     * @param patient
     * @param program
     * @param states,
     *            maps states to their respective uuid
     * @param patientProgram,
     *            must never be null
     * @param form,
     *            used for form managed states
     * @return patientProgram generated or null
     */
    public PatientProgram generatePatientProgram(boolean enrollment, Map<String, ProgramWorkflowState> states,
                    PatientProgram patientProgram, Encounter ecnounter);

    public Map<String, ProgramWorkflowState> getMsfStages();

    public void manageNCDProgram(Encounter encounter);

    public void saveTestOrders(Encounter encounter);

    public List<Order> getOrders(Patient patient, OrderType type, List<Concept> concepts, Pagination pagination);

    public List<Obs> getObservationsByOrderAndConcept(Order order, Concept concept);
}
