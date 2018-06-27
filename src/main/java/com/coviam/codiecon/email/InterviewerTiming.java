package com.coviam.codiecon.email;

public class InterviewerTiming {

    private String RedirectLink;
    private String password;

    public String getPassword() {
        return password;
    }

    public InterviewerTiming(String redirectLink, String password) {
        RedirectLink = redirectLink;
        this.password = password;
    }

    public InterviewerTiming() {
    }

    public InterviewerTiming(String redirectLink) {
        RedirectLink = redirectLink;
    }

    public String getRedirectLink() {
        return RedirectLink;
    }

    public void setRedirectLink(String redirectLink) {
        RedirectLink = redirectLink;
    }
}
