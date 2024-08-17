package example.micronaut;

import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton // <1>
class DefaultTipoService implements TipoService {

    private final TipoRepository tipoRepository;

    public DefaultTipoService(TipoRepository tipoRepository) {
        this.tipoRepository = tipoRepository;
    }

    public Iterable<Tipo> list() {
        return tipoRepository.findAll();
    }

    public Tipo save(Tipo tipo) {
        if (tipo.getId() == null) {
            return tipoRepository.save(tipo);
        } else {
            return tipoRepository.update(tipo);
        }
    }

    public Optional<Tipo> find(@NonNull String id) {
        return tipoRepository.findById(id);
    }

    public Iterable<Tipo> findByNombreInList(List<String> nombre) {
        return tipoRepository.findByNombreInList(nombre);
    }
}
