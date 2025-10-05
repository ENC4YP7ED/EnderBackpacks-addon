package com.enc4yp7ed.enderbackpacks.integration.accessories;

import com.enc4yp7ed.enderbackpacks.EnderBackpacks;
import com.enc4yp7ed.enderbackpacks.registry.EBItems;
import io.wispforest.accessories.api.AccessoriesAPI;

/**
 * Registers Ender Backpack with the Accessories API.
 * This allows ender backpacks to be equipped in dedicated accessory slots.
 */
public class EnderBackpackAccessoriesIntegration {

    private static boolean initialized = false;

    public static void init() {
        if (initialized) return;

        try {
            AccessoriesAPI.registerAccessory(EBItems.ENDER_BACKPACK.get(), new EnderBackpackAccessory());
            EnderBackpacks.LOGGER.info("Accessories integration enabled for Ender Backpacks");
            initialized = true;
        } catch (Exception e) {
            EnderBackpacks.LOGGER.error("Failed to initialize Accessories integration for Ender Backpacks", e);
        }
    }

    public static boolean isInitialized() {
        return initialized;
    }
}
