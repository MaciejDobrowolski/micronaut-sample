package pl.maciejdobrowolski.micronaut.treasure

import groovy.transform.CompileStatic

import javax.inject.Singleton
import java.util.stream.Stream

import static java.util.stream.Collectors.toList

@Singleton
@CompileStatic
class HintFollowingTreasureFinder implements TreasureFinder {

    private final TreasureMap map

    HintFollowingTreasureFinder(TreasureMap map) {
        this.map = map
    }

    Optional<List<Coordinate>> findPath(Coordinate startingPoint) {
        Set<Coordinate> visitedCoordinates = []
        List<Coordinate> path = Stream.iterate(startingPoint, { getNextCoordinate(it) })
                .takeWhile({ visitedCoordinates.add(it) })
                .collect(toList())
        return Optional.of(path)
                .filter({ solutionFound(it) })
    }

    private boolean solutionFound(List<Coordinate> path) {
        getNextCoordinate(path.last()) == path.last()
    }

    private Coordinate getNextCoordinate(Coordinate coordinate) {
        map.getCell(coordinate).nextCoordinate
    }


}
