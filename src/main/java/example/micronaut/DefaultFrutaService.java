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
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton // <1>
class DefaultFrutaService implements FrutaService {

    private final FrutaRepository frutaRepository;

    public DefaultFrutaService(FrutaRepository frutaRepository) {
        this.frutaRepository = frutaRepository;
    }

    public Iterable<Fruta> list() {
        return frutaRepository.findAll();
    }

    public Fruta save(Fruta fruta) {
        if (fruta.getId() == null) {
            return frutaRepository.save(fruta);
        } else {
            return frutaRepository.update(fruta);
        }
    }

    public Optional<Fruta> find(@NonNull String id) {
        return frutaRepository.findById(id);
    }

    public Iterable<Fruta> findByNombreInList(List<String> nombre) {
        return frutaRepository.findByNombreInList(nombre);
    }
}
