package com.enc4yp7ed.enderbackpacks.registry;

import com.enc4yp7ed.enderbackpacks.EnderBackpacks;
import com.enc4yp7ed.enderbackpacks.items.EnderBackpackItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EBItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(EnderBackpacks.MODID);

    public static final DeferredItem<EnderBackpackItem> ENDER_BACKPACK = ITEMS.register("ender_backpack",
            () -> new EnderBackpackItem(EBBlocks.ENDER_BACKPACK.get(), new Item.Properties()
                    .stacksTo(1)
                    .fireResistant()));
}
