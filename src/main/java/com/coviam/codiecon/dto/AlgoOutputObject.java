package com.coviam.codiecon.dto;

import java.util.List;
import java.util.Objects;

public class AlgoOutputObject {

    List<InterviewCandidateMapped> interviewCandidateMappedList;

    public AlgoOutputObject() {
    }

    public AlgoOutputObject(List<InterviewCandidateMapped> interviewCandidateMappedList) {
        this.interviewCandidateMappedList = interviewCandidateMappedList;
    }

    public List<InterviewCandidateMapped> getInterviewCandidateMappedList() {
        return interviewCandidateMappedList;
    }

    public void setInterviewCandidateMappedList(List<InterviewCandidateMapped> interviewCandidateMappedList) {
        this.interviewCandidateMappedList = interviewCandidateMappedList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlgoOutputObject that = (AlgoOutputObject) o;
        return Objects.equals(interviewCandidateMappedList, that.interviewCandidateMappedList);
    }

    @Override
    public int hashCode() {

        return Objects.hash(interviewCandidateMappedList);
    }

    @Override
    public String toString() {
        return "AlgoOutputObject{" +
                "interviewCandidateMappedList=" + interviewCandidateMappedList +
                '}';
    }
}
