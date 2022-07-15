package cn.thiamine128.swordsprite.entity.swordaction;

import cn.thiamine128.swordsprite.client.render.entity.animation.SwordAnimation;
import cn.thiamine128.swordsprite.client.render.entity.animation.SwordAnimations;
import cn.thiamine128.swordsprite.entity.SwordEntity;
import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.minecraft.util.math.Vec3d;

public class IdleAction extends AbstractSwordAction {
    protected int circulation = 40;

    public IdleAction(SwordEntity sword) {
        super(sword);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.actionTicks >= this.circulation)
            this.actionTicks = 0;
    }

    @Override
    public int getAnimationId() {
        return 0;
    }

    @Override
    public SwordAnimation getAnimation() {
        return SwordAnimations.IDLE;
    }

    @Override
    public Vec3d getRelativePos(float tickDelta) {
        return Vec3d.ZERO;
    }

    @Override
    public Vec3d getRelativeRotation(float tickDelta) {
        return Vec3d.ZERO;
    }
}
