package com.AidanAndino.tutorialmod.mmcosmetics;

import com.AidanAndino.tutorialmod.TutorialMod;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.MONKEE_TAIL,
                MonkeeTailModel::createBodyLayer); // changed from MonkeeTailLayerDefinition
    }

    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        EntityModelSet modelSet = event.getEntityModels();

        for (PlayerSkin.Model skin : event.getSkins()) {
            PlayerRenderer renderer = event.getPlayerSkin(skin);
            renderer.addLayer(new MonkeeTailLayer(renderer, modelSet));
        }
    }
}