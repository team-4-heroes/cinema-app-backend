package kea.dat3.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotNull
    private String username;
    @NotNull
    private String password;
}
