package pl.maciejdobrowolski.micronaut.treasure

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import io.micronaut.core.annotation.Introspected

import javax.validation.constraints.Max
import javax.validation.constraints.Min

@Introspected
@EqualsAndHashCode
@ToString(includePackage = false)
@CompileStatic
class Coordinate {

    @Min(1L)
    @Max(9L)
    int row

    @Min(1L)
    @Max(9L)
    int column

    Coordinate(int row, int column) {
        this.row = row
        this.column = column
    }

}
