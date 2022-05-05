package com.dirtboll.terramagic.registries;

import com.dirtboll.terramagic.TerraMagic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = TerraMagic.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RendererRegistry {

    @SubscribeEvent
    public static void registerRenderers(final FMLClientSetupEvent event) {
        TerraMagic.LOGGER.info("Registering renderers...");
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.FIRE_SPARK, renderManager -> new SpriteRenderer<>(renderManager, Minecraft.getInstance().getItemRenderer()));
    }
}
