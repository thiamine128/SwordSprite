package cn.thiamine128.swordsprite.client.networking;

import cn.thiamine128.swordsprite.network.packet.RideOnSwordPacket;
import cn.thiamine128.swordsprite.network.packet.TrySummonSwordC2SPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.network.ClientPlayerEntity;

public class ClientNetworkingUtils {
    public static void sendTrySummonSwordPacket(ClientPlayerEntity player) {
        ClientPlayNetworking.send(TrySummonSwordC2SPacket.PACKET_ID, TrySummonSwordC2SPacket.create(player));
    }

    public static void sendRideOnSwordPacket(ClientPlayerEntity player) {
        ClientPlayNetworking.send(RideOnSwordPacket.PACKET_ID, RideOnSwordPacket.create(player));
    }
}
