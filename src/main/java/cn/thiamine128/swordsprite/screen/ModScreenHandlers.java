package cn.thiamine128.swordsprite.screen;

import cn.thiamine128.swordsprite.SwordSpriteMod;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.registry.Registry;

public class ModScreenHandlers {
    public static final ScreenHandlerType<SwordSpriteScreenHandler> SWORD_SPRITE_SCREEN_HANDLER = new ScreenHandlerType<>(SwordSpriteScreenHandler::new);

    public static void register() {
        Registry.register(Registry.SCREEN_HANDLER, SwordSpriteMod.identify("sword_sprite"), SWORD_SPRITE_SCREEN_HANDLER);
    }
}
