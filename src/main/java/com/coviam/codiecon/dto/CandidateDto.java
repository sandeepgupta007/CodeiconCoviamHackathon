package com.coviam.codiecon.dto;


import com.coviam.codiecon.model.Interviewer;

import java.util.Objects;

public class CandidateDto {

    String name;
    String email;
    String password;
    Integer day;
    String preference;

    public CandidateDto() {
    }

    public CandidateDto(String name, String email, String password, Integer day, String preference) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.day = day;
        this.preference = preference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        CandidateDto that = (CandidateDto) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password) &&
                Objects.equals(day, that.day) &&
                Objects.equals(preference, that.preference);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, email, password, day, preference);
    }

    @Override
    public String toString() {
        return "CandidateDto{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", day=" + day +
                ", preference='" + preference + '\'' +
                '}';
    }
}
