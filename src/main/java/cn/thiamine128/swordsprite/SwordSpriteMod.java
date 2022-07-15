package cn.thiamine128.swordsprite;

import cn.thiamine128.swordsprite.item.ModItems;
import cn.thiamine128.swordsprite.screen.ModScreenHandlers;
import cn.thiamine128.swordsprite.server.ServerNetworkReceivers;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class SwordSpriteMod implements ModInitializer {
    public static final String MODID = "swordsprite";
    @Override
    public void onInitialize() {
        ModItems.register();
        ModScreenHandlers.register();

        ServerNetworkReceivers.register();
    }

    public static Identifier identify(String name) {
        return new Identifier(MODID, name);
    }
}
