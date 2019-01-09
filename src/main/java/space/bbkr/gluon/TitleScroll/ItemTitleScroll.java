package space.bbkr.gluon.TitleScroll;

import me.elucent.earlgray.api.TraitHolder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import space.bbkr.gluon.Gluon;

public class ItemTitleScroll extends Item {

	public ItemTitleScroll(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		if (!world.isClient) {
			if (player.getStackInHand(hand).hasDisplayName()) {
				((TraitHolder) player).getTraits().getTrait(Gluon.TITLE).setValue(player.getStackInHand(hand).getDisplayName().getText());
			} else {
				((TraitHolder) player).getTraits().getTrait(Gluon.TITLE).setValue("");
			}
			((TraitHolder) player).getTraits().markDirty();
		}
		return super.use(world, player, hand);
	}

}
