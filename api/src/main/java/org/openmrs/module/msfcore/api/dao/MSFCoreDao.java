/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * 
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.msfcore.api.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.criterion.Restrictions;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.Location;
import org.openmrs.LocationAttribute;
import org.openmrs.LocationAttributeType;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.idgen.IdentifierSource;
import org.openmrs.module.idgen.SequentialIdentifierGenerator;
import org.springframework.stereotype.Repository;

@Repository("msfcore.MSFCoreDao")
public class MSFCoreDao {

    private DbSession getSession() {
        return Context.getRegisteredComponents(DbSessionFactory.class).get(0).getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    public List<Concept> getAllConceptAnswers(Concept question) {
        List<Concept> answers = null;
        if (question != null && question.getDatatype().isCoded()) {
            answers = new ArrayList<Concept>();
            for (ConceptAnswer answer : (List<ConceptAnswer>) getSession().createCriteria(ConceptAnswer.class).add(
                            Restrictions.eq("concept", question)).list()) {
                answers.add(answer.getAnswerConcept());
            }
        }
        return answers;
    }

    @SuppressWarnings("unchecked")
    public List<LocationAttribute> getLocationAttributeByTypeAndLocation(LocationAttributeType type, Location location) {
        return getSession().createCriteria(LocationAttribute.class).add(Restrictions.eq("location", location)).add(
                        Restrictions.eq("attributeType", type)).list();
    }

    public void saveSequencyPrefix(SequentialIdentifierGenerator generator) {
        int updated = getSession().createSQLQuery("update idgen_seq_id_gen set prefix = :val where id = :id").setParameter("val",
                        generator.getPrefix()).setParameter("id", generator.getId()).executeUpdate();
        if (updated != 1) {
            throw new APIException("Expected to update 1 row but updated " + updated + " rows instead!");
        }
    }
}
