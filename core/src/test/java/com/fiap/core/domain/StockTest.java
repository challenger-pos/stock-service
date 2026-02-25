package com.fiap.core.domain;

import com.fiap.core.exception.BusinessRuleException;
import com.fiap.core.exception.enums.ErrorCodeEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockTest {

    @Test
    void shouldCreateStockWithValidValues() throws BusinessRuleException {
        Stock stock = Stock.of(10, 2);
        assertEquals(10, stock.getQuantity());
        assertEquals(2, stock.getReserved());
        assertEquals(8, stock.getAvailable());
    }

    @Test
    void shouldThrowExceptionWhenStockValuesNegative() {
        BusinessRuleException ex = assertThrows(
                BusinessRuleException.class,
                () -> Stock.of(-1, 0)
        );
        assertEquals("STOCK-001", ex.getCode());
    }

    @Test
    void shouldThrowExceptionWhenReservedNegative() {
        BusinessRuleException ex = assertThrows(
                BusinessRuleException.class,
                () -> Stock.of(10, -1)
        );
        assertEquals("STOCK-001", ex.getCode());
    }

    @Test
    void shouldReserveValidQuantity() throws BusinessRuleException {
        Stock stock = Stock.of(10, 2);

        stock.reserve(3);

        assertEquals(5, stock.getAvailable());
        assertEquals(5, stock.getReserved());
    }

    @Test
    void shouldThrowExceptionWhenReserveZeroOrNegative() throws BusinessRuleException {
        Stock stock = Stock.of(10, 2);

        BusinessRuleException ex = assertThrows(
                BusinessRuleException.class,
                () -> stock.reserve(0)
        );
        assertEquals(ErrorCodeEnum.STOCK0004.getCode(), ex.getCode());
    }

    @Test
    void shouldThrowExceptionWhenReserveMoreThanAvailable() throws BusinessRuleException {
        Stock stock = Stock.of(5, 1);

        BusinessRuleException ex = assertThrows(
                BusinessRuleException.class,
                () -> stock.reserve(10)
        );

        assertEquals(ErrorCodeEnum.STOCK0002.getCode(), ex.getCode());
    }


    @Test
    void shouldReleaseReservedStock() throws BusinessRuleException {
        Stock stock = Stock.of(10, 4);

        stock.release(3);

        assertEquals(1, stock.getReserved());
        assertEquals(9, stock.getAvailable());
    }

    @Test
    void shouldThrowExceptionWhenReleaseZeroNegativeOrGreaterThanReserved() throws BusinessRuleException {
        Stock stock = Stock.of(10, 4);

        BusinessRuleException ex1 = assertThrows(
                BusinessRuleException.class,
                () -> stock.release(0)
        );
        assertEquals(ErrorCodeEnum.STOCK0003.getCode(), ex1.getCode());

        BusinessRuleException ex2 = assertThrows(
                BusinessRuleException.class,
                () -> stock.release(-1)
        );
        assertEquals(ErrorCodeEnum.STOCK0003.getCode(), ex2.getCode());

        BusinessRuleException ex3 = assertThrows(
                BusinessRuleException.class,
                () -> stock.release(10)
        );
        assertEquals(ErrorCodeEnum.STOCK0003.getCode(), ex3.getCode());
    }

    @Test
    void shouldApplyEffectiveReservation() throws BusinessRuleException {
        Stock stock = Stock.of(10, 5);

        stock.effective(3);

        assertEquals(2, stock.getReserved());
        assertEquals(7, stock.getQuantity());
        assertEquals(5, stock.getAvailable());
    }

    @Test
    void shouldThrowExceptionWhenEffectiveZeroNegativeOrGreaterThanReserved() throws BusinessRuleException {
        Stock stock = Stock.of(10, 5);

        BusinessRuleException ex1 = assertThrows(
                BusinessRuleException.class,
                () -> stock.effective(0)
        );
        assertEquals(ErrorCodeEnum.STOCK0003.getCode(), ex1.getCode());

        BusinessRuleException ex2 = assertThrows(
                BusinessRuleException.class,
                () -> stock.effective(6)
        );
        assertEquals(ErrorCodeEnum.STOCK0003.getCode(), ex2.getCode());
    }

    @Test
    void shouldThrowExceptionWhenEffectiveMakesQuantityNegative() throws BusinessRuleException {
        Stock stock = Stock.of(1, 1);

        BusinessRuleException ex = assertThrows(
                BusinessRuleException.class,
                () -> stock.effective(1_000)
        );

        assertEquals(ErrorCodeEnum.STOCK0003.getCode(), ex.getCode());
    }
}
