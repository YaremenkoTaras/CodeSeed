package com.tyaremenko.userservice.converter;

import com.tyaremenko.userservice.domain.UserEntity;
import com.tyaremenko.userservice.dto.UserRequestDto;
import com.tyaremenko.userservice.dto.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    UserEntity toUserEntity(UserRequestDto userDto);

    UserResponseDto toUserDto(UserEntity userEntity);
}
