package net.rakheros.enchantedrings.item.custom;

import dev.emi.trinkets.api.*;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.List;


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
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        PotionContentsComponent pcc = stack.get(DataComponentTypes.POTION_CONTENTS);
        if (pcc != null) {
            for (StatusEffectInstance statusEffectInstance : pcc.getEffects()) {
                entity.addStatusEffect(statusEffectInstance);
            }
        }
        super.onEquip(stack, slot, entity);
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        PotionContentsComponent pcc = stack.get(DataComponentTypes.POTION_CONTENTS);
        if (pcc != null) {
            for (StatusEffectInstance statusEffectInstance : pcc.getEffects()) {
                entity.removeStatusEffect(statusEffectInstance.getEffectType());
            }
        }
        super.onUnequip(stack, slot, entity);
    }
}
