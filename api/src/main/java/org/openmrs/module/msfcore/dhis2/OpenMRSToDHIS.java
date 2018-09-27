package org.openmrs.module.msfcore.dhis2;

import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * Maps openmrs to dhis metadata
 */
public class OpenMRSToDHIS {

    public static String getGenderFromOptionSets(SimpleJSON optionSets, String openmrsGender) {
        if (StringUtils.isNotBlank(openmrsGender) && optionSets != null) {
            if (openmrsGender.toUpperCase().matches("F|FEMALE")) {
                return getCodeFromOptionSets(optionSets, "Sex", "Female");
            } else if (openmrsGender.toUpperCase().matches("M|MALE")) {
                return getCodeFromOptionSets(optionSets, "Sex", "Male");
            } else {
                return getCodeFromOptionSets(optionSets, "Sex", "Other");
            }
        }
        return null;
    }

    /**
     * This depends on DHIS2 optionSet label name being the same as its
     * equivalent concept name
     * 
     * @param optionSets
     * @param conceptName
     * @param conceptAnswerName
     * @return matched code of option/concept answer for conceptName
     */
    public static String getMatchedConceptCodeFromOptionSets(SimpleJSON optionSets, String conceptName, String conceptAnswerName) {
        if (StringUtils.isNotBlank(conceptName) && optionSets != null) {
            return getCodeFromOptionSets(optionSets, conceptName, conceptAnswerName);
        }
        return null;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static String getCodeFromOptionSets(SimpleJSON json, String name, String optionName) {
        if (json.containsKey("optionSets")) {
            for (Map entry : ((ArrayList<Map>) json.get("optionSets"))) {
                if (entry.containsKey("name") && ((String) entry.get("name")).equalsIgnoreCase(name) && entry.containsKey("options")) {
                    for (Map option : ((ArrayList<Map>) entry.get("options"))) {
                        if (optionName.equalsIgnoreCase((String) option.get("name")) && option.containsKey("code")) {
                            return (String) option.get("code");
                        }
                    }
                }
            }
        }
        return null;
    }

}
