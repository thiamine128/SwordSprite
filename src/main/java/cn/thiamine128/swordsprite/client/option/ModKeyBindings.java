package cn.thiamine128.swordsprite.client.option;

import cn.thiamine128.swordsprite.client.networking.ClientNetworkingUtils;
import cn.thiamine128.swordsprite.item.ScabbardItem;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemStack;
import org.lwjgl.glfw.GLFW;

public class ModKeyBindings {
    private static KeyBinding summonKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.swordsprite.summon",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            "category.swordsprite.summon"
    ));

    private static KeyBinding flyKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.swordsprite.fly",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_Y,
            "category.swordsprite.actions"
    ));

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (summonKey.wasPressed()) {
                ClientPlayerEntity player = client.player;
                if (hasSword(player)) {
                    ClientNetworkingUtils.sendTrySummonSwordPacket(player);
                }
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (flyKey.wasPressed()) {
                ClientPlayerEntity player = client.player;
                if (hasSword(player)) {
                    ClientNetworkingUtils.sendRideOnSwordPacket(player);
                }
            }
        });
    }

    public static boolean hasSword(ClientPlayerEntity player) {
        ItemStack itemStack = ScabbardItem.getEquippedItem(player);

        return !ScabbardItem.getSlot(itemStack).isEmpty();
    }
}
