package com.enc4yp7ed.enderbackpacks.client;

import com.enc4yp7ed.enderbackpacks.EnderBackpacks;
import com.enc4yp7ed.enderbackpacks.inventory.EnderBackpackContainer;
import com.enc4yp7ed.enderbackpacks.registry.EBItems;
import com.spydnel.backpacks.api.client.BackpackKeybindingRegistry;
import com.spydnel.backpacks.api.events.BackpackEventHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import org.lwjgl.glfw.GLFW;

/**
 * Keybinding registration for Ender Backpacks.
 * Registers a separate keybinding that also defaults to 'B' but can be configured independently.
 */
@OnlyIn(Dist.CLIENT)
public class EnderBackpackKeybindings {

    public static KeyMapping OPEN_ENDER_BACKPACK;

    /**
     * Register the Ender Backpack keybinding.
     * This should be called during mod construction on CLIENT side only.
     */
    public static void register() {
        OPEN_ENDER_BACKPACK = BackpackKeybindingRegistry.registerKeybinding(
            "key.enderbackpacks.open_ender_backpack",
            GLFW.GLFW_KEY_B,  // Default: B (same as regular backpack, but separately configurable)
            "Ender Backpack"
        );

        EnderBackpacks.LOGGER.info("Registered Ender Backpack keybinding");
    }

    @EventBusSubscriber(modid = EnderBackpacks.MODID, value = Dist.CLIENT)
    public static class KeyInputHandler {

        @SubscribeEvent
        public static void onClientTick(ClientTickEvent.Post event) {
            Minecraft mc = Minecraft.getInstance();

            if (mc.player == null || mc.screen != null) {
                return;
            }

            // Check if ender backpack keybinding was pressed
            if (OPEN_ENDER_BACKPACK != null && OPEN_ENDER_BACKPACK.consumeClick()) {
                // Check if player is wearing ender backpack (chest or accessories slot)
                ItemStack backpack = BackpackEventHelper.getBackpackFromEntity(mc.player, EBItems.ENDER_BACKPACK.get());

                if (backpack != null && backpack.is(EBItems.ENDER_BACKPACK)) {
                    // Open player's ender chest inventory
                    EnderBackpackContainer enderContainer = new EnderBackpackContainer(mc.player);

                    mc.player.openMenu(new SimpleMenuProvider(
                        (id, inventory, p) -> ChestMenu.threeRows(id, inventory, enderContainer),
                        Component.translatable("container.enderchest")
                    ));
                }
            }
        }
    }
}
