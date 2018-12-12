package space.bbkr.gluon.AnvilCrusher;

import com.google.common.collect.Lists;
import com.google.gson.*;
import com.sun.istack.internal.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class BlockIngredient implements Predicate<BlockState> {

//	private static final Predicate<? super BlockIngredient.Entry> allMatches = (var0) -> {
//		return !var0.getBlocks().stream().allMatch();
//	};
	public static final BlockIngredient EMPTY = new BlockIngredient(Stream.empty());
	private final BlockIngredient.Entry[] entries;
	private Block[] blockArray;
//	private IntList list;

	private BlockIngredient(Stream<? extends BlockIngredient.Entry> stream) {
		this.entries = stream/*.filter(allMatches)*/.toArray(Entry[]::new);
	}

	@Override
	public boolean test(BlockState blockState) {
		return false;
	}

	private void createBlockArray() {
		if (this.blockArray == null) {
			this.blockArray = Arrays.stream(this.entries).flatMap((var0) -> var0.getBlocks().stream()).distinct().toArray(Block[]::new);
		}

	}

	public boolean matches(@Nullable Block block) {
		if (block == null) {
			return false;
		} else {
			this.createBlockArray();

			for(Block curBlock: this.blockArray) {
				if (curBlock == block) {
					return true;
				}
			}

			return false;
		}
	}

	public void write(CustomPacketByteBuf buffer) {
		this.createBlockArray();
		buffer.writeVarInt(this.blockArray.length);

		for(int var2 = 0; var2 < this.blockArray.length; ++var2) {
			buffer.writeBlock(this.blockArray[var2]);
		}

	}

	public JsonElement toJson() {
		if (this.entries.length == 1) {
			return this.entries[0].toJson();
		} else {
			JsonArray array = new JsonArray();
			BlockIngredient.Entry[] entries = this.entries;

			for(BlockIngredient.Entry entry: entries) {
				array.add(entry.toJson());
			}

			return array;
		}
	}

	private static BlockIngredient ofEntries(Stream<? extends BlockIngredient.Entry> stream) {
		BlockIngredient ingredient = new BlockIngredient(stream);
		return ingredient.entries.length == 0 ? EMPTY : ingredient;
	}

	public static BlockIngredient fromTag(Tag<Block> tag) {
		return ofEntries(Stream.of(new BlockIngredient.TagEntry(tag)));
	}

	public static BlockIngredient fromPacket(CustomPacketByteBuf buffer) {
		int var1 = buffer.readVarInt();
		return ofEntries(Stream.generate(() -> {
			return new BlockIngredient.BlockEntry(buffer.readBlock());
		}).limit((long)var1));
	}

	public static BlockIngredient fromJson(@Nullable JsonElement json) {
		if (json != null && !json.isJsonNull()) {
			if (json.isJsonObject()) {
				return ofEntries(Stream.of(entryFromJson(json.getAsJsonObject())));
			} else if (json.isJsonArray()) {
				JsonArray var1 = json.getAsJsonArray();
				if (var1.size() == 0) {
					throw new JsonSyntaxException("Block array cannot be empty, at least one block must be defined");
				} else {
					return ofEntries(StreamSupport.stream(var1.spliterator(), false).map((var0x) -> {
						return entryFromJson(JsonHelper.asObject(var0x, "block"));
					}));
				}
			} else {
				throw new JsonSyntaxException("Expected block to be object or array of objects");
			}
		} else {
			throw new JsonSyntaxException("Block cannot be null");
		}
	}

	public static BlockIngredient.Entry entryFromJson(JsonObject json) {
		if (json.has("block") && json.has("tag")) {
			throw new JsonParseException("An ingredient entry is either a tag or a block, not both");
		} else {
			Identifier id;
			if (json.has("block")) {
				id = new Identifier(JsonHelper.getString(json, "block"));
				Block block = Registry.BLOCKS.get(id);
				if (block == null) {
					throw new JsonSyntaxException("Unknown block '" + id + "'");
				} else {
					return new BlockIngredient.BlockEntry(block);
				}
			} else if (json.has("tag")) {
				id = new Identifier(JsonHelper.getString(json, "tag"));
				Tag<Block> tag = BlockTags.getContainer().get(id);
				if (tag == null) {
					throw new JsonSyntaxException("Unknown item tag '" + id + "'");
				} else {
					return new BlockIngredient.TagEntry(tag);
				}
			} else {
				throw new JsonParseException("An ingredient entry needs either a tag or an item");
			}
		}
	}

	static class TagEntry implements BlockIngredient.Entry {
		private final Tag<Block> tag;

		private TagEntry(Tag<Block> tag) {
			this.tag = tag;
		}

		public Collection<Block> getBlocks() {
			List<Block> list = Lists.newArrayList();
			Iterator itr = this.tag.values().iterator();

			while(itr.hasNext()) {
				Block block = (Block)itr.next();
				list.add(block);
			}

			return list;
		}

		public JsonObject toJson() {
			JsonObject var1 = new JsonObject();
			var1.addProperty("tag", this.tag.getId().toString());
			return var1;
		}
	}

	static class BlockEntry implements BlockIngredient.Entry {
		private final Block block;

		private BlockEntry(Block block) {
			this.block = block;
		}

		public Collection<Block> getBlocks() {
			return Collections.singleton(this.block);
		}

		public JsonObject toJson() {
			JsonObject json = new JsonObject();
			json.addProperty("item", Registry.ITEMS.getId(this.block.getItem()).toString());
			return json;
		}
	}

	interface Entry {
		Collection<Block> getBlocks();

		JsonObject toJson();
	}

}
