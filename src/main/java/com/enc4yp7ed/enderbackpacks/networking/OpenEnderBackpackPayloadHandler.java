package com.enc4yp7ed.enderbackpacks.networking;

import com.enc4yp7ed.enderbackpacks.inventory.EnderBackpackContainer;
import com.enc4yp7ed.enderbackpacks.registry.EBItems;
import com.spydnel.backpacks.api.events.BackpackEventHelper;
import com.spydnel.backpacks.networking.OpenBackpackPayload;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Handles opening ender backpacks via keybinding.
 * Opens the player's own ender chest inventory.
 * Uses the Backpack for Dummies API for simplified backpack checking.
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
                // Check for ender backpack in chest slot and accessories slots
                ItemStack backpack = BackpackEventHelper.getBackpackFromEntity(serverPlayer, EBItems.ENDER_BACKPACK.get());

                // Open player's ender chest if wearing ender backpack
                if (backpack != null && backpack.is(EBItems.ENDER_BACKPACK)) {
                    EnderBackpackContainer enderContainer = new EnderBackpackContainer(serverPlayer);

                    serverPlayer.openMenu(new SimpleMenuProvider(
                        (id, inventory, p) -> ChestMenu.threeRows(id, inventory, enderContainer),
                        Component.translatable("container.enderchest")
                    ));
                }
            }
        });
    }
}
