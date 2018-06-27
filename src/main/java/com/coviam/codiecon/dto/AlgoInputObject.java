package com.coviam.codiecon.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class AlgoInputObject {

    @JsonFormat(pattern = "dd-MM-yyyy")
    Date startDate;
    Integer numberOfDays;
    Integer interviewDuration;
    List<CandidateDto> candidateDtoList;
    List<InterviewerDto> interviewerDtoList;

    public AlgoInputObject() {
    }

    public AlgoInputObject(Date startDate, Integer numberOfDays, Integer interviewDuration, List<CandidateDto> candidateDtoList, List<InterviewerDto> interviewerDtoList) {
        this.startDate = startDate;
        this.numberOfDays = numberOfDays;
        this.interviewDuration = interviewDuration;
        this.candidateDtoList = candidateDtoList;
        this.interviewerDtoList = interviewerDtoList;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public Integer getInterviewDuration() {
        return interviewDuration;
    }

    public void setInterviewDuration(Integer interviewDuration) {
        this.interviewDuration = interviewDuration;
    }

    public List<CandidateDto> getCandidateDtoList() {
        return candidateDtoList;
    }

    public void setCandidateDtoList(List<CandidateDto> candidateDtoList) {
        this.candidateDtoList = candidateDtoList;
    }

    public List<InterviewerDto> getInterviewerDtoList() {
        return interviewerDtoList;
    }

    public void setInterviewerDtoList(List<InterviewerDto> interviewerDtoList) {
        this.interviewerDtoList = interviewerDtoList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlgoInputObject that = (AlgoInputObject) o;
        return Objects.equals(startDate, that.startDate) &&
                Objects.equals(numberOfDays, that.numberOfDays) &&
                Objects.equals(interviewDuration, that.interviewDuration) &&
                Objects.equals(candidateDtoList, that.candidateDtoList) &&
                Objects.equals(interviewerDtoList, that.interviewerDtoList);
    }

    @Override
    public int hashCode() {

        return Objects.hash(startDate, numberOfDays, interviewDuration, candidateDtoList, interviewerDtoList);
    }

    @Override
    public String toString() {
        return "AlgoInputObject{" +
                "startDate=" + startDate +
                ", numberOfDays=" + numberOfDays +
                ", interviewDuration=" + interviewDuration +
                ", candidateDtoList=" + candidateDtoList +
                ", interviewerDtoList=" + interviewerDtoList +
                '}';
    }
}
