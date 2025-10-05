package com.enc4yp7ed.enderbackpacks.registry;

import com.enc4yp7ed.enderbackpacks.EnderBackpacks;
import com.enc4yp7ed.enderbackpacks.blocks.EnderBackpackBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EBBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, EnderBackpacks.MODID);

    public static final Supplier<BlockEntityType<EnderBackpackBlockEntity>> ENDER_BACKPACK = BLOCK_ENTITY_TYPES.register(
            "ender_backpack",
            () -> BlockEntityType.Builder.of(EnderBackpackBlockEntity::new, EBBlocks.ENDER_BACKPACK.get()).build(null));
}
