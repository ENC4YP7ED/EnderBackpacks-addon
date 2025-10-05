package com.enc4yp7ed.enderbackpacks.blocks;

import com.enc4yp7ed.enderbackpacks.inventory.EnderBackpackContainer;
import com.enc4yp7ed.enderbackpacks.registry.EBBlockEntities;
import com.spydnel.backpacks.api.block.BaseBackpackBlock;
import com.spydnel.backpacks.api.block.BaseBackpackBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

/**
 * Ender Backpack block - opens the player's ender chest inventory.
 * Each player sees their own ender chest contents when opening an ender backpack.
 * Uses the Backpack for Dummies API for base functionality.
 */
public class EnderBackpackBlock extends BaseBackpackBlock {

    public EnderBackpackBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected BlockEntityType<?> getBlockEntityType() {
        return EBBlockEntities.ENDER_BACKPACK.get();
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new EnderBackpackBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, EBBlockEntities.ENDER_BACKPACK.get(), BaseBackpackBlockEntity::tick);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else if (player.isSpectator()) {
            return InteractionResult.CONSUME;
        } else {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof EnderBackpackBlockEntity enderBackpackBlockEntity) {
                // Open the player's ender chest inventory
                EnderBackpackContainer enderContainer = new EnderBackpackContainer(player);
                player.openMenu(new SimpleMenuProvider(
                    (id, inventory, p) -> ChestMenu.threeRows(id, inventory, enderContainer),
                    Component.translatable("container.enderchest")
                ));

                // Trigger open/close animations
                enderBackpackBlockEntity.onOpen(player);
                player.containerMenu.addSlotListener(new net.minecraft.world.inventory.ContainerListener() {
                    @Override
                    public void slotChanged(net.minecraft.world.inventory.AbstractContainerMenu containerMenu, int slot, net.minecraft.world.item.ItemStack stack) {}

                    @Override
                    public void dataChanged(net.minecraft.world.inventory.AbstractContainerMenu containerMenu, int dataSlot, int value) {}
                });

                return InteractionResult.CONSUME;
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        // Don't drop items - ender backpacks don't store items themselves
        // Items are in the player's ender chest
        if (!state.is(newState.getBlock())) {
            level.updateNeighbourForOutputSignal(pos, this);
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }
}
