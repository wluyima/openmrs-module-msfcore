package org.openmrs.module.msfcore;

import java.util.Set;

import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.Person;
import org.openmrs.PersonAddress;
import org.openmrs.PersonAttribute;
import org.openmrs.api.context.Context;

public class RegistrationAppUiUtils {
    /**
     * Gets the person attribute value for the specified person for the
     * getPersonAttributeTypeByUuid that matches the specified uuid
     * 
     * @return the attribute value
     */
    public String getAttribute(Person person, String attributeTypeUuid) {
        if (person != null) {
            PersonAttribute attr = person.getAttribute(Context.getPersonService().getPersonAttributeTypeByUuid(attributeTypeUuid));
            if (attr != null) {
                return attr.getValue();
            }
        }

        return null;
    }

    public String getIdentifier(Patient patient, String identifierName) {
        if (patient != null) {
            PatientIdentifier identifier = patient.getPatientIdentifier(identifierName);
            if (identifier != null) {
                return identifier.getIdentifier();
            } else {
                return null;
            }
        }

        return null;
    }

    public Set<PersonAddress> getAddress(Patient patient) {
        if (patient != null) {
            return patient.getAddresses();
        } else {
            return null;
        }
    }
}