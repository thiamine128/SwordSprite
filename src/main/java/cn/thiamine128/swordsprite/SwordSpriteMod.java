package cn.thiamine128.swordsprite;

import cn.thiamine128.swordsprite.entity.ModEntityTypes;
import cn.thiamine128.swordsprite.items.ModItems;
import cn.thiamine128.swordsprite.screen.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class SwordSpriteMod implements ModInitializer {
    public static final String MODID = "swordsprite";
    @Override
    public void onInitialize() {
        ModItems.register();
        ModScreenHandlers.register();
    }

    public static Identifier identify(String name) {
        return new Identifier(MODID, name);
    }
}
