package space.bbkr.gluon;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.block.BlockItem;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeSerializers;
import net.minecraft.util.registry.Registry;
import space.bbkr.gluon.AnvilCrusher.UpsideDownAnvilBlock;

public class Gluon implements ModInitializer {

    public static final Block ROPE = register("rope", new BlockRope(Block.Settings.create(Material.ORGANIC)), ItemGroup.TOOLS);
    public static final Block UPSIDE_DOWN_ANVIL = register("upside_down_anvil", new UpsideDownAnvilBlock(Block.Settings.create(Material.ANVIL)), ItemGroup.DECORATIONS);
    public static final Item TITLE_SCROLL = register("title_scroll", new ItemTitleScroll(new Item.Settings().itemGroup(ItemGroup.MISC)));
    public static final RecipeSerializer<UpsideDownAnvilRecipe> CRUSHING = RecipeSerializers.register(new UpsideDownAnvilRecipe.Serializer());

    public static Block register(String name, Block block, ItemGroup tab) {
        Registry.register(Registry.BLOCKS, "gluon:" + name, block);
        BlockItem item = new BlockItem(block, new Item.Settings().itemGroup(tab));
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
