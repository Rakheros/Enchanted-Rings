package net.rakheros.enchantedrings.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.rakheros.enchantedrings.EnchantedRings;
import net.rakheros.enchantedrings.block.custom.AlchemyTableBlock;

public class ModBlocks {
    public static final Block ALCHEMY_TABLE = registerBlock("alchemy_table_block",
            new AlchemyTableBlock(AbstractBlock.Settings.create().strength(1f)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(EnchantedRings.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(EnchantedRings.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        EnchantedRings.LOGGER.info("Registering Mod Blocks for " + EnchantedRings.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> {
            entries.add(ALCHEMY_TABLE);
        });
    }
}
