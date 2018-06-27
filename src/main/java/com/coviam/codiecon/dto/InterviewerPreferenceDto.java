package com.coviam.codiecon.dto;

public class InterviewerPreferenceDto {

    int day;
    int slot;

    public InterviewerPreferenceDto(int day, int slot) {
        this.day = day;
        this.slot = slot;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }
}
