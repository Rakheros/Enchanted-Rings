package net.rakheros.enchantedrings.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.rakheros.enchantedrings.EnchantedRings;
import net.rakheros.enchantedrings.item.custom.EnchantedRingItem;

public class ModItems {
    public static final Item BASE_RING = registerItem("base_ring", new Item(new Item.Settings()));
    public static final Item ENCHANTED_RING = registerItem("enchanted_ring", new EnchantedRingItem(
            new Item.Settings().maxCount(1)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(EnchantedRings.MOD_ID, name), item);
    }

    public static void registerModItems() {
        EnchantedRings.LOGGER.info("Registering mod items for " + EnchantedRings.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(BASE_RING);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(ENCHANTED_RING);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.add(ENCHANTED_RING);
        });
    }
}
