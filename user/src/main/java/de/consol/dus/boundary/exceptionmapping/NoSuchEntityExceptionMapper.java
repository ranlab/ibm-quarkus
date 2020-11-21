package de.consol.dus.boundary.exceptionmapping;

@javax.ws.rs.ext.Provider
public class NoSuchEntityExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<de.consol.dus.exception.NoSuchEntityException> {
    @Override
    public javax.ws.rs.core.Response toResponse(de.consol.dus.exception.NoSuchEntityException exception) {
        return javax.ws.rs.core.Response
            .status(javax.ws.rs.core.Response.Status.NOT_FOUND)
                .entity(de.consol.dus.boundary.response.ErrorResponse
                    .builder()
                        .errorCode(de.consol.dus.boundary.response.ErrorCode.NOT_FOUND)
                        .errorMessage(exception.getMessage())
                        .build())
                .build();
    }
}
