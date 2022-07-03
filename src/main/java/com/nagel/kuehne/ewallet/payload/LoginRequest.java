package com.nagel.kuehne.ewallet.payload;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data @Builder
public class LoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
