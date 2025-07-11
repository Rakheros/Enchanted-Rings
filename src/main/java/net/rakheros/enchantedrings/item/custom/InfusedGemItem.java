package net.rakheros.enchantedrings.item.custom;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.List;

public class InfusedGemItem extends Item {
    public InfusedGemItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        PotionContentsComponent pcc = stack.get(DataComponentTypes.POTION_CONTENTS);
        if (pcc != null) {
            for (StatusEffectInstance statusEffectInstance : pcc.getEffects())  {
                tooltip.add(Text.translatable(statusEffectInstance.getTranslationKey()));
            }
        }
        super.appendTooltip(stack, context, tooltip, type);
    }
}
