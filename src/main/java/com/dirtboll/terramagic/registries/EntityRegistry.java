package com.dirtboll.terramagic.registries;

import com.dirtboll.terramagic.TerraMagic;
import com.dirtboll.terramagic.entities.FireSparkEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = TerraMagic.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(TerraMagic.MOD_ID)
public class EntityRegistry {

    public static final EntityType<FireSparkEntity> FIRE_SPARK = null;

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        TerraMagic.LOGGER.info("Registering entities...");

        event.getRegistry().registerAll(
                EntityType.Builder.<FireSparkEntity>create(FireSparkEntity::new, EntityClassification.MISC)
                        .size(1.5f, 1.5f)
                        .immuneToFire()
                        .setTrackingRange(10)
                        .setShouldReceiveVelocityUpdates(true)
                        .setUpdateInterval(60)
                        .build("")
                        .setRegistryName(TerraMagic.MOD_ID, "fire_spark")
        );
    }
}
