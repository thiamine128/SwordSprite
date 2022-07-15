package cn.thiamine128.swordsprite.client;

import cn.thiamine128.swordsprite.client.gui.screen.ingame.ModScreens;
import cn.thiamine128.swordsprite.client.option.ModKeyBindings;
import cn.thiamine128.swordsprite.client.render.entity.ModEntityRenderers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class SwordSpriteClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModScreens.register();
        ModEntityRenderers.register();

        ModKeyBindings.register();
    }
}
