package cn.thiamine128.swordsprite.client.render.entity.animation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public record SwordTransformation(SwordTransformation.Target target, SwordKeyframe... keyframes) {
    public SwordTransformation(SwordTransformation.Target target, SwordKeyframe... keyframes) {
        this.target = target;
        this.keyframes = keyframes;
    }

    public SwordTransformation.Target target() {
        return this.target;
    }

    public SwordKeyframe[] keyframes() {
        return this.keyframes;
    }

    @Environment(EnvType.CLIENT)
    public interface Target {
        void apply(TransformationInfo info, Vec3f vec3f);
    }

    @Environment(EnvType.CLIENT)
    public interface Interpolation {
        Vec3f apply(Vec3f vec3f, float delta, SwordKeyframe[] keyframes, int start, int end, float f);
    }

    @Environment(EnvType.CLIENT)
    public class Targets {
        public static final SwordTransformation.Target TRANSLATE = TransformationInfo::translate;
        public static final SwordTransformation.Target ROTATE = TransformationInfo::rotate;
        public static final SwordTransformation.Target SCALE = TransformationInfo::scale;
    }

    @Environment(EnvType.CLIENT)
    public static class Interpolations {
        public static final SwordTransformation.Interpolation LINEAR = (v, delta, keyframes, start, end, multiplier) -> {
            Vec3f startV = keyframes[start].target();
            Vec3f endV = keyframes[end].target();
            v.set(MathHelper.lerp(delta, startV.getX(), endV.getX()) * multiplier, MathHelper.lerp(delta, startV.getY(), endV.getY()) * multiplier, MathHelper.lerp(delta, startV.getZ(), endV.getZ()) * multiplier);
            return v;
        };
        public static final SwordTransformation.Interpolation OTHER = (v, delta, keyframes, start, end, multiplier) -> {
            Vec3f lastV = keyframes[Math.max(0, start - 1)].target();
            Vec3f startV = keyframes[start].target();
            Vec3f endV = keyframes[end].target();
            Vec3f nextV = keyframes[Math.min(keyframes.length - 1, end + 1)].target();
            v.set(MathHelper.method_41303(delta, lastV.getX(), startV.getX(), endV.getX(), nextV.getX()) * multiplier, MathHelper.method_41303(delta, lastV.getY(), startV.getY(), endV.getY(), nextV.getY()) * multiplier, MathHelper.method_41303(delta, lastV.getZ(), startV.getZ(), endV.getZ(), nextV.getZ()) * multiplier);
            return v;
        };
    }
}
