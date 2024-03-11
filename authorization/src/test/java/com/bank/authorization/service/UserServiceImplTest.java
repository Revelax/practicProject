package com.bank.authorization.service;

import com.bank.authorization.dto.UserDto;
import com.bank.authorization.entity.UserEntity;
import collect_entity.UsersDto;
import collect_entity.UsersEntity;
import com.bank.authorization.mapper.UserMapperImpl;
import com.bank.authorization.repository.UserRepository;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Spy
    private UserMapperImpl userMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @ParameterizedTest
    @EnumSource(UsersDto.class)
    @DisplayName("поиск по id, позитивный сценарий")
    void findByIdPositiveTest(UsersDto usersDto) {
        doReturn(Optional.of(UsersEntity.findUser(usersDto.getId())))
                .when(userRepository).findById(usersDto.getId());

        UserDto actualUsers = userService.findById(usersDto.getId());

        Assertions.assertAll(() -> {
            assertThat(actualUsers.getId()).isEqualTo(usersDto.getId());
            assertThat(actualUsers.getRole()).isEqualTo(usersDto.getUser().getRole());
            assertThat(actualUsers.getProfileId()).isEqualTo(usersDto.getUser().getProfileId());
        });
    }

    @ParameterizedTest
    @EnumSource(UsersDto.class)
    @DisplayName("поиск по несуществующему id, негативный сценарий")
    void findByIdIfUserNotExistNegativeTest(UsersDto usersDto) {
        doReturn(Optional.empty())
                .when(userRepository).findById(usersDto.getId());

        assertThatThrownBy(() -> userService.findById(usersDto.getId()))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @ParameterizedTest
    @EnumSource(UsersDto.class)
    @DisplayName("сохранение пользователя, позитивный сценарий")
    void savePositiveTest(UsersDto usersDto) {
        UserEntity shouldReturnedInMock = UsersEntity.findUser(usersDto.getId());

        doReturn(shouldReturnedInMock)
                .when(userRepository).save(shouldReturnedInMock);
        doReturn(shouldReturnedInMock)
                .when(userMapper).toEntity(usersDto.getUser());
        doReturn(usersDto.getUser())
                .when(userMapper).toDTO(shouldReturnedInMock);

        AssertionsForClassTypes.assertThat(userService.save(usersDto.getUser()))
                .isEqualTo(usersDto.getUser());
    }

    @Test
    @DisplayName("сохранение пользователя с null, негативный сценарий")
    void saveIfGiveNullNegativeTest() {
        doThrow(NullPointerException.class).when(userMapper).toEntity(null);

        assertThatThrownBy(() -> userService.save(null))
                .isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @EnumSource(UsersDto.class)
    @DisplayName("обновление данных, позитивный сценарий")
    void updatePositiveTest(UsersDto usersDto) {
        UserEntity shouldReturnedInMock = UsersEntity.findUser(usersDto.getId());

        doReturn(Optional.of(shouldReturnedInMock))
                .when(userRepository).findById(usersDto.getId());
        doReturn(shouldReturnedInMock)
                .when(userRepository).save(shouldReturnedInMock);

        AssertionsForClassTypes.assertThat(usersDto.getUser().getId())
                .isEqualTo(userService.update(usersDto.getId(), usersDto.getUser()).getId());
    }

    @ParameterizedTest
    @EnumSource(UsersDto.class)
    @DisplayName("обновление данных несуществующего пользователя, негативный сценарий")
    void updateIfUserNotExistNegativeTest(UsersDto usersDto) {
        doReturn(Optional.empty()).when(userRepository).findById(usersDto.getId());

        assertThatThrownBy(() -> userService.update(usersDto.getId(), usersDto.getUser()))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("поиск всех пользователей по id, позитивный сценарий")
    void findAllByIdsPositiveTest() {
        List<Long> ids = List.of(1L, 2L);
        List<UserDto> usersToDto = Arrays.stream(UsersDto.values())
                .map(UsersDto::getUser)
                .toList();
        List<UserEntity> usersToEntity = Arrays.stream(UsersEntity.values())
                .map(UsersEntity::getUser)
                .toList();

        doReturn(Optional.of(UsersEntity.findUser(1L)))
                .when(userRepository).findById(1L);
        doReturn(Optional.of(UsersEntity.findUser(2L)))
                .when(userRepository).findById(2L);
        doReturn(usersToDto).when(userMapper).toDtoList(usersToEntity);

        assertThat(userService.findAllByIds(ids)).containsAll(usersToDto);
    }

    @Test
    @DisplayName("поиск пользователей по несуществующему id, негативный сценарий")
    void findAllByIdsIfOneUserNotExistNegativeTest() {
        List<Long> ids = List.of(1L, 2L);

        doReturn(Optional.empty()).when(userRepository).findById(1L);

        assertThatThrownBy(() -> userService.findAllByIds(ids))
                .isInstanceOf(EntityNotFoundException.class);
    }
}