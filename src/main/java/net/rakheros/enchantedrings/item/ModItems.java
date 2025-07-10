package net.rakheros.enchantedrings.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.rakheros.enchantedrings.EnchantedRings;

public class ModItems {
    public static final Item BASE_RING = registerItem("base_ring", new Item(new Item.Settings()));
    public static final Item RING_WITH_GEM = registerItem("ring_with_gem", new Item(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(EnchantedRings.MOD_ID, name), item);
    }

    public static void registerModItems() {
        EnchantedRings.LOGGER.info("Registering mod items for " + EnchantedRings.MOD_ID);
    }
}
