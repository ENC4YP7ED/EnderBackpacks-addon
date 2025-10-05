package com.enc4yp7ed.enderbackpacks.integration.accessories;

import com.enc4yp7ed.enderbackpacks.inventory.EnderBackpackContainer;
import com.enc4yp7ed.enderbackpacks.registry.EBItems;
import com.spydnel.backpacks.api.integration.BaseBackpackAccessory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * Accessories integration for Ender Backpacks.
 * Unlike regular backpacks, ender backpacks don't store items themselves,
 * so they can always be equipped/unequipped freely.
 * Uses the Backpack for Dummies API for base functionality.
 */
public class EnderBackpackAccessory extends BaseBackpackAccessory {

    @Override
    protected Item getBackpackItem() {
        return EBItems.ENDER_BACKPACK.get();
    }

    /**
     * Ender backpacks can always be unequipped since items are in the ender chest.
     */
    @Override
    public boolean canUnequip(ItemStack stack, io.wispforest.accessories.api.slot.SlotReference reference) {
        return true; // Always allow unequipping
    }

    /**
     * Opens the player's ender chest inventory when accessing an ender backpack.
     */
    public static void openEnderBackpackMenu(Player player, LivingEntity wearer, ItemStack backpackStack) {
        if (!backpackStack.is(EBItems.ENDER_BACKPACK)) return;

        // Open the viewer's ender chest (not the wearer's!)
        EnderBackpackContainer enderContainer = new EnderBackpackContainer(player);

        player.openMenu(new SimpleMenuProvider(
            (id, inventory, p) -> ChestMenu.threeRows(id, inventory, enderContainer),
            Component.translatable("container.enderchest")
        ));
    }
}
