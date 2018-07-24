package org.openmrs.module.msfcore.audit;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.User;

@Entity
@Table(name = MSFCoreLog.TABLE_NAME)
public class MSFCoreLog implements Serializable {

  private static final long serialVersionUID = 1144489098510052829L;

  protected static final String TABLE_NAME = "msfcore_log";

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(nullable = false, updatable = false)
  private Date date;

  @Column(nullable = false, updatable = false)
  private String detail;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, updatable = false)
  private Event event;

  @ManyToOne(optional = true)
  @JoinColumn(name = "user_id", updatable = false)
  private User user;

  @ManyToOne(optional = true)
  @JoinColumn(name = "patient_id", updatable = false)
  private Patient patient;

  @ManyToOne(optional = true)
  @JoinColumn(name = "provider_id", updatable = false)
  private Provider provider;

  @ManyToOne(optional = true)
  @JoinColumn(name = "location_id", updatable = false)
  private Location location;

  @ManyToOne(optional = false)
  @JoinColumn(name = "creator", referencedColumnName = "user_id", updatable = false, nullable = false)
  private User creator;

  @Column(unique = true, nullable = false, length = 38, updatable = false)
  private String uuid = UUID.randomUUID().toString();

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public Event getEvent() {
    return event;
  }

  public void setEvent(Event event) {
    this.event = event;
  }

  public Patient getPatient() {
    return patient;
  }

  public void setPatient(Patient patient) {
    this.patient = patient;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Provider getProvider() {
    return provider;
  }

  public void setProvider(Provider provider) {
    this.provider = provider;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public User getCreator() {
    return creator;
  }

  public void setCreator(User creator) {
    this.creator = creator;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public enum Event {
    LOGIN, VIEW_PATIENT, REGISTER_PATIENT
  }

}
