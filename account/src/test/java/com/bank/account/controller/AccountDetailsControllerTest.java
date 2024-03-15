package com.bank.account.controller;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.service.AccountDetailsService;
import com.bank.common.handler.GlobalRestExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountDetailsController.class)
@ExtendWith(MockitoExtension.class)
@Import(GlobalRestExceptionHandler.class)
class AccountDetailsControllerTest {

    private static final AccountDetailsDto ACCOUNT_DETAILS_DTO = new AccountDetailsDto(
            1L, 1L, 1L, 1L,
            BigDecimal.valueOf(100), false, 1L
    );

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountDetailsService service;

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readPositiveTest() throws Exception {
        doReturn(ACCOUNT_DETAILS_DTO).when(service).findById(1L);

        mockMvc.perform(get("/details/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(7)))
                .andExpect(jsonPath("$.id").value(ACCOUNT_DETAILS_DTO.getId()))
                .andExpect(jsonPath("$.passportId").value(ACCOUNT_DETAILS_DTO.getPassportId()))
                .andExpect(jsonPath("$.accountNumber").value(ACCOUNT_DETAILS_DTO.getAccountNumber()))
                .andExpect(jsonPath("$.bankDetailsId").value(ACCOUNT_DETAILS_DTO.getBankDetailsId()))
                .andExpect(jsonPath("$.money").value(ACCOUNT_DETAILS_DTO.getMoney()))
                .andExpect(jsonPath("$.negativeBalance").value(ACCOUNT_DETAILS_DTO.getNegativeBalance()))
                .andExpect(jsonPath("$.profileId").value(ACCOUNT_DETAILS_DTO.getProfileId()));

        verify(service, times(1)).findById(1L);
    }

    @Test
    @DisplayName("чтение по id, передан неверный id, негативный сценарий")
    void readWithInvalidIdNegativeTest() throws Exception {
        doThrow(EntityNotFoundException.class).when(service).findById(1L);

        mockMvc.perform(get("/details/{id}", 1L))
                .andExpect(status().isNotFound());

        verify(service, times(1)).findById(1L);
    }

    @Test
    @DisplayName("сохранение сущности, позитивный сценарий")
    void createPositiveTest() throws Exception {
        doReturn(ACCOUNT_DETAILS_DTO).when(service).save(ACCOUNT_DETAILS_DTO);

        String detailsJson = objectMapper.writeValueAsString(ACCOUNT_DETAILS_DTO);

        mockMvc.perform(post("/details/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(detailsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(7)))
                .andExpect(jsonPath("$.id").value(ACCOUNT_DETAILS_DTO.getId()))
                .andExpect(jsonPath("$.passportId").value(ACCOUNT_DETAILS_DTO.getPassportId()))
                .andExpect(jsonPath("$.accountNumber").value(ACCOUNT_DETAILS_DTO.getAccountNumber()))
                .andExpect(jsonPath("$.bankDetailsId").value(ACCOUNT_DETAILS_DTO.getBankDetailsId()))
                .andExpect(jsonPath("$.money").value(ACCOUNT_DETAILS_DTO.getMoney()))
                .andExpect(jsonPath("$.negativeBalance").value(ACCOUNT_DETAILS_DTO.getNegativeBalance()))
                .andExpect(jsonPath("$.profileId").value(ACCOUNT_DETAILS_DTO.getProfileId()));

        verify(service, times(1)).save(ACCOUNT_DETAILS_DTO);
    }

    @Test
    @DisplayName("сохранение сущности, с пустым телом запроса, негативный сценарий")
    void createWithEmptyInputNegativeTest() throws Exception {

        mockMvc.perform(post("/details/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("обновление сущности, позитивный сценарий")
    void updatePositiveTest() throws Exception {
        doReturn(ACCOUNT_DETAILS_DTO).when(service).update(1L, ACCOUNT_DETAILS_DTO);

        String detailsJson = objectMapper.writeValueAsString(ACCOUNT_DETAILS_DTO);

        mockMvc.perform(put("/details/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(detailsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(7)))
                .andExpect(jsonPath("$.id").value(ACCOUNT_DETAILS_DTO.getId()))
                .andExpect(jsonPath("$.passportId").value(ACCOUNT_DETAILS_DTO.getPassportId()))
                .andExpect(jsonPath("$.accountNumber").value(ACCOUNT_DETAILS_DTO.getAccountNumber()))
                .andExpect(jsonPath("$.bankDetailsId").value(ACCOUNT_DETAILS_DTO.getBankDetailsId()))
                .andExpect(jsonPath("$.money").value(ACCOUNT_DETAILS_DTO.getMoney()))
                .andExpect(jsonPath("$.negativeBalance").value(ACCOUNT_DETAILS_DTO.getNegativeBalance()))
                .andExpect(jsonPath("$.profileId").value(ACCOUNT_DETAILS_DTO.getProfileId()));

        verify(service, times(1)).update(1L, ACCOUNT_DETAILS_DTO);
    }

    @Test
    @DisplayName("обновление сущности, с пустым телом запроса, негативный сценарий")
    void updateWithEmptyInputNegativeTest() throws Exception {

        mockMvc.perform(put("/details/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("чтение всех сущностей, позитивный сценарий")
    void readAllPositiveTest() throws Exception {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<AccountDetailsDto> accountDetailsDtoList = Arrays.asList(
                ACCOUNT_DETAILS_DTO,
                new AccountDetailsDto(
                        2L, 2L, 2L, 2L,
                        BigDecimal.valueOf(200L), true, 2L
                )
        );

        doReturn(accountDetailsDtoList).when(service).findAllById(ids);

        mockMvc.perform(get("/details/read/all")
                        .param("ids", "1", "2"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}, {}]"));

        verify(service, times(1)).findAllById(ids);
    }

    @Test
    @DisplayName("чтение всех сущностей, передан неверный id, негативный сценарий")
    void readAllWithInvalidIdNegativeTest() throws Exception {
        List<Long> ids = Arrays.asList(1L, 2L);

        doThrow(EntityNotFoundException.class).when(service).findAllById(ids);

        mockMvc.perform(get("/details/read/all")
                        .param("ids", "1", "2"))
                .andExpect(status().isNotFound());

        verify(service, times(1)).findAllById(ids);
    }
}