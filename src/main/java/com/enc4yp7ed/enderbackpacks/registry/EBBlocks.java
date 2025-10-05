package com.enc4yp7ed.enderbackpacks.registry;

import com.enc4yp7ed.enderbackpacks.EnderBackpacks;
import com.enc4yp7ed.enderbackpacks.blocks.EnderBackpackBlock;
import com.spydnel.backpacks.registry.BPSounds;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.ofFullCopy;

public class EBBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(EnderBackpacks.MODID);

    public static final DeferredBlock<EnderBackpackBlock> ENDER_BACKPACK = BLOCKS.register(
            "ender_backpack", () -> new EnderBackpackBlock(ofFullCopy(Blocks.BROWN_WOOL)
                    .sound(new SoundType(1.0F, 1.0F,
                            SoundEvents.WOOL_BREAK,
                            SoundEvents.WOOL_STEP,
                            BPSounds.BACKPACK_PLACE.value(),
                            SoundEvents.WOOL_HIT,
                            SoundEvents.WOOL_FALL))
                    .forceSolidOn()));
}
