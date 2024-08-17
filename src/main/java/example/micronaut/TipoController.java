package example.micronaut;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.annotation.RequestAttribute;
import io.micronaut.http.annotation.Status;
import io.micronaut.runtime.http.scope.RequestScope;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

@Controller("/tipos") // <1>
@ExecuteOn(TaskExecutors.BLOCKING)  // <2>
class TipoController {

    private final TipoService tipoService;

    TipoController(TipoService tipoService) {  // <3>
        this.tipoService = tipoService;
    }

    @Get  // <4>
    Iterable<Tipo> list() {
        return tipoService.list();
    }


    // @Post // <5>
    @Post(consumes = "application/x-www-form-urlencoded")
    @Status(HttpStatus.CREATED) // <6>
    Tipo save(String nombre, String descripcion) { // <7>
        Tipo tipo = new Tipo(nombre, descripcion);
        return tipoService.save(tipo);
    }

    @Put
    Tipo update(@NonNull @NotNull @Valid Tipo tipo) {
        return tipoService.save(tipo);
    }

    @Get("/{id}") // <8>
    Optional<Tipo> find(@PathVariable String id) {
        return tipoService.find(id);
    }

    @Get("/q") // <9>
    Iterable<Tipo> query(@QueryValue @NotNull List<String> nombres) { // <10>
        return tipoService.findByNombreInList(nombres);
    }
}
