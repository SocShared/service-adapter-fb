package ru.socshared.template.api.v1.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Hello Controller")
public interface HelloApi {

    @ApiOperation(value = "print hello, world", notes = "print hello, world")
    String print();
}
