package com.coviam.codiecon.email;

public class MailElements {

    String email;
    String SubjectOfMail;

    public MailElements() {
    }

    public MailElements(String email, String subjectOfMail) {
        this.email = email;
        SubjectOfMail = subjectOfMail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubjectOfMail() {
        return SubjectOfMail;
    }

    public void setSubjectOfMail(String subjectOfMail) {
        SubjectOfMail = subjectOfMail;
    }

}
