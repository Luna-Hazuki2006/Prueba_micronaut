/*
 * Copyright 2017-2024 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.micronaut;

import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.junit.jupiter.api.Test;

import jakarta.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
@Property(name = "spec.name", value = "controller-isolation")
public class ControllerIsolationTest {

    @Inject
    @Client("/")
    HttpClient httpClient;

    @Test
    void checkSerialization() {
        MutableHttpRequest<Object> get = HttpRequest.GET("/frutas");
        HttpResponse<List<Fruta>> response = httpClient.toBlocking().exchange(get, Argument.listOf(Fruta.class));

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().get(HttpHeaders.CONTENT_TYPE));
        assertTrue(response.getBody().isPresent());

        String all = response.getBody().get().stream().map(f -> f.getNombre() + ":" + f.getDescripcion()).collect(Collectors.joining(","));
        assertEquals("apple:red,banana:yellow", all);
    }

    @Singleton
    @Replaces(DefaultFrutaService.class)
    @Requires(property = "spec.name", value = "controller-isolation")
    static class MockService implements FrutaService {

        @Override
        public Iterable<Fruta> list() {
            return Arrays.asList(
                    new Fruta("apple", "red"),
                    new Fruta("banana", "yellow")
            );
        }

        @Override
        public Fruta save(Fruta fruta) {
            return fruta;
        }

        @Override
        public Optional<Fruta> find(@NotNull String id) {
            return Optional.empty();
        }

        @Override
        public Iterable<Fruta> findByNombreInList(List<String> nombre) {
            return Collections.emptyList();
        }
    }
}
