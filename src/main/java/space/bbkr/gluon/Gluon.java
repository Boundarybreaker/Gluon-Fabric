package space.bbkr.gluon;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.*;
import net.minecraft.sortme.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Gluon implements ModInitializer {

    public static final BlockRope ROPE = new BlockRope(Block.Builder.create(Material.ORGANIC));
    public static final ItemTitleScroll TITLE_SCROLL = new ItemTitleScroll(new Item.Builder().itemGroup(ItemGroup.MISC));

    @Override
    public void onInitialize() {
        Registry.register(Registry.BLOCKS, Identifier.create("gluon:rope"), ROPE);
        Registry.register(Registry.ITEMS, Identifier.create("gluon:title_scroll"), TITLE_SCROLL);
    }
}
