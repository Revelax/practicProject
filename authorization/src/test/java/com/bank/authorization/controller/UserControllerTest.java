package com.bank.authorization.controller;

import collect_entity.UsersDto;
import com.bank.authorization.dto.UserDto;
import com.bank.authorization.service.UserServiceImpl;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private final Gson gson = new Gson();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

    @ParameterizedTest
    @EnumSource(UsersDto.class)
    @DisplayName("создание пользователя, позитивный сценарий")
    void createPositiveTest(UsersDto usersDto) throws Exception {
        doReturn(usersDto.getUser())
                .when(userService).save(usersDto.getUser());

        String responseBody = mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(usersDto.getUser())))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto userDtoFromResponse = gson.fromJson(responseBody, UserDto.class);

        assertThat(userDtoFromResponse).isEqualTo(usersDto.getUser());
    }

    @Test
    @DisplayName("создание пользователя передан null, негативный сценарий")
    void createIfGiveNullNegativeTest() throws Exception {
        doThrow(NullPointerException.class).when(userService)
                .save(UsersDto.USER_1.getUser());

        mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().is4xxClientError());
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L})
    @DisplayName("чтение пользователя по id, положительный сценарий")
    void readPositiveTest(Long id) throws Exception {
        doReturn(UsersDto.findUser(id))
                .when(userService).findById(id);

        String responseBody = mockMvc.perform(get(String.format("/read/%d", id)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto userDtoFromResponse = gson.fromJson(responseBody, UserDto.class);

        assertThat(userDtoFromResponse).isEqualTo(UsersDto.findUser(id));
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readIfNotExistNegativeTest() throws Exception {
        doThrow(EntityNotFoundException.class)
                .when(userService).findById(1L);

        mockMvc.perform(get("/read/1"))
                .andExpect(status().isNotFound());
    }

    @ParameterizedTest
    @EnumSource(UsersDto.class)
    @DisplayName("обновление пользователя по id, положительный сценарий")
    void updatePositiveTest(UsersDto usersDto) throws Exception {
        doReturn(usersDto.getUser())
                .when(userService).update(usersDto.getId(), usersDto.getUser());

        String responseBody = mockMvc.perform(put(String.format("/%d/update", usersDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(usersDto.getUser())))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto userDtoFromResponse = gson.fromJson(responseBody, UserDto.class);

        assertThat(gson.fromJson(responseBody, UserDto.class))
                .isEqualTo(usersDto.getUser());
    }

    @Test
    @DisplayName("обновление пользователя по несуществующему id, негативный сценарий")
    void updateIfNotExistNegativeTest() throws Exception {
        doThrow(EntityNotFoundException.class)
                .when(userService).update(1L, UsersDto.USER_1.getUser());

        mockMvc.perform(put("/1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(UsersDto.USER_1.getUser())))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("чтение всех пользователей по id, положительный сценарий")
    void readAllPositiveTest() throws Exception {
        doReturn(Arrays.stream(UsersDto.values())
                .map(UsersDto::getUser)
                .toList())
                .when(userService).findAllByIds(List.of(1L, 2L));

        mockMvc.perform(get("/read/all")
                        .param("ids", "1", "2"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("чтение пользователей по несуществующему id, негативный сценарий")
    void readAllIfNotExistNegativeTest() throws Exception {
        doThrow(EntityNotFoundException.class)
                .when(userService).findAllByIds(List.of(1L, 2L));

        mockMvc.perform(get("/read/all")
                        .param("ids", "1", "2"))
                .andExpect(status().isNotFound());

    }
}
