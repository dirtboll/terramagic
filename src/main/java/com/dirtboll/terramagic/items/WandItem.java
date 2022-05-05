package com.dirtboll.terramagic.items;

import com.dirtboll.terramagic.TerraMagic;
import com.dirtboll.terramagic.capabilities.WandCapability;
import com.dirtboll.terramagic.capabilities.items.WandCapabilityProvider;
import com.dirtboll.terramagic.registries.CapabilityRegistry;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public abstract class WandItem extends BaseItem {

    public WandItem(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new WandCapabilityProvider(getCooldown());
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack wand = player.getHeldItem(hand);
        WandCapability capability = getWandCapability(wand);
        if (capability.cast() && cast(player, hand)) {
            return ActionResult.resultPass(wand);
        } else {
            return ActionResult.resultFail(wand);
        }
    }

    abstract long getCooldown();

    abstract boolean cast(PlayerEntity caster, Hand hand);

    static void shoot(ProjectileEntity entity, float pitch, float yaw, float roll, float velocity, float inaccuracy) {
        float x = -MathHelper.sin(yaw * ((float)Math.PI / 180F)) * MathHelper.cos(pitch * ((float)Math.PI / 180F));
        float y = -MathHelper.sin((pitch + roll) * ((float)Math.PI / 180F));
        float z = MathHelper.cos(yaw * ((float)Math.PI / 180F)) * MathHelper.cos(pitch * ((float)Math.PI / 180F));
        entity.shoot(x, y, z, velocity, inaccuracy);
    }

    @Nullable
    @Override
    public CompoundNBT getShareTag(ItemStack stack) {
        CompoundNBT baseTag = stack.getTag();
        INBT capabilityTag = CapabilityRegistry.WAND_CAPABILITY.writeNBT(getWandCapability(stack), null);

        CompoundNBT combinedTag = new CompoundNBT();
        if (baseTag != null) {
            combinedTag.put("base", baseTag);
        }
        if (capabilityTag != null) {
            combinedTag.put("capability", capabilityTag);
        }
        return combinedTag;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
        if (nbt == null) {
            stack.setTag(null);
            return;
        }
        CompoundNBT baseTag = nbt.getCompound("base");
        CompoundNBT capabilityTag = nbt.getCompound("capability");
        stack.setTag(baseTag);
        CapabilityRegistry.WAND_CAPABILITY.readNBT(getWandCapability(stack), null, capabilityTag);
    }

    public static WandCapability getWandCapability(ItemStack stack) {
        Optional<WandCapability> capabilityOpt = stack.getCapability(CapabilityRegistry.WAND_CAPABILITY).resolve();
        if (!capabilityOpt.isPresent()) {
            TerraMagic.LOGGER.error("WandItem did not have the expected WAND_CAPABILITY");
            WandCapability capability = new WandCapability();
            capability.setCooldown(((WandItem) stack.getItem()).getCooldown());
            return capability;
        }
        return capabilityOpt.get();
    }
}
