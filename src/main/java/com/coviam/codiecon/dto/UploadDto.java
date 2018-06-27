package com.coviam.codiecon.dto;

import com.opencsv.bean.CsvBindByName;

public class UploadDto {

    @CsvBindByName
    private String name;

    @CsvBindByName
    private String email;

    public UploadDto() {
    }

    public UploadDto(String name, String email) {
        this.name = name;
        this.email = email;
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

    @Override
    public String toString() {
        return "UploadDto{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
