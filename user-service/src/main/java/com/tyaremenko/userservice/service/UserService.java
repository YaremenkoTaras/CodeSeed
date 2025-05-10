package com.tyaremenko.userservice.service;

import com.tyaremenko.userservice.domain.UserEntity;
import com.tyaremenko.userservice.dto.UserRequestDto;
import com.tyaremenko.userservice.dto.UserResponseDto;
import com.tyaremenko.userservice.exception.EmailAlreadyExistsException;
import com.tyaremenko.userservice.exception.PatientNotFoundException;
import com.tyaremenko.userservice.grpc.BillingServiceGrpcClient;
import com.tyaremenko.userservice.kafka.KafkaProducer;
import com.tyaremenko.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.tyaremenko.userservice.converter.UserMapper.USER_MAPPER;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

    public List<UserResponseDto> getPatients() {
        List<UserEntity> patients = userRepository.findAll();

        return patients.stream().map(USER_MAPPER::toUserDto).toList();
    }

    public UserResponseDto createPatient(UserRequestDto UserRequestDto) {
        if (userRepository.existsByEmail(UserRequestDto.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "A patient with this email " + "already exists"
                            + UserRequestDto.getEmail());
        }

        UserEntity newPatient = userRepository.save(
                USER_MAPPER.toUserEntity(UserRequestDto));

        billingServiceGrpcClient.createBillingAccount(newPatient.getId().toString(),
                                                      newPatient.getName(), newPatient.getEmail());

        kafkaProducer.sendEvent(newPatient);

        return USER_MAPPER.toUserDto(newPatient);
    }

    public UserResponseDto updatePatient(UUID id,
                                         UserRequestDto UserRequestDto) {

        UserEntity patient = userRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException("Patient not found with ID: " + id));

        if (userRepository.existsByEmailAndIdNot(UserRequestDto.getEmail(),
                                                 id)) {
            throw new EmailAlreadyExistsException(
                    "A patient with this email " + "already exists"
                            + UserRequestDto.getEmail());
        }

        patient.setName(UserRequestDto.getName());
        patient.setAddress(UserRequestDto.getAddress());
        patient.setEmail(UserRequestDto.getEmail());
        patient.setDateOfBirth(LocalDate.parse(UserRequestDto.getDateOfBirth()));

        UserEntity updatedPatient = userRepository.save(patient);
        return USER_MAPPER.toUserDto(updatedPatient);
    }

    public void deletePatient(UUID id) {
        userRepository.deleteById(id);
    }
}
