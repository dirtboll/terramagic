package com.dirtboll.terramagic.registries;

import com.dirtboll.terramagic.TerraMagic;
import com.dirtboll.terramagic.capabilities.WandCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = TerraMagic.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CapabilityRegistry {
    @CapabilityInject(WandCapability.class)
    public static Capability<WandCapability> WAND_CAPABILITY = null;

    @SubscribeEvent
    public static void registerCapabilities(FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(
                WandCapability.class,
                new WandCapability.WandCapabilityNBTStorage(),
                WandCapability::new
        );
    }
}
