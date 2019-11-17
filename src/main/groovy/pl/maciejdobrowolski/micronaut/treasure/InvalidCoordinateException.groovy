package pl.maciejdobrowolski.micronaut.treasure

import groovy.transform.CompileStatic

@CompileStatic
class InvalidCoordinateException extends RuntimeException {

    InvalidCoordinateException(Coordinate coordinate) {
        super("Invalid coordinate: (${coordinate.row}, ${coordinate.column})")
    }
}
