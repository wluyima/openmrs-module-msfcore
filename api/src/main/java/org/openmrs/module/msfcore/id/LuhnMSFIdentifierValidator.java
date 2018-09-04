package org.openmrs.module.msfcore.id;

import org.openmrs.module.idgen.validator.LuhnModNIdentifierValidator;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.patient.UnallowedIdentifierException;

public class LuhnMSFIdentifierValidator extends LuhnModNIdentifierValidator {

    @Override
    public String getBaseCharacters() {
        return MSFCoreConfig.MSF_ID_BASE_CHARACTER_SET;
    }

    @Override
    public String getValidIdentifier(String undecoratedIdentifier) throws UnallowedIdentifierException {
        return standardizeValidIdentifier(undecoratedIdentifier);
    }
}
