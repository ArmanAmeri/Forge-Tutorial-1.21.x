package com.AidanAndino.tutorialmod.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {
    public static final FoodProperties PIZZA = new FoodProperties.Builder().nutrition(10).saturationModifier(0.8f)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 400), 1f).build();

    public static final FoodProperties COLA = new FoodProperties.Builder().nutrition(2).saturationModifier(1f)
            .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400), 0.50f).build();

    public static final FoodProperties HONEY_BERRY = new FoodProperties.Builder().nutrition(2).saturationModifier(0.15f).fast().build();
}
