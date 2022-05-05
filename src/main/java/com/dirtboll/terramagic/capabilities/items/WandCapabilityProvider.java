package com.dirtboll.terramagic.capabilities.items;

import com.dirtboll.terramagic.capabilities.WandCapability;
import com.dirtboll.terramagic.registries.CapabilityRegistry;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WandCapabilityProvider implements ICapabilitySerializable<INBT> {

    private final WandCapability wandCapability;
    private final LazyOptional<WandCapability> optional;

    public WandCapabilityProvider(long cooldown) {
        wandCapability = new WandCapability();
        wandCapability.setCooldown(cooldown);
        optional = LazyOptional.of(() -> wandCapability);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
        if (capability == CapabilityRegistry.WAND_CAPABILITY) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return CapabilityRegistry.WAND_CAPABILITY.writeNBT(wandCapability, null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        CapabilityRegistry.WAND_CAPABILITY.readNBT(wandCapability, null, nbt);
    }
}
