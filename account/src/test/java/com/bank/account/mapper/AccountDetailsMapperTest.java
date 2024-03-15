package com.bank.account.mapper;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetailsEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AccountDetailsMapperTest {

    private static final AccountDetailsEntity ACCOUNT_DETAILS_ENTITY = new AccountDetailsEntity(
            1L, 1L, 1L, 1L,
            BigDecimal.valueOf(100), false, 1L
    );

    private static final AccountDetailsDto ACCOUNT_DETAILS_DTO = new AccountDetailsDto(
            1L, 1L, 1L, 1L,
            BigDecimal.valueOf(100), false, 1L
    );

    @Spy
    private AccountDetailsMapperImpl mapper;

    @Test
    @DisplayName("маппинг в entity")
    void toEntityTest() {
        AccountDetailsEntity accountDetails = mapper.toEntity(ACCOUNT_DETAILS_DTO);

        assertThat(accountDetails).isNotNull();
        assertThat(accountDetails.getPassportId()).isEqualTo(ACCOUNT_DETAILS_DTO.getPassportId());
        assertThat(accountDetails.getAccountNumber()).isEqualTo(ACCOUNT_DETAILS_DTO.getAccountNumber());
        assertThat(accountDetails.getBankDetailsId()).isEqualTo(ACCOUNT_DETAILS_DTO.getBankDetailsId());
        assertThat(accountDetails.getMoney()).isEqualTo(ACCOUNT_DETAILS_DTO.getMoney());
        assertThat(accountDetails.getNegativeBalance()).isEqualTo(ACCOUNT_DETAILS_DTO.getNegativeBalance());
        assertThat(accountDetails.getProfileId()).isEqualTo(ACCOUNT_DETAILS_DTO.getProfileId());
    }

    @Test
    @DisplayName("маппинг в entity, на вход передан null")
    void toEntityNullTest() {
        AccountDetailsEntity accountDetails = mapper.toEntity(null);
        assertThat(accountDetails).isNull();
    }

    @Test
    @DisplayName("маппинг в dto")
    void toDtoTest() {
        AccountDetailsDto dto = mapper.toDto(ACCOUNT_DETAILS_ENTITY);

        assertThat(dto).isNotNull();
        assertThat(dto.getPassportId()).isEqualTo(ACCOUNT_DETAILS_ENTITY.getPassportId());
        assertThat(dto.getAccountNumber()).isEqualTo(ACCOUNT_DETAILS_ENTITY.getAccountNumber());
        assertThat(dto.getBankDetailsId()).isEqualTo(ACCOUNT_DETAILS_ENTITY.getBankDetailsId());
        assertThat(dto.getMoney()).isEqualTo(ACCOUNT_DETAILS_ENTITY.getMoney());
        assertThat(dto.getNegativeBalance()).isEqualTo(ACCOUNT_DETAILS_ENTITY.getNegativeBalance());
        assertThat(dto.getProfileId()).isEqualTo(ACCOUNT_DETAILS_ENTITY.getProfileId());
    }

    @Test
    @DisplayName("маппинг в dto, на вход передан null")
    void toDtoNullTest() {
        AccountDetailsDto dto = mapper.toDto(null);
        assertThat(dto).isNull();
    }

    @Test
    @DisplayName("маппинг в список Dto")
    void toDtoListTest() {
        List<AccountDetailsEntity> accountDetailsEntityList = Arrays.asList(
                ACCOUNT_DETAILS_ENTITY,
                new AccountDetailsEntity(
                        2L, 2L, 2L, 2L,
                        BigDecimal.valueOf(200L), true, 2L
                )
        );

        List<AccountDetailsDto> dtoList = mapper.toDtoList(accountDetailsEntityList);

        assertThat(dtoList).hasSameSizeAs(accountDetailsEntityList);
        for (int i = 0; i < accountDetailsEntityList.size(); i++) {
            assertThat(dtoList.get(i).getId())
                    .isEqualTo(accountDetailsEntityList.get(i).getId());
            assertThat(dtoList.get(i).getPassportId())
                    .isEqualTo(accountDetailsEntityList.get(i).getPassportId());
            assertThat(dtoList.get(i).getAccountNumber())
                    .isEqualTo(accountDetailsEntityList.get(i).getAccountNumber());
            assertThat(dtoList.get(i).getBankDetailsId())
                    .isEqualTo(accountDetailsEntityList.get(i).getBankDetailsId());
            assertThat(dtoList.get(i).getMoney())
                    .isEqualTo(accountDetailsEntityList.get(i).getMoney());
            assertThat(dtoList.get(i).getNegativeBalance())
                    .isEqualTo(accountDetailsEntityList.get(i).getNegativeBalance());
            assertThat(dtoList.get(i).getProfileId())
                    .isEqualTo(accountDetailsEntityList.get(i).getProfileId());
        }
    }

    @Test
    @DisplayName("маппинг в список Dto, если вместо списка entity передан null")
    void toDtoListNullTest() {
        List<AccountDetailsDto> dtoList = mapper.toDtoList(null);
        assertThat(dtoList).isNull();
    }

    @Test
    @DisplayName("слияние dto в entity")
    void mergeToEntityTest() {
        AccountDetailsEntity entity = mapper.mergeToEntity(ACCOUNT_DETAILS_ENTITY, ACCOUNT_DETAILS_DTO);

        assertThat(entity).isNotNull();
        assertThat(entity.getPassportId()).isEqualTo(ACCOUNT_DETAILS_DTO.getPassportId());
        assertThat(entity.getAccountNumber()).isEqualTo(ACCOUNT_DETAILS_DTO.getAccountNumber());
        assertThat(entity.getBankDetailsId()).isEqualTo(ACCOUNT_DETAILS_DTO.getBankDetailsId());
        assertThat(entity.getMoney()).isEqualTo(ACCOUNT_DETAILS_DTO.getMoney());
        assertThat(entity.getNegativeBalance()).isEqualTo(ACCOUNT_DETAILS_DTO.getNegativeBalance());
        assertThat(entity.getProfileId()).isEqualTo(ACCOUNT_DETAILS_DTO.getProfileId());
    }

    @Test
    @DisplayName("слияние dto в entity, если вместо dto передан null")
    void mergeToEntityNullDtoTest() {
        AccountDetailsEntity entity = mapper.mergeToEntity(ACCOUNT_DETAILS_ENTITY, null);

        assertThat(entity)
                .isNotNull()
                .isEqualTo(ACCOUNT_DETAILS_ENTITY);
    }
}