package com.coviam.codiecon.dto;

public class ResponseDto<T>{
    private Boolean status;
    private String message;
    T response;

    public ResponseDto(Boolean status, String message, T response) {
        this.status = status;
        this.message = message;
        this.response = response;
    }

    public ResponseDto(T response) {
        this.response = response;
        this.status = true;
        this.message = "Success";
    }

    @Override
    public String toString() {
        return "ResponseDto{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", response=" + response +
                '}';
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

}
