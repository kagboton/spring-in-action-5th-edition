package io.kagboton.tacoscloud.controller;

import io.kagboton.tacoscloud.domain.Taco;
import io.kagboton.tacoscloud.repository.TacoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/design", produces = "application/json")
@CrossOrigin(origins = "*")
public class DesignTacoController {

    private TacoRepository tacoRepository;

    @Autowired
    public DesignTacoController(TacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco createTaco(@RequestBody Taco taco){
        return tacoRepository.save(taco);
    }

    @GetMapping("/recent")
    public Iterable<Taco> getRecentTacos(){
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending()); // page request object
        return tacoRepository.findAll(page).getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> getTacoById(@PathVariable("id") Long id){
        Optional<Taco> optionalTaco = tacoRepository.findById(id);
        if(optionalTaco.isPresent()){
            return new ResponseEntity<>(optionalTaco.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}
