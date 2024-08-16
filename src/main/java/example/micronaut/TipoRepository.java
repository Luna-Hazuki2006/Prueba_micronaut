package example.micronaut;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;

@MongoRepository // <1>
public interface TipoRepository extends CrudRepository<Tipo, String> {

    @NonNull
    Iterable<Tipo> findByNombreInList(@NonNull List<String> nombres); // <2>
    
}
