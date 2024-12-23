package com.crio.videoRental.dto;

import org.springframework.http.HttpStatus;

public class ResponseDto<T> {
    private T data; // Use a generic type for the data field
    private String message;
    private HttpStatus code;

    public ResponseDto(T data, String message, HttpStatus code) {
        this.data = data;
        this.message = message;
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getCode(){
        return code;
    }

    public void setCode(HttpStatus code){
        this.code = code;
    }
}
