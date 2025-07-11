package net.rakheros.enchantedrings.screen.custom;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.CrafterOutputSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import net.rakheros.enchantedrings.EnchantedRings;
import net.rakheros.enchantedrings.block.entity.custom.AlchemyTableBlockEntity;
import net.rakheros.enchantedrings.screen.ModScreenHandlers;

public class AlchemyTableScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    public final AlchemyTableBlockEntity blockEntity;

    public AlchemyTableScreenHandler(int syncId, PlayerInventory inventory, BlockPos pos) {
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(pos));
    }

    public AlchemyTableScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity) {
        super(ModScreenHandlers.ALCHEMY_TABLE_SCREEN_HANDLER, syncId);
        this.inventory = (Inventory) blockEntity;
        this.blockEntity = (AlchemyTableBlockEntity) blockEntity;

        this.addSlot(new Slot(inventory, 0, 29, 34));
        this.addSlot(new Slot(inventory, 1, 54, 34));
        this.addSlot(new Slot(inventory, 2, 79, 34));
        this.addSlot(new Slot(inventory, 3, 129, 34) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });

        addPlayerInventory(playerInventory);
        addPlayerHotBar(playerInventory);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; i++) {
            for (int l = 0; l < 9; l++) {
                this.addSlot(new Slot(playerInventory, l + i*9 + 9, 8 + l*18, 84 + i*18));
            }
        }
    }

    private void addPlayerHotBar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i*18, 142));
        }
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        //this.context.run((world, pos) -> this.dropInventory(player, this.input));
    }
}
