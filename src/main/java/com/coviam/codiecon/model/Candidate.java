package com.coviam.codiecon.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;


/**
 * @author: Sandeep Gupta
 * */

@Document(collection = "candidate")
public class Candidate {
    @Id
    String email;
    String name;
    String password;
    Integer day;
    String preference;

    public Candidate() {
    }

    public Candidate(String email, String name, String password, Integer day, String preference) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.day = day;
        this.preference = preference;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        Candidate candidate = (Candidate) o;
        return Objects.equals(email, candidate.email) &&
                Objects.equals(name, candidate.name) &&
                Objects.equals(password, candidate.password) &&
                Objects.equals(day, candidate.day) &&
                Objects.equals(preference, candidate.preference);
    }

    @Override
    public int hashCode() {

        return Objects.hash(email, name, password, day, preference);
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", day=" + day +
                ", preference='" + preference + '\'' +
                '}';
    }
}
