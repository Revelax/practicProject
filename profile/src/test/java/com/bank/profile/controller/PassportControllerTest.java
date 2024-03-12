package com.bank.profile.controller;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.service.impl.PassportServiceImp;
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

@DisplayName(value = "Тест контроллера PassportController ")
@SpringBootTest
class PassportControllerTest {

    private MockMvc mockMvc;
    @Mock
    private PassportServiceImp service;
    @InjectMocks
    private PassportController passportController;

    private final PassportDto userRobDto = new PassportDto();

    {
        userRobDto.setId(1L);
        userRobDto.setFirstName("Rob");
    }

    private final PassportDto userAliceDto = new PassportDto();

    {
        userAliceDto.setId(2L);
        userAliceDto.setFirstName("Alice");
    }

    private final Long id = 1L;


    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(passportController).build();
    }

    @Test
    @DisplayName(value = "чтение по id, позитивный сценарий")
    public void readReadByIdPositiveTest() throws Exception {

        Mockito.when(service.findById(id)).thenReturn(userRobDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/passport/read/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json(objectMapper.writeValueAsString(userRobDto)));
    }

    @Test
    @DisplayName(value = "чтение по несуществующему id, негативный сценарий")
    public void readNotFoundNegativeTest() throws Exception {

        Mockito.when(service.findById(id)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/passport/read/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    @DisplayName(value = "добавление новой записи, позитивный сценарий")
    public void createSavePositiveTest() throws Exception {
        Mockito.when(service.save(userAliceDto)).thenReturn(userAliceDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/passport/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userAliceDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json(objectMapper.writeValueAsString(userAliceDto)));
    }

    @Test
    @DisplayName(value = "добавление новой записи, негативный сценарий")
    public void createFailedSaveNegativeTest() throws Exception {
        Mockito.when(service.save(userAliceDto)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/passport/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userAliceDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    @DisplayName(value = "обновление записи, позитивный сценарий")
    public void updateUpdatePositiveTest() throws Exception {

        Mockito.when(service.update(id, userRobDto)).thenReturn(userRobDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/passport/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRobDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json(objectMapper.writeValueAsString(userRobDto)));
    }

    @Test
    @DisplayName(value = "обновление записи, негативный сценарий")
    public void updateFailedUpdateNegativeTest() throws Exception {

        Mockito.when(service.update(id, userRobDto)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/passport/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRobDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    @DisplayName(value = "чтение записей по списку id, позитивный сценарий")
    public void readAllReadAllByIdPositiveTest() throws Exception {
        Mockito.when(service.findAllById(Arrays.asList(1L, 2L)))
                .thenReturn(Arrays.asList(userRobDto, userAliceDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/passport/read/all")
                        .param("ids", "1", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName(value = "чтение записей по списку id, негативный сценарий")
    public void readAllReadAllByIdFailedNegativeTest() throws Exception {
        Mockito.when(service.findAllById(Arrays.asList(1L, 2L)))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/passport/read/all")
                        .param("ids", "1", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}
