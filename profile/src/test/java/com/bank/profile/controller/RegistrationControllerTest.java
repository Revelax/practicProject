package com.bank.profile.controller;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.service.impl.RegistrationServiceImp;
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

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@DisplayName(value = "Тест контроллера RegistrationController ")
@SpringBootTest
class RegistrationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RegistrationServiceImp service;

    @InjectMocks
    private RegistrationController registrationController;

    private final RegistrationEntity userRob = new RegistrationEntity();

    {
        userRob.setId(1L);
        userRob.setIndex(124L);
    }

    private final RegistrationEntity userAlice = new RegistrationEntity();

    {
        userAlice.setId(2L);
        userAlice.setIndex(123L);
    }

    private final RegistrationDto userRobDto = new RegistrationDto();

    {
        userRobDto.setId(1L);
        userRobDto.setIndex(124L);
    }

    private final RegistrationDto userAliceDto = new RegistrationDto();

    {
        userAliceDto.setId(2L);
        userAliceDto.setIndex(123L);
    }

    private final Long id = 1L;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
    }
    @DisplayName(value = "поиск по id, позитивный сценарий")
    @Test
    public void read_FindById_PositiveTest() throws Exception {

        Mockito.when(service.findById(id)).thenReturn(userRobDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/registration/read/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(userRobDto)));
    }
    @DisplayName(value = "поиск по несуществующему id, негативный сценарий")
    @Test
    public void read_NotFound_NegativeTest() throws Exception {

        Mockito.when(service.findById(id)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/registration/read/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }
    @DisplayName(value = "добавление новой записи, позитивный сценарий")
    @Test
    public void create_Save_PositiveTest() throws Exception {
        Mockito.when(service.save(userAliceDto)).thenReturn(userAliceDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/registration/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userAliceDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(userAliceDto)));
    }
    @DisplayName(value = "добавление новой записи, негативный сценарий")
    @Test
    public void create_FailedSave_NegativeTest() throws Exception {
        Mockito.when(service.save(userAliceDto)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/registration/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userAliceDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }
    @DisplayName(value = "обновление записи, позитивный сценарий")
    @Test
    public void update_Update_PositiveTest() throws Exception {
        Long id = 1L;

        Mockito.when(service.update(id, userRobDto)).thenReturn(userRobDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/registration/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRobDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(userRobDto)));
    }
    @DisplayName(value = "обновление записи, негативный сценарий")
    @Test
    public void update_FailedUpdate_NegativeTest() throws Exception {

        Mockito.when(service.update(id, userRobDto)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/registration/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRobDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }
    @DisplayName(value = "получение всех записей, позитивный сценарий")
    @Test
    public void readAll_FindAllById_PositiveTest() throws Exception {
        Mockito.when(service.findAllById(Arrays.asList(1L, 2L)))
                .thenReturn(Arrays.asList(userRobDto, userAliceDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/registration/read/all")
                        .param("ids", "1", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @DisplayName(value = "получение всех записей, негативный сценарий")
    @Test
    public void readAll_FindAllByIdFailed_NegativeTest() throws Exception {
        Mockito.when(service.findAllById(Arrays.asList(1L, 2L)))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/registration/read/all")
                        .param("ids", "1", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));

    }
}