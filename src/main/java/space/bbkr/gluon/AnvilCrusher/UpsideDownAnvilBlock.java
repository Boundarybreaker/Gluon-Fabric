package space.bbkr.gluon.AnvilCrusher;

import net.minecraft.block.AnvilBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Facing;
import net.minecraft.world.World;
import net.minecraft.world.loot.LootSupplier;
import net.minecraft.world.loot.context.LootContext;
import net.minecraft.world.loot.context.LootContextTypes;

import java.util.List;

public class UpsideDownAnvilBlock extends AnvilBlock {

	public UpsideDownAnvilBlock(Settings settings) {
		super(settings);
	}

	@Override
	public void method_10127(World world, BlockPos pos, BlockState state, BlockState landState) {
		world.fireWorldEvent(1031, pos, 0);
		BlockPos landedOnPos = pos.offset(Facing.DOWN);
//		Block landedBlock = world.getBlockState(landedOnPos).getBlock();
		AnvilCrush setup = AnvilCrusherManager.get(world, pos.offset(Facing.DOWN)); //TODO: mixin a getSetupManager?
		System.out.println(setup);
		if (setup != null) {
			Block blockResult = setup.getBlockOutput();
			world.setBlockState(landedOnPos, blockResult.getDefaultState());
			if (setup.getLootOutout() != null) {
				LootSupplier supplier = world.getServer().getLootManager().getSupplier(setup.getLootOutout());
				LootContext.Builder builder = new LootContext.Builder((ServerWorld) world).setRandom(25);
				List<ItemStack> drops = supplier.getDrops(builder.build(LootContextTypes.BLOCK));
				for (ItemStack stack : drops) {
					ItemEntity item = new ItemEntity(world);
					item.setStack(stack);
					world.spawnEntity(item);
				}
			}
		}
	}
}
