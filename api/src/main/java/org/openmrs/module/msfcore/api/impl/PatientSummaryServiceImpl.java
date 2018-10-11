package org.openmrs.module.msfcore.api.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.AllergyReaction;
import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.msfcore.OMRSConstants;
import org.openmrs.module.msfcore.api.AuditService;
import org.openmrs.module.msfcore.api.PatientSummaryService;
import org.openmrs.module.msfcore.audit.AuditLog;
import org.openmrs.module.msfcore.audit.AuditLog.Event;
import org.openmrs.module.msfcore.summary.Age;
import org.openmrs.module.msfcore.summary.Allergy;
import org.openmrs.module.msfcore.summary.Allergy.AllergyBuilder;
import org.openmrs.module.msfcore.summary.Demographics;
import org.openmrs.module.msfcore.summary.Observation;
import org.openmrs.module.msfcore.summary.Observation.ObservationBuilder;
import org.openmrs.module.msfcore.summary.PatientSummary;
import org.openmrs.module.msfcore.summary.PatientSummary.PatientSummaryBuilder;
import org.openmrs.module.msfcore.summary.PatientSummary.Representation;
import org.openmrs.module.msfcore.summary.Vitals;
import org.openmrs.module.msfcore.summary.Vitals.VitalsBuilder;

public class PatientSummaryServiceImpl extends BaseOpenmrsService implements PatientSummaryService {

    private Obs getLastObsByPersonAndConcept(Patient who, Concept question) {
        List<Obs> obsList = Context.getObsService().getObservationsByPersonAndConcept(who, question);
        Obs fakeObs = new Obs();
        fakeObs.setConcept(question);
        return !obsList.isEmpty() ? obsList.get(0) : fakeObs;
    }

    /*
     * write or convert to handle non numeric values
     */
    private Observation convertNumericObs(Obs obs) {
        ObservationBuilder ob = Observation.builder();
        ob.name(obs.getConcept().getName().getName());
        ob.unit(getConceptUnit(obs.getConcept()));
        if (obs.getValueNumeric() != null) {
            ob.value(cleanObservationValue(Double.toString(obs.getValueNumeric())));
        }
        return ob.build();
    }

    /*
     * retrieve concept unit from messages.properties, else concept if numeric
     * in the future
     */
    private String getConceptUnit(Concept concept) {
        if (concept.getConceptId().equals(
                        Integer.parseInt(Context.getAdministrationService().getGlobalProperty(OMRSConstants.GP_CONCEPT_ID_HEIGHT)))) {
            return Context.getMessageSourceService().getMessage(OMRSConstants.MESSAGE_UNITS_CENTIMETERS);
        } else if (concept.getConceptId().equals(
                        Integer.parseInt(Context.getAdministrationService().getGlobalProperty(OMRSConstants.GP_CONCEPT_ID_WEIGHT)))) {
            return Context.getMessageSourceService().getMessage(OMRSConstants.MESSAGE_UNITS_KILOGRAMS);
        } else if (concept.getConceptId().toString().matches(
                        Context.getAdministrationService().getGlobalProperty(OMRSConstants.GP_CONCEPT_ID_PULSE)
                                        + "|"
                                        + Context.getAdministrationService()
                                                        .getGlobalProperty(OMRSConstants.GP_CONCEPT_ID_RESPIRATORY_RATE))) {
            return Context.getMessageSourceService().getMessage(OMRSConstants.MESSAGE_UNITS_PER_MINUTE);
        } else if (concept.getConceptId().equals(
                        Integer.parseInt(Context.getAdministrationService().getGlobalProperty(OMRSConstants.GP_CONCEPT_ID_TEMPERATURE)))) {
            return Context.getMessageSourceService().getMessage(OMRSConstants.MESSAGE_UNITS_DEGREES);
        } else if (concept.getConceptId().equals(
                        Integer.parseInt(Context.getAdministrationService().getGlobalProperty(
                                        OMRSConstants.GP_CONCEPT_ID_BLOOD_OXYGEN_SATURATION)))) {
            return Context.getMessageSourceService().getMessage(OMRSConstants.MESSAGE_UNITS_PERCENT);
        } else {
            return "";
        }
    }

    private String cleanObservationValue(String value) {
        if (value.endsWith(".0")) {
            value = value.replaceAll(".0$", "");
        }
        return value;
    }

    /*
     * systolic/diastolic
     */
    private Observation getBloodPressure(Patient patient) {
        Observation sBP = convertNumericObs(getLastObsByPersonAndConcept(patient, Context.getConceptService().getConcept(
                        Integer.parseInt(Context.getAdministrationService().getGlobalProperty(
                                        OMRSConstants.GP_CONCEPT_ID_SYSTOLIC_BLOOD_PRESSURE)))));
        Observation dBP = convertNumericObs(getLastObsByPersonAndConcept(patient, Context.getConceptService().getConcept(
                        Integer.parseInt(Context.getAdministrationService().getGlobalProperty(
                                        OMRSConstants.GP_CONCEPT_ID_DIASTOLIC_BLOOD_PRESSURE)))));
        ObservationBuilder bpb = Observation.builder().name("Blood Pressure");
        if (StringUtils.isNotBlank(sBP.getValue()) && StringUtils.isNotBlank(dBP.getValue())) {
            bpb.value(cleanObservationValue(sBP.getValue()) + "/" + cleanObservationValue(dBP.getValue()));
        }
        return bpb.build();
    }

