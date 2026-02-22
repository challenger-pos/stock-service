package com.fiap.core.domain;

import com.fiap.core.exception.BusinessRuleException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void shouldCreateMoneyWithValidValue() throws BusinessRuleException {
        Money money = Money.of(BigDecimal.TEN);
        assertEquals(BigDecimal.TEN, money.getValue());
    }

    @Test
    void shouldThrowExceptionWhenValueIsNull() {
        BusinessRuleException ex = assertThrows(
                BusinessRuleException.class,
                () -> Money.of(null)
        );
        assertEquals("MONEY-001", ex.getCode());
    }

    @Test
    void shouldThrowExceptionWhenValueIsNegative() {
        BusinessRuleException ex = assertThrows(
                BusinessRuleException.class,
                () -> Money.of(BigDecimal.valueOf(-1))
        );
        assertEquals("MONEY-001", ex.getCode());
    }

    @Test
    void shouldRespectEqualsAndHashCode() throws BusinessRuleException {
        Money m1 = Money.of(BigDecimal.ONE);
        Money m2 = Money.of(BigDecimal.ONE);

        assertEquals(m1, m2);
        assertEquals(m1.hashCode(), m2.hashCode());
    }

    @Test
    void shouldNotEqualDifferentValues() throws BusinessRuleException {
        Money m1 = Money.of(BigDecimal.ONE);
        Money m2 = Money.of(BigDecimal.TEN);

        assertNotEquals(m1, m2);
    }
}
