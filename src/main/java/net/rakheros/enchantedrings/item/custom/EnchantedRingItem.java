package net.rakheros.enchantedrings.item.custom;

import java.util.List;
import java.util.Optional;

import dev.emi.trinkets.api.*;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import net.rakheros.enchantedrings.item.ModItems;

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
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (!entity.getEntityWorld().isClient()) {
            Optional<TrinketComponent> otc = TrinketsApi.getTrinketComponent(entity);
            if (otc.isPresent()) {
                TrinketComponent component = otc.get();
                for (Pair<SlotReference, ItemStack> pair : component.getEquipped(ModItems.ENCHANTED_RING)) {
                    PotionContentsComponent pcc = pair.getRight().get(DataComponentTypes.POTION_CONTENTS);
                    if (pcc != null) {
                        for (StatusEffectInstance statusEffectInstance : pcc.getEffects()) {
                            entity.addStatusEffect(new StatusEffectInstance(statusEffectInstance.getEffectType(), 100));
                        }
                    }
                }
            }
        }

        super.tick(stack, slot, entity);
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
