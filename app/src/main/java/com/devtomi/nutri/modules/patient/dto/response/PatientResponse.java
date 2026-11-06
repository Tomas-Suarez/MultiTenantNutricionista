package com.devtomi.nutri.modules.patient.dto.response;

import java.util.List;

public class PatientResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String gender;
    private Double initialWeight;
    private Double heightCm;
    private String invitationStatus;
    private String userId;
    private GoalResponse goal;
    private List<PathologyResponse> pathologyList;
    private String lastWeighingDate;
    private Double bmi;
    private boolean active;

    public PatientResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Double getInitialWeight() {
        return initialWeight;
    }

    public void setInitialWeight(Double initialWeight) {
        this.initialWeight = initialWeight;
    }

    public Double getHeightCm() {
        return heightCm;
    }

    public void setHeightCm(Double heightCm) {
        this.heightCm = heightCm;
    }

    public String getInvitationStatus() {
        return invitationStatus;
    }

    public void setInvitationStatus(String invitationStatus) {
        this.invitationStatus = invitationStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public GoalResponse getGoal() {
        return goal;
    }

    public void setGoal(GoalResponse goal) {
        this.goal = goal;
    }

    public List<PathologyResponse> getPathologyList() {
        return pathologyList;
    }

    public void setPathologyList(List<PathologyResponse> pathologyList) {
        this.pathologyList = pathologyList;
    }

    public String getLastWeighingDate() {
        return lastWeighingDate;
    }

    public void setLastWeighingDate(String lastWeighingDate) {
        this.lastWeighingDate = lastWeighingDate;
    }

    public Double getBmi() {
        return bmi;
    }

    public void setBmi(Double bmi) {
        this.bmi = bmi;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
