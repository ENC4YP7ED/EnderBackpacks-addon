package com.enc4yp7ed.enderbackpacks.events;

import com.enc4yp7ed.enderbackpacks.EnderBackpacks;
import com.enc4yp7ed.enderbackpacks.inventory.EnderBackpackContainer;
import com.enc4yp7ed.enderbackpacks.registry.EBItems;
import com.spydnel.backpacks.api.events.BackpackEventHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

/**
 * Handles opening ender backpacks worn by other players.
 * Opens the viewer's own ender chest inventory, not the wearer's.
 * Uses the Backpack for Dummies API for event handling utilities.
 */
@SuppressWarnings("unused")
@EventBusSubscriber(modid = EnderBackpacks.MODID)
public class EnderBackpackEntityInteractionEvents {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        LivingEntity target = event.getTarget() instanceof LivingEntity ? (LivingEntity) event.getTarget() : null;

        if (target == null || !BackpackEventHelper.isBehind(player, target)) return;

        // Check for ender backpack in chest slot and accessories slots
        ItemStack item = BackpackEventHelper.getBackpackFromEntity(target, EBItems.ENDER_BACKPACK.get());

        if (item != null && item.is(EBItems.ENDER_BACKPACK)) {
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
}
