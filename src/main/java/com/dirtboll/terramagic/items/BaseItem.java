package com.dirtboll.terramagic.items;

import com.dirtboll.terramagic.TerraMagicItemGroup;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public abstract class BaseItem extends Item {
    public BaseItem(Properties properties) {
        super(properties.group(TerraMagicItemGroup.INSTANCE));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        ITextComponent description = new TranslationTextComponent( getTranslationKey() + ".tooltip")
                .mergeStyle(TextFormatting.DARK_PURPLE, TextFormatting.ITALIC);
        tooltip.add(description);
    }
}
