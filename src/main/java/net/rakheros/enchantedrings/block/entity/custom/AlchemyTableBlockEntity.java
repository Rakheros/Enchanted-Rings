package net.rakheros.enchantedrings.block.entity.custom;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.rakheros.enchantedrings.block.entity.ImplementedInventory;
import net.rakheros.enchantedrings.block.entity.renderer.ModBlockEntities;
import net.rakheros.enchantedrings.item.ModItems;
import net.rakheros.enchantedrings.screen.custom.AlchemyTableScreenHandler;
import org.jetbrains.annotations.Nullable;

public class AlchemyTableBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);

    private static final int INPUT_BASE_RING_SLOT = 0;
    private static final int INPUT_DIAMOND_SLOT = 1;
    private static final int INPUT_POTION_SLOT = 2;
    private static final int OUTPUT_SLOT = 3;

    public AlchemyTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ALCHEMY_TABLE_BE, pos, state);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return this.pos;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.enchanted-rings.alchemy_table_block");
    }

    @Override
    @Nullable
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new AlchemyTableScreenHandler(syncId, playerInventory, this);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        Inventories.readNbt(nbt, inventory, registryLookup);
        super.readNbt(nbt, registryLookup);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (hasRecipe()) {
            createItem();
        } else {
            emptyOutputSlot();
        }
    }

    private boolean hasRecipe() {
        if (!this.getStack(INPUT_BASE_RING_SLOT).isOf(ModItems.BASE_RING)) return false;
        if (!this.getStack(INPUT_DIAMOND_SLOT).isOf(Items.DIAMOND)) return false;

        ItemStack potionSlot = this.getStack(INPUT_POTION_SLOT);
        if (!(potionSlot.getItem() instanceof PotionItem)) return false;

        PotionContentsComponent pcc = potionSlot.get(DataComponentTypes.POTION_CONTENTS);
        if (pcc == null) return false;

        for (StatusEffectInstance statusEffectInstance : pcc.getEffects()) {
            if (statusEffectInstance.getEffectType().value().getCategory() == StatusEffectCategory.HARMFUL) return false;
            if (statusEffectInstance.getEffectType().value().isInstant()) return false;
        }

        return true;
    }

    private void createItem() {
        ItemStack item = new ItemStack(ModItems.ENCHANTED_RING, 1);
        PotionContentsComponent pcc = this.getStack(INPUT_POTION_SLOT).get(DataComponentTypes.POTION_CONTENTS);
        if (pcc != null) {
            pcc.forEachEffect(effect -> {
                effect = new StatusEffectInstance(effect.getEffectType(), StatusEffectInstance.INFINITE);
            });
            // now all these effects SHOULD have infinite duration...
        }
        item.set(DataComponentTypes.POTION_CONTENTS, pcc);
        this.setStack(OUTPUT_SLOT, item);
    }

    private void emptyOutputSlot() {
        this.setStack(OUTPUT_SLOT, ItemStack.EMPTY);
    }

    @Override
    @Nullable
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }
}
