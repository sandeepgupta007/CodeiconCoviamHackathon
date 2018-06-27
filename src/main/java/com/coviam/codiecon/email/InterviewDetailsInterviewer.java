package com.coviam.codiecon.email;

import java.util.List;

public class InterviewDetailsInterviewer {
    private List<InterviewerCandidateMapping> interviewDetails;

    public InterviewDetailsInterviewer() {
    }

    public InterviewDetailsInterviewer(List<InterviewerCandidateMapping> interviewDetails) {
        this.interviewDetails = interviewDetails;
    }

    public List<InterviewerCandidateMapping> getInterviewDetails() {
        return interviewDetails;
    }

    public void setInterviewDetails(List<InterviewerCandidateMapping> interviewDetails) {
        this.interviewDetails = interviewDetails;
    }

    public void addNewCandidate(InterviewerCandidateMapping interviewerCandidateMapping)
    {
        interviewDetails.add(interviewerCandidateMapping);
    }
}
