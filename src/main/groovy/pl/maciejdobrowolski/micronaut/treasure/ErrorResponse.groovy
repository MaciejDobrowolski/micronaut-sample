package pl.maciejdobrowolski.micronaut.treasure

import groovy.transform.CompileStatic
import io.micronaut.core.annotation.Introspected

@Introspected
@CompileStatic
class ErrorResponse {

    String message

    ErrorResponse(String message) {
        this.message = message
    }
}
