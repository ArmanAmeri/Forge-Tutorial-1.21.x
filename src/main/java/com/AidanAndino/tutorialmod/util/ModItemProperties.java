package com.AidanAndino.tutorialmod.util;

import com.AidanAndino.tutorialmod.TutorialMod;
import com.AidanAndino.tutorialmod.component.ModDataComponentTypes;
import com.AidanAndino.tutorialmod.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ModItemProperties {
    public static void addCustomItemProperties() {
        ItemProperties.register(ModItems.CHISEL.get(), ResourceLocation.fromNamespaceAndPath(TutorialMod.MOD_ID, "used"),
                (pStack, pLevel, pEntity, pSeed) -> pStack.get(ModDataComponentTypes.COORDINATES.get()) != null ? 1f : 0f);
    }
}
