package com.coviam.codiecon.dto;

import java.util.List;
import java.util.Objects;

public class CandidateAdminObject {
    private String email;
    private List<CandidateDto> candidateDtoList;

    public CandidateAdminObject() {
    }

    public CandidateAdminObject(String email, List<CandidateDto> candidateDtoList) {
        this.email = email;
        this.candidateDtoList = candidateDtoList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<CandidateDto> getCandidateDtoList() {
        return candidateDtoList;
    }

    public void setCandidateDtoList(List<CandidateDto> candidateDtoList) {
        this.candidateDtoList = candidateDtoList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CandidateAdminObject that = (CandidateAdminObject) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(candidateDtoList, that.candidateDtoList);
    }

    @Override
    public int hashCode() {

        return Objects.hash(email, candidateDtoList);
    }

    @Override
    public String toString() {
        return "CandidateAdminObject{" +
                "email='" + email + '\'' +
                ", candidateDtoList=" + candidateDtoList +
                '}';
    }
}
