package com.coviam.codiecon.email;

public class CandidateInterviewDetail {
    private String interviewDate;
    private String startTime;
    private String endTime;

    public CandidateInterviewDetail(String interviewDate, String startTime, String endTime) {
        this.interviewDate = interviewDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public CandidateInterviewDetail() {

    }

    public String getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(String interviewDate) {
        this.interviewDate = interviewDate;
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
