package cn.thiamine128.swordsprite.entity.swordaction;

import cn.thiamine128.swordsprite.client.render.entity.animation.SwordAnimation;
import cn.thiamine128.swordsprite.entity.SwordEntity;
import net.minecraft.util.math.Vec3d;

public abstract class AbstractSwordAction {
    protected SwordEntity sword;
    protected int actionTicks;

    public AbstractSwordAction(SwordEntity sword) {
        this.sword = sword;
        this.actionTicks = 0;
    }

    public abstract boolean isFinished();

    public void tick() {
        this.actionTicks ++;
    }

    public abstract int getAnimationId();
    public abstract SwordAnimation getAnimation();
    public abstract Vec3d getRelativePos(float tickDelta);
    public abstract Vec3d getRelativeRotation(float tickDelta);
}
