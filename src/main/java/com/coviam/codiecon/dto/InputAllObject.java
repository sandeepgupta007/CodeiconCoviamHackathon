package com.coviam.codiecon.dto;

import java.util.List;
import java.util.Objects;

public class InputAllObject {
    private String email;
    private AlgoInputObject algoInputObject;

    public InputAllObject() {
    }

    public InputAllObject(String email, AlgoInputObject algoInputObject) {
        this.email = email;
        this.algoInputObject = algoInputObject;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AlgoInputObject getAlgoInputObject() {
        return algoInputObject;
    }

    public void setAlgoInputObject(AlgoInputObject algoInputObject) {
        this.algoInputObject = algoInputObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InputAllObject that = (InputAllObject) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(algoInputObject, that.algoInputObject);
    }

    @Override
    public int hashCode() {

        return Objects.hash(email, algoInputObject);
    }

    @Override
    public String toString() {
        return "InputAllObject{" +
                "email='" + email + '\'' +
                ", algoInputObject=" + algoInputObject +
                '}';
    }
}
