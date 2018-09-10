package org.openmrs.module.msfcore;

public class SimplifiedLocation {
    private String display;
    private String code;
    private String uuid;

    public SimplifiedLocation(String display, String code, String uuid) {
        setDisplay(display);
        setCode(code);
        setUuid(uuid);
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
