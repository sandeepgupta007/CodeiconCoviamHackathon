package com.coviam.codiecon.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

/**
 * @author: Sandeep Gupta
 * */

@Document(collection = "interviewer")
public class Interviewer {

    @Id
    String email;
    String name;
    String password;
    List<String> availablityOfInterviewer;

    public Interviewer() {
    }

    public Interviewer(String email, String name, String password, List<String> availablityOfInterviewer) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.availablityOfInterviewer = availablityOfInterviewer;
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

    public List<String> getAvailablityOfInterviewer() {
        return availablityOfInterviewer;
    }

    public void setAvailablityOfInterviewer(List<String> availablityOfInterviewer) {
        this.availablityOfInterviewer = availablityOfInterviewer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interviewer that = (Interviewer) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(name, that.name) &&
                Objects.equals(password, that.password) &&
                Objects.equals(availablityOfInterviewer, that.availablityOfInterviewer);
    }

    @Override
    public int hashCode() {

        return Objects.hash(email, name, password, availablityOfInterviewer);
    }

    @Override
    public String toString() {
        return "Interviewer{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", availablityOfInterviewer=" + availablityOfInterviewer +
                '}';
    }
}
