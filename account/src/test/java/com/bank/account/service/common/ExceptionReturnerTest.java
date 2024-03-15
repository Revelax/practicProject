package com.bank.account.service.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionReturnerTest {

    @Test
    @DisplayName("получение EntityNotFoundException")
    void getEntityNotFoundExceptionTest() {
        String msg = "message";
        ExceptionReturner exceptionReturner = new ExceptionReturner();
        EntityNotFoundException exception = exceptionReturner.getEntityNotFoundException(msg);
        assertThat(exception).isInstanceOf(EntityNotFoundException.class);
        assertThat(exception.getMessage()).isEqualTo(msg);
    }
}