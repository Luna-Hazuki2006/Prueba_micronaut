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
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Client("/frutas")
interface FrutaClient {

    @Get
    Iterable<Fruta> list();

    @Get("/{id}")
    Optional<Fruta> find(@PathVariable String id);

    @Get("/q")
    Iterable<Fruta> query(@QueryValue @NotNull List<String> names);

    @Post
    HttpResponse<Fruta> save(Fruta fruta);

    @Put
    Fruta update(Fruta fruta);
}
