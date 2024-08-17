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

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
class FrutaControllerTest {

    @Inject
    FrutaClient frutaClient;

    @Test
    void emptyDatabaseContainsNoFruta() {
        assertEquals(0, StreamSupport.stream(frutaClient.list().spliterator(), false).count());
    }

    @Test
    void testInteractionWithTheController() {
        HttpResponse<Fruta> response = frutaClient.save(new Fruta("banana", null, "https://elnuevopais.net/wp-content/uploads/2017/07/kk.jpg", "dulce"));
        assertEquals(HttpStatus.CREATED, response.getStatus());
        Fruta banana = response.getBody().get();

        Iterable<Fruta> frutas = frutaClient.list();

        List<Fruta> frutaList = StreamSupport.stream(frutas.spliterator(), false).collect(Collectors.toList());
        assertEquals(1, frutaList.size());
        assertEquals(banana.getNombre(), frutaList.get(0).getNombre());
        assertNull(frutaList.get(0).getDescripcion());

        response = frutaClient.save(new Fruta("apple", "Keeps the doctor away", "https://www.recetasnestlecam.com/sites/default/files/2022-04/tipos-de-manzana-royal-gala.jpg", "dulce"));
        assertEquals(HttpStatus.CREATED, response.getStatus());

        frutas = frutaClient.list();
        assertTrue(StreamSupport.stream(frutas.spliterator(), false)
                .anyMatch(f -> "Keeps the doctor away".equals(f.getDescripcion())));

        banana.setDescripcion("Yellow and curved");
        frutaClient.update(banana);

        frutas = frutaClient.list();

        assertEquals(
                Stream.of("Keeps the doctor away", "Yellow and curved").collect(Collectors.toSet()),
                StreamSupport.stream(frutas.spliterator(), false)
                        .map(Fruta::getDescripcion)
                        .collect(Collectors.toSet())
        );
    }

    @Test
    void testSearchWorksAsExpected() {
        frutaClient.save(new Fruta("apple", "Keeps the doctor away", "https://www.recetasnestlecam.com/sites/default/files/2022-04/tipos-de-manzana-royal-gala.jpg", "dulce"));
        frutaClient.save(new Fruta("pineapple", "Delicious", "https://www.gastronomiavasca.net/uploads/image/file/3415/pi_a.jpg", "dulce"));
        frutaClient.save(new Fruta("lemon", "Lemonentary my dear Dr Watson", "https://t1.uc.ltmcdn.com/es/posts/9/7/7/como_aprovechar_al_maximo_un_limon_15779_600.jpg", "dulce"));

        Iterable<Fruta> fruta = frutaClient.query(Arrays.asList("apple", "pineapple"));

        assertTrue(StreamSupport.stream(fruta.spliterator(), false)
                .allMatch(f -> f.getNombre().equals("apple") || f.getNombre().equals("pineapple")));
    }
}
