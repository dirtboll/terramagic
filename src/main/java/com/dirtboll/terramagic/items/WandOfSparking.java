package com.dirtboll.terramagic.items;

import com.dirtboll.terramagic.entities.FireSparkEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.SwordItem;
import net.minecraft.util.*;

public class WandOfSparking extends WandItem {
    public static int MAX_DAMAGE = 60;
    public static long COOLDOWN_MIN = 500;
    public static long COOLDOWN_RANGE = 1000;
    public static int IGNITE_DURATION_MIN = 2;
    public static int IGNITE_DURATION_RANGE = 5;

    public WandOfSparking() {
        super(new Properties().maxStackSize(1).maxDamage(MAX_DAMAGE));
    }

    @Override
    public boolean isDamageable() {
        return super.isDamageable();
    }

    @Override
    long getCooldown() {
        return COOLDOWN_MIN + (long)(random.nextDouble() * (double)COOLDOWN_RANGE);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        return super.onItemUse(context);
    }

    @Override
    boolean cast(PlayerEntity caster, Hand hand) {
        caster.swingArm(hand);

        if (caster.world.isRemote)
            return true;

        FireSparkEntity fireSpark = new FireSparkEntity(caster.world, caster);
        shoot(fireSpark, caster.rotationPitch, caster.rotationYaw, 0f, 2f, 1f);
        caster.world.addEntity(fireSpark);

        ItemStack wand = caster.getHeldItem(hand);
        wand.damageItem(1, caster, x -> {});

        caster.world.playSound(null, fireSpark.getPosX(), fireSpark.getPosY(), fireSpark.getPosZ(), SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.PLAYERS, 1.0f, 1.0f);
        return true;
    }
}
