package space.bbkr.gluon.AnvilCrusher;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.PacketByteBuf;

public class CustomPacketByteBuf extends PacketByteBuf {
	public CustomPacketByteBuf(ByteBuf buffer) {
		super(buffer);
	}

	public PacketByteBuf writeBlock(Block block) {
		return this.writeVarInt(Block.getRawIdFromState(block.getDefaultState()));
	}

	public Block readBlock() {
		return Block.getStateFromRawId(this.readVarInt()).getBlock();
	}

}
