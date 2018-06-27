package com.coviam.codiecon.email;

public class InterviewerCandidateMapping {
    private String candidateEmail;
    private String candidateName;
    private String dateOfInterview;
    private String startTime;
    private String endTime;

    public InterviewerCandidateMapping() {
    }

    public InterviewerCandidateMapping(String candidateEmail, String candidateName, String dateOfInterview, String startTime, String endTime) {
        this.candidateEmail = candidateEmail;
        this.candidateName = candidateName;
        this.dateOfInterview = dateOfInterview;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getCandidateEmail() {
        return candidateEmail;
    }

    public void setCandidateEmail(String candidateEmail) {
        this.candidateEmail = candidateEmail;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getDateOfInterview() {
        return dateOfInterview;
    }

    public void setDateOfInterview(String dateOfInterview) {
        this.dateOfInterview = dateOfInterview;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
