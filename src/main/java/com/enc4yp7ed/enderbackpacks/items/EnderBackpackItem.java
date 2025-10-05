package com.enc4yp7ed.enderbackpacks.items;

import com.spydnel.backpacks.api.item.BaseBackpackItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.block.Block;

/**
 * Ender Backpack variant with teal/cyan default coloring.
 * Uses the Backpack for Dummies API for easy integration.
 * Default color: #136157 (RGB: 19, 97, 87 | HSV: 172, 80, 38 | CMYK: 80, 0, 10, 62)
 */
public class EnderBackpackItem extends BaseBackpackItem {

    private static final int DEFAULT_COLOR = 0x136157; // Hex: #136157, RGB: 19, 97, 87

    public EnderBackpackItem(Block block, Properties properties) {
        super(block, properties);
    }

    /**
     * Apply default ender backpack color to newly created items.
     */
    @Override
    public void onCraftedBy(ItemStack stack, net.minecraft.world.level.Level level, net.minecraft.world.entity.player.Player player) {
        super.onCraftedBy(stack, level, player);
        if (!stack.has(DataComponents.DYED_COLOR)) {
            stack.set(DataComponents.DYED_COLOR, new DyedItemColor(DEFAULT_COLOR, true));
        }
    }
}
