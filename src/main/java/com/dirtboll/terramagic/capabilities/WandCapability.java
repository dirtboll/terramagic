package com.dirtboll.terramagic.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.LongNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class WandCapability {

    private long cooldown;
    private long lastCast;

    public long getCooldown() {
        return cooldown;
    }

    public void setCooldown(long cooldown) {
        this.cooldown = cooldown;
    }

    public long getLastCast() {
        return lastCast;
    }

    public void setLastCast(long lastCast) {
        this.lastCast = lastCast;
    }

    public boolean cast() {
        boolean casted = canCast();
        if (casted) lastCast = System.currentTimeMillis();
        return casted;
    }

    public boolean canCast() {
        return System.currentTimeMillis() > lastCast + cooldown;
    }

    public static class WandCapabilityNBTStorage implements Capability.IStorage<WandCapability> {

        @Nullable
        @Override
        public INBT writeNBT(Capability<WandCapability> capability, WandCapability instance, Direction side) {
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.put("cooldown", LongNBT.valueOf(instance.cooldown));
            compoundNBT.put("lastCast", LongNBT.valueOf(instance.lastCast));
            return compoundNBT;
        }

        @Override
        public void readNBT(Capability<WandCapability> capability, WandCapability instance, Direction side, INBT nbt) {
            if (nbt.getType() != CompoundNBT.TYPE) return;
            instance.cooldown = ((CompoundNBT) nbt).getLong("cooldown");
            instance.lastCast = ((CompoundNBT) nbt).getLong("lastCast");
        }
    }

}
