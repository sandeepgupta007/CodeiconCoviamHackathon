package com.coviam.codiecon.dto;


import java.util.Objects;

public class CandidatePreferenceDto {

    Integer day;
    String preference;

    public CandidatePreferenceDto() {
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CandidatePreferenceDto that = (CandidatePreferenceDto) o;
        return Objects.equals(day, that.day) &&
                Objects.equals(preference, that.preference);
    }

    @Override
    public int hashCode() {

        return Objects.hash(day, preference);
    }

    @Override
    public String toString() {
        return "CandidatePreferenceDto{" +
                "day=" + day +
                ", preference='" + preference + '\'' +
                '}';
    }
}
