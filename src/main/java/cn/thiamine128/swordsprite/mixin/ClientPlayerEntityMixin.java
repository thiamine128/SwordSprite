package cn.thiamine128.swordsprite.mixin;

import cn.thiamine128.swordsprite.entity.SwordEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.BoatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Shadow private boolean riding;

    @Inject(method = "tickRiding", at = @At(value = "TAIL"))
    private void tickRiding(CallbackInfo ci) {
        ClientPlayerEntity player = (ClientPlayerEntity) ((Object) this);
        if (player.getVehicle() instanceof SwordEntity) {
            SwordEntity sword = (SwordEntity) player.getVehicle();
            sword.setInputs(player.input.pressingLeft, player.input.pressingRight, player.input.pressingForward, player.input.pressingBack);
            this.riding |= player.input.pressingLeft || player.input.pressingRight || player.input.pressingForward || player.input.pressingBack;
        }
    }
}
