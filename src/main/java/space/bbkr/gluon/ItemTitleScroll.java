package space.bbkr.gluon;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ItemTitleScroll extends Item {

	public ItemTitleScroll(Settings settings) {
		super(settings);
	}


	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		if (this.getTranslatedNameTrimmed(player.getActiveItem()).equals(this.getTextComponent())) {
			((ITitle)player).setTitle(this.getTranslatedNameTrimmed(player.getActiveItem()));
		} else {
			((ITitle)player).setTitle(null);
		}
		return super.use(world, player, hand);
	}

}
