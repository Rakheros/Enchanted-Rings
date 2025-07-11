package net.rakheros.enchantedrings.block.entity.renderer;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.rakheros.enchantedrings.EnchantedRings;
import net.rakheros.enchantedrings.block.ModBlocks;
import net.rakheros.enchantedrings.block.entity.custom.AlchemyTableBlockEntity;

public class ModBlockEntities {
    public static final BlockEntityType<AlchemyTableBlockEntity> ALCHEMY_TABLE_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(EnchantedRings.MOD_ID, "alchemy_table_be"),
                    BlockEntityType.Builder.create(AlchemyTableBlockEntity::new, ModBlocks.ALCHEMY_TABLE).build(null));

    public static void registerModBlockEntities() {
        EnchantedRings.LOGGER.info("Registering Mod Block Entities for " + EnchantedRings.MOD_ID);
    }
}
