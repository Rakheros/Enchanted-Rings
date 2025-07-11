package net.rakheros.enchantedrings.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.rakheros.enchantedrings.EnchantedRings;
import net.rakheros.enchantedrings.screen.custom.AlchemyTableScreenHandler;

public class ModScreenHandlers {
    public static final ScreenHandlerType<AlchemyTableScreenHandler> ALCHEMY_TABLE_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(EnchantedRings.MOD_ID, "alchemy_table_screen_handler"),
                    new ExtendedScreenHandlerType<>(AlchemyTableScreenHandler::new, BlockPos.PACKET_CODEC));

    public static void registerScreenHandlers() {
        EnchantedRings.LOGGER.info("Registering Screen Handlers for " + EnchantedRings.MOD_ID);
    }
}
