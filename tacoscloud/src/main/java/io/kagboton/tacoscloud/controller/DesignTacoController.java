package io.kagboton.tacoscloud.controller;

import io.kagboton.tacoscloud.domain.Taco;
import io.kagboton.tacoscloud.repository.TacoRepository;

import io.kagboton.tacoscloud.utils.TacoResource;
import io.kagboton.tacoscloud.utils.TacoResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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
    public Resources<Resource<Taco>> getRecentTacos(){
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending()); // page request object

        List<Taco> tacos = tacoRepository.findAll(page).getContent(); // get the recent tacos list

        Resources<Resource<Taco>> recentResources = Resources.wrap(tacos);

        recentResources.add(
                ControllerLinkBuilder.linkTo(DesignTacoController.class)
                        .slash("recent")
                        .withRel("recents")); // dynamic create links with ControllerLinkBuilder

        return recentResources;
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
