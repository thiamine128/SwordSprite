package cn.thiamine128.swordsprite.client.gui.screen.ingame;

import cn.thiamine128.swordsprite.screen.ModScreenHandlers;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class ModScreens {
    public static void register() {
        HandledScreens.register(ModScreenHandlers.SCABBARD_SCREEN_HANDLER, SwordSpriteScreen::new);
    }
}
