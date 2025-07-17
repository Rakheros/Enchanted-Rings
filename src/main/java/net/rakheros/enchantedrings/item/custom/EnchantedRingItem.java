package net.rakheros.enchantedrings.item.custom;

import dev.emi.trinkets.api.*;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import net.minecraft.world.World;
import net.rakheros.enchantedrings.item.ModItems;

import java.util.List;
import java.util.Optional;


public class EnchantedRingItem extends TrinketItem {
    public EnchantedRingItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        PotionContentsComponent pcc = stack.get(DataComponentTypes.POTION_CONTENTS);
        if (pcc != null) {
            for (StatusEffectInstance statusEffectInstance : pcc.getEffects()) {
                tooltip.add((Text.translatable("tooltip.enchanted-rings.enchanted_ring.tooltip")));
                tooltip.add(Text.translatable(statusEffectInstance.getTranslationKey()));
            }
        }
        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient()) {
            if (entity instanceof PlayerEntity player) {
                Optional<TrinketComponent> otc = TrinketsApi.getTrinketComponent((LivingEntity) entity);
                if (otc.isPresent()) {
                    TrinketComponent component = otc.get();
                    for (Pair<SlotReference, ItemStack> pair : component.getEquipped(ModItems.ENCHANTED_RING)) {
                        PotionContentsComponent pcc = pair.getRight().get(DataComponentTypes.POTION_CONTENTS);
                        if (pcc != null) {
                            for (StatusEffectInstance statusEffectInstance : pcc.getEffects()) {
                                player.addStatusEffect(new StatusEffectInstance(statusEffectInstance.getEffectType(), 100));
                            }
                        }
                    }
                }
            }
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        super.onEquip(stack, slot, entity);
    }

    @Override
    public boolean canEquipFromUse(ItemStack stack, LivingEntity entity) {
        return true;
    }
}
