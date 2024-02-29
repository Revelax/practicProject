package com.bank.test.profile.service;
import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.dto.PassportDto;
import com.bank.profile.dto.ProfileDto;
import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.entity.ProfileEntity;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.mapper.PassportMapper;
import com.bank.profile.mapper.RegistrationMapper;
import com.bank.profile.repository.PassportRepository;
import com.bank.profile.repository.RegistrationRepository;
import com.bank.profile.service.PassportService;
import com.bank.profile.service.impl.RegistrationServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PassportServiceImplTest {
    private  final PassportEntity userRob = new PassportEntity(1L, 1234, 56789L, "Black", "Rob", "Michael"
            , "Male", LocalDate.of(1990, 5, 15), "New York", "Department of State", LocalDate.of(2010, 8, 20), 100,LocalDate.of(2010, 8, 20), new RegistrationEntity());
    private  final PassportEntity userAlice = new PassportEntity(2L, 124L, "alice@mail.ru", "Alice", 124L, 124L
            , new PassportEntity(), new ActualRegistrationEntity());
    private  final PassportDto userRobDto = new PassportDto(1L, 123L, "rob@mail.ru", "Rob", 123L, 123L
            , new PassportDto(), new ActualRegistrationDto());
    private  final PassportDto userAliceDto = new PassportDto(2L, 124L, "alice@mail.ru", "Alice", 124L, 124L
            , new PassportDto(), new ActualRegistrationDto());

    @Mock
    private PassportRepository repository;

    @Mock
    private PassportMapper mapper;

    @InjectMocks
    private PassportService passportService;

}
