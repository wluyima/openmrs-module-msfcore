package org.openmrs.module.msfcore.api.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openmrs.Allergen;
import org.openmrs.AllergenType;
import org.openmrs.Allergies;
import org.openmrs.Allergy;
import org.openmrs.AllergyReaction;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.springframework.stereotype.Component;

@Component
public class AllergenUtils {

    public Allergies createAllergies(Encounter encounter) {

		final Patient patient = encounter.getPatient();
		final Set<Obs> obsToLevel = encounter.getObsAtTopLevel(false);
		final List<Obs> obsGroups = obsToLevel.stream()
				.filter(o -> o.getConcept().getUuid().equals(MSFCoreConfig.ALLERGY_UUID)).collect(Collectors.toList());
		final Allergies allergies = new Allergies();

		for (final Obs obsGroup : obsGroups) {

			final Optional<Obs> optionalComment = this.getStream(obsGroup, MSFCoreConfig.COMMENT_UUID).findAny();
			final Optional<Obs> optionalSeverity = this.getStream(obsGroup, MSFCoreConfig.ALLERGY_PROBLEM_SEVERITY_UUID)
					.findAny();
			final Optional<Obs> optionalCodedAllergen = this.getStream(obsGroup, MSFCoreConfig.ALLERGY_TYPE_UUID)
					.findAny();

			final String comment = optionalComment.isPresent() ? optionalComment.get().getValueText() : null;
			final Concept severity = optionalSeverity.isPresent() ? optionalSeverity.get().getValueCoded() : null;
			final Concept codedAllergen = optionalCodedAllergen.isPresent()
					? optionalCodedAllergen.get().getValueCoded()
					: null;

			if (codedAllergen != null) {

				final List<AllergyReaction> reactions = new ArrayList<>();
				final Allergen allergen = new Allergen(this.getAllergenType(codedAllergen), codedAllergen, null);
				final Allergy allergy = new Allergy(patient, allergen, severity, comment, reactions);

				this.getStream(obsGroup, MSFCoreConfig.ALLERGIC_REACTIONS_UUID).collect(Collectors.toList())
						.forEach(o -> reactions.add(new AllergyReaction(allergy, o.getValueCoded(), o.getComment())));
				allergies.add(allergy);
			}
		}
		if (!allergies.isEmpty()) {
			Context.getPatientService().getAllergies(patient)
					.forEach(a -> Context.getPatientService().removeAllergy(a, null));
		}
		return Context.getPatientService().setAllergies(patient, allergies);
	}
    private Stream<Obs> getStream(Obs obsGroup, String conceptUuidFilter) {
		return obsGroup.getGroupMembers().stream().filter(o -> o.getConcept().getUuid().equals(conceptUuidFilter));
	}
    private AllergenType getAllergenType(Concept codedAllergen) {

        if (codedAllergen.getUuid().equals(MSFCoreConfig.REFERENCE_APPLICATION_COMMON_DRUG_ALLERGENS_UUID)) {
            return AllergenType.DRUG;
        }
        if (codedAllergen.getUuid().equals(MSFCoreConfig.REFERENCE_APPLICATION_COMMON_FOOD_ALLERGENS_UUID)) {
            return AllergenType.FOOD;
        }
        if (codedAllergen.getUuid().equals(MSFCoreConfig.REFERENCE_APPLICATION_COMMON_ENVIROMENTAL_ALLERGENS_UUID)) {
            return AllergenType.ENVIRONMENT;
        }
        throw new IllegalArgumentException("AllergenType not found for the concept " + codedAllergen);
    }
}