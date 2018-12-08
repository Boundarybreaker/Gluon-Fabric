package space.bbkr.gluon;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TextComponent;

public class DummyAnvilInventory implements Inventory {

	private ItemStack dummyItem;

	public DummyAnvilInventory(Block target) {
		dummyItem = new ItemStack(target, 1);
	}

	@Override
	public int getInvSize() {
		return 1;
	}

	@Override
	public boolean isInvEmpty() {
		return false;
	}

	@Override
	public ItemStack getInvStack(int i) {
		return dummyItem;
	}

	@Override
	public ItemStack takeInvStack(int i, int i1) {
		return null;
	}

	@Override
	public ItemStack removeInvStack(int i) {
		return null;
	}

	@Override
	public void setInvStack(int i, ItemStack itemStack) {

	}

	@Override
	public int getInvMaxStackAmount() {
		return 1;
	}

	@Override
	public void markDirty() {

	}

	@Override
	public boolean canPlayerUseInv(PlayerEntity playerEntity) {
		return false;
	}

	@Override
	public void onInvOpen(PlayerEntity playerEntity) {

	}

	@Override
	public void onInvClose(PlayerEntity playerEntity) {

	}

	@Override
	public boolean isValidInvStack(int i, ItemStack itemStack) {
		return false;
	}

	@Override
	public int getInvProperty(int i) {
		return 0;
	}

	@Override
	public void setInvProperty(int i, int i1) {

	}

	@Override
	public int getInvPropertyCount() {
		return 0;
	}

	@Override
	public void clearInv() {

	}

	@Override
	public TextComponent getName() {
		return null;
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}
}
