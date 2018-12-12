package space.bbkr.gluon;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.FoodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.AbstractRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class AddJellyRecipe extends AbstractRecipe {

	private int foodSlot = 0;

	public AddJellyRecipe(Identifier id) {
		super(id);
	}

	public AddJellyRecipe(Object o) {
		super((Identifier)o);
	}

	@Override
	public boolean matches(Inventory inv, World world) {
		ItemStack targetFood = ItemStack.EMPTY;
		ItemStack targetJelly = ItemStack.EMPTY;
		if (!(inv instanceof CraftingInventory)) return false;
		else {
			for (int i = 0; i < inv.getInvMaxStackAmount(); i++) {
				ItemStack stack = inv.getInvStack(i);
				if (!stack.isEmpty()) {
					if (stack.getItem() instanceof FoodItem) {
						if (!targetFood.isEmpty()) return false;
						targetFood = stack;
						foodSlot = i;
						System.out.println(foodSlot);
					} else if (stack.getItem() == Gluon.JELLY) {
						if (targetJelly.isEmpty()) return false;
						targetJelly = stack;
					} else {
						return false;
					}
				}
			}
		}
		if (!targetFood.hasTag()) return true;
		else return !targetFood.getTag().containsKey("jellied");
	}

	@Override
	public ItemStack craft(Inventory inv) {
		ItemStack food = inv.getInvStack(foodSlot);
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
		return Gluon.JELLY_ADDING;
	}
}
