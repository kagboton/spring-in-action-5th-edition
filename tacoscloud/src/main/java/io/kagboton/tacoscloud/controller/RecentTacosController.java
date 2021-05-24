package io.kagboton.tacoscloud.controller;

import io.kagboton.tacoscloud.domain.Taco;
import io.kagboton.tacoscloud.repository.TacoRepository;
import io.kagboton.tacoscloud.utils.TacoResource;
import io.kagboton.tacoscloud.utils.TacoResourceAssembler;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RepositoryRestController
public class RecentTacosController {

    private TacoRepository tacoRepository;

    public RecentTacosController(TacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }

    @GetMapping(path = "/tacos/recent", produces = "application/hal+json")
    public ResponseEntity<Resources<TacoResource>> getRecentTacos(){

        PageRequest pageRequest = PageRequest.of(0, 12, Sort.by("createdAt").descending());

        List<Taco> tacos = tacoRepository.findAll(pageRequest).getContent();

        List<TacoResource> tacoResources = new TacoResourceAssembler().toResources(tacos);

        Resources<TacoResource> recentResources = new Resources<TacoResource>(tacoResources);

        recentResources.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(RecentTacosController.class).getRecentTacos()).withRel("recents"));

        return new ResponseEntity<>(recentResources, HttpStatus.OK);

    }
}
