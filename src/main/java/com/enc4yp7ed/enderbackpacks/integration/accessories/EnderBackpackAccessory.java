package com.enc4yp7ed.enderbackpacks.integration.accessories;

import com.enc4yp7ed.enderbackpacks.inventory.EnderBackpackContainer;
import com.enc4yp7ed.enderbackpacks.registry.EBItems;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;

/**
 * Accessories integration for Ender Backpacks.
 * Unlike regular backpacks, ender backpacks don't store items themselves,
 * so they can always be equipped/unequipped freely.
 */
public class EnderBackpackAccessory implements Accessory {

    @Override
    public void onEquip(ItemStack stack, SlotReference reference) {
        // Ender backpacks don't need initialization - they use player's ender chest
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference reference) {
        // No cleanup needed - ender backpacks don't store items
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        // Don't allow equipping if there's already an ender backpack in chest slot
        if (reference.entity() instanceof LivingEntity livingEntity) {
            ItemStack chestItem = livingEntity.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.CHEST);
            if (chestItem.is(EBItems.ENDER_BACKPACK)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack stack, SlotReference reference) {
        // Always allow unequipping - ender backpacks don't store items
        // Items are in the player's ender chest, not in the backpack
        return true;
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
