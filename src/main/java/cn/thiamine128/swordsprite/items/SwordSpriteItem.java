package cn.thiamine128.swordsprite.items;

import cn.thiamine128.swordsprite.SwordSpriteMod;
import cn.thiamine128.swordsprite.entity.ModEntityTypes;
import cn.thiamine128.swordsprite.entity.SwordEntity;
import cn.thiamine128.swordsprite.screen.SwordSpriteScreenHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.UUID;

public class SwordSpriteItem extends Item {
    public SwordSpriteItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        ItemStack swordItem = getSlot(itemStack);
        if (user.isSneaking()) {
            user.openHandledScreen(createNamedScreenHandlerFactory(itemStack));
            return TypedActionResult.success(itemStack, world.isClient);
        } else if (!world.isClient) {
            NbtCompound nbt = itemStack.getNbt();
            boolean shouldSummon = false;
            if (nbt.contains("Sword")) {
                UUID uuid = nbt.getUuid("Sword");
                ServerWorld serverWorld = (ServerWorld) world;
                Entity sword = serverWorld.getEntity(uuid);
                if (sword != null && !sword.isRemoved()) {
                    sword.discard();
                    nbt.remove("Sword");
                } else {
                    shouldSummon = true;
                }
            } else {
                shouldSummon = true;
            }

            shouldSummon = shouldSummon && !(swordItem == ItemStack.EMPTY);

            if (shouldSummon) {
                SwordEntity swordEntity = ModEntityTypes.SWORD.create(world);
                swordEntity.setOwner(user);
                swordEntity.setItem(swordItem);
                swordEntity.refreshPositionAndAngles(user.getX(), user.getEyeY(), user.getZ(), user.getYaw(), user.getPitch());
                world.spawnEntity(swordEntity);
                nbt.putUuid("Sword", swordEntity.getUuid());
            }
        }
        return super.use(world, user, hand);
    }

    public static ItemStack getSlot(ItemStack swordSprite) {
        NbtCompound nbt = swordSprite.getOrCreateSubNbt("Items");
        NbtList nbtList = nbt.getList("Items", 10);
        NbtCompound itemNBT = nbtList.getCompound(0);
        return ItemStack.fromNbt(itemNBT);

    }

    protected NamedScreenHandlerFactory createNamedScreenHandlerFactory(ItemStack itemStack) {
        return new SimpleNamedScreenHandlerFactory(((syncId, inv, player) -> {
            return new SwordSpriteScreenHandler(syncId, inv, new SwordSpriteInventory(itemStack));
        }), itemStack.getName());
    }
}
