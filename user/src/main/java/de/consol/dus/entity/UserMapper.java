package de.consol.dus.entity;

/**
 * Hier ist ein 1:1 Mapping zwischen den Feldern User <--> Response 
 * 
 * @see https://www.codeflow.site/de/article/mapstruct
 * @author promyx
 */
@org.mapstruct.Mapper(componentModel = "cdi")
public interface UserMapper {
    de.consol.dus.boundary.response.UserResponse entityToResponse(de.consol.dus.entity.User user);
    de.consol.dus.entity.User requestToUser(de.consol.dus.boundary.request.CreateUserRequest request);
}
