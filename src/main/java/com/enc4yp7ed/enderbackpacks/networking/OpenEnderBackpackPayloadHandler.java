package com.enc4yp7ed.enderbackpacks.networking;

import com.enc4yp7ed.enderbackpacks.inventory.EnderBackpackContainer;
import com.enc4yp7ed.enderbackpacks.registry.EBItems;
import com.spydnel.backpacks.BackpackAccessoriesHelper;
import com.spydnel.backpacks.networking.OpenBackpackPayload;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Handles opening ender backpacks via keybinding.
 * Opens the player's own ender chest inventory.
 */
@EventBusSubscriber(modid = "enderbackpacks", bus = EventBusSubscriber.Bus.MOD)
public class OpenEnderBackpackPayloadHandler {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void register(final RegisterPayloadHandlersEvent event) {
        event.registrar("enderbackpacks")
            .playToServer(
                OpenBackpackPayload.TYPE,
                OpenBackpackPayload.STREAM_CODEC,
                OpenEnderBackpackPayloadHandler::handleServerData
            );
    }

    public static void handleServerData(final OpenBackpackPayload data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer serverPlayer) {
                // First check chest slot for ender backpack
                ItemStack backpack = serverPlayer.getItemBySlot(EquipmentSlot.CHEST);

                // If no ender backpack in chest slot and Accessories is loaded, check accessories slot
                if (!backpack.is(EBItems.ENDER_BACKPACK) && ModList.get().isLoaded("accessories")) {
                    backpack = getEnderBackpackFromAccessories(serverPlayer);
                }

                // Open player's ender chest if wearing ender backpack
                if (backpack.is(EBItems.ENDER_BACKPACK)) {
                    EnderBackpackContainer enderContainer = new EnderBackpackContainer(serverPlayer);

                    serverPlayer.openMenu(new SimpleMenuProvider(
                        (id, inventory, p) -> ChestMenu.threeRows(id, inventory, enderContainer),
                        Component.translatable("container.enderchest")
                    ));
                }
            }
        });
    }

    private static ItemStack getEnderBackpackFromAccessories(ServerPlayer player) {
        // Use mixin interface for fast access (no reflection!)
        if (player instanceof BackpackAccessoriesHelper helper) {
            ItemStack item = helper.backpacks$getAccessoriesBackpack();
            if (item.is(EBItems.ENDER_BACKPACK)) {
                return item;
            }
        }
        return ItemStack.EMPTY;
    }
}
