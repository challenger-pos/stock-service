package com.fiap.core.domain;

import com.fiap.core.exception.BusinessRuleException;
import com.fiap.core.exception.enums.ErrorCodeEnum;

public class Stock {

    private int quantity;
    private int reserved;

    public Stock(int quantity, int reserved) {
        this.quantity = quantity;
        this.reserved = reserved;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getReserved() {
        return reserved;
    }

    public int getAvailable() {
        return quantity - reserved;
    }

    public void reserve(int qty) throws BusinessRuleException {
        if (qty <= 0) {
            throw new BusinessRuleException(ErrorCodeEnum.STOCK0004.getMessage(), ErrorCodeEnum.STOCK0004.getCode());
        }
        if (getAvailable() < qty) {
            throw new BusinessRuleException(ErrorCodeEnum.STOCK0002.getMessage(), ErrorCodeEnum.STOCK0002.getCode());
        }
        reserved += qty;
    }

    public void release(int qty) throws BusinessRuleException {
        if (qty <= 0 || qty > reserved) {
            throw new BusinessRuleException(ErrorCodeEnum.STOCK0003.getMessage(), ErrorCodeEnum.STOCK0003.getCode());
        }
        reserved -= qty;
    }

    public void effective(int qty) throws BusinessRuleException {

        if (qty <= 0 || qty > reserved) {
            throw new BusinessRuleException(ErrorCodeEnum.STOCK0003.getMessage(), ErrorCodeEnum.STOCK0003.getCode());
        }

        reserved -= qty;
        quantity -= qty;

        if (quantity < 0) {
            throw new BusinessRuleException(ErrorCodeEnum.STOCK0005.getMessage(), ErrorCodeEnum.STOCK0005.getCode());
        }
    }

    public static Stock of(int stockQuantity, int reservedStock) throws BusinessRuleException {
        if (stockQuantity < 0 || reservedStock < 0) {
            throw new BusinessRuleException("Stock values cannot be negative.", "STOCK-001");
        }
        return new Stock(stockQuantity, reservedStock);
    }
}

