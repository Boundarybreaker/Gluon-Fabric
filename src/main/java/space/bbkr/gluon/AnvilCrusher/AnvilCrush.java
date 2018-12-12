package space.bbkr.gluon.AnvilCrusher;

import com.sun.istack.internal.Nullable;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

public class AnvilCrush {
	private final Identifier id;
	private final BlockIngredient input;
	private final Block blockOutput;
	@Nullable
	private final Identifier lootOutput;

	public AnvilCrush(Identifier id, BlockIngredient input, Block output) {
		this(id, input, output, null);
	}

	public AnvilCrush(Identifier id, BlockIngredient input, Block blockOutput, @Nullable Identifier lootOutput) {
		this.id = id;
		this.input = input;
		this.blockOutput = blockOutput;
		this.lootOutput = lootOutput;
	}

	boolean matches(Block crushedBlock) {
		return input.matches(crushedBlock);
	}

	BlockIngredient getInput() {
		return input;
	}

	Block getBlockOutput() {
		return blockOutput;
	}

	Identifier getLootOutout() {
		return lootOutput;
	}

	Identifier getId() {
		return id;
	}


}
