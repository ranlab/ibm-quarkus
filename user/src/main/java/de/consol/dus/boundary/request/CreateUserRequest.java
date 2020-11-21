package de.consol.dus.boundary.request;

@lombok.NoArgsConstructor
@lombok.Data
public class CreateUserRequest {

    @javax.validation.constraints.NotNull
    @javax.validation.constraints.Size(max = 255)
    private String username;

    @javax.validation.constraints.NotNull
    @javax.validation.constraints.Email
    private String email;

    @javax.validation.constraints.NotNull
    @javax.validation.constraints.Size(max = 255)
    private String favoriteColor;
}
