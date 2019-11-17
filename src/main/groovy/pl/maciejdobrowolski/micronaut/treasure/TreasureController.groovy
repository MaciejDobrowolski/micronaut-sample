package pl.maciejdobrowolski.micronaut.treasure

import groovy.transform.CompileStatic
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error
import io.micronaut.http.annotation.Get

import javax.validation.Valid

@CompileStatic
@Controller("/treasures")
class TreasureController {

    private final TreasureFinder treasureFinder

    TreasureController(TreasureFinder treasureFinder) {
        this.treasureFinder = treasureFinder
    }

    @Get("/solution{?startingPoint*}")
    HttpResponse<?> index(@Valid Coordinate startingPoint) {
        treasureFinder.findPath(startingPoint)
                .map({ solution -> HttpResponse.ok(solution) })
                .orElseGet({ HttpResponse.notFound('NO TREASURE FOUND') })
    }

    @Error
    HttpResponse<ErrorResponse> handle(InvalidCoordinateException e) {
        HttpResponse.badRequest(new ErrorResponse(e.message))
    }

}