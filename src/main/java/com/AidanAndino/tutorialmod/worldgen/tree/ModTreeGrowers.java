package com.AidanAndino.tutorialmod.worldgen.tree;


import com.AidanAndino.tutorialmod.TutorialMod;
import com.AidanAndino.tutorialmod.worldgen.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower WALNUT = new TreeGrower(TutorialMod.MOD_ID + ":walnut",
            Optional.empty(), Optional.of(ModConfiguredFeatures.WALNUT_KEY), Optional.empty());
}
