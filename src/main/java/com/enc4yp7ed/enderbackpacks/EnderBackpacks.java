package com.enc4yp7ed.enderbackpacks;

import com.enc4yp7ed.enderbackpacks.registry.EBBlocks;
import com.enc4yp7ed.enderbackpacks.registry.EBBlockEntities;
import com.enc4yp7ed.enderbackpacks.registry.EBItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
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

        LOGGER.info("Ender Backpacks for Dummies addon initialized successfully");
    }
}
