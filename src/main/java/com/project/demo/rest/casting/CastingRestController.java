package com.project.demo.rest.casting;

import com.project.demo.logic.entity.actor.Actor;
import com.project.demo.logic.entity.actor.ActorRepository;
import com.project.demo.logic.entity.casting.Casting;
import com.project.demo.logic.entity.casting.CastingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
                    existingCasting.setName(casting.getName());
                    existingCasting.setActor(casting.getActor());
                    return CastingRepository.save(existingCasting);
                })
                .orElseGet(() -> {
                    casting.setId(id);
                    return CastingRepository.save(casting);
                });
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
