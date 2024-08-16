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
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

import jakarta.validation.constraints.NotBlank;

@MappedEntity(value = "frutas") // <1>
public class Fruta {

    @Id // <2>
    @GeneratedValue
    private String id;

    @NonNull
    @NotBlank // <3>
    private final String nombre;

    @Nullable
    private String descripcion;

    @Nullable
    private String foto;

    @NonNull
    @NotBlank
    private final String tipo;

    public Fruta(@NonNull String nombre, @Nullable String descripcion, @NonNull String foto, @NonNull String tipo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.foto = foto;
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NonNull
    public String getNombre() {
        return nombre;
    }

    @Nullable
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(@Nullable String descripcion) {
        this.descripcion = descripcion;
    }

    @Nullable
    public String getFoto() {
        return foto;
    }

    public void setFoto(@Nullable String foto) {
        this.foto = foto;
    }

    @NonNull
    public String getTipo() {
        return tipo;
    }

}
