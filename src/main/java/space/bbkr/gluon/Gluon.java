package space.bbkr.gluon;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.block.BlockItem;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeSerializers;
import net.minecraft.sortme.ItemGroup;
import net.minecraft.util.registry.Registry;

public class Gluon implements ModInitializer {

    public static final Block ROPE = register("rope", new BlockRope(Block.Builder.create(Material.ORGANIC)), ItemGroup.TOOLS);
    public static final Block UPSIDE_DOWN_ANVIL = register("upside_down_anvil", new UpsideDownAnvilBlock(Block.Builder.create(Material.ANVIL)), ItemGroup.DECORATIONS);
    public static final Item TITLE_SCROLL = register("title_scroll", new ItemTitleScroll(new Item.Builder().itemGroup(ItemGroup.MISC)));
    public static final RecipeSerializer<UpsideDownAnvilRecipe> CRUSHING = RecipeSerializers.register(new UpsideDownAnvilRecipe.Serializer());

    public static Block register(String name, Block block, ItemGroup tab) {
        Registry.register(Registry.BLOCKS, "gluon:" + name, block);
        BlockItem item = new BlockItem(block, new Item.Builder().itemGroup(tab));
        item.registerBlockItemMap(Item.BLOCK_ITEM_MAP, item);
        register(name, item);
        return block;
    }

    public static Item register(String name, Item item) {
        Registry.register(Registry.ITEMS, "gluon:" + name, item);
        return item;
    }

    @Override
    public void onInitialize() {

    }
}
