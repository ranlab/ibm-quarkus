package de.consol.dus.boundary;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@javax.enterprise.context.ApplicationScoped
@javax.ws.rs.Path(UserResource.PATH)
@javax.ws.rs.Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
@javax.ws.rs.Consumes(javax.ws.rs.core.MediaType.APPLICATION_JSON)
@lombok.AllArgsConstructor
public class UserResource {

    public static final String PATH = "/users";

    private final de.consol.dus.UserService userService;

    @Operation(summary = "Create a new user.")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "When the newly created user was successfully created.", content = @Content(mediaType = javax.ws.rs.core.MediaType.APPLICATION_JSON, schema = @Schema(implementation = de.consol.dus.boundary.response.UserResponse.class))),
            @APIResponse(responseCode = "400", description = "When the username is longer than 255 characters, the email "
                + "malformed or a user with this username or email already exists", content = @Content(schema = @Schema(implementation = de.consol.dus.boundary.response.ErrorResponse.class))) })
    @org.eclipse.microprofile.metrics.annotation.Counted(name = "postUserCount", description = "How often POST /users has been called")
    @org.eclipse.microprofile.metrics.annotation.Metered(name = "postUserMeter", description = "Meter information for POST /users")
    @org.eclipse.microprofile.metrics.annotation.Timed(name = "postUserTimer", description = "How long it takes to to create a single users.")
    @javax.ws.rs.POST
    public javax.ws.rs.core.Response postUser(@javax.validation.Valid de.consol.dus.boundary.request.CreateUserRequest request) {
        return javax.ws.rs.core.Response
            .created(java.net.URI.create(String.format("%s/%s", PATH, request.getUsername())))
                .entity(userService.createUser(request))
                .build();
    }

    @Operation(summary = "Gets a user by their username (case-insensitive).")
    @APIResponses(value = { @APIResponse(responseCode = "200", description = "When the user was fetched.", //
        content = @Content(mediaType = javax.ws.rs.core.MediaType.APPLICATION_JSON, schema = @Schema(implementation = de.consol.dus.boundary.response.UserResponse.class))), //
            @APIResponse(responseCode = "404", description = "When no user with this username has been found.", //
                content = @Content(schema = @Schema(implementation = de.consol.dus.boundary.response.ErrorResponse.class))) })
    @org.eclipse.microprofile.metrics.annotation.Counted(name = "getUserCount", description = "How often GET /users/{username} has been called")
    @org.eclipse.microprofile.metrics.annotation.Metered(name = "getUserMeter", description = "Meter information GET /users/{username}")
    @org.eclipse.microprofile.metrics.annotation.Timed(name = "getUserTimer", description = "How long it takes to to fetch a single users.")
    @javax.ws.rs.Path("/{username}")
    @javax.ws.rs.GET
    public javax.ws.rs.core.Response getUser(@javax.ws.rs.PathParam("username") String username) {
        return javax.ws.rs.core.Response.ok(userService.getUserByUsername(username)).build();
    }
}
