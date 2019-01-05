package tacos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resources;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import tacos.model.Taco;
import tacos.repository.TacoRepository;
import tacos.resources.TacoResource;
import tacos.resources.TacoResourceAssembler;

@RepositoryRestController
public class RecentTacosController {

	private TacoRepository tacoRepo;

	@Autowired
	public RecentTacosController(TacoRepository tacoRepo) {
		this.tacoRepo = tacoRepo;
	}

	@GetMapping(path = "/tacos/recent", produces = "application/hal+json")
	public ResponseEntity<Resources<TacoResource>> recentTacos() {
		PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
		List<Taco> tacos = tacoRepo.findAll(page).getContent();

		List<TacoResource> tacoResources = new TacoResourceAssembler().toResources(tacos);
		Resources<TacoResource> recentTacos = new Resources<TacoResource>(tacoResources);
		recentTacos.add(linkTo(methodOn(RecentTacosController.class).recentTacos()).withRel("recents"));
		return new ResponseEntity<>(recentTacos, HttpStatus.OK);
	}
}
