package com.enc4yp7ed.enderbackpacks.blocks;

import com.enc4yp7ed.enderbackpacks.registry.EBBlockEntities;
import com.spydnel.backpacks.api.block.BaseBackpackBlockEntity;
import com.spydnel.backpacks.registry.BPSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Ender Backpack block entity - does not store items itself.
 * Items are stored in each player's ender chest inventory.
 * This block entity only handles animations and color data.
 * Uses the Backpack for Dummies API for base functionality.
 */
public class EnderBackpackBlockEntity extends BaseBackpackBlockEntity {

    public EnderBackpackBlockEntity(BlockPos pos, BlockState blockState) {
        super(EBBlockEntities.ENDER_BACKPACK.get(), pos, blockState);
    }

    /**
     * Ender backpacks don't store items - items are in player's ender chest.
     */
    @Override
    protected boolean shouldStoreItems() {
        return false;
    }

    @Override
    protected SoundEvent getOpenSound() {
        return BPSounds.BACKPACK_OPEN.value();
    }

    @Override
    protected SoundEvent getCloseSound() {
        return BPSounds.BACKPACK_CLOSE.value();
    }
}

