package com.dirtboll.terramagic.registries;

import com.dirtboll.terramagic.TerraMagic;
import com.dirtboll.terramagic.items.WandOfSparking;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = TerraMagic.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(TerraMagic.MOD_ID)
public class ItemRegistry {

    public static final Item WAND_OF_SPARKING = null;

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        TerraMagic.LOGGER.info("Registering items...");
        event.getRegistry().registerAll(
                new WandOfSparking()
                        .setRegistryName(TerraMagic.MOD_ID, "wand_of_sparking")
        );
    }
}
