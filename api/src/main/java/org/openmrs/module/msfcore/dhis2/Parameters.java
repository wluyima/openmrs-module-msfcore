package org.openmrs.module.msfcore.dhis2;

public class Parameters {
    private String orgUnit;
    private String programDate;
    private String eventDate;

    public Parameters() {
    }

    public String getOrgUnit() {
        return orgUnit;
    }
    public void setOrgUnit(String orgUnit) {
        this.orgUnit = orgUnit;
    }
    public String getProgramDate() {
        return programDate;
    }
    public void setProgramDate(String programDate) {
        this.programDate = programDate;
    }
    public String getEventDate() {
        return eventDate;
    }
    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }
}
