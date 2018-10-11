package org.openmrs.module.msfcore.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.OMRSConstants;
import org.openmrs.module.msfcore.summary.PatientSummary;
import org.openmrs.module.msfcore.summary.PatientSummary.Representation;
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
    public void generatePatientSummary_shouldDefaultRepresentationToSummary() {
        assertEquals(Representation.SUMMARY, patientSummaryService.generatePatientSummary(patient, null).getRepresentation());
    }

    @Test
    public void generatePatientSummary_shouldSetRepresentationRightly() {
        assertEquals(Representation.FULL, patientSummaryService.generatePatientSummary(patient, Representation.FULL).getRepresentation());
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
        PatientSummary patientSummary = patientSummaryService.generatePatientSummary(patient, null);
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
        PatientSummary patientSummary = patientSummaryService.generatePatientSummary(patient, null);

        assertEquals("WEIGHT (KG)", patientSummary.getVitals().getWeight().getName());
        assertEquals("62.3", patientSummary.getVitals().getWeight().getValue());
        assertEquals("kg", getUnit(patientSummary.getVitals().getWeight().getUnit()));

        assertEquals("Height (cm)", patientSummary.getVitals().getHeight().getName());
        assertEquals("167.9", patientSummary.getVitals().getHeight().getValue());
        assertEquals("cm", getUnit(patientSummary.getVitals().getHeight().getUnit()));

        assertEquals("BMI", patientSummary.getVitals().getBmi().getName());
        assertEquals("22.1", patientSummary.getVitals().getBmi().getValue());
        assertEquals("", getUnit(patientSummary.getVitals().getBmi().getUnit()));

        assertEquals("Temperature (C)", patientSummary.getVitals().getTemperature().getName());
        assertEquals("37", patientSummary.getVitals().getTemperature().getValue());
        assertEquals("°C", getUnit(patientSummary.getVitals().getTemperature().getUnit()));

        assertEquals("Pulse", patientSummary.getVitals().getPulse().getName());
        assertEquals("70", patientSummary.getVitals().getPulse().getValue());
        assertEquals("/min", getUnit(patientSummary.getVitals().getPulse().getUnit()));

        assertEquals("Respiratory rate", patientSummary.getVitals().getRespiratoryRate().getName());
        assertEquals("16", patientSummary.getVitals().getRespiratoryRate().getValue());
        assertEquals("/min", getUnit(patientSummary.getVitals().getRespiratoryRate().getUnit()));

        assertEquals("Blood Pressure", patientSummary.getVitals().getBloodPressure().getName());
        assertEquals("130/97", patientSummary.getVitals().getBloodPressure().getValue());
        assertEquals("", getUnit(patientSummary.getVitals().getBloodPressure().getUnit()));

        assertEquals("Blood oxygen saturation", patientSummary.getVitals().getBloodOxygenRate().getName());
        assertEquals("_", patientSummary.getVitals().getBloodOxygenRate().getValue());
        assertEquals("%", getUnit(patientSummary.getVitals().getBloodOxygenRate().getUnit()));
    }

    @Test
    public void generatePatientSummary_shouldSetDiagnosesRightly() throws ParseException {
        PatientSummary patientSummary = patientSummaryService.generatePatientSummary(patient, null);
        assertEquals(2, patientSummary.getDiagnoses().size());
        assertThat(patientSummary.getDiagnoses(), contains("Diabates", "Hypertension"));
    }

    @Test
    public void generatePatientSummary_shouldSetAllergiesRightly() throws ParseException {
        PatientSummary patientSummary = patientSummaryService.generatePatientSummary(patient, null);
        assertEquals("ASPIRIN", patientSummary.getAllergies().get(0).getName());
        assertEquals("Severe", patientSummary.getAllergies().get(0).getSeverity());
        assertEquals(1, patientSummary.getAllergies().get(0).getReactions().size());
        assertThat(patientSummary.getAllergies().get(0).getReactions(), contains("Hives"));
    }
}
