package space.bbkr.gluon;

import net.minecraft.block.AnvilBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.block.BlockItem;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Facing;
import net.minecraft.world.World;

public class UpsideDownAnvilBlock extends AnvilBlock {

	public UpsideDownAnvilBlock(Builder builder) {
		super(builder);
	}

	@Override
	public void method_10127(World world, BlockPos pos, BlockState state, BlockState landState) {
		world.fireWorldEvent(1031, pos, 0);
		BlockPos landedOnPos = pos.offset(Facing.DOWN);
		Block landedBlock = world.getBlockState(landedOnPos).getBlock();
		Inventory inv = new DummyAnvilInventory(landedBlock);
		Recipe recipe = world.getRecipeManager().get(inv, world);
		if (recipe != null && recipe.matches(inv, world)) {
			if (recipe.getOutput().getItem() instanceof BlockItem && recipe.getOutput().getAmount() == 1) {
				Block blockResult = ((BlockItem) recipe.getOutput().getItem()).getBlock();
				world.setBlockState(landedOnPos, blockResult.getDefaultState());
			} else {
				ItemEntity item = new ItemEntity(world);
				item.setStack(recipe.getOutput());
				world.spawnEntity(item);
				world.setBlockState(landedOnPos, Blocks.AIR.getDefaultState());
			}
		}
	}




}
