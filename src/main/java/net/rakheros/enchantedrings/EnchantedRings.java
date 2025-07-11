package net.rakheros.enchantedrings;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.rakheros.enchantedrings.block.ModBlocks;
import net.rakheros.enchantedrings.block.entity.renderer.ModBlockEntities;
import net.rakheros.enchantedrings.item.ModItemGroups;
import net.rakheros.enchantedrings.item.ModItems;
import net.rakheros.enchantedrings.screen.ModScreenHandlers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class EnchantedRings implements ModInitializer {
	public static final String MOD_ID = "enchanted-rings";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerModBlockEntities();
		ModScreenHandlers.registerScreenHandlers();

		//ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
		//		Objects.requireNonNull(stack.get(DataComponentTypes.POTION_CONTENTS)).getColor(), ModItems.INFUSED_GEM);

		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
			if (tintIndex != 0) return -1;
			PotionContentsComponent pcc = stack.get(DataComponentTypes.POTION_CONTENTS);
			if (pcc == null) return -1;
			return pcc.getColor();
		}, ModItems.ENCHANTED_RING);
	}
}