    private Observation getBMI(Observation weight, Observation height) {
        ObservationBuilder bmi = Observation.builder().name("BMI");
        if (StringUtils.isNotBlank(weight.getValue()) && StringUtils.isNotBlank(height.getValue())) {
            bmi.value(cleanObservationValue(Double.toString(roundABout(Double.parseDouble(weight.getValue())
                            / (Math.pow(Double.parseDouble(height.getValue()) * 0.01, 2)), 2))));
        }
        return bmi.build();
    }

    private Double roundABout(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    private void setVitalsAndObs(Patient patient, ConceptService conceptService, VitalsBuilder vitalsBuilder) {
        Observation weight = convertNumericObs(getLastObsByPersonAndConcept(patient, conceptService.getConcept(Integer.parseInt(Context
                        .getAdministrationService().getGlobalProperty(OMRSConstants.GP_CONCEPT_ID_WEIGHT)))));
        Observation height = convertNumericObs(getLastObsByPersonAndConcept(patient, conceptService.getConcept(Integer.parseInt(Context
                        .getAdministrationService().getGlobalProperty(OMRSConstants.GP_CONCEPT_ID_HEIGHT)))));
        vitalsBuilder.weight(weight);
        vitalsBuilder.height(height);
        vitalsBuilder.bmi(getBMI(weight, height));
        vitalsBuilder.temperature(convertNumericObs(getLastObsByPersonAndConcept(patient, conceptService.getConcept(Integer
                        .parseInt(Context.getAdministrationService().getGlobalProperty(OMRSConstants.GP_CONCEPT_ID_TEMPERATURE))))));
        vitalsBuilder.pulse(convertNumericObs(getLastObsByPersonAndConcept(patient, conceptService.getConcept(Integer.parseInt(Context
                        .getAdministrationService().getGlobalProperty(OMRSConstants.GP_CONCEPT_ID_PULSE))))));
        vitalsBuilder.respiratoryRate(convertNumericObs(getLastObsByPersonAndConcept(patient, conceptService.getConcept(Integer
                        .parseInt(Context.getAdministrationService().getGlobalProperty(OMRSConstants.GP_CONCEPT_ID_RESPIRATORY_RATE))))));
        vitalsBuilder.bloodPressure(getBloodPressure(patient));
        vitalsBuilder.bloodOxygenRate(convertNumericObs(getLastObsByPersonAndConcept(patient, conceptService
                        .getConcept(Integer.parseInt(Context.getAdministrationService().getGlobalProperty(
                                        OMRSConstants.GP_CONCEPT_ID_BLOOD_OXYGEN_SATURATION))))));
    }

    private void setAllergies(Patient patient, PatientSummary patientSummary) {
        for (org.openmrs.Allergy a : Context.getPatientService().getAllergies(patient)) {
            AllergyBuilder allergyBuilder = Allergy.builder();
            allergyBuilder.name(a.getAllergen().getCodedAllergen() != null ? a.getAllergen().getCodedAllergen().getName().getName() : a
                            .getAllergen().getNonCodedAllergen());
            if (a.getSeverity() != null) {
                allergyBuilder.severity(a.getSeverity().getName().getName());
            }
            Allergy allergy = allergyBuilder.build();
            for (AllergyReaction reaction : a.getReactions()) {
                allergy.getReactions().add(
                                reaction.getReaction() != null ? reaction.getReaction().getName().getName() : reaction
                                                .getReactionNonCoded());
            }
            patientSummary.getAllergies().add(allergy);
        }
    }

    public PatientSummary generatePatientSummary(Patient patient, Representation representation) {
        ConceptService conceptService = Context.getConceptService();
        PatientSummaryBuilder patientSummarybuilder = PatientSummary.builder();
        // representation defaults to SUMMARY, set if else
        if (representation != null) {
            patientSummarybuilder.representation(representation);
        }

        // set demographics
        patientSummarybuilder.demographics(Demographics.builder().name(patient.getPersonName().getFullName()).age(
                        new Age(patient.getBirthdate(), new Date(), Context.getDateFormat())).build());

        // set recent vitals and observations
        VitalsBuilder vitalsBuilder = Vitals.builder();
        setVitalsAndObs(patient, conceptService, vitalsBuilder);
        patientSummarybuilder.vitals(vitalsBuilder.build());

        PatientSummary patientSummary = patientSummarybuilder.build();

        // set working diagnoses
        for (Obs o : Context.getObsService().getObservationsByPersonAndConcept(
                        patient,
                        conceptService.getConcept(Integer.parseInt(Context.getAdministrationService().getGlobalProperty(
                                        OMRSConstants.GP_CONCEPT_ID_PROBLEM_LIST))))) {
            patientSummary.getDiagnoses().add(o.getValueCoded().getName().getName());
        }

        // set allergies
        setAllergies(patient, patientSummary);

        if (Representation.FULL.equals(representation)) {
            // TODO after adding extra properties for full, set them here
        }

        return patientSummary;
    }

    public PatientSummary requestPatientSummary(Patient patient, Representation representation) {
        PatientSummary patientSummary = generatePatientSummary(patient, representation);
        // log audit log
        AuditLog requestLog = AuditLog.builder().event(Event.REQUEST_PATIENT_SUMMARY).detail(
                        Context.getMessageSourceService()
                                        .getMessage(
                                                        "msfcore.requestPatientSummary",
                                                        new Object[]{patient.getPersonName().getFullName(),
                                                                        patient.getPatientIdentifier().getIdentifier()}, null)).user(
                        Context.getAuthenticatedUser()).patient(patient).build();
        Context.getService(AuditService.class).saveAuditLog(requestLog);
        return patientSummary;
    }

}
