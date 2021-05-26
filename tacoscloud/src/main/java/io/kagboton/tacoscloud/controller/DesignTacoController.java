package io.kagboton.tacoscloud.controller;

import io.kagboton.tacoscloud.domain.Taco;
import io.kagboton.tacoscloud.repository.TacoRepository;

import io.kagboton.tacoscloud.utils.resources.TacoResource;
import io.kagboton.tacoscloud.utils.resources.TacoResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
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
    public Resources<TacoResource> getRecentTacos(){
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending()); // page request object

        List<Taco> tacos = tacoRepository.findAll(page).getContent(); // get the recent tacos list

        List<TacoResource> tacoResources = new TacoResourceAssembler().toResources(tacos); // convert our Taco objects list to TacoResource objects list using TacoResourceAssembler

        Resources<TacoResource> recentResources =  new Resources<TacoResource>(tacoResources);

        recentResources.add(
                linkTo(methodOn(DesignTacoController.class).getRecentTacos())
                        .withRel("recents") // dynamically populate our Resources<TacoResource> with the recents links
        );

        return recentResources; // return resources of tacoResources to take advantage of our new TacoResource type
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
