package cn.thiamine128.swordsprite.item;

import cn.thiamine128.swordsprite.entity.ModEntityTypes;
import cn.thiamine128.swordsprite.entity.SwordEntity;
import cn.thiamine128.swordsprite.screen.ScabbardScreenHandler;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Pair;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ScabbardItem extends TrinketItem {
    public ScabbardItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack scabbardStack = user.getStackInHand(hand);

        if (user.isSneaking()) {
            openConfigScreen(scabbardStack, user);
            return TypedActionResult.success(scabbardStack, world.isClient);
        }

        return super.use(world, user, hand);
    }

    public static ItemStack getSlot(ItemStack scabbard) {
        NbtCompound nbt = scabbard.getOrCreateSubNbt("Items");
        NbtList nbtList = nbt.getList("Items", 10);
        NbtCompound itemNBT = nbtList.getCompound(0);
        return ItemStack.fromNbt(itemNBT);
    }

    public static void openConfigScreen(ItemStack scabbardStack, PlayerEntity user) {
        user.openHandledScreen(createNamedScreenHandlerFactory(scabbardStack));
    }

    public static void summonSword(PlayerEntity player, ItemStack scabbardStack) {
        ItemStack sword = getSlot(scabbardStack);

        if (sword == null || sword.isEmpty()) {
            return;
        }

        NbtCompound nbt = scabbardStack.getNbt();

        SwordEntity swordEntity = ModEntityTypes.SWORD.create(player.world);
        swordEntity.setOwner(player);
        swordEntity.setItem(sword);
        Vec3d spawnPos = swordEntity.getStuckPos();
        swordEntity.refreshPositionAndAngles(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), -player.bodyYaw, 0.0F);
        player.world.spawnEntity(swordEntity);

        nbt.putUuid("Sword", swordEntity.getUuid());
    }

    public static void discardSword(PlayerEntity player, ItemStack scabbardStack) {
        NbtCompound nbt = scabbardStack.getNbt();
        if (nbt.contains("Sword")) {
            UUID uuid = nbt.getUuid("Sword");
            ServerWorld world = (ServerWorld) player.world;
            SwordEntity swordEntity = (SwordEntity) world.getEntity(uuid);
            if (swordEntity == null || swordEntity.isRemoved())
                return;
            swordEntity.discard();
            nbt.remove("Sword");
        }
    }

    public static ItemStack getEquippedItem(PlayerEntity player) {
        Optional<TrinketComponent> optional = TrinketsApi.getTrinketComponent(player);
        if (optional.isPresent()) {
            TrinketComponent trinketComponent = optional.get();
            List<Pair<SlotReference, ItemStack>> equipped = trinketComponent.getEquipped(ModItems.SCABBARD);
            if (equipped.size() > 0) {
                ItemStack first = equipped.get(0).getRight();

                return first;
            }
        }

        return ItemStack.EMPTY;
    }

    protected static NamedScreenHandlerFactory createNamedScreenHandlerFactory(ItemStack itemStack) {
        return new SimpleNamedScreenHandlerFactory(((syncId, inv, player) -> {
            return new ScabbardScreenHandler(syncId, inv, new ScabbardInventory(itemStack));
        }), itemStack.getName());
    }
}
