package space.bbkr.gluon;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.block.BlockRenderLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.ViewableWorld;
import net.minecraft.world.World;

import java.util.Random;

public class BlockRope extends Block {

	public static final BooleanProperty ANCHOR = BooleanProperty.create("anchor");
	public static final BooleanProperty EXTENDED = Properties.EXTENDED;

	public BlockRope(Settings settings) {
		super(settings);
		this.setDefaultState(this.getStateFactory().getDefaultState().with(ANCHOR, false).with(EXTENDED, false));
	}

	@Override
	public VoxelShape getBoundingShape(BlockState state, BlockView view, BlockPos shape) {
		return Block.createCubeShape(6.0,0.0,6.0,10.0,16.0,10.0);
	}
	

//	@Override
//	public VoxelShapeContainer getCollisionShape(BlockState p_getCollisionShape_1_, BlockView p_getCollisionShape_2_, BlockPos p_getCollisionShape_3_) {
//		return Block.createCubeShape(6.05, 0.0, 6.05, 9.95, 16.0, 9.95);
//	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (entity instanceof ItemEntity) return;
		if (entity.field_5976) { //TODO: find out what this actually is
			entity.velocityY = 0.35;
		} else if (entity.isSneaking()) {
			entity.velocityY = 0.08; //Stop, but also counteract EntityLivingBase-applied microgravity
		} else if (entity.velocityY<-0.20) {
			entity.velocityY = -0.20;
		}

	}

	protected void appendProperties(net.minecraft.state.StateFactory.Builder<Block, BlockState> builder) {
		builder.with(ANCHOR);
		builder.with(EXTENDED);
	}

	@Override
	public boolean hasSolidTopSurface(BlockState blockState, BlockView blockView, BlockPos blockPos) {
		return false;
	}



	@Override
	public BlockState getRenderingState(BlockState state, Direction facing, BlockState newState, IWorld world, BlockPos pos, BlockPos posFrom) {
		if (!state.canPlaceAt(world, pos) || !state.get(EXTENDED)) {
			world.getBlockTickScheduler().schedule(pos, this, 2);
			return super.getRenderingState(state, facing, newState, world, pos, posFrom);
		}

		return super.getRenderingState(state, facing, newState, world, pos, posFrom);
	}

	public void scheduledTick(BlockState state, World world, BlockPos pos, Random rand) {
		if (!state.canPlaceAt(world, pos)) {
			world.breakBlock(pos, true);
		}
		if (world.getBlockState(pos.offset(Direction.DOWN)).isAir() && !state.get(EXTENDED)) {
			world.setBlockState(pos.offset(Direction.DOWN), this.getDefaultState());
			world.setBlockState(pos, state.with(EXTENDED, true));
		}
	}


	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		world.getBlockTickScheduler().schedule(pos, this, 2);
		super.onPlaced(world, pos, state, placer, stack);
	}

	public boolean canPlaceAt(BlockState state, ViewableWorld world, BlockPos pos) {
		BlockPos checkPos = pos.offset(Direction.UP);
		BlockState checkBlock = world.getBlockState(checkPos);
		return !checkBlock.isAir() && (checkBlock.isFullBoundsCubeForCulling() || checkBlock.getBlock() == Gluon.ROPE);
	}

	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(ANCHOR, true);
	}

	@Override
	public BlockSoundGroup getSoundGroup(BlockState blockState) {
		return BlockSoundGroup.LADDER;
	}

}
