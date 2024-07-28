package com.project.demo.rest.casting;

import com.project.demo.logic.entity.actor.Actor;
import com.project.demo.logic.entity.actor.ActorRepository;
import com.project.demo.logic.entity.casting.Casting;
import com.project.demo.logic.entity.casting.CastingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/casting")
public class CastingRestController {

    @Autowired
    private CastingRepository CastingRepository;
    @Autowired
    private ActorRepository ActorRepository;

    @GetMapping
    public List<Casting> getAllCast() {
        return CastingRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public Casting addCast(@RequestBody Casting casting) {
        return CastingRepository.save(casting);
    }

    @GetMapping("/{id}")
    public Casting getCastById(@PathVariable Long id) {
        return CastingRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public Casting updateCasting(@PathVariable Long id, @RequestBody Casting casting) {
        return CastingRepository.findById(id)
                .map(existingCasting -> {
                    // Actualizar otros campos del casting
                    existingCasting.setName(casting.getName());

                    // Limpiar la lista existente de actores
                    List<Actor> updatedActors = new ArrayList<>();
                    if (casting.getActor() != null) {
                        for (Actor actor : casting.getActor()) {
                            // Asegurarse de que los actores sean nuevos o existentes
                            Actor managedActor = ActorRepository.findById(actor.getId())
                                    .orElseThrow(() -> new RuntimeException("Actor not found with id " + actor.getId()));

                            // Añadir el actor al casting
                            if (!existingCasting.getActor().contains(managedActor)) {
                                updatedActors.add(managedActor);
                                // Sincronizar la relación bidireccional
                                if (managedActor.getCasting() == null) {
                                    managedActor.setCasting(new ArrayList<>());
                                }
                                if (!managedActor.getCasting().contains(existingCasting)) {
                                    managedActor.getCasting().add(existingCasting);
                                }
                            }
                        }
                    }

                    // Asignar los actores actualizados al casting
                    existingCasting.setActor(updatedActors);

                    // Guardar el casting actualizado
                    return CastingRepository.save(existingCasting);
                })
                .orElseThrow(() -> new RuntimeException("Casting not found with id " + id));
    }





    @PutMapping("/{id}/actors")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public Casting addActorsToCasting(@PathVariable Long id, @RequestBody List<Long> actorIds) {
        Optional<Casting> optionalCasting = CastingRepository.findById(id);

        if (optionalCasting.isPresent()) {
            Casting existingCasting = optionalCasting.get();
            List<Actor> actors = ActorRepository.findAllById(actorIds);

            for (Actor actor : actors) {
                if (!existingCasting.getActor().contains(actor)) {
                    existingCasting.getActor().add(actor);
                    actor.getCasting().add(existingCasting);
                }
            }

            CastingRepository.save(existingCasting);
            // Clear the association to prevent circular JSON serialization in Spring
            for (Actor actor : actors) {
                actor.getCasting().clear();
            }
            return existingCasting;

        } else {
            throw new RuntimeException("Casting not found with id " + id);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public void deleteCast(@PathVariable Long id) {
        CastingRepository.deleteById(id);
    }
}
