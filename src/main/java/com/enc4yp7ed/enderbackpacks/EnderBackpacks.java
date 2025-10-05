package com.enc4yp7ed.enderbackpacks;

import com.enc4yp7ed.enderbackpacks.registry.EBBlocks;
import com.enc4yp7ed.enderbackpacks.registry.EBBlockEntities;
import com.enc4yp7ed.enderbackpacks.registry.EBItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(EnderBackpacks.MODID)
public class EnderBackpacks {
    public static final String MODID = "enderbackpacks";
    public static final Logger LOGGER = LoggerFactory.getLogger(EnderBackpacks.class);

    public EnderBackpacks(IEventBus modEventBus) {
        LOGGER.info("Initializing Ender Backpacks for Dummies addon");

        EBBlocks.BLOCKS.register(modEventBus);
        EBItems.ITEMS.register(modEventBus);
        EBBlockEntities.BLOCK_ENTITY_TYPES.register(modEventBus);

        // Register for common setup event to initialize integrations
        modEventBus.addListener(this::commonSetup);

        LOGGER.info("Ender Backpacks for Dummies addon initialized successfully");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // Initialize Accessories integration if mod is loaded
            if (ModList.get().isLoaded("accessories")) {
                try {
                    Class.forName("com.enc4yp7ed.enderbackpacks.integration.accessories.EnderBackpackAccessoriesIntegration")
                        .getMethod("init")
                        .invoke(null);
                } catch (Exception e) {
                    LOGGER.error("Failed to load Accessories integration", e);
                }
            }
        });
    }
}
