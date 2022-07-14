package cn.thiamine128.swordsprite.items;

import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;

public class SwordSpriteInventory implements IInventory{
    private final ItemStack swordSpriteStack;
    private final DefaultedList<ItemStack> items;

    public SwordSpriteInventory(ItemStack swordSpriteStack) {
        this.swordSpriteStack = swordSpriteStack;
        items = DefaultedList.ofSize(1, ItemStack.EMPTY);

        NbtCompound nbt = swordSpriteStack.getSubNbt("Items");
        if (nbt != null) {
            Inventories.readNbt(nbt, items);
        }
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public void markDirty() {
        NbtCompound nbt = swordSpriteStack.getOrCreateSubNbt("Items");
        Inventories.writeNbt(nbt, items);
    }
}
