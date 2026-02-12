package com.fiap.core.exception.enums;

public enum ErrorCodeEnum {

    STOCK0001("Part not found", "STOCK-0001"),
    STOCK0002("Not enough stock", "STOCK-0002"),
    STOCK0003("Invalid quantity", "STOCK-0003"),
    STOCK0004("Quantity must be greater than zero", "STOCK-0004"),
    ;

    private String message;
    private String code;

    ErrorCodeEnum(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
