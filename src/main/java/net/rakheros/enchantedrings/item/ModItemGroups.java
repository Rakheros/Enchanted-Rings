package net.rakheros.enchantedrings.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.rakheros.enchantedrings.EnchantedRings;

public class ModItemGroups {
    public static final ItemGroup ENCHANTED_RINGS_ITEMS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(EnchantedRings.MOD_ID, "enchanted_rings_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.RING_WITH_GEM))
                    .displayName(Text.translatable("itemgoup.enchanted-rings.enchanted_rings_items"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.BASE_RING);
                        entries.add(ModItems.RING_WITH_GEM);
                    }).build()
            );

    public static void registerItemGroups() {
        EnchantedRings.LOGGER.info("Registering Item Groups for " + EnchantedRings.MOD_ID);
    }
}
