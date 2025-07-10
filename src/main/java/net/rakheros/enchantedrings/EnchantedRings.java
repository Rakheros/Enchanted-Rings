package net.rakheros.enchantedrings;

import net.fabricmc.api.ModInitializer;

import net.rakheros.enchantedrings.item.ModItemGroups;
import net.rakheros.enchantedrings.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnchantedRings implements ModInitializer {
	public static final String MOD_ID = "enchanted-rings";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
	}
}