package com.bank.profile.controller;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.entity.AuditEntity;
import com.bank.profile.service.impl.AuditServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@DisplayName(value = "Тест контроллера AuditController ")
@SpringBootTest
class AuditControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuditServiceImpl service;

    @InjectMocks
    private AuditController auditController;

    private final AuditEntity auditEntity = new AuditEntity();

    {
        auditEntity.setId(1L);
        auditEntity.setEntityType("User");
    }

    private final AuditDto userRobDto = new AuditDto();

    {
        userRobDto.setId(1L);
        userRobDto.setEntityType("User");
    }

    private final Long id = 1L;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(auditController).build();
    }
    @DisplayName(value = "поиск по id, позитивный сценарий")
    @Test
    public void read_FindById_PositiveTest() throws Exception {

        Mockito.when(service.findById(id)).thenReturn(userRobDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/audit/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(userRobDto)));
    }
    @DisplayName(value = "поиск по несуществующему id, негативный сценарий")
    @Test
    public void read_NotFound_NegativeTest() throws Exception {

        Mockito.when(service.findById(id)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/audit/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

}