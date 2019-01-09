package space.bbkr.gluon.mixins;

import me.elucent.earlgray.api.TraitHolder;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.bbkr.gluon.Gluon;

@Mixin(PlayerEntityRenderer.class)
public abstract class MixinPlayerRender extends EntityRenderer {
	public MixinPlayerRender(EntityRenderDispatcher manager) {
		super(manager);
	}

	@Inject(method = "method_4213", at = @At("HEAD"))
	public void renderTitle(AbstractClientPlayerEntity abstractClientPlayerEntity_1, double double_1, double double_2, double double_3, String string_1, double double_4, CallbackInfo ci) {
		if (((TraitHolder) abstractClientPlayerEntity_1).getTraits().hasTrait(Gluon.TITLE)) {
			System.out.println("Trait found!");
			if (!((TraitHolder)abstractClientPlayerEntity_1).getTraits().getTrait(Gluon.TITLE).getValue().equals(""))

			this.renderEntityLabel(abstractClientPlayerEntity_1, (((TraitHolder) abstractClientPlayerEntity_1).getTraits().getTrait(Gluon.TITLE).getValue()), double_1, double_2, double_3, 64);
		}
	}
}
