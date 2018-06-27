package com.coviam.codiecon.model;


import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "candidate-interviewer-map")
public class CandidateInterviewerMap {

    private String candidate;

    private String interviewer;

    private int timeSlot;

    private int day;

    public CandidateInterviewerMap(String candidate, String interviewer, int timeSlot, int day) {
        this.candidate = candidate;
        this.interviewer = interviewer;
        this.timeSlot = timeSlot;
        this.day = day;
    }

    public String getCandidate() {
        return candidate;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }

    public String getInterviewer() {
        return interviewer;
    }

    public void setInterviewer(String interviewer) {
        this.interviewer = interviewer;
    }

    public int getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(int timeSlot) {
        this.timeSlot = timeSlot;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
