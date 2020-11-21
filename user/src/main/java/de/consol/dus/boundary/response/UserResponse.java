package de.consol.dus.boundary.response;

@lombok.Builder
@lombok.Value
public class UserResponse {
    String username;
    String email;
    String favoriteColor;
}
