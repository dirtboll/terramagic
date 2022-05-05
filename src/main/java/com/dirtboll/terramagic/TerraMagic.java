package com.dirtboll.terramagic;

import com.dirtboll.terramagic.registries.CapabilityRegistry;
import com.dirtboll.terramagic.registries.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(TerraMagic.MOD_ID)
public class TerraMagic {

    public static final String MOD_ID = "terramagic";

    public static final Logger LOGGER = LogManager.getLogger(TerraMagic.MOD_ID);

    public TerraMagic() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    public void setup(FMLCommonSetupEvent event) {
        LOGGER.info("Setting up Terra Magic");
    }
}
