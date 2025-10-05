package com.enc4yp7ed.enderbackpacks.blocks;

import com.enc4yp7ed.enderbackpacks.registry.EBBlockEntities;
import com.spydnel.backpacks.blocks.BackpackBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

/**
 * Ender Backpack block - extends base BackpackBlock with ender-themed functionality.
 */
public class EnderBackpackBlock extends BackpackBlock {

    public EnderBackpackBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new EnderBackpackBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, EBBlockEntities.ENDER_BACKPACK.get(), EnderBackpackBlockEntity::tick);
    }
}
