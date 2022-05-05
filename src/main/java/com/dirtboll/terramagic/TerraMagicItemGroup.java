package com.dirtboll.terramagic;

import com.dirtboll.terramagic.registries.ItemRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class TerraMagicItemGroup extends ItemGroup {

    public static final TerraMagicItemGroup INSTANCE = new TerraMagicItemGroup();

    private TerraMagicItemGroup() {
        super(TerraMagic.MOD_ID);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ItemRegistry.WAND_OF_SPARKING);
    }
}
