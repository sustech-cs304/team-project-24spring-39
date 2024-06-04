package com.example.cs304.response;
public class Response<T> {
    private int status;
    private String message;
    private T data;

    public Response(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <E> Response<E> success(E data) {
        if (data == null) {
            return new Response<>(200, "successful", null);
        } else {
            return new Response<>(200, "successful", data);
        }
    }

    public static <E> Response<E> fail(String message) {
        return new Response<>(400, message, null);
    }

    public static <E> Response<E> fail(int status, String message) {
        return new Response<>(status, message, null);
    }
    public Response() {}

    // getters and setters

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}