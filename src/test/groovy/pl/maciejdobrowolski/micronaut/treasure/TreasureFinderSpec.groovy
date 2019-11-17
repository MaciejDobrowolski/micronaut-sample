package pl.maciejdobrowolski.micronaut.treasure

import pl.maciejdobrowolski.micronaut.treasure.TreasureMap.Cell
import spock.lang.Specification
import spock.lang.Unroll

class TreasureFinderSpec extends Specification {

    def 'should find solution with proper path'() {
        given:
        HintFollowingTreasureFinder finder = new HintFollowingTreasureFinder(TreasureMap.ofCells([
                [cell(1, 2), cell(2, 1)],
                [cell(2, 2), cell(2, 2)]
        ] as Cell[][]))

        when:
        def path = finder.findPath(coordinate(1, 1))

        then:
        path.isPresent()
        path.get() == [
                coordinate(1, 1),
                coordinate(1, 2),
                coordinate(2, 1),
                coordinate(2, 2)
        ]
    }

    @Unroll
    def 'should find solution with starting point #startingPoint'() {
        given:
        HintFollowingTreasureFinder finder = new HintFollowingTreasureFinder(TreasureMap.ofCells([
                [cell(1, 2), cell(2, 1)],
                [cell(2, 2), cell(2, 2)]
        ] as Cell[][]))

        when:
        def path = finder.findPath(startingPoint)

        then:
        path.isPresent()
        path.get().size() == pathSize

        where:
        startingPoint    || pathSize
        coordinate(1, 1) || 4
        coordinate(1, 2) || 3
        coordinate(2, 1) || 2
        coordinate(2, 2) || 1
    }

    def 'should not find solution'() {
        given:
        HintFollowingTreasureFinder finder = new HintFollowingTreasureFinder(TreasureMap.ofCells([
                [cell(1, 2), cell(1, 1)],
                [cell(2, 2), cell(2, 2)]
        ] as Cell[][]))

        when:
        def path = finder.findPath(coordinate(1, 1))

        then:
        path.isEmpty()
    }

    static Coordinate coordinate(int row, int column) {
        return new Coordinate(row, column)
    }

    static Cell cell(int hintRow, hintColumn) {
        new Cell(new Coordinate(hintRow, hintColumn))
    }

}
