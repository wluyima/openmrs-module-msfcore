package org.openmrs.module.msfcore.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.OMRSConstants;
import org.openmrs.module.msfcore.audit.AuditLog;
import org.openmrs.module.msfcore.audit.AuditLog.Event;
import org.openmrs.module.msfcore.patientSummary.Disease;
import org.openmrs.module.msfcore.patientSummary.Observation;
import org.openmrs.module.msfcore.patientSummary.PatientSummary;
import org.openmrs.module.msfcore.patientSummary.PatientSummary.Representation;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class PatientSummaryServiceTest extends BaseModuleContextSensitiveTest {
    PatientSummaryService patientSummaryService;
    Patient patient;

    @Before
    public void setup() {
        executeDataSet("PatientSummary.xml");

        patientSummaryService = Context.getService(PatientSummaryService.class);
        patient = Context.getPatientService().getPatient(7);
    }

    @Test
    public void generatePatientSummary_shouldSetSummaryRepresentationRightly() {
        patientSummaryService.setRepresentation(Representation.SUMMARY);
        assertEquals(Representation.SUMMARY, patientSummaryService.generatePatientSummary(patient).getRepresentation());
    }

    @Test
    public void generatePatientSummary_shouldSetFullRepresentationRightly() {
        patientSummaryService.setRepresentation(Representation.FULL);
        assertEquals(Representation.FULL, patientSummaryService.generatePatientSummary(patient).getRepresentation());
    }

    @Test
    public void generatePatientSummary_shouldSetFacilityRightly() {
        // Un known location has id 1 and is default by default
        assertEquals("Unknown Location", patientSummaryService.generatePatientSummary(patient).getFacility());
        Context.getAdministrationService().setGlobalProperty("default_location", "Unknown Location");
        assertEquals("Unknown Location", patientSummaryService.generatePatientSummary(patient).getFacility());
        Context.getAdministrationService().setGlobalProperty("default_location", "Baalbak Clinic");
        assertEquals("Baalbak Clinic, Baalbak Project", patientSummaryService.generatePatientSummary(patient).getFacility());
    }

    private int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH)
                        || (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }
        return diff;
    }

    private Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    @Test
    public void generatePatientSummary_shouldSetDemographicsRightly() throws ParseException {
        PatientSummary patientSummary = patientSummaryService.generatePatientSummary(patient);
        assertEquals("Collet Test Chebaskwony", patientSummary.getDemographics().getName());
        assertEquals(getDiffYears(new SimpleDateFormat("yyyy-MM-dd").parse("1976-08-25"), new Date()) + " years", patientSummary
                        .getDemographics().getAge().getAge());
        assertEquals("25/08/1976", patientSummary.getDemographics().getAge().getFormattedBirthDate());
    }

    /*
     * messages from uicommons module aren't initialised, emulate them
     */
    private String getUnit(String unitMessageCode) {
        if (unitMessageCode.equals(Context.getMessageSourceService().getMessage(OMRSConstants.MESSAGE_UNITS_CENTIMETERS))) {
            return "cm";
        } else if (unitMessageCode.equals(Context.getMessageSourceService().getMessage(OMRSConstants.MESSAGE_UNITS_KILOGRAMS))) {
            return "kg";
        } else if (unitMessageCode.equals(Context.getMessageSourceService().getMessage(OMRSConstants.MESSAGE_UNITS_PER_MINUTE))) {
            return "/min";
        } else if (unitMessageCode.equals(Context.getMessageSourceService().getMessage(OMRSConstants.MESSAGE_UNITS_DEGREES))) {
            return "°C";
        } else if (unitMessageCode.equals(Context.getMessageSourceService().getMessage(OMRSConstants.MESSAGE_UNITS_PERCENT))) {
            return "%";
        } else {
            return unitMessageCode;
        }
    }

    @Test
    public void generatePatientSummary_shouldSetVitalsRightly() {
        PatientSummary patientSummary = patientSummaryService.generatePatientSummary(patient);

        assertEquals(2, patientSummary.getVitals().size());
        // latest is positioned at 0

        assertEquals("Temperature (C)", patientSummary.getVitals().get(0).getTemperature().getName());
        assertEquals("35.6", patientSummary.getVitals().get(0).getTemperature().getValue());
        assertEquals("°C", getUnit(patientSummary.getVitals().get(0).getTemperature().getUnit()));

        assertEquals("Blood oxygen saturation", patientSummary.getVitals().get(0).getBloodOxygenSaturation().getName());
        assertEquals("72.7", patientSummary.getVitals().get(0).getBloodOxygenSaturation().getValue());
        assertEquals("%", getUnit(patientSummary.getVitals().get(0).getBloodOxygenSaturation().getUnit()));
        // non set defaulting
        assertEquals("Weight", patientSummary.getVitals().get(0).getWeight().getName());
        assertEquals("_", patientSummary.getVitals().get(0).getWeight().getValue());
        assertEquals("kg", getUnit(patientSummary.getVitals().get(0).getWeight().getUnit()));

        assertEquals("WEIGHT (KG)", patientSummary.getVitals().get(1).getWeight().getName());
        assertEquals("62.3", patientSummary.getVitals().get(1).getWeight().getValue());
        assertEquals("kg", getUnit(patientSummary.getVitals().get(1).getWeight().getUnit()));

        assertEquals("Height (cm)", patientSummary.getVitals().get(1).getHeight().getName());
        assertEquals("167.9", patientSummary.getVitals().get(1).getHeight().getValue());
        assertEquals("cm", getUnit(patientSummary.getVitals().get(1).getHeight().getUnit()));

        assertEquals("BMI", patientSummary.getVitals().get(1).getBmi().getName());
        assertEquals("22.1", patientSummary.getVitals().get(1).getBmi().getValue());
        assertEquals("", getUnit(patientSummary.getVitals().get(1).getBmi().getUnit()));

        assertEquals("Temperature (C)", patientSummary.getVitals().get(1).getTemperature().getName());
        assertEquals("37", patientSummary.getVitals().get(1).getTemperature().getValue());
        assertEquals("°C", getUnit(patientSummary.getVitals().get(1).getTemperature().getUnit()));

        assertEquals("Pulse", patientSummary.getVitals().get(1).getPulse().getName());
        assertEquals("70", patientSummary.getVitals().get(1).getPulse().getValue());
        assertEquals("/min", getUnit(patientSummary.getVitals().get(1).getPulse().getUnit()));

        assertEquals("Respiratory rate", patientSummary.getVitals().get(1).getRespiratoryRate().getName());
        assertEquals("16", patientSummary.getVitals().get(1).getRespiratoryRate().getValue());
        assertEquals("/min", getUnit(patientSummary.getVitals().get(1).getRespiratoryRate().getUnit()));

        assertEquals("Blood Pressure", patientSummary.getVitals().get(1).getBloodPressure().getName());
        assertEquals("130/97", patientSummary.getVitals().get(1).getBloodPressure().getValue());
        assertEquals("", getUnit(patientSummary.getVitals().get(1).getBloodPressure().getUnit()));

        assertEquals("Blood oxygen saturation", patientSummary.getVitals().get(1).getBloodOxygenSaturation().getName());
        assertEquals("_", patientSummary.getVitals().get(1).getBloodOxygenSaturation().getValue());
        assertEquals("%", getUnit(patientSummary.getVitals().get(1).getBloodOxygenSaturation().getUnit()));
    }

    @Test
    public void generatePatientSummary_shouldSetDiagnosesRightly() {
        PatientSummary patientSummary = patientSummaryService.generatePatientSummary(patient);
        assertEquals(3, patientSummary.getDiagnoses().size());
        assertThat(patientSummary.getDiagnoses(), contains(Disease.builder().name("Diabates").build(), Disease.builder().name(
                        "Hypertension").build(), Disease.builder().name("Unknown Cancer").build()));
    }

    @Test
    public void generatePatientSummary_shouldSetAllergiesRightly() {
        PatientSummary patientSummary = patientSummaryService.generatePatientSummary(patient);
        assertEquals("ASPIRIN", patientSummary.getAllergies().get(0).getName());
        assertEquals("Severe", patientSummary.getAllergies().get(0).getSeverity());
        assertEquals(1, patientSummary.getAllergies().get(0).getReactions().size());
        assertThat(patientSummary.getAllergies().get(0).getReactions(), contains("Hives"));
    }

    @Test
    public void generatePatientSummary_shouldSetMedicationsRightly() {
        PatientSummary patientSummary = patientSummaryService.generatePatientSummary(patient);
        assertEquals(3, patientSummary.getDiagnoses().size());
        assertThat(patientSummary.getCurrentMedications(), contains(Observation.builder().name("Medication 1").value(
                        "Aspirin -- Aspicot 100 mg").build(), Observation.builder().name("Medication 1").value(
                        "Bisoprolol Fumarate -- Biscor 5 mg").build(), Observation.builder().name("Other medication").value(
                        "No more DNA damage drug missing?").build()));
    }

    @Test
    public void generatePatientSummary_shouldSetClinicalNotesRightly() {
        PatientSummary patientSummary = patientSummaryService.generatePatientSummary(patient);
        assertEquals(1, patientSummary.getClinicalNotes().size());
        assertEquals(Observation.builder().name("Text of encounter note").value("Sample doctor's notes").build(), patientSummary
                        .getClinicalNotes().get(0));
    }

    @Test
    public void generatePatientSummary_shouldSetLabResultsRightly() {
        PatientSummary patientSummary = patientSummaryService.generatePatientSummary(patient);
        assertEquals(2, patientSummary.getRecentLabResults().size());
        assertEquals(Observation.builder().name("Creatinine").value("54.3").build(), patientSummary.getRecentLabResults().get(0));
        assertEquals(Observation.builder().name("Total cholesterol").value("183").build(), patientSummary.getRecentLabResults().get(1));
    }

    @Test
    public void requestPatientSummary_shouldGeneratePatientSummaryAndSaveAuditLog() {
        PatientSummary patientSummary = patientSummaryService.requestPatientSummary(patient);
        assertNotNull(patientSummary);
        List<AuditLog> summaryAudits = Context.getService(AuditService.class).getAuditLogs(null, null,
                        Arrays.asList(Event.REQUEST_PATIENT_SUMMARY), Arrays.asList(patient), null, null, null);
        assertEquals(1, summaryAudits.size());
        assertEquals(summaryAudits.get(0).getDetail(), "Patient Summary Request: Collet Test Chebaskwony - 6TS-4");
    }
}
