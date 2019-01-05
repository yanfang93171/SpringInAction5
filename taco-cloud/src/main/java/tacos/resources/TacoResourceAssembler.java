package tacos.resources;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import tacos.controller.RecentTacosController;
import tacos.model.Taco;

public class TacoResourceAssembler extends ResourceAssemblerSupport<Taco, TacoResource> {

	public TacoResourceAssembler() {

		super(RecentTacosController.class, TacoResource.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public TacoResource instantiateResource(Taco taco) {
		return new TacoResource(taco);

	}

	@Override
	public TacoResource toResource(Taco taco) {
		return createResourceWithId(taco.getId(), taco);
	}

}
