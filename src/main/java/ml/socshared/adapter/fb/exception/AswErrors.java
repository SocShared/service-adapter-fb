package ml.socshared.adapter.fb.exception;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@ApiModel(
        value = "AswErrors",
        description = "Enumeration of error codes")
@Getter
@RequiredArgsConstructor
public enum AswErrors {

    @ApiModelProperty("Internal error")
    INTERNAL("Internal error"),

    @ApiModelProperty("Internal error")
    CLIENT_ALREADY_EXISTS("Client already exists"),

    @ApiModelProperty("Internal error")
    NOT_FOUND("Not found exception"),

    @ApiModelProperty("Internal error")
    UNAVAILABLE_REQUEST("Unavailable request");

    private final String message;
}

