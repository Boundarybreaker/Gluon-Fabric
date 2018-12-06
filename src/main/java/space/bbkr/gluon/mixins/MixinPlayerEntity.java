package space.bbkr.gluon.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.TextComponent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import space.bbkr.gluon.ITitle;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends Entity implements ITitle {
	public MixinPlayerEntity(EntityType<?> type, World world) {
		super(type, world);
	}

	private TextComponent title = null;

	public TextComponent getTitle() {
		return title;
	}

	public void setTitle(TextComponent newTitle) {
		this.title = newTitle;
	}

//	public boolean hasTitle(ITitle title) {
//		return title.getTitle() != null && !title.getTitle().equals("");
//	}
}
