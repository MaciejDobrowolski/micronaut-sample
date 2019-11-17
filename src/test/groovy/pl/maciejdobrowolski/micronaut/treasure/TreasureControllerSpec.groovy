package pl.maciejdobrowolski.micronaut.treasure

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.test.annotation.MockBean
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class TreasureControllerSpec extends Specification {

    @Inject
    @Client("/")
    RxHttpClient client

    TreasureFinder finder = Mock()

    @MockBean(HintFollowingTreasureFinder)
    TreasureFinder mockTreasureFinder() {
        finder
    }

    def "should return 404 if treasure cannot be found"() {
        given:
        HttpRequest request = HttpRequest.GET('/treasures/solution?row=1&column=1')
        finder.findPath(_ as Coordinate) >> Optional.empty()

        when:
        client.toBlocking().exchange(request)

        then:
        HttpClientResponseException exception = thrown(HttpClientResponseException)
        exception.response.status() == HttpStatus.NOT_FOUND
    }

    def "should return path to treasure if it can be found"() {
        given:
        HttpRequest request = HttpRequest.GET('/treasures/solution?row=1&column=1')
        def treasurePath = [new Coordinate(1, 2), new Coordinate(2, 1)]
        finder.findPath(_ as Coordinate) >> Optional.of(treasurePath)

        when:
        List<Coordinate> coordinates = client.toBlocking().retrieve(request, Argument.of(List, Coordinate))

        then:
        coordinates == treasurePath
    }

    def "should handle invalid coordinates"() {
        given:
        HttpRequest request = HttpRequest.GET('/treasures/solution?row=9&column=9')
        finder.findPath(_ as Coordinate) >> {
            throw new InvalidCoordinateException(new Coordinate(9, 9))
        }

        when:
        client.toBlocking().exchange(request)

        then:
        HttpClientResponseException exception = thrown(HttpClientResponseException)
        exception.response.status() == HttpStatus.BAD_REQUEST
    }

}
