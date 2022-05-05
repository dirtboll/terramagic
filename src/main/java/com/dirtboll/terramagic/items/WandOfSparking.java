package com.dirtboll.terramagic.items;

import com.dirtboll.terramagic.entities.FireSparkEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;

public class WandOfSparking extends WandItem {
    public static long COOLDOWN = 1000;
    public static int MAX_DAMAGE = 60;

    public WandOfSparking() {
        super(new Properties().maxStackSize(1).maxDamage(MAX_DAMAGE));
    }

    @Override
    public boolean isDamageable() {
        return super.isDamageable();
    }

    @Override
    long getCooldown() {
        return COOLDOWN;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        return super.onItemUse(context);
    }

    @Override
    boolean cast(PlayerEntity caster, Hand hand) {
        caster.swing(hand, false);
        FireSparkEntity entity = new FireSparkEntity(caster.world, caster);
        shoot(entity, caster.rotationPitch, caster.rotationYaw, 0f, 2f, 1f);
        caster.world.addEntity(entity);

        ItemStack wand = caster.getHeldItem(hand);
        wand.damageItem(1, caster, x -> {});
        return true;
    }
}
