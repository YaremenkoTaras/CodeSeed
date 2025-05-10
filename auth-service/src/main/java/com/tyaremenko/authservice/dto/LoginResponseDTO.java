package com.tyaremenko.authservice.dto;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@Builder
@With
public class LoginResponseDTO {
    String token;

    public static LoginResponseDTO of(String token) {
        return LoginResponseDTO.builder()
                               .token(token)
                               .build();
    }
}
