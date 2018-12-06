package space.bbkr.gluon.mixins;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.bbkr.gluon.ITitle;

@Mixin(PlayerEntityRenderer.class)
public abstract class MixinPlayerRender extends EntityRenderer {
	public MixinPlayerRender(EntityRenderDispatcher manager) {
		super(manager);
	}

	@Inject(method = "method_4213", at = @At("HEAD"))
	public void renderTitle(AbstractClientPlayerEntity p_renderEntityName_1_, double p_renderEntityName_2_, double p_renderEntityName_4_, double p_renderEntityName_6_, String p_renderEntityName_8_, double p_renderEntityName_9, CallbackInfo ci) {
		if (hasTitle((ITitle) p_renderEntityName_1_)) {
			this.renderEntityLabel(p_renderEntityName_1_, ((ITitle) p_renderEntityName_1_).getTitle().toString(), p_renderEntityName_2_, p_renderEntityName_4_, p_renderEntityName_6_, 64);
		}
	}

	private boolean hasTitle(ITitle title) {
		return title.getTitle() != null && !title.getTitle().equals("");
	}
}
