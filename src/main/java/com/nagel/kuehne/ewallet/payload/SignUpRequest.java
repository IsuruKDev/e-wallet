package com.nagel.kuehne.ewallet.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class SignUpRequest {

    @NotBlank
    @Size(min = 3, max = 10)
    private String username;

    @NotBlank
    @Size(min = 5, max = 20)
    private String password;

    private Set<String> role;

}
