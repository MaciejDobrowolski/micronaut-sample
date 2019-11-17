package pl.maciejdobrowolski.micronaut.treasure

import groovy.transform.CompileStatic
import io.micronaut.context.annotation.Context
import io.micronaut.context.annotation.Value
import io.micronaut.core.io.ResourceResolver

import javax.annotation.PostConstruct
import javax.inject.Singleton
import java.nio.charset.StandardCharsets

@Singleton
@Context
@CompileStatic
class TreasureMap {

    private final String mapLocation
    private final ResourceResolver resolver

    private Cell[][] map

    TreasureMap(ResourceResolver resolver,
                @Value('${map.location}') String mapLocation) {
        this.resolver = resolver
        this.mapLocation = mapLocation
    }

    private TreasureMap(Cell[][] map) {
        this.map = map
    }

    @PostConstruct
    void loadMap() {
        map = resolver.getResource(mapLocation)
                .map({ it.getText(StandardCharsets.UTF_8.name()) })
                .map({ constructMap(it) })
                .orElseThrow()
    }

    private static Cell[][] constructMap(String text) {
        text.split('\\n')
                .collect {
                    it.split('\\s+').collect { new Cell(it) }
                } as Cell[][]
    }

    Cell getCell(Coordinate coordinate) {
        try {
            map[coordinate.row - 1][coordinate.column - 1]
        } catch (ArrayIndexOutOfBoundsException ignored) {
            throw new InvalidCoordinateException(coordinate)
        }
    }

    static class Cell {
        final Coordinate nextCoordinate

        private Cell(String hintCoordinate) {
            def split = hintCoordinate.split('')
            nextCoordinate = new Coordinate(Integer.valueOf(split[0]), Integer.valueOf(split[1]))
        }

        Cell(Coordinate hintCoordinate) {
            nextCoordinate = hintCoordinate
        }
    }

    static TreasureMap ofCells(Cell[][] cells) {
        new TreasureMap(cells)
    }

}
