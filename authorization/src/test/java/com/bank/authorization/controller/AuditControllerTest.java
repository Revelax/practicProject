package com.bank.authorization.controller;

import collect_entity.AuditsDto;
import com.bank.authorization.dto.AuditDto;
import com.bank.authorization.service.AuditServiceImpl;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.ObjectUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuditController.class)
@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
class AuditControllerTest {

    private final Gson gson = new Gson();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuditServiceImpl auditService;

    @InjectMocks
    private AuditController auditController;

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L})
    @DisplayName("чтение  по id, положительный сценарий")
    void readPositiveTest(Long id) throws Exception {
        doReturn(AuditsDto.findAudit(id))
                .when(auditService).findById(id);

        String responseBody = mockMvc.perform(get(String.format("/audit/%d", id)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        boolean answer = ObjectUtils.equals(
                gson.fromJson(responseBody, AuditDto.class),
                AuditsDto.findAudit(id)
        );

        assertThat(answer).isTrue();
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readIfNotExistNegativeTest() throws Exception {
        doThrow(EntityNotFoundException.class)
                .when(auditService).findById(1L);

        mockMvc.perform(get("/audit/1"))
                .andExpect(status().isNotFound());
    }
}
