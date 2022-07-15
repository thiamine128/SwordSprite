package cn.thiamine128.swordsprite.screen;

import cn.thiamine128.swordsprite.SwordSpriteMod;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.registry.Registry;

public class ModScreenHandlers {
    public static final ScreenHandlerType<ScabbardScreenHandler> SCABBARD_SCREEN_HANDLER = new ScreenHandlerType<>(ScabbardScreenHandler::new);

    public static void register() {
        Registry.register(Registry.SCREEN_HANDLER, SwordSpriteMod.identify("sword_sprite"), SCABBARD_SCREEN_HANDLER);
    }
}
