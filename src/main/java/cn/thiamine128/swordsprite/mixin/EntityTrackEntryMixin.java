package cn.thiamine128.swordsprite.mixin;

import cn.thiamine128.swordsprite.entity.SwordEntity;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.EntityTrackerEntry;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityTrackerEntry.class)
public class EntityTrackEntryMixin {
    @Shadow private boolean hadVehicle;

    @Shadow @Final private Entity entity;

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;hasVehicle()Z"))
    private boolean checkCondition(Entity instance) {
        return instance.hasVehicle() || (instance instanceof SwordEntity);
    }

    @Redirect(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/server/network/EntityTrackerEntry;hadVehicle:Z", opcode = Opcodes.PUTFIELD, ordinal = 0))
    private void hadVehiclePatch(EntityTrackerEntry instance, boolean value) {
        hadVehicle = entity.hasVehicle();
    }

}
