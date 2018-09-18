/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * 
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.msfcore.api.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.openmrs.Location;
import org.openmrs.LocationAttribute;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.Person;
import org.openmrs.PersonAttribute;
import org.openmrs.PersonAttributeType;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.module.msfcore.api.DHISService;
import org.openmrs.module.msfcore.api.dao.MSFCoreDao;
import org.openmrs.module.msfcore.dhis2.Data;
import org.openmrs.module.msfcore.dhis2.OpenMRSToDHIS;
import org.openmrs.module.msfcore.dhis2.Parameters;
import org.openmrs.module.msfcore.dhis2.PatientTrackableAttributes;
import org.openmrs.module.msfcore.dhis2.SimpleJSON;
import org.openmrs.module.msfcore.dhis2.TrackerInstance;
import org.openmrs.util.OpenmrsUtil;

public class DHISServiceImpl extends BaseOpenmrsService implements DHISService {
    private static final String ADMIN = "admin";
    private static final String MSF = "msf";
    private static final String MSFCORE = "msfcore";

    MSFCoreDao dao;

    /**
     * Injected in moduleApplicationContext.xml
     */
    public void setDao(MSFCoreDao dao) {
        this.dao = dao;
    }

    private Log log = LogFactory.getLog(getClass());

    public LocationAttribute getLocationUidAttribute(Location location) {
        return getLocationAttribute(location, MSFCoreConfig.LOCATION_ATTR_TYPE_UID_UUID);
    }

    private LocationAttribute getLocationAttribute(Location location, String attributeTypeUuid) {
        if (location != null) {
            List<LocationAttribute> attrributes = dao.getLocationAttributeByTypeAndLocation(Context.getLocationService()
                            .getLocationAttributeTypeByUuid(attributeTypeUuid), location);
            if (!attrributes.isEmpty()) {
                return attrributes.get(0);
            }
        }
        return null;
    }
    public String getLocationDHISUid(Location location) {
        if (location == null) {
            return null;
        }
        return getLocationAttribute(getLocationAttribute(location, MSFCoreConfig.LOCATION_ATTR_TYPE_UID_UUID));
    }

    private String getLocationAttribute(LocationAttribute locAttribute) {
        if (locAttribute != null) {
            return locAttribute.getValue().toString();
        }
        return "";
    }

    private File getDHIS2MappingsFile() {
        File msfcoreStorage = new File(OpenmrsUtil.getApplicationDataDirectory() + File.separator + MSFCORE);
        if (!msfcoreStorage.exists()) {
            msfcoreStorage.mkdirs();
        }
        return new File(msfcoreStorage.getAbsoluteFile() + File.separator + FILENAME_DHIS_MAPPINGS_PROPERTIES);
    }

    public void transferDHISMappingsToDataDirectory() {
        transferFileToDataDirectory(FILENAME_DHIS_MAPPINGS_PROPERTIES, getDHIS2MappingsFile());
    }

