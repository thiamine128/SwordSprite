package cn.thiamine128.swordsprite.client.render.entity.animation;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SwordAnimationHelper {
    public static TransformationInfo getAnimationTransform(String name, SwordAnimation animation, long runningTime, float f, Vec3f v) {
        float runningSeconds = getRunningSeconds(animation, runningTime);

        List<SwordTransformation> transformations = animation.boneAnimations().get(name);

        TransformationInfo transformationInfo = new TransformationInfo(Vec3f.ZERO.copy(), Vec3f.ZERO.copy(), new Vec3f(1.0F, 1.0F, 1.0F));

        transformations.forEach(
                (transformation) -> {
                    SwordKeyframe[] keyframes = transformation.keyframes();
                    int current = Math.max(0, MathHelper.binarySearch(0, keyframes.length, (index) -> {
                        return runningSeconds <= keyframes[index].timestamp();
                    }) - 1);
                    int next = Math.min(keyframes.length - 1, current + 1);
                    SwordKeyframe currentFrame = keyframes[current];
                    SwordKeyframe nextFrame = keyframes[next];
                    float timeDelta = runningSeconds - currentFrame.timestamp();
                    float delta = MathHelper.clamp(timeDelta / (nextFrame.timestamp() - currentFrame.timestamp()), 0.0F, 1.0F);
                    nextFrame.interpolation().apply(v, delta, keyframes, current, next, f);
                    transformation.target().apply(transformationInfo, v);
                }
        );
        return transformationInfo;
    }

    private static float getRunningSeconds(SwordAnimation animation, long runningTime) {
        float f = (float)runningTime / 1000.0F;
        return animation.looping() ? f % animation.lengthInSeconds() : f;
    }
}
