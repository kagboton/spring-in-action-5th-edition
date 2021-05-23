package io.kagboton.tacoscloud.utils;

        import io.kagboton.tacoscloud.controller.DesignTacoController;
        import io.kagboton.tacoscloud.domain.Taco;
        import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

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
        return createResourceWithId(taco.getId(), taco);
    }
}
