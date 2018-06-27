package com.coviam.codiecon.dto;

import java.util.List;
import java.util.Objects;

public class InterviewerDto {
    String name;
    String email;
    String password;
    List<String> availablityOfInterviewer;

    public InterviewerDto() {
    }

    public InterviewerDto(String name, String email, String password, List<String> availablityOfInterviewer) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.availablityOfInterviewer = availablityOfInterviewer;
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
        InterviewerDto that = (InterviewerDto) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password) &&
                Objects.equals(availablityOfInterviewer, that.availablityOfInterviewer);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, email, password, availablityOfInterviewer);
    }

    @Override
    public String toString() {
        return "InterviewerDto{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", availablityOfInterviewer=" + availablityOfInterviewer +
                '}';
    }
}
