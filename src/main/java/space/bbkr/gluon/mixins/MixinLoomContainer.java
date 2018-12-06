package space.bbkr.gluon.mixins;

import net.minecraft.container.LoomContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(LoomContainer.class)
public abstract class MixinLoomContainer {

	public MixinLoomContainer() {

	}

	@ModifyConstant(method = "onContentChanged", constant = @Constant(intValue = 6))
	private int changeBannerPatternLimit(int orig) {
		return 16;
	}
}
