package com.utfpr.concessionaria.modelException.error;

public class ErrorMessage {
    //#region atributes
    private String message;

    private String title;

    private Integer status;
    //#endregion

    //#region GettersAndSetters
    public String getMessage() {
        return message;
    }

    public ErrorMessage(String message, String title, Integer status) {
        this.message = message;
        this.title = title;
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    //#endregion

}