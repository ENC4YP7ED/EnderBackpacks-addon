package com.enc4yp7ed.enderbackpacks.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.ItemStack;

/**
 * Container that wraps a player's ender chest inventory.
 * Each player sees their own ender chest contents when opening an ender backpack.
 */
public class EnderBackpackContainer implements Container {
    private final PlayerEnderChestContainer enderChestInventory;
    private final Player player;

    public EnderBackpackContainer(Player player) {
        this.player = player;
        this.enderChestInventory = player.getEnderChestInventory();
    }

    @Override
    public int getContainerSize() {
        return enderChestInventory.getContainerSize();
    }

    @Override
    public boolean isEmpty() {
        return enderChestInventory.isEmpty();
    }

    @Override
    public ItemStack getItem(int slot) {
        return enderChestInventory.getItem(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack result = enderChestInventory.removeItem(slot, amount);
        setChanged();
        return result;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return enderChestInventory.removeItemNoUpdate(slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        enderChestInventory.setItem(slot, stack);
        setChanged();
    }

    @Override
    public void setChanged() {
        enderChestInventory.setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return player == this.player && !player.isRemoved();
    }

    @Override
    public void clearContent() {
        enderChestInventory.clearContent();
    }

    public PlayerEnderChestContainer getEnderChestInventory() {
        return enderChestInventory;
    }
}
