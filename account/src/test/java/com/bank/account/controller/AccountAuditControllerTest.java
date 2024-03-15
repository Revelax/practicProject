package com.bank.account.controller;

import com.bank.account.dto.AuditDto;
import com.bank.account.service.AccountAuditService;
import com.bank.common.handler.GlobalRestExceptionHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountAuditController.class)
@ExtendWith(MockitoExtension.class)
@Import(GlobalRestExceptionHandler.class)
class AccountAuditControllerTest {

    private static final AuditDto AUDIT_DTO = new AuditDto(
            1L, "arg1", "arg2", "arg3", "arg4",
            Timestamp.valueOf("2022-03-01 12:00:00.000"),
            Timestamp.valueOf("2024-03-01 12:00:00.000"),
            "args5",
            "args6"
    );

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountAuditService service;

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readPositiveTest() throws Exception {
        doReturn(AUDIT_DTO).when(service).findById(1L);

        mockMvc.perform(get("/audit/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(9)))
                .andExpect(jsonPath("$.id", is(Math.toIntExact(AUDIT_DTO.getId()))))
                .andExpect(jsonPath("$.entityType", is(AUDIT_DTO.getEntityType())))
                .andExpect(jsonPath("$.operationType", is(AUDIT_DTO.getOperationType())))
                .andExpect(jsonPath("$.createdBy", is(AUDIT_DTO.getCreatedBy())))
                .andExpect(jsonPath("$.modifiedBy", is(AUDIT_DTO.getModifiedBy())))
                .andExpect(jsonPath("$.createdAt", notNullValue()))
                .andExpect(jsonPath("$.modifiedAt", notNullValue()))
                .andExpect(jsonPath("$.newEntityJson", is(AUDIT_DTO.getNewEntityJson())))
                .andExpect(jsonPath("$.entityJson", is(AUDIT_DTO.getEntityJson())));

        verify(service, times(1)).findById(1L);
    }

    @Test
    @DisplayName("чтение по id, передан неверный id, негативный сценарий")
    void readWithInvalidIdNegativeTest() throws Exception {
        doThrow(EntityNotFoundException.class).when(service).findById(1L);

        mockMvc.perform(get("/audit/{id}", 1L))
                .andExpect(status().isNotFound());
    }
}
