package cn.thiamine128.swordsprite.network.packet;

import cn.thiamine128.swordsprite.SwordSpriteMod;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class TrySummonSwordC2SPacket {
    public static Identifier PACKET_ID = SwordSpriteMod.identify("try_summon_sword");

    public static PacketByteBuf create(ClientPlayerEntity player) {
        return PacketByteBufs.empty();
    }
}
