package cn.thiamine128.swordsprite.client.render.entity.animation;

import net.minecraft.util.math.Vec3f;

import java.util.HashMap;
import java.util.Map;

public class SwordAnimations {
    public static Map<Integer, SwordAnimation> ANIMATIONS = new HashMap<>();
    public static final SwordAnimation IDLE = SwordAnimation.Builder.create(2.0f)
            .looping()
            .addBoneAnimation("root", new SwordTransformation(SwordTransformation.Targets.TRANSLATE,
                    new SwordKeyframe[]{
                            new SwordKeyframe(0.0F, new Vec3f(0.0F, 0.0F, 0.0F), SwordTransformation.Interpolations.OTHER),
                            new SwordKeyframe(1.0F, new Vec3f(0.0F, 0.1F, 0.0F), SwordTransformation.Interpolations.OTHER),
                            new SwordKeyframe(2.0F, new Vec3f(0.0F, 0.0F, 0.0F), SwordTransformation.Interpolations.OTHER)
                    }))
            .build();

    static {
        ANIMATIONS.put(0, IDLE);
    }
}