    private void transferFileToDataDirectory(String fileName, File destination) {
        if (destination.exists()) {
            return;
        }
        File file = new File(getClass().getClassLoader().getResource(fileName).getFile());
        try {
            FileUtils.copyFile(file, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Properties getDHISMappings() {
        Properties prop = new Properties();
        File mappingFile = getDHIS2MappingsFile();
        if (!mappingFile.exists()) {
            mappingFile = new File(getClass().getClassLoader().getResource(FILENAME_DHIS_MAPPINGS_PROPERTIES).getFile());
        }
        try {
            prop.load(new FileInputStream(mappingFile));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return prop;
    }

    String getOpenHIMClientUsername() {
        String username = Context.getRuntimeProperties().getProperty(MSFCoreConfig.PROP_OPENHIM_USERNAME);
        return StringUtils.isBlank(username) ? MSF : username;
    }

    String getOpenHIMClientPassword() {
        String pass = Context.getRuntimeProperties().getProperty(MSFCoreConfig.PROP_OPENHIM_PASSWORD);
        return StringUtils.isBlank(pass) ? MSF : pass;
    }

    String getDHIS2Username() {
        String username = Context.getRuntimeProperties().getProperty(MSFCoreConfig.PROP_DHIS2_USERNAME);
        return StringUtils.isBlank(username) ? ADMIN : username;
    }

    String getDHIS2Password() {
        String pass = Context.getRuntimeProperties().getProperty(MSFCoreConfig.PROP_DHIS2_PASSWORD);
        return StringUtils.isBlank(pass) ? "district" : pass;
    }

    private List<PersonAttribute> getPersonAttributeByTypeAndPerson(PersonAttributeType personAttributeType, Person person) {
        return dao.getPersonAttributeByTypeAndPerson(personAttributeType, person);
    }

    private String getPersonAttributeValue(Person person, String personAttributeTypeUuid) {
        List<PersonAttribute> attrs = getPersonAttributeByTypeAndPerson(Context.getPersonService().getPersonAttributeTypeByUuid(
                        personAttributeTypeUuid), person);
        if (!attrs.isEmpty()) {
            return attrs.get(0).getValue();
        }
        return null;
    }

    private List<PatientIdentifier> getPatientIdentifierByTypeAndPatient(PatientIdentifierType type, Patient patient) {
        return dao.getPatientIdentifierByTypeAndPatient(type, patient);
    }

    private String getPatientIdentifierValue(Patient patient, String patientIndentifierTypeUuid) {
        List<PatientIdentifier> ids = getPatientIdentifierByTypeAndPatient(Context.getPatientService().getPatientIdentifierTypeByUuid(
                        patientIndentifierTypeUuid), patient);
        if (!ids.isEmpty()) {
            return ids.get(0).getIdentifier();
        }
        return null;
    }
    private void setIdentifiers(Patient patient, Map<String, String> attributes, Properties dhisMappings) {
        String patientId = getPatientIdentifierValue(patient, MSFCoreConfig.PATIENT_ID_TYPE_PRIMARY_UUID);
        if (StringUtils.isNotBlank(patientId)) {
            attributes.put(getDHISMappings().getProperty(PatientTrackableAttributes.PATIENT_NUMBER.name()), patientId);
        }
        String UNHCR = getPatientIdentifierValue(patient, MSFCoreConfig.PATIENT_ID_TYPE_UNHCR_ID_UUID);
        if (StringUtils.isNotBlank(UNHCR)) {
            attributes.put(getDHISMappings().getProperty(PatientTrackableAttributes.REFUGEE_UNHCR.name()), "true");
        }
        String UNRWA = getPatientIdentifierValue(patient, MSFCoreConfig.PATIENT_ID_TYPE_UNRWA_ID_UUID);
        if (StringUtils.isNotBlank(UNRWA)) {
            attributes.put(getDHISMappings().getProperty(PatientTrackableAttributes.REFUGEE_UNRWA.name()), "true");
        }
        String oldPatientId = getPatientIdentifierValue(patient, MSFCoreConfig.PATIENT_ID_TYPE_OLD_PATIENT_ID_UUID);
        if (StringUtils.isNotBlank(oldPatientId)) {
            attributes.put(getDHISMappings().getProperty(PatientTrackableAttributes.OLD_PATIENT_ID.name()), oldPatientId);
        }
    }

    private void setPersonAttributes(Patient patient, Map<String, String> attributes, SimpleJSON optionSets, Properties dhisMappings) {
        String provenance = getPersonAttributeValue(patient, MSFCoreConfig.PERSON_ATTRIBUTE_PROVENANCE_UUID);
        if (provenance != null && optionSets != null) {
            addMatchedConceptAttribute(attributes, optionSets, PatientTrackableAttributes.PROVENANCE, dhisMappings, "Provenance",
                            provenance);
        }
        String nationality = getPersonAttributeValue(patient, MSFCoreConfig.PERSON_ATTRIBUTE_NATIONALITY_UUID);
        if (StringUtils.isNotBlank(nationality) && optionSets != null) {
            addMatchedConceptAttribute(attributes, optionSets, PatientTrackableAttributes.NATIONALITY, dhisMappings, "Nationality",
                            nationality);
        }
        String conditionOfLiving = getPersonAttributeValue(patient, MSFCoreConfig.PERSON_ATTRIBUTE_CONDITION_OF_LIVING_UUID);
        if (StringUtils.isNotBlank(conditionOfLiving)) {
            addMatchedConceptAttribute(attributes, optionSets, PatientTrackableAttributes.CONDITION_OF_LIVING, dhisMappings,
                            "Condition of living", conditionOfLiving);
        }
        String maritalStatus = getPersonAttributeValue(patient, MSFCoreConfig.PERSON_ATTRIBUTE_MARITAL_STATUS_UUID);
        if (StringUtils.isNotBlank(maritalStatus) && optionSets != null) {
            addMatchedConceptAttribute(attributes, optionSets, PatientTrackableAttributes.MARITAL_STATUS, dhisMappings, "Marital status",
                            maritalStatus);
        }
        String phoneNumber = getPersonAttributeValue(patient, MSFCoreConfig.PERSON_ATTRIBUTE_PHONENUMBER_UUID);
        if (StringUtils.isNotBlank(phoneNumber)) {
            attributes.put(dhisMappings.getProperty(PatientTrackableAttributes.PHONE_NUMBER.name()), (String) phoneNumber);
        }
        // TODO add MOTHER_NAME, EDUCATION(???), PATIENT_TYPE?

    }

    private void addMatchedConceptAttribute(Map<String, String> attributes, SimpleJSON optionSets, PatientTrackableAttributes attributeMap,
                    Properties dhisMappings, String conceptName, String conceptAnserId) {
        attributes.put(dhisMappings.getProperty(attributeMap.name()), OpenMRSToDHIS.getMatchedConceptCodeFromOptionSets(optionSets,
                        conceptName, Context.getConceptService().getConcept(Integer.parseInt(conceptAnserId)).getName().getName()));
    }

    private TrackerInstance generateTrackerInstanceForPatient(Patient patient) {
        Properties dhisMappings = getDHISMappings();
        Location defaultLocation = Context.getLocationService().getDefaultLocation();
        TrackerInstance trackerInstance = new TrackerInstance();
        trackerInstance.setUrl(TrackerInstance.generateUrl(getDHIS2Username(), getDHIS2Password(), Context.getAdministrationService()
                        .getGlobalProperty(MSFCoreConfig.GP_DHIS_HOST)));
        trackerInstance.setProgram(Context.getAdministrationService().getGlobalProperty(MSFCoreConfig.GP_DHIS_NCD_PROGRAM_UID));
        trackerInstance.setProgramStage(Context.getAdministrationService().getGlobalProperty(MSFCoreConfig.GP_DHIS_NCD_PROGRAMSTAGE_UID));
        trackerInstance.setTrackedEntity(Context.getAdministrationService().getGlobalProperty(MSFCoreConfig.GP_DHIS_TRACKENTITYTYPE_UID));
        Data data = new Data();
        // TODO do we need set dataElements
        Parameters parameters = new Parameters();
        SimpleJSON optionSets = getOptionSets();
        // TODO introduce programs for MSF and control when to enroll patient
        // etc then call this functionality there
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());
        parameters.setEventDate(today);
        parameters.setProgramDate(today);
        if (defaultLocation != null) {
            parameters.setOrgUnit(getLocationDHISUid(defaultLocation));
        }
        data.setParameters(parameters);

        // add patient details
        Map<String, String> attributes = data.getAttributes();
        attributes.put(dhisMappings.getProperty(PatientTrackableAttributes.FIRST_NAME.name()), patient.getGivenName());
        attributes.put(dhisMappings.getProperty(PatientTrackableAttributes.LAST_NAME.name()), patient.getFamilyName());
        attributes.put(dhisMappings.getProperty(PatientTrackableAttributes.AGE_IN_YEARS.name()), String.valueOf(patient.getAge()));
        attributes.put(dhisMappings.getProperty(PatientTrackableAttributes.DATE_OF_BIRTH.name()), sdf.format(patient.getBirthdate()));
        if (optionSets != null && StringUtils.isNotBlank(patient.getGender())) {
            attributes.put(dhisMappings.getProperty(PatientTrackableAttributes.SEX.name()), OpenMRSToDHIS.getGenderFromOptionSets(
                            optionSets, patient.getGender()));
        }
        setIdentifiers(patient, attributes, dhisMappings);
        setPersonAttributes(patient, attributes, optionSets, dhisMappings);
        data.setAttributes(attributes);

        trackerInstance.setData(data);
        return trackerInstance;
    }

    public String postTrackerInstanceThroughOpenHimForAPatient(Patient patient) {
        TrackerInstance trackerInstance = generateTrackerInstanceForPatient(patient);
        DefaultHttpClient client = null;
        String payload = "";
        String url = Context.getAdministrationService().getGlobalProperty(MSFCoreConfig.GP_OPENHIM_TRACKER_URL);
        try {
            URL dhisURL = new URL(url);
            HttpHost targetHost = new HttpHost(dhisURL.getHost(), dhisURL.getPort(), dhisURL.getProtocol());
            client = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(dhisURL.getPath());
            BasicHttpContext localcontext = new BasicHttpContext();
            setHeaders(httpPost, localcontext);
            httpPost.setEntity(new StringEntity(trackerInstance.toString()));
            HttpResponse response = client.execute(targetHost, httpPost, localcontext);
            HttpEntity entity = response.getEntity();
            if (entity != null && response.getStatusLine().getStatusCode() == 200) {
                payload = EntityUtils.toString(entity);
                log.info("POST: to " + url + " returned " + payload);
            }
        } catch (Exception ex) {
            log.error("POST: to " + url + " returned " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (client != null) {
                client.getConnectionManager().shutdown();
            }
        }
        return payload;
    }

    private void setHeaders(HttpRequestBase httpRequesst, HttpContext localcontext) throws AuthenticationException {
        Credentials creds = new UsernamePasswordCredentials(getOpenHIMClientUsername(), getOpenHIMClientPassword());
        Header bs = new BasicScheme().authenticate(creds, httpRequesst, localcontext);
        httpRequesst.addHeader("Authorization", bs.getValue());
        httpRequesst.addHeader("Content-Type", "application/json");
        httpRequesst.addHeader("Accept", "application/json");
    }

    String generateDHISAPIUrl(String host) {
        host = host.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "");
        return "http://" + host;
    }

    public void installDHIS2Metadata() {
        try {
            installOptionSets();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void installOptionSets() throws IOException {
        transferFileToDataDirectory(FILENAME_OPTION_SETS_JSON, getDHIS2optionSetsFile());
        overWriteFile(getDHIS2optionSetsFile(), getOptionSets().toString());
    }

    private File getDHIS2optionSetsFile() {
        File msfcoreStorage = new File(OpenmrsUtil.getApplicationDataDirectory() + File.separator + MSFCORE);
        if (!msfcoreStorage.exists()) {
            msfcoreStorage.mkdirs();
        }
        return new File(msfcoreStorage.getAbsoluteFile() + File.separator + FILENAME_OPTION_SETS_JSON);
    }

    private void overWriteFile(File file, String content) throws IOException {
        if (StringUtils.isNotBlank(content)) {
            FileWriter fooWriter = new FileWriter(file, false);
            fooWriter.write(content);
            fooWriter.close();
        }
    }

    public SimpleJSON getOptionSets() {
        SimpleJSON optionSets = getDataFromDHISEndpoint(generateDHISAPIUrl(Context.getAdministrationService().getGlobalProperty(
                        MSFCoreConfig.GP_DHIS_HOST))
                        + Context.getAdministrationService().getGlobalProperty(MSFCoreConfig.URL_POSTFIX_OPTIONSETS));
        if (optionSets == null) {
            try {
                optionSets = SimpleJSON.readFromInputStream(new FileInputStream(getDHIS2optionSetsFile()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return optionSets;
    }

    SimpleJSON getDataFromDHISEndpoint(String endPointUrl) {
        SimpleJSON payload = null;
        DefaultHttpClient client = null;
        if (StringUtils.isNotBlank(endPointUrl)) {
            try {
                URL dhisURL = new URL(endPointUrl);
                HttpHost targetHost = new HttpHost(dhisURL.getHost(), dhisURL.getPort(), dhisURL.getProtocol());
                client = new DefaultHttpClient();
                BasicHttpContext localcontext = new BasicHttpContext();
                HttpGet httpGet = new HttpGet(endPointUrl);
                setHeaders(httpGet, localcontext);
                HttpResponse response = client.execute(targetHost, httpGet, localcontext);
                HttpEntity entity = response.getEntity();

                if (entity != null && response.getStatusLine().getStatusCode() == 200) {
                    payload = SimpleJSON.parseJson(EntityUtils.toString(entity));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (client != null) {
                    client.getConnectionManager().shutdown();
                }
            }
        }
        return payload;
    }
}
