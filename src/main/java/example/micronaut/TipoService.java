package example.micronaut;

import io.micronaut.core.annotation.NonNull;

import java.util.List;
import java.util.Optional;

interface TipoService {

    Iterable<Tipo> list();

    Tipo save(Tipo tipo);

    Optional<Tipo> find(@NonNull String id);

    Iterable<Tipo> findByNombreInList(List<String> nombre);
    
}
