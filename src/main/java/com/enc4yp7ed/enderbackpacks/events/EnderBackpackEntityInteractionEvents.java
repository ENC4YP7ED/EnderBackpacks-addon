package com.enc4yp7ed.enderbackpacks.events;

import com.enc4yp7ed.enderbackpacks.EnderBackpacks;
import com.enc4yp7ed.enderbackpacks.inventory.EnderBackpackContainer;
import com.enc4yp7ed.enderbackpacks.registry.EBItems;
import com.spydnel.backpacks.BackpackAccessoriesHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

/**
 * Handles opening ender backpacks worn by other players.
 * Opens the viewer's own ender chest inventory, not the wearer's.
 */
@SuppressWarnings("unused")
@EventBusSubscriber(modid = EnderBackpacks.MODID)
public class EnderBackpackEntityInteractionEvents {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        LivingEntity target = event.getTarget() instanceof LivingEntity ? (LivingEntity) event.getTarget() : null;

        if (target == null || !isBehind(player, target)) return;

        // Check chest slot first
        ItemStack item = target.getItemBySlot(EquipmentSlot.CHEST);

        // If no ender backpack in chest slot, check accessories slot (if mod is loaded)
        if (!item.is(EBItems.ENDER_BACKPACK) && ModList.get().isLoaded("accessories")) {
            item = getEnderBackpackFromAccessories(target);
        }

        if (item.is(EBItems.ENDER_BACKPACK)) {
            // Open the VIEWER's ender chest inventory (not the target's!)
            // This is like vanilla ender chests - each player sees their own items
            EnderBackpackContainer enderContainer = new EnderBackpackContainer(player);

            player.openMenu(new SimpleMenuProvider(
                (id, inventory, p) -> ChestMenu.threeRows(id, inventory, enderContainer),
                Component.translatable("container.enderchest")
            ));

            event.setCancellationResult(InteractionResult.CONSUME);
            event.setCanceled(true);
        }
    }

    private static ItemStack getEnderBackpackFromAccessories(LivingEntity entity) {
        // Use mixin interface for fast access (no reflection!)
        if (entity instanceof BackpackAccessoriesHelper helper) {
            ItemStack accessoryItem = helper.backpacks$getAccessoriesBackpack();
            if (accessoryItem.is(EBItems.ENDER_BACKPACK)) {
                return accessoryItem;
            }
        }
        return ItemStack.EMPTY;
    }

    public static boolean isBehind(Player player, LivingEntity target) {
        float t = 1.0F;
        Vec3 vector = player.getPosition(t).subtract(target.getPosition(t)).normalize();
        vector = new Vec3(vector.x, 0, vector.z);
        return target.getViewVector(t).dot(vector) < 0;
    }
}
