package com.enc4yp7ed.enderbackpacks.integration.accessories;

import com.enc4yp7ed.enderbackpacks.EnderBackpacks;
import com.enc4yp7ed.enderbackpacks.registry.EBItems;
import com.spydnel.backpacks.api.integration.AccessoriesIntegrationHelper;

/**
 * Registers Ender Backpack with the Accessories API.
 * This allows ender backpacks to be equipped in dedicated accessory slots.
 * Uses the Backpack for Dummies API for simplified registration.
 */
public class EnderBackpackAccessoriesIntegration {

    private static boolean initialized = false;

    public static void init() {
        if (initialized) return;

        initialized = AccessoriesIntegrationHelper.registerAccessory(
            EBItems.ENDER_BACKPACK.get(),
            new EnderBackpackAccessory(),
            EnderBackpacks.LOGGER,
            "Ender Backpacks"
        );
    }

    public static boolean isInitialized() {
        return initialized;
    }
}
