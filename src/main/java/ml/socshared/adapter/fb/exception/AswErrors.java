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
    EXAMPLE("Example errors");

    private final String message;
}

