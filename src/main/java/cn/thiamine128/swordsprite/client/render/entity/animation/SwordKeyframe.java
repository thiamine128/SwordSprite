package cn.thiamine128.swordsprite.client.render.entity.animation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public record SwordKeyframe(float timestamp, Vec3f target, SwordTransformation.Interpolation interpolation) {
    public SwordKeyframe(float timestamp, Vec3f target, SwordTransformation.Interpolation interpolation) {
        this.timestamp = timestamp;
        this.target = target;
        this.interpolation = interpolation;
    }

    public float timestamp() {
        return this.timestamp;
    }

    public Vec3f target() {
        return this.target;
    }

    public SwordTransformation.Interpolation interpolation() {
        return this.interpolation;
    }
}
