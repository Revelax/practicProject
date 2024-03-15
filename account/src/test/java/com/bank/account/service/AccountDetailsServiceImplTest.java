package com.bank.account.service;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetailsEntity;
import com.bank.account.mapper.AccountDetailsMapperImpl;
import com.bank.account.repository.AccountDetailsRepository;
import com.bank.account.service.common.ExceptionReturner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountDetailsServiceImplTest {

    private static final AccountDetailsEntity ACCOUNT_DETAILS_ENTITY = new AccountDetailsEntity(
            1L, 1L, 1L, 1L,
            BigDecimal.valueOf(100), false, 1L
    );

    private static final AccountDetailsDto ACCOUNT_DETAILS_DTO = new AccountDetailsDto(
            1L, 1L, 1L, 1L,
            BigDecimal.valueOf(100), false, 1L
    );

    private static final String MESSAGE_PREFIX = "Не существующий id = ";

    @Spy
    private AccountDetailsMapperImpl mapper;

    @Mock
    private AccountDetailsRepository repository;

    @Spy
    private ExceptionReturner exceptionReturner;

    @InjectMocks
    private AccountDetailsServiceImpl service;

    @Test
    @DisplayName("поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        Mockito.doReturn(Optional.of(ACCOUNT_DETAILS_ENTITY))
                .when(repository).findById(ACCOUNT_DETAILS_ENTITY.getId());

        AccountDetailsDto actualResult = service.findById(ACCOUNT_DETAILS_ENTITY.getId());

        verify(repository, times(1)).findById(ACCOUNT_DETAILS_ENTITY.getId());
        verify(mapper, times(1)).toDto(ACCOUNT_DETAILS_ENTITY);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getId()).isEqualTo(ACCOUNT_DETAILS_ENTITY.getId());
        assertThat(actualResult.getPassportId()).isEqualTo(ACCOUNT_DETAILS_ENTITY.getPassportId());
        assertThat(actualResult.getAccountNumber()).isEqualTo(ACCOUNT_DETAILS_ENTITY.getAccountNumber());
        assertThat(actualResult.getBankDetailsId()).isEqualTo(ACCOUNT_DETAILS_ENTITY.getBankDetailsId());
        assertThat(actualResult.getMoney()).isEqualTo(ACCOUNT_DETAILS_ENTITY.getMoney());
        assertThat(actualResult.getNegativeBalance()).isEqualTo(ACCOUNT_DETAILS_ENTITY.getNegativeBalance());
        assertThat(actualResult.getProfileId()).isEqualTo(ACCOUNT_DETAILS_ENTITY.getProfileId());
    }

    @Test
    @SuppressWarnings("ThrowableNotThrown")
    @DisplayName("поиск по id, передан неверный id, негативный сценарий")
    void findByIdWithInvalidIdNegativeTest() {
        Mockito.doReturn(Optional.empty()).when(repository).findById(1L);

        assertThatThrownBy(() -> service.findById(1L)).isInstanceOf(EntityNotFoundException.class);

        verify(repository, times(1)).findById(1L);
        verify(exceptionReturner, times(1))
                .getEntityNotFoundException(MESSAGE_PREFIX + 1L);
    }

    @Test
    @DisplayName("поиск всех сущностей по id, позитивный сценарий")
    void findAllByIdPositiveTest() {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<AccountDetailsEntity> accountDetailsEntityList = Arrays.asList(
                ACCOUNT_DETAILS_ENTITY,
                new AccountDetailsEntity(
                        2L, 2L, 2L, 2L,
                        BigDecimal.valueOf(200L), true, 2L
                )
        );

        when(repository.findById(anyLong())).thenAnswer(invocation -> {
            Long id = invocation.getArgument(0);
            if (ids.contains(id)) {
                return Optional.of(accountDetailsEntityList.get(ids.indexOf(id)));
            } else {
                return Optional.empty();
            }
        });

        List<AccountDetailsDto> actualResult = service.findAllById(ids);

        verify(repository, atLeast(1)).findById(anyLong());
        verify(mapper, times(1)).toDtoList(accountDetailsEntityList);

        assertThat(actualResult).hasSameSizeAs(accountDetailsEntityList);
        for (int i = 0; i < accountDetailsEntityList.size(); i++) {
            assertThat(actualResult.get(i).getId())
                    .isEqualTo(accountDetailsEntityList.get(i).getId());
            assertThat(actualResult.get(i).getPassportId())
                    .isEqualTo(accountDetailsEntityList.get(i).getPassportId());
            assertThat(actualResult.get(i).getAccountNumber())
                    .isEqualTo(accountDetailsEntityList.get(i).getAccountNumber());
            assertThat(actualResult.get(i).getBankDetailsId())
                    .isEqualTo(accountDetailsEntityList.get(i).getBankDetailsId());
            assertThat(actualResult.get(i).getMoney())
                    .isEqualTo(accountDetailsEntityList.get(i).getMoney());
            assertThat(actualResult.get(i).getNegativeBalance())
                    .isEqualTo(accountDetailsEntityList.get(i).getNegativeBalance());
            assertThat(actualResult.get(i).getProfileId())
                    .isEqualTo(accountDetailsEntityList.get(i).getProfileId());
        }
    }

    @Test
    @SuppressWarnings("ThrowableNotThrown")
    @DisplayName("поиск всех сущностей по id, передан неверный id, негативный сценарий")
    void findAllByIdWithInvalidIdNegativeTest() {
        List<Long> ids = Arrays.asList(3L, 2L);
        Mockito.doReturn(Optional.empty()).when(repository).findById(3L);

        assertThrows(EntityNotFoundException.class, () -> service.findAllById(ids));

        verify(exceptionReturner, times(1))
                .getEntityNotFoundException(MESSAGE_PREFIX + 3L);
    }

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void savePositiveTest() {
        Mockito.doReturn(ACCOUNT_DETAILS_ENTITY).when(repository).save(any(AccountDetailsEntity.class));

        AccountDetailsDto resultDto = service.save(ACCOUNT_DETAILS_DTO);

        assertThat(resultDto).isEqualTo(ACCOUNT_DETAILS_DTO);
        verify(mapper, times(1)).toEntity(ACCOUNT_DETAILS_DTO);
        verify(repository, times(1)).save(any(AccountDetailsEntity.class));
        verify(mapper, times(1)).toDto(ACCOUNT_DETAILS_ENTITY);
    }


    @Test
    @SuppressWarnings("DataFlowIssue")
    @DisplayName("сохранение, передан null, негативный сценарий")
    void saveWithNullInputNegativeTest() {
        doThrow(IllegalArgumentException.class).when(repository).save(null);
        assertThrows(IllegalArgumentException.class, () -> service.save(null));
        verify(mapper, times(1)).toEntity(null);
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() {
        Mockito.doReturn(Optional.of(ACCOUNT_DETAILS_ENTITY))
                .when(repository).findById(ACCOUNT_DETAILS_DTO.getId());
        Mockito.doReturn(ACCOUNT_DETAILS_ENTITY).when(repository).save(any());

        AccountDetailsDto resultDto = service.update(ACCOUNT_DETAILS_DTO.getId(), ACCOUNT_DETAILS_DTO);

        assertThat(resultDto).isEqualTo(ACCOUNT_DETAILS_DTO);
        verify(repository, times(1)).findById(ACCOUNT_DETAILS_DTO.getId());
        verify(mapper, times(1)).mergeToEntity(any(), any());
        verify(repository, times(1)).save(any());
        verify(mapper, times(1)).toDto(ACCOUNT_DETAILS_ENTITY);
    }

    @Test
    @SuppressWarnings("ThrowableNotThrown")
    @DisplayName("обновление, передан неверный id, негативный сценарий")
    void updateWithInvalidIdNegativeTest() {
        doReturn(Optional.empty()).when(repository).findById(1L);

        assertThrows(EntityNotFoundException.class, () -> service.update(1L, ACCOUNT_DETAILS_DTO));

        verify(repository, times(1)).findById(1L);
        verify(exceptionReturner, times(1))
                .getEntityNotFoundException(MESSAGE_PREFIX + 1L);
    }

    @Test
    @DisplayName("обновление сущности, передан null, негативный сценарий")
    void updateWithNullInputNegativeTest() {
        doReturn(Optional.of(ACCOUNT_DETAILS_ENTITY)).when(repository).findById(1L);
        doReturn(ACCOUNT_DETAILS_ENTITY).when(repository).save(ACCOUNT_DETAILS_ENTITY);

        AccountDetailsDto resultDto = service.update(1L, null);

        assertThat(resultDto).isNotNull();
        assertThat(resultDto.getId()).isEqualTo(1L);

        verify(mapper, times(1)).mergeToEntity(ACCOUNT_DETAILS_ENTITY, null);
        verify(mapper, times(1)).toDto(ACCOUNT_DETAILS_ENTITY);
    }
}
