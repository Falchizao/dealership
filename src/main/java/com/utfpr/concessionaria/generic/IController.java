package com.utfpr.concessionaria.generic;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

// A for Response, B for all, C for Request
public abstract class IController <A, B, C>{

    @GetMapping
    public abstract ResponseEntity<List<A>> getAll();

    @GetMapping("/{id}")
    public abstract ResponseEntity<Optional<A>> getById(@PathVariable Long id);

    @PostMapping("/registrar")
    public abstract ResponseEntity<A> add(@RequestBody C model);

    @DeleteMapping("/{id}")
    public abstract B delete(@PathVariable Long id);

    @PutMapping("/{id}")
    public abstract ResponseEntity<A> update(@RequestBody C model, @PathVariable Long id);
}
