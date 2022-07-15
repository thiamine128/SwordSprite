package cn.thiamine128.swordsprite.entity;

import net.minecraft.entity.AnimationState;
import net.minecraft.util.math.MathHelper;

import java.util.function.Consumer;

public class SwordAnimationState {
    private static final long STOPPED = 9223372036854775807L;
    private long updatedAt = 9223372036854775807L;
    private long timeRunning;

    public SwordAnimationState() {

    }

    public void start(int age) {
        this.updatedAt = (long)age * 1000L / 20L;
        this.timeRunning = 0L;
    }

    public void startIfNotRunning(int age) {
        if (!this.isRunning()) {
            this.start(age);
        }

    }

    public void stop() {
        this.updatedAt = 9223372036854775807L;
    }

    public void run(Consumer<SwordAnimationState> consumer) {
        if (this.isRunning()) {
            consumer.accept(this);
        }
    }

    public void update(float animationProgress, float speedMultiplier) {
        if (this.isRunning()) {
            long l = MathHelper.lfloor((double)(animationProgress * 1000.0F / 20.0F));
            this.timeRunning += (long)((float)(l - this.updatedAt) * speedMultiplier);
            this.updatedAt = l;
        }
    }

    public long getTimeRunning() {
        return this.timeRunning;
    }

    public boolean isRunning() {
        return this.updatedAt != 9223372036854775807L;
    }
}
