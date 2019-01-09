package space.bbkr.gluon;

import me.elucent.earlgray.api.TraitEntry;
import me.elucent.earlgray.api.TraitRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.block.BlockItem;
import net.minecraft.recipe.RecipeSerializers;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import space.bbkr.gluon.AnvilCrusher.UpsideDownAnvilBlock;
import space.bbkr.gluon.TitleScroll.ItemTitleScroll;
import space.bbkr.gluon.TitleScroll.StringTrait;

public class Gluon implements ModInitializer {

    public static final Block ROPE = register("rope", new BlockRope(Block.Settings.of(Material.ORGANIC)), ItemGroup.TOOLS);
    public static final Block UPSIDE_DOWN_ANVIL = register("upside_down_anvil", new UpsideDownAnvilBlock(Block.Settings.of(Material.ANVIL)), ItemGroup.DECORATIONS);
    public static final Item TITLE_SCROLL = register("title_scroll", new ItemTitleScroll(new Item.Settings().itemGroup(ItemGroup.MISC)));
    public static final Item JELLY = register("jelly", new Item(new Item.Settings().itemGroup(ItemGroup.FOOD)));

    public static TraitEntry<StringTrait> TITLE;

    public static Block register(String name, Block block, ItemGroup tab) {
        Registry.register(Registry.BLOCK, "gluon:" + name, block);
        BlockItem item = new BlockItem(block, new Item.Settings().itemGroup(tab));
        item.registerBlockItemMap(Item.BLOCK_ITEM_MAP, item);
        register(name, item);
        return block;
    }

    public static Item register(String name, Item item) {
        Registry.register(Registry.ITEM, "gluon:" + name, item);
        return item;
    }

    @Override
    public void onInitialize() {
        RecipeSerializers.register(AddJellyRecipe.SERIALIZER);
        TITLE = (TraitEntry<StringTrait>) TraitRegistry.register(new Identifier("gluon:title"), StringTrait.class);
        TraitRegistry.addInherent(PlayerEntity.class, (Entity e) -> new StringTrait());
    }
}
