package cn.thiamine128.swordsprite.items;

import cn.thiamine128.swordsprite.SwordSpriteMod;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(
            SwordSpriteMod.identify("sword_sprite"),
            () -> new ItemStack(Items.DIAMOND_SWORD)
    );

    public static final SwordSpriteItem SWORD_SPRITE = new SwordSpriteItem(
            new FabricItemSettings().group(ITEM_GROUP)
    );

    public static void register() {
        Registry.register(Registry.ITEM, SwordSpriteMod.identify("sword_sprite"), SWORD_SPRITE);
    }
}
