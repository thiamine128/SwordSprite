package cn.thiamine128.swordsprite.item;

import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;

public class ScabbardInventory implements IInventory{
    private final ItemStack scabbardStack;
    private final DefaultedList<ItemStack> items;

    public ScabbardInventory(ItemStack scabbardStack) {
        this.scabbardStack = scabbardStack;
        items = DefaultedList.ofSize(1, ItemStack.EMPTY);

        NbtCompound nbt = scabbardStack.getSubNbt("Items");
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
        NbtCompound nbt = scabbardStack.getOrCreateSubNbt("Items");
        Inventories.writeNbt(nbt, items);
    }
}
