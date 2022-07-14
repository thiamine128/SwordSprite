package cn.thiamine128.swordsprite.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.UUID;

public class SwordEntity extends Entity {
    protected Entity owner;
    protected UUID ownerUUID;

    private static final TrackedData<ItemStack> SWORD_ITEM = DataTracker.registerData(SwordEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);

    public SwordEntity(EntityType<?> type, World world) {
        super(type, world);

        this.setNoGravity(true);
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(SWORD_ITEM, ItemStack.EMPTY);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("Owner")) {
            this.ownerUUID = nbt.getUuid("Owner");
        }
        if (nbt.contains("Sword")) {
            this.setItem(ItemStack.fromNbt(nbt.getCompound("Sword")));
        }
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
        if (this.getOwnerLazy() != null)
            this.setPosition(this.getOwnerLazy().getPos());
    }

    public ItemStack getItem() {
        return this.dataTracker.get(SWORD_ITEM);
    }

    public void setItem(ItemStack item) {
        this.dataTracker.set(SWORD_ITEM, item);
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
}
