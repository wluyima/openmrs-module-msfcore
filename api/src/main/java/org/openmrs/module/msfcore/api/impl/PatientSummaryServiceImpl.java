package org.openmrs.module.msfcore.api.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.AllergyReaction;
import org.openmrs.Concept;
import org.openmrs.ConceptDatatype;
import org.openmrs.Diagnosis;
import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.module.msfcore.OMRSConstants;
import org.openmrs.module.msfcore.api.AuditService;
import org.openmrs.module.msfcore.api.PatientSummaryService;
import org.openmrs.module.msfcore.audit.AuditLog;
import org.openmrs.module.msfcore.audit.AuditLog.Event;
import org.openmrs.module.msfcore.summary.Age;
import org.openmrs.module.msfcore.summary.Allergy;
import org.openmrs.module.msfcore.summary.Allergy.AllergyBuilder;
import org.openmrs.module.msfcore.summary.Demographics;
import org.openmrs.module.msfcore.summary.Disease;
import org.openmrs.module.msfcore.summary.Disease.DiseaseBuilder;
import org.openmrs.module.msfcore.summary.Observation;
import org.openmrs.module.msfcore.summary.Observation.ObservationBuilder;
import org.openmrs.module.msfcore.summary.PatientSummary;
import org.openmrs.module.msfcore.summary.PatientSummary.PatientSummaryBuilder;
import org.openmrs.module.msfcore.summary.PatientSummary.Representation;
import org.openmrs.module.msfcore.summary.Vitals;
import org.openmrs.module.msfcore.summary.Vitals.VitalsBuilder;
import org.openmrs.parameter.EncounterSearchCriteria;
import org.openmrs.parameter.EncounterSearchCriteriaBuilder;

public class PatientSummaryServiceImpl extends BaseOpenmrsService implements PatientSummaryService {
    Representation representation;

    public void setRepresentation(Representation representation) {
        this.representation = representation;
    }

