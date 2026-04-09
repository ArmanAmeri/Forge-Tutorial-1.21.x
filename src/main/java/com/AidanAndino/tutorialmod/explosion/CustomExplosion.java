package com.AidanAndino.tutorialmod.explosion;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class CustomExplosion {

    public static void explode(Level level, double x, double y, double z, float radius, float Hmultiplier, float pdamage) {
        if (level.isClientSide) return;

        ServerLevel serverLevel = (ServerLevel) level;
        int r = (int) radius;
        int rimStart = r - 3;

        // --- Squished sphere block removal ---
        for (int dx = -r; dx <= r; dx++) {
            for (int dy = -r; dy <= r; dy++) {
                for (int dz = -r; dz <= r; dz++) {
                    double distSq = dx * dx + (dy * Hmultiplier) * (dy * Hmultiplier) + dz * dz;

                    if (distSq <= r * r) {
                        BlockPos pos = new BlockPos((int) x + dx, (int) y + dy, (int) z + dz);
                        if (level.getBlockState(pos).is(Blocks.BEDROCK)) continue;
                        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                    }
                }
            }
        }

        // --- Scatter fire on top of rim blocks ---
        for (int dx = -r; dx <= r; dx++) {
            for (int dz = -r; dz <= r; dz++) {
                double distSq = dx * dx + dz * dz;
                if (distSq <= r * r && distSq >= rimStart * rimStart) {
                    if (Math.random() < 0.3) {
                        BlockPos firePos = new BlockPos((int) x + dx, (int) y + 1, (int) z + dz);
                        if (level.getBlockState(firePos).isAir()) {
                            level.setBlock(firePos, Blocks.FIRE.defaultBlockState(), 3);
                        }
                    }
                }
            }
        }

        // --- Round entity damage ---
        List<LivingEntity> entities = level.getEntitiesOfClass(
                LivingEntity.class,
                new AABB(x - radius, y - radius, z - radius,
                        x + radius, y + radius, z + radius)
        );
        for (LivingEntity entity : entities) {
            double dist = entity.position().distanceTo(new Vec3(x, y, z));
            if (dist <= radius) {
                float damage = (float) (pdamage * (1.0 - dist / radius));
                entity.hurt(level.damageSources().explosion(null, null), damage);
            }
        }

        // --- Particles ---
        serverLevel.sendParticles(ParticleTypes.EXPLOSION_EMITTER, x, y, z, 1, 0, 0, 0, 0);
        serverLevel.sendParticles(ParticleTypes.EXPLOSION_EMITTER, x, y + 5, z, 3, 3, 3, 3, 0);
        serverLevel.sendParticles(ParticleTypes.FLAME, x, y, z, 200, radius * 0.5, radius * 0.5, radius * 0.5, 0.2);
        serverLevel.sendParticles(ParticleTypes.SMOKE, x, y, z, 300, radius * 0.6, radius * 0.6, radius * 0.6, 0.05);
        serverLevel.sendParticles(ParticleTypes.LAVA, x, y, z, 50, radius * 0.3, radius * 0.3, radius * 0.3, 0.1);
        serverLevel.sendParticles(ParticleTypes.ASH, x, y, z, 400, radius, radius, radius, 0.1);

        // --- Sound ---
        level.playSound(null, x, y, z, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.BLOCKS, 10.0f, 0.5f);
        level.playSound(null, x, y, z, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.BLOCKS, 10.0f, 0.6f);
    }
}