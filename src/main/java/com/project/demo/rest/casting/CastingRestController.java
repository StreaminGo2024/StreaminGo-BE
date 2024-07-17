package com.project.demo.rest.casting;

import com.project.demo.logic.entity.casting.Casting;
import com.project.demo.logic.entity.casting.CastingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/casting")
public class CastingRestController {

    @Autowired
    private CastingRepository CastingRepository;

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
//AGREGAR AUTOR
    /**@PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public Cast updateCast(@PathVariable Long id, @RequestBody Cast cast) {
        return CastRepository.findById(id)
                .map(existingCast -> {
                    existingCast.setActores(cast.getActores());
                    return CastRepository.save(existingCast);
                })
                .orElseGet(() -> {
                    cast.setId(id);
                    return CastRepository.save(cast);
                });
    }*/

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public void deleteCast(@PathVariable Long id) {
        CastingRepository.deleteById(id);
    }
}