    private Observation convertObs(Obs obs) {
        ObservationBuilder ob = Observation.builder();
        ob.name(obs.getConcept().getName().getName());
        if (obs.getConcept().getDatatype().getUuid().equals(ConceptDatatype.NUMERIC_UUID)) {
            ob.unit(getConceptUnit(obs.getConcept()));
            if (obs.getValueNumeric() != null) {
                ob.value(cleanObservationValue(Double.toString(obs.getValueNumeric())));
            }
        } else if (obs.getConcept().getDatatype().getUuid().equals(ConceptDatatype.TEXT_UUID) && obs.getValueText() != null) {
            ob.value(cleanObservationValue(obs.getValueText()));
        } else if (obs.getConcept().getDatatype().getUuid().equals(ConceptDatatype.BOOLEAN_UUID) && obs.getValueBoolean() != null) {
            ob.value(cleanObservationValue(Boolean.toString(obs.getValueBoolean())));
        } else if (obs.getConcept().getDatatype().getUuid().equals(ConceptDatatype.DATE_UUID) && obs.getValueDate() != null) {
            ob.value(cleanObservationValue(Context.getDateFormat().format(obs.getValueDate())));
        } else if (obs.getConcept().getDatatype().getUuid().equals(ConceptDatatype.DATETIME_UUID) && obs.getValueDatetime() != null) {
            ob.value(cleanObservationValue(Context.getDateTimeFormat().format(obs.getValueDatetime())));
        } else if (obs.getConcept().getDatatype().getUuid().equals(ConceptDatatype.CODED_UUID) && obs.getValueCoded() != null) {
            ob.value(cleanObservationValue(obs.getValueCoded().getName().getName()));
        } else if (obs.getConcept().getDatatype().getUuid().equals(ConceptDatatype.COMPLEX_UUID) && obs.getValueComplex() != null) {
            // TODO handle complext objects for things like signature
            // see: https://wiki.openmrs.org/display/docs/Complex+Obs+Support
        }

        if (Representation.FULL.equals(representation)) {
            // TODO set both visit and encounter dates
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
    private Observation getBloodPressure(Observation sBP, Observation dBP) {
        ObservationBuilder bpb = Observation.builder().name("Blood Pressure");
        if (observationValueBlank(sBP.getValue()) && observationValueBlank(dBP.getValue())) {
            bpb.value(cleanObservationValue(sBP.getValue()) + "/" + cleanObservationValue(dBP.getValue()));
        }
        return bpb.build();
    }

    private boolean observationValueBlank(String value) {
        return StringUtils.isNotBlank(value) && !value.equals("_");
    }

    private Observation getBMI(Observation weight, Observation height) {
        ObservationBuilder bmi = Observation.builder().name("BMI");
        if (observationValueBlank(weight.getValue()) && observationValueBlank(height.getValue())) {
            bmi.value(cleanObservationValue(Double.toString(roundABout(Double.parseDouble(weight.getValue())
                            / (Math.pow(Double.parseDouble(height.getValue()) * 0.01, 2)), 2))));
        }
        return bmi.build();
    }

    private Double roundABout(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    private void setVital(VitalsBuilder vitalsBuilder, Obs obs) {
        String heightId = Context.getAdministrationService().getGlobalProperty(OMRSConstants.GP_CONCEPT_ID_HEIGHT);
        String weightId = Context.getAdministrationService().getGlobalProperty(OMRSConstants.GP_CONCEPT_ID_WEIGHT);
        String temparatureId = Context.getAdministrationService().getGlobalProperty(OMRSConstants.GP_CONCEPT_ID_TEMPERATURE);
        String respRateId = Context.getAdministrationService().getGlobalProperty(OMRSConstants.GP_CONCEPT_ID_RESPIRATORY_RATE);
        String bloodSatId = Context.getAdministrationService().getGlobalProperty(OMRSConstants.GP_CONCEPT_ID_BLOOD_OXYGEN_SATURATION);
        String sBP = Context.getAdministrationService().getGlobalProperty(OMRSConstants.GP_CONCEPT_ID_SYSTOLIC_BLOOD_PRESSURE);
        String dBP = Context.getAdministrationService().getGlobalProperty(OMRSConstants.GP_CONCEPT_ID_DIASTOLIC_BLOOD_PRESSURE);
        String pulseId = Context.getAdministrationService().getGlobalProperty(OMRSConstants.GP_CONCEPT_ID_PULSE);
        Observation observation = convertObs(obs);

        if (obs.getConcept().getConceptId().toString().equals(heightId)) {
            vitalsBuilder.height(observation);
        } else if (obs.getConcept().getConceptId().toString().equals(weightId)) {
            vitalsBuilder.weight(observation);
        } else if (obs.getConcept().getConceptId().toString().equals(temparatureId)) {
            vitalsBuilder.temperature(observation);
        } else if (obs.getConcept().getConceptId().toString().equals(respRateId)) {
            vitalsBuilder.respiratoryRate(observation);
        } else if (obs.getConcept().getConceptId().toString().equals(bloodSatId)) {
            vitalsBuilder.bloodOxygenSaturation(observation);
        } else if (obs.getConcept().getConceptId().toString().equals(sBP)) {
            vitalsBuilder.sBloodPressure(observation);
        } else if (obs.getConcept().getConceptId().toString().equals(dBP)) {
            vitalsBuilder.dBloodPressure(observation);
        } else if (obs.getConcept().getConceptId().toString().equals(pulseId)) {
            vitalsBuilder.pulse(observation);
        }
    }

    private List<Vitals> getVitals(Patient patient) {
        List<Vitals> vitals = new ArrayList<Vitals>();
        EncounterSearchCriteria encSearch = new EncounterSearchCriteriaBuilder().setPatient(patient).setEncounterTypes(
                        Arrays.asList(Context.getEncounterService().getEncounterType(
                                        Integer.parseInt(Context.getAdministrationService().getGlobalProperty(
                                                        OMRSConstants.GP_ENCOUNTER_TYPE_ID_VITALS))))).createEncounterSearchCriteria();
        List<Obs> vitalsObs = Context.getObsService()
                        .getObservations(Arrays.asList(patient.getPerson()), Context.getEncounterService().getEncounters(encSearch), null,
                                        null, null, null, null, null, null, null, null, false);
        // arrange obs by encounter
        Collections.sort(vitalsObs, new Comparator<Obs>() {
            @Override
            public int compare(Obs o1, Obs o2) {
                return o2.getEncounter().getEncounterId().compareTo(o1.getEncounter().getEncounterId());
            }
        });
        Encounter obsEncounter = null;
        Obs missedObs = null;
        VitalsBuilder vitalsBuilder = null;

        for (int i = 0; i < vitalsObs.size(); i++) {
            Obs obs = vitalsObs.get(i);

            if (vitalsBuilder == null) {
                vitalsBuilder = Vitals.builder();
            }
            if (obsEncounter == null || obsEncounter.equals(obs.getEncounter())) {
                if (missedObs != null) {
                    setVital(vitalsBuilder, missedObs);
                }
                setVital(vitalsBuilder, obs);
                if (i == vitalsObs.size() - 1) {// add last item straight away
                    setCalculatedObservationsAndAddToVitals(vitals, vitalsBuilder);
                }
            } else { // first obs in vitals encounters
                setCalculatedObservationsAndAddToVitals(vitals, vitalsBuilder);
                if (i == vitalsObs.size() - 1) {// add last item straight away
                    vitalsBuilder = Vitals.builder();
                    setVital(vitalsBuilder, obs);
                    setCalculatedObservationsAndAddToVitals(vitals, vitalsBuilder);
                }
                vitalsBuilder = null;
                missedObs = obs;
            }
            obsEncounter = obs.getEncounter();
        }

        return vitals;
    }

    private void setCalculatedObservationsAndAddToVitals(List<Vitals> vitals, VitalsBuilder vitalsBuilder) {
        Vitals v = vitalsBuilder.build();
        // add calculated values
        if (v.getWeight() != null && v.getHeight() != null) {
            v.setBmi(getBMI(v.getWeight(), v.getHeight()));
        }
        if (v.getSBloodPressure() != null && v.getDBloodPressure() != null) {
            v.setBloodPressure(getBloodPressure(v.getSBloodPressure(), v.getDBloodPressure()));
        }
        vitals.add(v);
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

    private void setFacility(PatientSummaryBuilder patientSummarybuilder) {
        Location defaultLocation = Context.getLocationService().getDefaultLocation();

        if (defaultLocation != null) {
            if (defaultLocation.getTags().contains(
                            Context.getLocationService().getLocationTagByUuid(MSFCoreConfig.LOCATION_TAG_UUID_CLINIC))) {
                patientSummarybuilder.facility(defaultLocation.getName() + ", " + defaultLocation.getParentLocation().getName());
            } else {
                patientSummarybuilder.facility(defaultLocation.getName());
            }
        }
    }

    private List<Concept> getMedicationsConcepts() {
        List<Concept> medications = new ArrayList<Concept>();
        for (String id : Context.getAdministrationService().getGlobalProperty(OMRSConstants.GP_CONCEPT_ID_MEDICATIONS).split(",")) {
            medications.add(Context.getConceptService().getConcept(Integer.parseInt(id)));
        }
        return medications;
    }

    public PatientSummary generatePatientSummary(Patient patient) {
        PatientSummaryBuilder patientSummarybuilder = PatientSummary.builder();
        // representation defaults to SUMMARY, set if else
        if (representation != null) {
            patientSummarybuilder.representation(representation);
        }

        // set facility
        setFacility(patientSummarybuilder);

        // set demographics
        patientSummarybuilder.demographics(Demographics.builder().name(patient.getPersonName().getFullName()).age(
                        new Age(patient.getBirthdate(), new Date(), Context.getDateFormat())).build());

        PatientSummary patientSummary = patientSummarybuilder.build();

        // set recent vitals and observations
        patientSummary.getVitals().addAll(getVitals(patient));

        // set working diagnoses
        for (Diagnosis diagnosis : Context.getDiagnosisService().getDiagnoses(patient, null)) {
            String d = diagnosis.getDiagnosis().getCoded() != null ? diagnosis.getDiagnosis().getCoded().getName().getName() : diagnosis
                            .getDiagnosis().getNonCoded();
            DiseaseBuilder dB = Disease.builder().name(d);
            patientSummary.getDiagnoses().add(dB.build());
        }

        // set allergies
        setAllergies(patient, patientSummary);

        // set medications
        for (Obs obs : Context.getObsService().getObservations(Arrays.asList(patient.getPerson()), null, getMedicationsConcepts(), null,
                        null, null, null, null, null, null, null, false)) {
            patientSummary.getCurrentMedications().add(convertObs(obs));
        }

        // set clinical notes
        for (Obs obs : Context.getObsService().getObservationsByPersonAndConcept(
                        patient,
                        Context.getConceptService().getConcept(
                                        Integer.parseInt(Context.getAdministrationService().getGlobalProperty(
                                                        OMRSConstants.GP_CONCEPT_ID_NOTES))))) {
            patientSummary.getClinicalNotes().add(convertObs(obs));
        }

        if (Representation.FULL.equals(representation)) {
            // TODO after adding extra properties for full, set them here
        }

        return patientSummary;
    }

    public PatientSummary requestPatientSummary(Patient patient) {
        PatientSummary patientSummary = generatePatientSummary(patient);
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
