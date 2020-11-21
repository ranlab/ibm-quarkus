package de.consol.dus.boundary.response;

@lombok.Builder
@lombok.Value
public class ErrorResponse {
    de.consol.dus.boundary.response.ErrorCode errorCode;
    java.lang.String errorMessage;

    @com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }
}
