package cn.thiamine128.swordsprite.server;

import cn.thiamine128.swordsprite.entity.SwordEntity;
import cn.thiamine128.swordsprite.item.ScabbardItem;
import cn.thiamine128.swordsprite.network.packet.RideOnSwordPacket;
import cn.thiamine128.swordsprite.network.packet.TrySummonSwordC2SPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;

public class ServerNetworkReceivers {
    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(TrySummonSwordC2SPacket.PACKET_ID,
                (server, player, handler, buf, responseSender) -> {
                    ItemStack itemStack = ScabbardItem.getEquippedItem(player);

                    NbtCompound nbt = itemStack.getNbt();

                    if (nbt.contains("Sword")) {
                        ScabbardItem.discardSword(player, itemStack);
                    } else {
                        ScabbardItem.summonSword(player, itemStack);
                    }
                });
        ServerPlayNetworking.registerGlobalReceiver(RideOnSwordPacket.PACKET_ID,
                (server, player, handler, buf, responseSender) -> {
                    ItemStack itemStack = ScabbardItem.getEquippedItem(player);

                    NbtCompound nbt = itemStack.getNbt();

                    if (nbt.contains("Sword")) {
                        ServerWorld world = (ServerWorld) player.world;
                        SwordEntity sword = (SwordEntity) world.getEntity(nbt.getUuid("Sword"));
                        if (sword != null) {
                            player.startRiding(sword, false);
                            sword.setFlying(true);
                        }
                    }
                });
    }
}
