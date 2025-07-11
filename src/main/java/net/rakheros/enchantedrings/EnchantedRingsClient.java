package net.rakheros.enchantedrings;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.rakheros.enchantedrings.screen.ModScreenHandlers;
import net.rakheros.enchantedrings.screen.custom.AlchemyTableScreen;

public class EnchantedRingsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.ALCHEMY_TABLE_SCREEN_HANDLER, AlchemyTableScreen::new);
    }
}