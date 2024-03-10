package com.bank.authorization.mapper;

import collect_entity.UsersDto;
import collect_entity.UsersEntity;
import com.bank.authorization.dto.UserDto;
import com.bank.authorization.entity.UserEntity;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    @Spy
    private UserMapperImpl userMapper;

    @ParameterizedTest
    @EnumSource(UsersEntity.class)
    @DisplayName("маппинг в Dto")
    void toDtoTest(UsersEntity usersEntity) {
        assertThat(userMapper.toDTO(usersEntity.getUser()))
                .isEqualTo(UsersDto.findUser(usersEntity.getId()));
    }

    @Test
    @DisplayName("маппинг в Dto, на вход null")
    void toDtoNullTest() {
        assertThat(userMapper.toDTO(null)).isNull();
    }

    @ParameterizedTest
    @EnumSource(UsersDto.class)
    @DisplayName("маппинг в Entity")
    void toEntityTest(UsersDto usersDto) {
        UserEntity actualUser = userMapper.toEntity(usersDto.getUser());
        UserEntity expectedUser = UsersEntity.findUser(usersDto.getId());

        assertAll(() -> {
            assertThat(actualUser.getPassword()).isEqualTo(expectedUser.getPassword());
            assertThat(actualUser.getProfileId()).isEqualTo(expectedUser.getProfileId());
            assertThat(actualUser.getRole()).isEqualTo(expectedUser.getRole());
        });
    }

    @Test
    @DisplayName("маппинг в Entity, на вход null")
    void toEntityNullTest() {
        assertThat(userMapper.toEntity(null)).isNull();
    }

    @Test
    @DisplayName("маппинг в ListDto")
    void toDtoListTest() {
        List<UserEntity> actualUsersEntity = Arrays.stream(UsersEntity.values())
                .map(UsersEntity::getUser)
                .toList();
        List<UserDto> expectedUsersDto = Arrays.stream(UsersDto.values())
                .map(UsersDto::getUser)
                .toList();

        // Захардкодер. Нужен equls в UserMapper.
        doReturn(UsersDto.findUser(UsersEntity.USER_1.getId()))
                .when(userMapper).toDTO(UsersEntity.USER_1.getUser());
        doReturn(UsersDto.findUser(UsersEntity.USER_2.getId()))
                .when(userMapper).toDTO(UsersEntity.USER_2.getUser());

        assertThat(userMapper.toDtoList(actualUsersEntity))
                .containsAll(expectedUsersDto);
    }

    @Test
    @DisplayName("маппинг в ListDto, есть null")
    void toDtoListNullTest() {
        assertThat(userMapper.toDtoList(null)).isNull();
    }

    @ParameterizedTest
    @EnumSource(UsersDto.class)
    @DisplayName("слияние в entity")
    void mergeToEntityTest(UsersDto usersDto) {
        UserEntity exampleUser = new UserEntity();

        userMapper.mergeToEntity(usersDto.getUser(), exampleUser);

        assertAll(() -> {
            assertThat(usersDto.getUser().getRole()).isEqualTo(exampleUser.getRole());
            assertThat(usersDto.getUser().getPassword()).isEqualTo(exampleUser.getPassword());
            assertThat(usersDto.getUser().getProfileId()).isEqualTo(exampleUser.getProfileId());
        });
    }

    @ParameterizedTest
    @EnumSource(UsersEntity.class)
    @DisplayName("слияние в entity, на вход Dto null,")
    void mergeToEntityNullTest(UsersEntity usersEntity) {
        assertThat(userMapper.mergeToEntity(null, usersEntity.getUser()))
                .isEqualTo(usersEntity.getUser());
    }
}
