package space.bbkr.gluon.AnvilCrusher;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.loot.LootTables;

public class AnvilCrushSerializer {
	
	public AnvilCrushSerializer() {
	}

	public static AnvilCrush read(Identifier id, JsonObject json) {
		BlockIngredient input;
		if (JsonHelper.isArray(json, "ingredient")) {
			input = BlockIngredient.fromJson(JsonHelper.getArray(json, "ingredient"));
		} else {
			input = BlockIngredient.fromJson(JsonHelper.getObject(json, "ingredient"));
		}

		String result = JsonHelper.getString(json, "result_block");
		Block resultBlock = Registry.BLOCKS.get(new Identifier(result));
		Identifier resultLoot = new Identifier(JsonHelper.getString(json, "result_loot"));
		if (resultLoot != LootTables.EMPTY) {
			return new AnvilCrush(id, input, resultBlock, resultLoot);
		} else {
			return new AnvilCrush(id, input, resultBlock, null);
		}
	}

	public AnvilCrush read(Identifier id, CustomPacketByteBuf buffer) {
		BlockIngredient input = BlockIngredient.fromPacket(buffer);
		Block output = buffer.readBlock();
		Identifier lootOutput = buffer.readIdentifier();
		return new AnvilCrush(id, input, output, lootOutput);
	}

	public void write(CustomPacketByteBuf buffer, AnvilCrush crush) {
		crush.getInput().write(buffer);
		buffer.writeBlock(crush.getBlockOutput());
		if (crush.getLootOutout() != null) buffer.writeIdentifier(crush.getLootOutout());
		else buffer.writeIdentifier(LootTables.EMPTY);
	}

}
