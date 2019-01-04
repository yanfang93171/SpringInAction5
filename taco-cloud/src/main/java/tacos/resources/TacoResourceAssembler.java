package tacos.resources;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import tacos.controller.DesignTacoController;
import tacos.model.Taco;

public class TacoResourceAssembler extends ResourceAssemblerSupport<Taco, TacoResource> {

	public TacoResourceAssembler() {
		super(DesignTacoController.class, TacoResource.class);
	}

	@Override
	protected TacoResource instantiateResource(Taco taco) {
		return new TacoResource(taco);
	}

	@Override
	public TacoResource toResource(Taco taco) {
		// TODO Auto-generated method stub
		return createResourceWithId(taco.getId(), taco);
	}

}
