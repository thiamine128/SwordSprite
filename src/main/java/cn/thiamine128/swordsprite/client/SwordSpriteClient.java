package cn.thiamine128.swordsprite.client;

import cn.thiamine128.swordsprite.client.gui.screen.ingame.ModScreens;
import cn.thiamine128.swordsprite.client.render.entity.ModEntityRenderers;
import net.fabricmc.api.ClientModInitializer;

public class SwordSpriteClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModScreens.register();
        ModEntityRenderers.register();
    }
}
