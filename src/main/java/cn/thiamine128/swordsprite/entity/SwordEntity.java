package cn.thiamine128.swordsprite.entity;

import cn.thiamine128.swordsprite.client.render.entity.animation.SwordAnimations;
import cn.thiamine128.swordsprite.entity.swordaction.IdleAction;
import cn.thiamine128.swordsprite.entity.swordaction.SwordActionStack;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;

import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.BoatPaddleStateC2SPacket;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

import java.util.*;

public class SwordEntity extends Entity {
    protected Entity owner;
    protected UUID ownerUUID;
    protected SwordActionStack actions;
    protected int vanishTimer;

    protected boolean pressingLeft;
    protected boolean pressingRight;
    protected boolean pressingForward;
    protected boolean pressingBack;

    public Map<Integer, SwordAnimationState> animationStates = new HashMap<>();

    private static final TrackedData<ItemStack> SWORD_ITEM = DataTracker.registerData(SwordEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
    private static final TrackedData<Integer> ACTION_TYPE = DataTracker.registerData(SwordEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> FLYING = DataTracker.registerData(SwordEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> LEFT_MOVING = DataTracker.registerData(SwordEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> RIGHT_MOVING = DataTracker.registerData(SwordEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public SwordEntity(EntityType<?> type, World world) {
        super(type, world);

        actions = new SwordActionStack();
        actions.addAction(new IdleAction(this));

        for (int id : SwordAnimations.ANIMATIONS.keySet()) {
            animationStates.put(id, new SwordAnimationState());
        }

        vanishTimer = 0;

        this.setNoGravity(true);
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(SWORD_ITEM, ItemStack.EMPTY);
        this.dataTracker.startTracking(ACTION_TYPE, 0);
        this.dataTracker.startTracking(FLYING, false);
        this.dataTracker.startTracking(LEFT_MOVING, false);
        this.dataTracker.startTracking(RIGHT_MOVING, false);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("Owner")) {
            this.ownerUUID = nbt.getUuid("Owner");
        }
        if (nbt.contains("Sword")) {
            this.setItem(ItemStack.fromNbt(nbt.getCompound("Sword")));
        }

        this.setFlying(nbt.getBoolean("Flying"));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        if (getOwnerLazy() != null) {
            nbt.putUuid("Owner", getOwnerLazy().getUuid());
        }

        ItemStack itemStack = this.getItem();
        if (itemStack != ItemStack.EMPTY && itemStack != null) {
            NbtCompound itemNBT = new NbtCompound();
            itemStack.writeNbt(itemNBT);
            nbt.put("Sword", itemNBT);
        }

        nbt.putBoolean("Flying", this.isFlying());
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this, this.getOwnerLazy() == null ? 0 : this.getOwnerLazy().getId());
    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);
        Entity owner = this.world.getEntityById(packet.getEntityData());
        if (owner != null) {
            this.setOwner(owner);
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.isFlying()) {
            this.setPosition(this.getStuckPos());
            if (this.getOwnerLazy() != null)
                this.setYaw(MathHelper.wrapDegrees(owner.getYaw()));
        }

        while(this.getYaw() - this.prevYaw < -180.0F) {
            this.prevYaw -= 360.0F;
        }
        while(this.getYaw() - this.prevYaw >= 180.0F) {
            this.prevYaw += 360.0F;
        }

        actions.tick();

        if (!this.world.isClient) {
            if (this.getOwnerLazy() == null || this.getOwnerLazy().isRemoved())
                ++ this.vanishTimer;

            if (this.vanishTimer >= 100) {
                this.discard();
            }
        }


        // Flying Process
        if (this.isFlying()) {

        }
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        super.onTrackedDataSet(data);
        if (ACTION_TYPE.equals(data)) {
            int actionType = this.getActionType();
            Iterator it = this.animationStates.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Integer, SwordAnimationState> entry = (Map.Entry<Integer, SwordAnimationState>) it.next();
                if (actionType == entry.getKey()) {
                    entry.getValue().start(this.age);
                } else {
                    entry.getValue().stop();
                }
            }
        }
    }

    @Override
    public boolean collidesWith(Entity other) {
        if (this.getOwnerLazy() != null)
            return this.getOwnerLazy().collidesWith(other);
        return super.collidesWith(other);
    }

    @Override
    public boolean isCollidable() {
        return this.isFlying();
    }

    @Override
    public boolean isPushable() {
        return this.isFlying();
    }

    @Override
    public boolean collides() {
        return !this.isRemoved() && this.isFlying();
    }

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        if (passenger == this.getOwnerLazy()) {
            this.setFlying(false);
        }
        return super.updatePassengerForDismount(passenger);
    }

    public void setMovings(boolean left, boolean right) {
        this.dataTracker.set(LEFT_MOVING, left);
        this.dataTracker.set(RIGHT_MOVING, right);
    }

    public ItemStack getItem() {
        return this.dataTracker.get(SWORD_ITEM);
    }

    public void setItem(ItemStack item) {
        this.dataTracker.set(SWORD_ITEM, item);
    }

    public int getActionType() {
        return this.dataTracker.get(ACTION_TYPE);
    }

    public void setActionType(int actionType) {
        this.dataTracker.set(ACTION_TYPE, actionType);
    }

    public Entity getOwnerLazy() {
        if (this.owner != null)
            return owner;
        if (this.ownerUUID != null && this.world instanceof ServerWorld)
            this.owner = ((ServerWorld) world).getEntity(ownerUUID);
        return this.owner;
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
        this.ownerUUID = owner.getUuid();
    }

    public SwordAnimationState getCurrentAnimationState() {
        if (animationStates.containsKey(actions.current().getAnimationId()))
            return animationStates.get(actions.current().getAnimationId());
        else
            return null;
    }

    public Vec3d getStuckPos() {
        Entity owner = this.getOwnerLazy();
        if (owner == null) {
            return this.getPos();
        }
        float yaw = 0.0F;
        float pitch = 0.0F;
        if (owner instanceof LivingEntity) {
            yaw = ((LivingEntity) owner).bodyYaw;
        } else {
            yaw = owner.getBodyYaw();
        }
        float dx = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        float dy = -MathHelper.sin((pitch) * 0.017453292F) + 0.8F;
        float dz = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        Vec3d offset = (new Vec3d(dx, dy, dz).normalize().multiply(0.4D));
        Vec3d positiveY = new Vec3d(0.0D, 1.0D, 0.0D);
        Vec3d horizontalOffset = offset.crossProduct(positiveY).normalize().multiply(-0.4D);
        return owner.getEyePos().subtract(offset.add(horizontalOffset));
    }

    public void keepAlive() {
        this.vanishTimer = 0;
    }

    public boolean isMoving(int i) {
        if (i == 0)
            return this.dataTracker.get(LEFT_MOVING);
        else
            return this.dataTracker.get(RIGHT_MOVING);
    }

    public boolean isFlying() {
        return this.dataTracker.get(FLYING);
    }

    public void setFlying(boolean flying) {
        this.dataTracker.set(FLYING, flying);
    }

    public void setInputs(boolean pressingLeft, boolean pressingRight, boolean pressingForward, boolean pressingBack) {
        this.pressingLeft = pressingLeft;
        this.pressingRight = pressingRight;
        this.pressingForward = pressingForward;
        this.pressingBack = pressingBack;
    }
}
