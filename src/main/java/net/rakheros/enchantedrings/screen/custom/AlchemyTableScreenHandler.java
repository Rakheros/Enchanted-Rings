package net.rakheros.enchantedrings.screen.custom;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.rakheros.enchantedrings.block.entity.custom.AlchemyTableBlockEntity;
import net.rakheros.enchantedrings.item.ModItems;
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

        this.addSlot(new Slot(inventory, AlchemyTableBlockEntity.INPUT_BASE_RING_SLOT, 35, 39) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(ModItems.BASE_RING);
            }
        });

        this.addSlot(new Slot(inventory, AlchemyTableBlockEntity.INPUT_DIAMOND_SLOT, 53, 39) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(Items.DIAMOND);
            }
        });

        this.addSlot(new Slot(inventory, AlchemyTableBlockEntity.INPUT_POTION_SLOT, 71, 39) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() instanceof PotionItem;
            }
        });

        this.addSlot(new Slot(inventory, AlchemyTableBlockEntity.OUTPUT_SLOT, 125, 39) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                for (int i = 0; i < 3; i++) {
                    this.inventory.setStack(i,
                            new ItemStack(this.inventory.getStack(i).getItem(), this.inventory.getStack(i).getCount() - 1));
                }
                super.onTakeItem(player, stack);
            }
        });

        addPlayerInventory(playerInventory);
        addPlayerHotBar(playerInventory);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                return ItemStack.EMPTY;
            } else {
                slot.markDirty();
            }
        }
        return newStack;
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
        if (!player.isAlive() || player instanceof ServerPlayerEntity && ((ServerPlayerEntity)player).isDisconnected()) {
            for (int i = 0; i < inventory.size() - 1; i++) {
                player.dropItem(inventory.removeStack(i), false);
            }
        } else {
            for (int i = 0; i < inventory.size() - 1; i++) {
                PlayerInventory playerInventory = player.getInventory();
                if (playerInventory.player instanceof ServerPlayerEntity) {
                    playerInventory.offerOrDrop(inventory.removeStack(i));
                }
            }
        }
        super.onClosed(player);
    }
}
