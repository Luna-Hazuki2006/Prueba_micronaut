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

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.annotation.Status;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

@Controller("/frutas") // <1>
@ExecuteOn(TaskExecutors.BLOCKING)  // <2>
class FrutaController {

    private final FrutaService FrutaService;

    FrutaController(FrutaService FrutaService) {  // <3>
        this.FrutaService = FrutaService;
    }

    @Get  // <4>
    Iterable<Fruta> list() {
        return FrutaService.list();
    }

    @Post // <5>
    @Status(HttpStatus.CREATED) // <6>
    Fruta save(@NonNull @NotNull @Valid Fruta fruta) { // <7>
        return FrutaService.save(fruta);
    }

    @Put
    Fruta update(@NonNull @NotNull @Valid Fruta fruta) {
        return FrutaService.save(fruta);
    }

    @Get("/{id}") // <8>
    Optional<Fruta> find(@PathVariable String id) {
        return FrutaService.find(id);
    }

    @Get("/q") // <9>
    Iterable<Fruta> query(@QueryValue @NotNull List<String> nombres) { // <10>
        return FrutaService.findByNombreInList(nombres);
    }
}
