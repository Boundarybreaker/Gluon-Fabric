package space.bbkr.gluon;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.FoodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.AbstractRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeSerializers;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class AddJellyRecipe extends AbstractRecipe {

	private int foodSlot = 0;

	public AddJellyRecipe(Identifier id) {
		super(id);
	}

	@Override
	public boolean matches(Inventory inv, World world) {
		ItemStack targetFood = ItemStack.EMPTY;
		boolean hasJelly = false;
		if (!(inv instanceof CraftingInventory)) return false;
		else {
			for (int i = 0; i < inv.getInvSize(); i++) {
				ItemStack stack = inv.getInvStack(i);
				if (!stack.isEmpty()) {
					if (stack.getItem() instanceof FoodItem) {
						if (!targetFood.isEmpty()) return false;
						targetFood = stack;
						foodSlot = i;
					} else if (stack.getItem() == Gluon.JELLY) {
						if (hasJelly) return false;
						hasJelly = true;
					} else {
						return false;
					}
				}
			}
		}
//		if (!targetFood.hasTag()) targetFood.setTag(new CompoundTag());
		return (!targetFood.hasTag() || !targetFood.getTag().containsKey("jellied")) && hasJelly;
	}

	@Override
	public ItemStack craft(Inventory inv) {
		ItemStack food = inv.getInvStack(foodSlot).copy();
		if (!food.hasTag()) food.setTag(new CompoundTag());
		CompoundTag tag = food.getTag().copy();
		tag.putByte("jellied", (byte)0);
		food.setTag(tag);
		return food;
	}

	@Override
	public boolean fits(int x, int y) {
		return x >= 2 && y >= 2;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}

	public static final RecipeSerializer<?> SERIALIZER = new RecipeSerializers.Dummy<>(
			"gluon:crafting_special_jellyadding", AddJellyRecipe::new
	);
}
