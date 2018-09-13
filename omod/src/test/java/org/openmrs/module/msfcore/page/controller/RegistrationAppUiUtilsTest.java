package org.openmrs.module.msfcore.page.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.PersonAddress;
import org.openmrs.PersonAttribute;
import org.openmrs.PersonAttributeType;
import org.openmrs.api.LocationService;
import org.openmrs.api.PersonService;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.module.msfcore.RegistrationAppUiUtils;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Context.class)
public class RegistrationAppUiUtilsTest {

    @Mock
    private PersonService personService;

    @Mock
    private LocationService locationService;

    @Test
    public void getAttribute_shouldReturnAttributeValue() {
        // Fixture setup
        String attributeTypeUuid = "ed5ba03e-f44a-4bbf-9b5d-d59d47e699a0";
        String attributeValue = "VALUE";
        Patient patient = new Patient();

        PersonAttributeType personAttributeType = new PersonAttributeType();
        personAttributeType.setId(1);

        PersonAttribute personAttribute = new PersonAttribute();
        personAttribute.setAttributeType(personAttributeType);
        personAttribute.setValue(attributeValue);

        patient.getAttributes().add(personAttribute);

        // Mock setup
        PowerMockito.mockStatic(Context.class);
        Mockito.when(personService.getPersonAttributeTypeByUuid(attributeTypeUuid)).thenReturn(personAttributeType);
        Mockito.when(Context.getPersonService()).thenReturn(personService);

        // Execution
        String attribute = new RegistrationAppUiUtils().getAttribute(patient, attributeTypeUuid);

        // Assertion
        Assert.assertEquals(attributeValue, attribute);
    }

    @Test
    public void getAttribute_withPatientNull_shouldReturnNull() {
        // Fixture setup
        String attributeTypeUuid = "ed5ba03e-f44a-4bbf-9b5d-d59d47e699a0";

        // Execution
        String attribute = new RegistrationAppUiUtils().getAttribute(null, attributeTypeUuid);

        // Assertion
        Assert.assertNull(attribute);
    }

    @Test
    public void getAttribute_withAttributeNull_shouldReturnNull() {
        // Fixture setup
        String attributeTypeUuid = "ed5ba03e-f44a-4bbf-9b5d-d59d47e699a0";
        Patient patient = new Patient();

        // Mock setup
        PowerMockito.mockStatic(Context.class);
        Mockito.when(personService.getPersonAttributeTypeByUuid(attributeTypeUuid)).thenReturn(null);
        Mockito.when(Context.getPersonService()).thenReturn(personService);

        // Execution
        String attribute = new RegistrationAppUiUtils().getAttribute(patient, attributeTypeUuid);

        // Assertion
        Assert.assertNull(attribute);
    }

    @Test
    public void getIdentifier_shouldReturnIdentifierValue() {
        // Fixture setup
        String identifierName = "IDENTIFIER NAME";
        Patient patient = new Patient();

        PatientIdentifier patientIdentifier = new PatientIdentifier();
        patientIdentifier.setIdentifier(identifierName);
        patientIdentifier.setPreferred(true);
        patientIdentifier.setVoided(false);
        PatientIdentifierType identifierType = new PatientIdentifierType();
        identifierType.setName(identifierName);
        patientIdentifier.setIdentifierType(identifierType);

        patient.addIdentifier(patientIdentifier);
        patient.getPatientIdentifier(identifierName);

        // Execution
        String identifier = new RegistrationAppUiUtils().getIdentifier(patient, identifierName);

        // Assertion
        Assert.assertEquals(identifierName, identifier);
    }

    @Test
    public void getIdentifier_withPatientNull_shouldReturnNull() {
        // Fixture setup
        String identifierName = "IDENTIFIER NAME";

        // Execution
        String identifier = new RegistrationAppUiUtils().getIdentifier(null, identifierName);

        // Assertion
        Assert.assertNull(identifier);
    }

    @Test
    public void getIdentifier_withIdentifierNull_shouldReturnNull() {
        // Fixture setup
        String identifierName = "IDENTIFIER NAME";
        Patient patient = new Patient();

        // Execution
        String identifier = new RegistrationAppUiUtils().getIdentifier(patient, identifierName);

        // Assertion
        Assert.assertNull(identifier);
    }

    @Test
    public void getDateOfArrival_withDateOfArrivalNull_shouldReturnCurrentDate() {
        // Fixture setup
        String attributeTypeUuid = MSFCoreConfig.PERSON_ATTRIBUTE_DATE_OF_ARRIVAL_UUID;
        Date expectedDate = new Date();
        Patient patient = new Patient();

        // Mock setup
        PowerMockito.mockStatic(Context.class);
        Mockito.when(personService.getPersonAttributeTypeByUuid(attributeTypeUuid)).thenReturn(null);
        Mockito.when(Context.getPersonService()).thenReturn(personService);

        // Execution
        Date date = new RegistrationAppUiUtils().getDateOfArrival(patient);

        // Assertion
        Assert.assertTrue(date.getTime() - expectedDate.getTime() < 1000);
    }

