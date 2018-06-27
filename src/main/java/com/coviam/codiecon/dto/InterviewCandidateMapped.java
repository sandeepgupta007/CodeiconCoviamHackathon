package com.coviam.codiecon.dto;

import java.util.Objects;

public class InterviewCandidateMapped {

    private String candidate;
    private String interviewer;
    private Integer timeSlot;
    private Integer day;

    public InterviewCandidateMapped() {
    }

    public InterviewCandidateMapped(String candidate, String interviewer, Integer timeSlot, Integer day) {
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

    public Integer getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(Integer timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterviewCandidateMapped that = (InterviewCandidateMapped) o;
        return Objects.equals(candidate, that.candidate) &&
                Objects.equals(interviewer, that.interviewer) &&
                Objects.equals(timeSlot, that.timeSlot) &&
                Objects.equals(day, that.day);
    }

    @Override
    public int hashCode() {

        return Objects.hash(candidate, interviewer, timeSlot, day);
    }

    @Override
    public String toString() {
        return "InterviewCandidateMapped{" +
                "candidate='" + candidate + '\'' +
                ", interviewer='" + interviewer + '\'' +
                ", timeSlot=" + timeSlot +
                ", day=" + day +
                '}';
    }
}
