package pl.maciejdobrowolski.micronaut.treasure

import groovy.transform.CompileStatic

@CompileStatic
interface TreasureFinder {
    Optional<List<Coordinate>> findPath(Coordinate startingPoint)
}