    @Test
    public void getDateOfArrival_shouldReturnDateOfArrival() throws Exception {
        // Fixture setup
        String attributeTypeUuid = MSFCoreConfig.PERSON_ATTRIBUTE_DATE_OF_ARRIVAL_UUID;
        String attributeValue = "2018-09-06 16:30:59.786";
        Date expectedDate = new SimpleDateFormat("yyyy-M-dd").parse(attributeValue);
        Patient patient = new Patient();

        PersonAttributeType personAttributeType = new PersonAttributeType();
        personAttributeType.setId(1);

        PersonAttribute personAttribute = new PersonAttribute();
        personAttribute.setAttributeType(personAttributeType);
        personAttribute.setValue(attributeValue);

        patient.getAttributes().add(personAttribute);

        // Mock setup
        PowerMockito.mockStatic(Context.class);
        Mockito.when(personService.getPersonAttributeTypeByUuid(attributeTypeUuid)).thenReturn(personAttributeType);
        Mockito.when(Context.getPersonService()).thenReturn(personService);

        // Execution
        Date date = new RegistrationAppUiUtils().getDateOfArrival(patient);

        // Assertion
        Assert.assertEquals(expectedDate, date);
    }

    @Test
    public void getLocation_shouldReturnLocation() {
        // Fixture setup
        String attributeTypeUuid = MSFCoreConfig.PERSON_ATTRIBUTE_HEALTH_CENTER_UUID;
        String locationId = "4";
        Location expectedLocation = new Location();
        expectedLocation.setId(4);
        Patient patient = new Patient();

        PersonAttributeType personAttributeType = new PersonAttributeType();
        personAttributeType.setId(1);

        PersonAttribute personAttribute = new PersonAttribute();
        personAttribute.setAttributeType(personAttributeType);
        personAttribute.setValue(locationId);

        patient.getAttributes().add(personAttribute);

        // Mock setup
        PowerMockito.mockStatic(Context.class);
        Mockito.when(personService.getPersonAttributeTypeByUuid(attributeTypeUuid)).thenReturn(personAttributeType);
        Mockito.when(locationService.getLocation(4)).thenReturn(expectedLocation);
        Mockito.when(Context.getPersonService()).thenReturn(personService);
        Mockito.when(Context.getLocationService()).thenReturn(locationService);

        // Execution
        Location location = new RegistrationAppUiUtils().getLocation(patient);

        // Assertion
        Assert.assertEquals(expectedLocation.getId(), location.getId());
    }

    @Test
    public void getLocation_whenLocationIsNull_shouldReturnDefaultLocation() {
        // Fixture setup
        String attributeTypeUuid = MSFCoreConfig.PERSON_ATTRIBUTE_HEALTH_CENTER_UUID;
        Location expectedLocation = new Location();
        expectedLocation.setId(4);
        Patient patient = new Patient();

        // Mock setup
        PowerMockito.mockStatic(Context.class);
        Mockito.when(personService.getPersonAttributeTypeByUuid(attributeTypeUuid)).thenReturn(null);
        Mockito.when(locationService.getDefaultLocation()).thenReturn(expectedLocation);
        Mockito.when(Context.getPersonService()).thenReturn(personService);
        Mockito.when(Context.getLocationService()).thenReturn(locationService);

        // Execution
        Location location = new RegistrationAppUiUtils().getLocation(patient);

        // Assertion
        Assert.assertEquals(expectedLocation.getId(), location.getId());
    }

    @Test
    public void getAddress_shouldExtractAndReturnTheActiveAddress() {
        // Fixture setup
        Patient patient = new Patient();
        PersonAddress address = new PersonAddress();
        address.setVoided(false);
        address.setAddress1("Address line 1");
        address.setId(10);

        patient.addAddress(address);

        // Execution
        PersonAddress result = new RegistrationAppUiUtils().getAddress(patient);

        // Assertion
        Assert.assertEquals(new Integer(10), result.getId());
    }

    @Test
    public void getAddress_whenPatientNull_shouldReturnNull() {
        // Execution
        PersonAddress address = new RegistrationAppUiUtils().getAddress(null);

        // Assertion
        Assert.assertNull(address);
    }

    @Test
    public void getAddress_whenExtractedAddressesIsNull_shouldReturnNull() {
        // Fixture setup
        Patient patient = new Patient();

        // Execution
        PersonAddress result = new RegistrationAppUiUtils().getAddress(patient);

        // Assertion
        Assert.assertNull(result);
    }

    @Test
    public void getAddress_whenAllExtractedAddressesAreVoided_shouldReturnNull() {
        // Fixture setup
        Patient patient = new Patient();
        PersonAddress address1 = new PersonAddress();
        address1.setId(1);
        address1.setVoided(true);
        address1.setAddress1("Address line");
        PersonAddress address2 = new PersonAddress();
        address2.setId(2);
        address2.setVoided(true);
        address2.setAddress1("Another address line");

        patient.addAddress(address1);
        patient.addAddress(address2);

        // Execution
        PersonAddress result = new RegistrationAppUiUtils().getAddress(patient);

        // Assertion
        Assert.assertNull(result);
    }

}
