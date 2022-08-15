/*
 * Copyright (c) LuciferMorningstarDev <contact@lucifer-morningstar.dev>
 * Copyright (c) surviv.fun <contact@surviv.fun>
 * Copyright (C) surviv.fun team and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package fun.surviv.survival.ai.mobs.overworld;

import fun.surviv.survival.ai.MobAI;
import fun.surviv.survival.ai.mobs.BetterMob;
import fun.surviv.survival.ai.mobs.attacks.SkeletonAttack;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import java.util.Map;

/**
 * SurvivalSystem; fun.surviv.survival.ai.mobs.overworld:BetterSkeleton
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.08.2022
 */

public class BetterSkeleton extends BetterMob {
    public static Map<String, String> lastArrow;

    private int scheduler;

    public BetterSkeleton(Skeleton skeleton) {
        super((LivingEntity) skeleton);
    }

    public void normalAttack(final Player player) {
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterSkeleton.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Skeleton.NormalAttack.Speed"), 10.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterSkeleton.this.entity.isDead()) {
                        BetterSkeleton.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterSkeleton.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterSkeleton.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Skeleton.NormalAttack.Speed"), 10.0D);
                    } else {
                        BetterSkeleton.this.track(BetterSkeleton.this.entity.getLocation(), 0.0F, 0.0D);
                        Arrow arrow = (Arrow) BetterSkeleton.this.entity.getWorld().spawnEntity(BetterSkeleton.this.entity.getEyeLocation().clone().add(0.0D, 1.0D, 0.0D), EntityType.ARROW);
                        Vector vector = player.getLocation().subtract(BetterSkeleton.this.entity.getEyeLocation().clone().add(0.0D, 1.0D, 0.0D)).toVector();
                        arrow.setShooter((ProjectileSource) BetterSkeleton.this.entity);
                        BetterSkeleton.lastArrow.put(player.getUniqueId().toString(), "None");
                        arrow.setVelocity(vector.multiply(1));
                        BetterSkeleton.this.randomAttack(player, MobAI.settings.configuration.getInt("Skeleton.NormalAttack.NextAttackDelay"));
                        Bukkit.getScheduler().cancelTask(BetterSkeleton.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void burningArrowAttack(final Player player) {
        if (MobAI.settings.configuration.getBoolean("Skeleton.BurningArrowAttack.Disable")) {
            normalAttack(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterSkeleton.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Skeleton.BurningArrowAttack.Speed"), 10.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterSkeleton.this.entity.isDead()) {
                        BetterSkeleton.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterSkeleton.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterSkeleton.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Skeleton.BurningArrowAttack.Speed"), 10.0D);
                    } else {
                        BetterSkeleton.this.track(BetterSkeleton.this.entity.getLocation(), 0.0F, 0.0D);
                        Arrow arrow = (Arrow) BetterSkeleton.this.entity.getWorld().spawnEntity(BetterSkeleton.this.entity.getEyeLocation().clone().add(0.0D, 1.0D, 0.0D), EntityType.ARROW);
                        Vector vector = player.getLocation().subtract(BetterSkeleton.this.entity.getEyeLocation().clone().add(0.0D, 1.0D, 0.0D)).toVector();
                        arrow.setFireTicks(200);
                        arrow.setShooter((ProjectileSource) BetterSkeleton.this.entity);
                        BetterSkeleton.lastArrow.put(player.getUniqueId().toString(), "Burning");
                        arrow.setVelocity(vector.multiply(1));
                        BetterSkeleton.this.randomAttack(player, MobAI.settings.configuration.getInt("Skeleton.BurningArrowAttack.NextAttackDelay"));
                        Bukkit.getScheduler().cancelTask(BetterSkeleton.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void poisonedArrowAttack(final Player player) {
        if (MobAI.settings.configuration.getBoolean("Skeleton.PoisonArrowAttack.Disable")) {
            normalAttack(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterSkeleton.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Skeleton.PoisonArrowAttack.Speed"), 10.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterSkeleton.this.entity.isDead()) {
                        BetterSkeleton.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterSkeleton.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterSkeleton.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Skeleton.PoisonArrowAttack.Speed"), 10.0D);
                    } else {
                        BetterSkeleton.this.track(BetterSkeleton.this.entity.getLocation(), 0.0F, 0.0D);
                        Arrow arrow = (Arrow) BetterSkeleton.this.entity.getWorld().spawnEntity(BetterSkeleton.this.entity.getEyeLocation().clone().add(0.0D, 1.0D, 0.0D), EntityType.ARROW);
                        Vector vector = player.getLocation().subtract(BetterSkeleton.this.entity.getEyeLocation().clone().add(0.0D, 1.0D, 0.0D)).toVector();
                        BetterSkeleton.lastArrow.put(player.getUniqueId().toString(), "Poisoned");
                        arrow.setShooter((ProjectileSource) BetterSkeleton.this.entity);
                        arrow.setVelocity(vector.multiply(1));
                        BetterSkeleton.this.randomAttack(player, MobAI.settings.configuration.getInt("Skeleton.PoisonArrowAttack.NextAttackDelay"));
                        Bukkit.getScheduler().cancelTask(BetterSkeleton.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void nailingArrowAttack(final Player player) {
        if (MobAI.settings.configuration.getBoolean("Skeleton.NailingArrowAttack.Disable")) {
            normalAttack(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterSkeleton.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Skeleton.NailingArrowAttack.Speed"), 10.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterSkeleton.this.entity.isDead()) {
                        BetterSkeleton.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterSkeleton.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterSkeleton.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Skeleton.NailingArrowAttack.Speed"), 10.0D);
                    } else {
                        BetterSkeleton.this.track(BetterSkeleton.this.entity.getLocation(), 0.0F, 0.0D);
                        Arrow arrow = (Arrow) BetterSkeleton.this.entity.getWorld().spawnEntity(BetterSkeleton.this.entity.getEyeLocation().clone().add(0.0D, 1.0D, 0.0D), EntityType.ARROW);
                        Vector vector = player.getLocation().subtract(BetterSkeleton.this.entity.getEyeLocation().clone().add(0.0D, 1.0D, 0.0D)).toVector();
                        BetterSkeleton.lastArrow.put(player.getUniqueId().toString(), "Nailing");
                        arrow.setShooter((ProjectileSource) BetterSkeleton.this.entity);
                        arrow.setVelocity(vector.multiply(1));
                        BetterSkeleton.this.randomAttack(player, MobAI.settings.configuration.getInt("Skeleton.NailingArrowAttack.NextAttackDelay"));
                        Bukkit.getScheduler().cancelTask(BetterSkeleton.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void getARide() {
        if (MobAI.settings.configuration.getBoolean("Skeleton.GetARide.Disable")) return;
        int j = MobAI.settings.configuration.getInt("Skeleton.GetARide.Radius");
        for (Entity e : this.entity.getNearbyEntities(j, j, j)) {
            if (e.getType().equals(EntityType.SPIDER)) {
                if (e instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) e;
                    if (livingEntity.getPassenger() == null) {
                        livingEntity.addPassenger((Entity) this.entity);
                        BetterSpider spider = new BetterSpider((Spider) e);
                        spider.setBusy(true);
                        break;
                    }
                }
                continue;
            }
            if (e.getType().equals(EntityType.CAVE_SPIDER) && e instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) e;
                if (livingEntity.getPassenger() == null) {
                    livingEntity.addPassenger((Entity) this.entity);
                    BetterCaveSpider spider = new BetterCaveSpider((CaveSpider) e);
                    spider.setBusy(true);
                    break;
                }
            }
        }
    }

    public void arrowRainAttack(final Player player) {
        if (MobAI.settings.configuration.getBoolean("Skeleton.ArrowRain.Disable")) {
            normalAttack(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterSkeleton.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Skeleton.ArrowRain.Speed"), 10.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterSkeleton.this.entity.isDead()) {
                        BetterSkeleton.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterSkeleton.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterSkeleton.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Skeleton.ArrowRain.Speed"), 10.0D);
                    } else {
                        BetterSkeleton.this.track(BetterSkeleton.this.entity.getLocation(), 0.0F, 0.0D);
                        BetterSkeleton.lastArrow.put(player.getUniqueId().toString(), "None");
                        for (int i = 0; i < MobAI.settings.configuration.getInt("Skeleton.ArrowRain.Arrows"); i++) {
                            Vector vector;
                            Arrow arrow = (Arrow) BetterSkeleton.this.entity.getWorld().spawnEntity(BetterSkeleton.this.entity.getEyeLocation().clone().add(0.0D, 1.0D, 0.0D), EntityType.ARROW);
                            switch (i) {
                                case 0:
                                    vector = player.getLocation().subtract(BetterSkeleton.this.entity.getEyeLocation().clone().add(0.3D, 1.0D, 0.0D)).toVector();
                                    break;
                                case 1:
                                    vector = player.getLocation().subtract(BetterSkeleton.this.entity.getEyeLocation().clone().add(0.3D, 1.0D, 0.3D)).toVector();
                                    break;
                                case 2:
                                    vector = player.getLocation().subtract(BetterSkeleton.this.entity.getEyeLocation().clone().add(0.0D, 1.0D, 0.0D)).toVector();
                                    break;
                                case 3:
                                    vector = player.getLocation().subtract(BetterSkeleton.this.entity.getEyeLocation().clone().add(-0.3D, 1.0D, 0.0D)).toVector();
                                    break;
                                case 4:
                                    vector = player.getLocation().subtract(BetterSkeleton.this.entity.getEyeLocation().clone().add(-0.3D, 1.0D, -0.3D)).toVector();
                                    break;
                                case 5:
                                    vector = player.getLocation().subtract(BetterSkeleton.this.entity.getEyeLocation().clone().add(-0.3D, 1.0D, 0.3D)).toVector();
                                    break;
                                case 6:
                                    vector = player.getLocation().subtract(BetterSkeleton.this.entity.getEyeLocation().clone().add(0.3D, 1.0D, -0.3D)).toVector();
                                    break;
                                default:
                                    vector = player.getLocation().subtract(BetterSkeleton.this.entity.getEyeLocation().clone().add(0.0D, 1.0D, 0.0D)).toVector();
                                    break;
                            }
                            arrow.setShooter((ProjectileSource) BetterSkeleton.this.entity);
                            arrow.setVelocity(vector.multiply(1));
                        }
                        BetterSkeleton.this.randomAttack(player, MobAI.settings.configuration.getInt("Skeleton.ArrowRain.NextAttackDelay"));
                        Bukkit.getScheduler().cancelTask(BetterSkeleton.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void trackAndKill(final Player player) {
        if (!isBusy()) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterSkeleton.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Skeleton.TrackingSpeed"), 15.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterSkeleton.this.entity.isDead()) {
                        BetterSkeleton.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterSkeleton.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterSkeleton.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Skeleton.TrackingSpeed"), 15.0D);
                    } else {
                        BetterSkeleton.this.track(BetterSkeleton.this.entity.getLocation(), 0.0F, 0.0D);
                        BetterSkeleton.this.setBusy(false);
                        BetterSkeleton.this.randomAttack(player, 0);
                        Bukkit.getScheduler().cancelTask(BetterSkeleton.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void randomAttack(Player player, int delay) {
        setBusy(true);
        getARide();
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) MobAI.getInstance().getPlugin(), () -> {
            SkeletonAttack attack = SkeletonAttack.values()[this.random.nextInt((SkeletonAttack.values()).length)];
            switch (attack) {
                case NORMAL_ATTACK:
                    normalAttack(player);
                    return;
                case ARROW_RAIN_ATTACK:
                    if (this.random.nextInt(10) < 4) arrowRainAttack(player);
                    return;
                case BURNING_ARROW_ATTACK:
                    burningArrowAttack(player);
                    return;
                case NAILING_ARROW_ATTACK:
                    nailingArrowAttack(player);
                    return;
                case POISONED_ARROW_ATTACK:
                    poisonedArrowAttack(player);
                    return;
                default:
                    break;
            }
            normalAttack(player);
        }, (20 * delay));
    }
}
