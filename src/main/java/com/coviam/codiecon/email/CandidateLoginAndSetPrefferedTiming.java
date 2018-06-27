package com.coviam.codiecon.email;

public class CandidateLoginAndSetPrefferedTiming {
    private String generatedPassword;
    private String RedirectLink;

    public CandidateLoginAndSetPrefferedTiming() {
    }

    public CandidateLoginAndSetPrefferedTiming(String generatedPassword, String redirectLink) {
        this.generatedPassword = generatedPassword;
        RedirectLink = redirectLink;
    }

    public String getGeneratedPassword() {
        return generatedPassword;
    }

    public void setGeneratedPassword(String generatedPassword) {
        this.generatedPassword = generatedPassword;
    }

    public String getRedirectLink() {
        return RedirectLink;
    }

    public void setRedirectLink(String redirectLink) {
        RedirectLink = redirectLink;
    }
}
