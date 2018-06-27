package com.coviam.codiecon.model;

import com.coviam.codiecon.dto.AlgoInputObject;
import com.coviam.codiecon.dto.AlgoOutputObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Document(collection = "admin")
public class Admin {
    @Id
    String email;
    String password;
    String name;
    List<AlgoInputObject> algoInputObjectList;
    List<AlgoOutputObject> algoOutputObjectList;

    public Admin() {
    }

    public Admin(String email, String password, String name, List<AlgoInputObject> algoInputObjectList, List<AlgoOutputObject> algoOutputObjectList) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.algoInputObjectList = algoInputObjectList;
        this.algoOutputObjectList = algoOutputObjectList;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AlgoInputObject> getAlgoInputObjectList() {
        return algoInputObjectList;
    }

    public void setAlgoInputObjectList(List<AlgoInputObject> algoInputObjectList) {
        this.algoInputObjectList = algoInputObjectList;
    }

    public List<AlgoOutputObject> getAlgoOutputObjectList() {
        return algoOutputObjectList;
    }

    public void setAlgoOutputObjectList(List<AlgoOutputObject> algoOutputObjectList) {
        this.algoOutputObjectList = algoOutputObjectList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return Objects.equals(email, admin.email) &&
                Objects.equals(password, admin.password) &&
                Objects.equals(name, admin.name) &&
                Objects.equals(algoInputObjectList, admin.algoInputObjectList) &&
                Objects.equals(algoOutputObjectList, admin.algoOutputObjectList);
    }

    @Override
    public int hashCode() {

        return Objects.hash(email, password, name, algoInputObjectList, algoOutputObjectList);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", algoInputObjectList=" + algoInputObjectList +
                ", algoOutputObjectList=" + algoOutputObjectList +
                '}';
    }
}
