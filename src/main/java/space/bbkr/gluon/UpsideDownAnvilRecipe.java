package space.bbkr.gluon;

import com.google.gson.JsonObject;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class UpsideDownAnvilRecipe implements Recipe {

	private final Identifier id;
	private final String group;
	private final Ingredient input;
	private final ItemStack output;

	public UpsideDownAnvilRecipe(Identifier id, String group, Ingredient input, ItemStack output ) {
		this.id = id;
		this.group = group;
		this.input = input;
		this.output = output;
	}

	@Override
	public boolean matches(Inventory inv, World world) {
		return inv instanceof DummyAnvilInventory && input.test(inv.getInvStack(0));
	}

	@Override
	public ItemStack craft(Inventory inventory) {
		return this.output.copy();
	}

	@Override
	public boolean fits(int i, int i1) {
		return true;
	}

	@Override
	public ItemStack getOutput() {
		return output;
	}

	@Override
	public Identifier getId() {
		return this.id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return Gluon.CRUSHING;
	}

	public static class Serializer implements RecipeSerializer<UpsideDownAnvilRecipe> {
		public Serializer() {
		}

		public UpsideDownAnvilRecipe read(Identifier id, JsonObject json) {
			String group = JsonHelper.getString(json, "group", "");
			Ingredient input;
			if (JsonHelper.isArray(json, "ingredient")) {
				input = Ingredient.fromJson(JsonHelper.getArray(json, "ingredient"));
			} else {
				input = Ingredient.fromJson(JsonHelper.getArray(json, "ingredient"));
			}

			String result = JsonHelper.getString(json, "result");
			Item resultItem = Registry.ITEMS.get(new Identifier(result));
			if (resultItem != null) {
				ItemStack output = new ItemStack(resultItem);
				return new UpsideDownAnvilRecipe(id, group, input, output);
			} else {
				throw new IllegalStateException(result + " did not exist");
			}
		}

		public UpsideDownAnvilRecipe read(Identifier id, PacketByteBuf buffer) {
			String group = buffer.readString(32767);
			Ingredient input = Ingredient.fromPacket(buffer);
			ItemStack output = buffer.readItemStack();
			return new UpsideDownAnvilRecipe(id, group, input, output);
		}

		public void write(PacketByteBuf buffer, UpsideDownAnvilRecipe recipe) {
			buffer.writeString(recipe.group);
			recipe.input.write(buffer);
			buffer.writeItemStack(recipe.output);
		}

		public String getId() {
			return "crushing";
		}
	}
}