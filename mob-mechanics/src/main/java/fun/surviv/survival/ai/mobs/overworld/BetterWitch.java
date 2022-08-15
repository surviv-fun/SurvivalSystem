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
import fun.surviv.survival.ai.mobs.attacks.WitchAttack;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Witch;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;
import java.util.Random;

/**
 * SurvivalSystem; fun.surviv.survival.ai.mobs.overworld:BetterWitch
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.08.2022
 */

public class BetterWitch extends BetterMob {
    private int scheduler;

    private int secondary;

    public BetterWitch(Witch witch) {
        super((LivingEntity) witch);
    }

    public void normalAttack(final Player player) {
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterWitch.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Witch.NormalAttack.Speed"), 15.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterWitch.this.entity.isDead()) {
                        BetterWitch.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterWitch.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterWitch.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Witch.NormalAttack.Speed"), 15.0D);
                    } else {
                        BetterWitch.this.track(BetterWitch.this.entity.getLocation(), 0.0F, 0.0D);
                        BetterWitch.this.particleNormalAttack(player);
                        Bukkit.getScheduler().cancelTask(BetterWitch.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void particleNormalAttack(final Player player) {
        this.entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20000, 100, true));
        final double health = this.entity.getHealth();
        this.secondary = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
            double i = 0.0D;

            double r = 1.0D;

            int j = 0;

            public void run() {
                if (BetterWitch.this.entity.getHealth() < health || player.isDead() || !player.isOnline()) {
                    BetterWitch.this.entity.removePotionEffect(PotionEffectType.SLOW);
                    BetterWitch.this.randomAttack(player, 0);
                    Bukkit.getScheduler().cancelTask(BetterWitch.this.secondary);
                } else if (this.j < 20) {
                    if (this.i > 3.0D) this.i = 0.0D;
                    this.i += 0.19634954084936207D;
                    double x = this.r * Math.cos(this.i);
                    double y = 0.5D * this.i;
                    double z = this.r * Math.sin(this.i);
                    Location loc1 = BetterWitch.this.entity.getLocation().clone().add(x, y, z);
                    Location loc2 = BetterWitch.this.entity.getLocation().clone().add(-x, y, -z);
                    Location loc3 = BetterWitch.this.entity.getLocation().clone().add(-x, y, z);
                    Location loc4 = BetterWitch.this.entity.getLocation().clone().add(x, y, -z);
                    BetterWitch.this.entity.getWorld().spawnParticle(Particle.MOB_APPEARANCE, loc1, 0, 0.0D, 0.0D, 0.0D, 1.0D);
                    BetterWitch.this.entity.getWorld().spawnParticle(Particle.MOB_APPEARANCE, loc2, 0, 0.0D, 0.0D, 0.0D, 1.0D);
                    BetterWitch.this.entity.getWorld().spawnParticle(Particle.MOB_APPEARANCE, loc3, 0, 0.0D, 0.0D, 0.0D, 1.0D);
                    BetterWitch.this.entity.getWorld().spawnParticle(Particle.MOB_APPEARANCE, loc4, 0, 0.0D, 0.0D, 0.0D, 1.0D);
                    this.j++;
                } else {
                    BetterWitch.this.entity.removePotionEffect(PotionEffectType.SLOW);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.HARM, 1, 1));
                    BetterWitch.this.track(BetterWitch.this.entity.getLocation(), 0.0F, 0.0D);
                    BetterWitch.this.randomAttack(player, MobAI.settings.configuration.getInt("Witch.NormalAttack.NextAttackDelay"));
                    Bukkit.getScheduler().cancelTask(BetterWitch.this.secondary);
                }
            }
        }, 0L, 2L);
    }

    public void blackMagicAttack(final Player player) {
        if (MobAI.settings.configuration.getBoolean("Witch.BlackMagicAttack.Disable")) {
            normalAttack(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterWitch.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Witch.BlackMagicAttack.Speed"), 15.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterWitch.this.entity.isDead()) {
                        BetterWitch.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterWitch.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterWitch.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Witch.BlackMagicAttack.Speed"), 15.0D);
                    } else {
                        BetterWitch.this.track(BetterWitch.this.entity.getLocation(), 0.0F, 0.0D);
                        BetterWitch.this.particleBlackMagicAttack(player);
                        Bukkit.getScheduler().cancelTask(BetterWitch.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void particleBlackMagicAttack(final Player player) {
        this.entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20000, 100, true));
        final double health = this.entity.getHealth();
        this.secondary = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
            double i = 0.0D;

            double r = 1.0D;

            int j = 0;

            public void run() {
                if (BetterWitch.this.entity.getHealth() < health || player.isDead() || !player.isOnline()) {
                    BetterWitch.this.entity.removePotionEffect(PotionEffectType.SLOW);
                    BetterWitch.this.randomAttack(player, 0);
                    Bukkit.getScheduler().cancelTask(BetterWitch.this.secondary);
                } else if (this.j < 20) {
                    if (this.i > 3.0D) this.i = 0.0D;
                    this.i += 0.19634954084936207D;
                    double x = this.r * Math.cos(this.i);
                    double y = 0.5D * this.i;
                    double z = this.r * Math.sin(this.i);
                    Location loc1 = BetterWitch.this.entity.getLocation().clone().add(x, y, z);
                    Location loc2 = BetterWitch.this.entity.getLocation().clone().add(-x, y, -z);
                    Location loc3 = BetterWitch.this.entity.getLocation().clone().add(-x, y, z);
                    Location loc4 = BetterWitch.this.entity.getLocation().clone().add(x, y, -z);
                    BetterWitch.this.entity.getWorld().spawnParticle(Particle.PORTAL, loc1, 0, 0.0D, 0.0D, 0.0D, 10.0D);
                    BetterWitch.this.entity.getWorld().spawnParticle(Particle.PORTAL, loc2, 0, 0.0D, 0.0D, 0.0D, 10.0D);
                    BetterWitch.this.entity.getWorld().spawnParticle(Particle.PORTAL, loc3, 0, 0.0D, 0.0D, 0.0D, 10.0D);
                    BetterWitch.this.entity.getWorld().spawnParticle(Particle.PORTAL, loc4, 0, 0.0D, 0.0D, 0.0D, 10.0D);
                    this.j++;
                } else {
                    BetterWitch.this.entity.removePotionEffect(PotionEffectType.SLOW);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * MobAI.settings.configuration.getInt("Witch.BlackMagicAttack.EffectLength"), 1));
                    BetterWitch.this.track(BetterWitch.this.entity.getLocation(), 0.0F, 0.0D);
                    BetterWitch.this.randomAttack(player, MobAI.settings.configuration.getInt("Witch.BlackMagicAttack.NextAttackDelay"));
                    Bukkit.getScheduler().cancelTask(BetterWitch.this.secondary);
                }
            }
        }, 0L, 2L);
    }

    public void burnPlayerAttack(final Player player) {
        if (MobAI.settings.configuration.getBoolean("Witch.FireAttack.Disable")) {
            normalAttack(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterWitch.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Witch.FireAttack.Speed"), 15.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterWitch.this.entity.isDead()) {
                        BetterWitch.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterWitch.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterWitch.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Witch.FireAttack.Speed"), 15.0D);
                    } else {
                        BetterWitch.this.track(BetterWitch.this.entity.getLocation(), 0.0F, 0.0D);
                        BetterWitch.this.particleBurnAttack(player);
                        Bukkit.getScheduler().cancelTask(BetterWitch.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void particleBurnAttack(final Player player) {
        this.entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20000, 100, true));
        final double health = this.entity.getHealth();
        this.secondary = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
            double i = 0.0D;

            double r = 1.0D;

            int j = 0;

            public void run() {
                if (BetterWitch.this.entity.getHealth() < health || player.isDead() || !player.isOnline()) {
                    BetterWitch.this.entity.removePotionEffect(PotionEffectType.SLOW);
                    BetterWitch.this.randomAttack(player, 0);
                    Bukkit.getScheduler().cancelTask(BetterWitch.this.secondary);
                } else if (this.j < 20) {
                    if (this.i > 3.0D) this.i = 0.0D;
                    this.i += 0.19634954084936207D;
                    double x = this.r * Math.cos(this.i);
                    double y = 0.5D * this.i;
                    double z = this.r * Math.sin(this.i);
                    Location loc1 = BetterWitch.this.entity.getLocation().clone().add(x, y, z);
                    Location loc2 = BetterWitch.this.entity.getLocation().clone().add(-x, y, -z);
                    Location loc3 = BetterWitch.this.entity.getLocation().clone().add(-x, y, z);
                    Location loc4 = BetterWitch.this.entity.getLocation().clone().add(x, y, -z);
                    BetterWitch.this.entity.getWorld().spawnParticle(Particle.SMOKE_LARGE, loc1, 0, 0.0D, 0.0D, 0.0D, 5.0D);
                    BetterWitch.this.entity.getWorld().spawnParticle(Particle.SMOKE_LARGE, loc2, 0, 0.0D, 0.0D, 0.0D, 5.0D);
                    BetterWitch.this.entity.getWorld().spawnParticle(Particle.SMOKE_LARGE, loc3, 0, 0.0D, 0.0D, 0.0D, 5.0D);
                    BetterWitch.this.entity.getWorld().spawnParticle(Particle.SMOKE_LARGE, loc4, 0, 0.0D, 0.0D, 0.0D, 5.0D);
                    this.j++;
                } else {
                    BetterWitch.this.entity.removePotionEffect(PotionEffectType.SLOW);
                    player.setFireTicks(10 * MobAI.settings.configuration.getInt("Witch.FireAttack.EffectLength"));
                    BetterWitch.this.track(BetterWitch.this.entity.getLocation(), 0.0F, 0.0D);
                    BetterWitch.this.randomAttack(player, MobAI.settings.configuration.getInt("Witch.FireAttack.NextAttackDelay"));
                    Bukkit.getScheduler().cancelTask(BetterWitch.this.secondary);
                }
            }
        }, 0L, 2L);
    }

    public void fireCircleAttack(final Player player) {
        if (MobAI.settings.configuration.getBoolean("Witch.FireCircleAttack.Disable")) {
            normalAttack(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterWitch.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Witch.FireCircleAttack.Speed"), 15.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterWitch.this.entity.isDead()) {
                        BetterWitch.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterWitch.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterWitch.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Witch.FireCircleAttack.Speed"), 15.0D);
                    } else {
                        BetterWitch.this.track(BetterWitch.this.entity.getLocation(), 0.0F, 0.0D);
                        BetterWitch.this.particleFireCircleAttack(player);
                        Bukkit.getScheduler().cancelTask(BetterWitch.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void particleFireCircleAttack(final Player player) {
        this.entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20000, 100, true));
        final double health = this.entity.getHealth();
        this.secondary = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
            double i = 0.0D;

            double r = 1.0D;

            int j = 0;

            public void run() {
                if (BetterWitch.this.entity.getHealth() < health || player.isDead() || !player.isOnline()) {
                    BetterWitch.this.entity.removePotionEffect(PotionEffectType.SLOW);
                    BetterWitch.this.randomAttack(player, 0);
                    Bukkit.getScheduler().cancelTask(BetterWitch.this.secondary);
                } else if (this.j < 20) {
                    if (this.i > 3.0D) this.i = 0.0D;
                    this.i += 0.19634954084936207D;
                    double x = this.r * Math.cos(this.i);
                    double y = 0.5D * this.i;
                    double z = this.r * Math.sin(this.i);
                    Location loc1 = BetterWitch.this.entity.getLocation().clone().add(x, y, z);
                    Location loc2 = BetterWitch.this.entity.getLocation().clone().add(-x, y, -z);
                    Location loc3 = BetterWitch.this.entity.getLocation().clone().add(-x, y, z);
                    Location loc4 = BetterWitch.this.entity.getLocation().clone().add(x, y, -z);
                    BetterWitch.this.entity.getWorld().spawnParticle(Particle.FLAME, loc1, 0, 0.0D, 0.0D, 0.0D, 1.0D);
                    BetterWitch.this.entity.getWorld().spawnParticle(Particle.FLAME, loc2, 0, 0.0D, 0.0D, 0.0D, 1.0D);
                    BetterWitch.this.entity.getWorld().spawnParticle(Particle.FLAME, loc3, 0, 0.0D, 0.0D, 0.0D, 1.0D);
                    BetterWitch.this.entity.getWorld().spawnParticle(Particle.FLAME, loc4, 0, 0.0D, 0.0D, 0.0D, 1.0D);
                    this.j++;
                } else {
                    BetterWitch.this.entity.removePotionEffect(PotionEffectType.SLOW);
                    for (int i = 4; i < 4 + MobAI.settings.configuration.getInt("Witch.FireCircleAttack.Radius"); i++) {
                        for (int x = player.getLocation().getBlockX() - i; x < player.getLocation().getBlockX() + i; x++) {
                            ((World) Objects.<World>requireNonNull(player.getLocation().getWorld())).getHighestBlockAt(x, player.getLocation().getBlockZ() - i).setType(Material.FIRE);
                            player.getLocation().getWorld().getHighestBlockAt(x, player.getLocation().getBlockZ() + i).setType(Material.FIRE);
                        }
                        for (int z = player.getLocation().getBlockZ() - i; z < player.getLocation().getBlockZ() + i; z++) {
                            ((World) Objects.<World>requireNonNull(player.getLocation().getWorld())).getHighestBlockAt(player.getLocation().getBlockX() - i, z).setType(Material.FIRE);
                            player.getLocation().getWorld().getHighestBlockAt(player.getLocation().getBlockX() + i, z).setType(Material.FIRE);
                        }
                    }
                    BetterWitch.this.randomAttack(player, MobAI.settings.configuration.getInt("Witch.FireCircleAttack.NextAttackDelay"));
                    Bukkit.getScheduler().cancelTask(BetterWitch.this.secondary);
                }
            }
        }, 0L, 2L);
    }

    public void lavaAttack(final Player player) {
        if (MobAI.settings.configuration.getBoolean("Witch.LavaAttack.Disable")) {
            normalAttack(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterWitch.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Witch.LavaAttack.Speed"), 15.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterWitch.this.entity.isDead()) {
                        BetterWitch.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterWitch.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterWitch.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Witch.LavaAttack.Speed"), 15.0D);
                    } else {
                        BetterWitch.this.track(BetterWitch.this.entity.getLocation(), 0.0F, 0.0D);
                        BetterWitch.this.particleLavaAttack(player);
                        Bukkit.getScheduler().cancelTask(BetterWitch.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void particleLavaAttack(final Player player) {
        this.entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20000, 100, true));
        final double health = this.entity.getHealth();
        this.secondary = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
            double i = 0.0D;

            double r = 1.0D;

            int j = 0;

            public void run() {
                if (BetterWitch.this.entity.getHealth() < health || player.isDead() || !player.isOnline()) {
                    BetterWitch.this.entity.removePotionEffect(PotionEffectType.SLOW);
                    BetterWitch.this.randomAttack(player, 0);
                    Bukkit.getScheduler().cancelTask(BetterWitch.this.secondary);
                } else if (this.j < 20) {
                    if (this.i > 3.0D) this.i = 0.0D;
                    this.i += 0.19634954084936207D;
                    double x = this.r * Math.cos(this.i);
                    double y = 0.5D * this.i;
                    double z = this.r * Math.sin(this.i);
                    Location loc1 = BetterWitch.this.entity.getLocation().clone().add(x, y, z);
                    Location loc2 = BetterWitch.this.entity.getLocation().clone().add(-x, y, -z);
                    Location loc3 = BetterWitch.this.entity.getLocation().clone().add(-x, y, z);
                    Location loc4 = BetterWitch.this.entity.getLocation().clone().add(x, y, -z);
                    BetterWitch.this.entity.getWorld().spawnParticle(Particle.DRIP_LAVA, loc1, 0, 0.0D, 0.0D, 0.0D, 1.0D);
                    BetterWitch.this.entity.getWorld().spawnParticle(Particle.DRIP_LAVA, loc2, 0, 0.0D, 0.0D, 0.0D, 1.0D);
                    BetterWitch.this.entity.getWorld().spawnParticle(Particle.DRIP_LAVA, loc3, 0, 0.0D, 0.0D, 0.0D, 1.0D);
                    BetterWitch.this.entity.getWorld().spawnParticle(Particle.DRIP_LAVA, loc4, 0, 0.0D, 0.0D, 0.0D, 1.0D);
                    this.j++;
                } else {
                    BetterWitch.this.entity.removePotionEffect(PotionEffectType.SLOW);
                    Material material = player.getEyeLocation().getBlock().getType();
                    player.getEyeLocation().getBlock().setType(Material.LAVA);
                    player.getEyeLocation().getBlock().setType(material);
                    player.setFireTicks(50);
                    BetterWitch.this.track(BetterWitch.this.entity.getLocation(), 0.0F, 0.0D);
                    BetterWitch.this.randomAttack(player, MobAI.settings.configuration.getInt("Witch.LavaAttack.NextAttackDelay"));
                    Bukkit.getScheduler().cancelTask(BetterWitch.this.secondary);
                }
            }
        }, 0L, 2L);
    }

    public void poisonAttack(final Player player) {
        if (MobAI.settings.configuration.getBoolean("Witch.PoisonAttack.Disable")) {
            normalAttack(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterWitch.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Witch.PoisonAttack.Speed"), 15.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterWitch.this.entity.isDead()) {
                        BetterWitch.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterWitch.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterWitch.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Witch.PoisonAttack.Speed"), 15.0D);
                    } else {
                        BetterWitch.this.track(BetterWitch.this.entity.getLocation(), 0.0F, 0.0D);
                        BetterWitch.this.particlePoisonAttack(player);
                        Bukkit.getScheduler().cancelTask(BetterWitch.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void particlePoisonAttack(final Player player) {
        this.entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20000, 100, true));
        final double health = this.entity.getHealth();
        this.secondary = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
            double i = 0.39269908169872414D;

            int j = 0;

            public void run() {
                if (BetterWitch.this.entity.getHealth() < health || player.isDead() || !player.isOnline()) {
                    BetterWitch.this.entity.removePotionEffect(PotionEffectType.SLOW);
                    BetterWitch.this.randomAttack(player, 0);
                    Bukkit.getScheduler().cancelTask(BetterWitch.this.secondary);
                } else if (this.j < 20) {
                    this.i += 0.3141592653589793D;
                    for (double alpha = 0.0D; alpha <= 6.283185307179586D; alpha += 0.09817477042468103D) {
                        double x = this.i * Math.cos(alpha);
                        double y = 2.0D * Math.exp(-0.1D * this.i) * Math.sin(this.i) + 1.5D;
                        double z = this.i * Math.sin(alpha);
                        Location loc1 = BetterWitch.this.entity.getLocation().clone().add(x, y, z);
                        BetterWitch.this.entity.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, loc1, 0, 0.0D, 0.0D, 0.0D, 1.0D);
                    }
                    this.j++;
                } else {
                    BetterWitch.this.entity.removePotionEffect(PotionEffectType.SLOW);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * MobAI.settings.configuration.getInt("Witch.PoisonAttack.EffectLength"), 1));
                    BetterWitch.this.track(BetterWitch.this.entity.getLocation(), 0.0F, 0.0D);
                    BetterWitch.this.randomAttack(player, MobAI.settings.configuration.getInt("Witch.PoisonAttack.NextAttackDelay"));
                    Bukkit.getScheduler().cancelTask(BetterWitch.this.secondary);
                }
            }
        }, 0L, 2L);
    }

    public void slownessAttack(final Player player) {
        if (MobAI.settings.configuration.getBoolean("Witch.SlownessAttack.Disable")) {
            normalAttack(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterWitch.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Witch.SlownessAttack.Speed"), 15.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterWitch.this.entity.isDead()) {
                        BetterWitch.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterWitch.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterWitch.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Witch.SlownessAttack.Speed"), 15.0D);
                    } else {
                        BetterWitch.this.track(BetterWitch.this.entity.getLocation(), 0.0F, 0.0D);
                        BetterWitch.this.particleSlownessAttack(player);
                        Bukkit.getScheduler().cancelTask(BetterWitch.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void particleSlownessAttack(final Player player) {
        this.entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20000, 100, true));
        final double health = this.entity.getHealth();
        this.secondary = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
            double i = 0.39269908169872414D;

            int j = 0;

            public void run() {
                if (BetterWitch.this.entity.getHealth() < health || player.isDead() || !player.isOnline()) {
                    BetterWitch.this.entity.removePotionEffect(PotionEffectType.SLOW);
                    BetterWitch.this.randomAttack(player, 0);
                    Bukkit.getScheduler().cancelTask(BetterWitch.this.secondary);
                } else if (this.j < 20) {
                    this.i += 0.3141592653589793D;
                    for (double alpha = 0.0D; alpha <= 6.283185307179586D; alpha += 0.39269908169872414D) {
                        double x = this.i * Math.cos(alpha);
                        double y = 2.0D * Math.exp(-0.1D * this.i) * Math.sin(this.i) + 1.5D;
                        double z = this.i * Math.sin(alpha);
                        Location loc1 = BetterWitch.this.entity.getLocation().clone().add(x, y, z);
                        BetterWitch.this.entity.getWorld().spawnParticle(Particle.SNOW_SHOVEL, loc1, 0, 0.0D, 0.0D, 0.0D, 1.0D);
                    }
                    this.j++;
                } else {
                    BetterWitch.this.entity.removePotionEffect(PotionEffectType.SLOW);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * MobAI.settings.configuration.getInt("Witch.SlownessAttack.EffectLength"), 5));
                    BetterWitch.this.track(BetterWitch.this.entity.getLocation(), 0.0F, 0.0D);
                    BetterWitch.this.randomAttack(player, MobAI.settings.configuration.getInt("Witch.SlownessAttack.NextAttackDelay"));
                    Bukkit.getScheduler().cancelTask(BetterWitch.this.secondary);
                }
            }
        }, 0L, 2L);
    }

    public void endermitesAttack(final Player player) {
        if (MobAI.settings.configuration.getBoolean("Witch.EndermitesAttack.Disable")) {
            normalAttack(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterWitch.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Witch.EndermitesAttack.Speed"), 15.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterWitch.this.entity.isDead()) {
                        BetterWitch.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterWitch.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterWitch.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Witch.EndermitesAttack.Speed"), 15.0D);
                    } else {
                        BetterWitch.this.track(BetterWitch.this.entity.getLocation(), 0.0F, 0.0D);
                        BetterWitch.this.endermitesAttackParticle(player);
                        Bukkit.getScheduler().cancelTask(BetterWitch.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void endermitesAttackParticle(final Player player) {
        this.entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20000, 100, true));
        final double health = this.entity.getHealth();
        this.secondary = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
            double i = 0.0D;

            double r = 1.0D;

            int j = 0;

            public void run() {
                if (BetterWitch.this.entity.getHealth() < health || player.isDead() || !player.isOnline()) {
                    BetterWitch.this.entity.removePotionEffect(PotionEffectType.SLOW);
                    BetterWitch.this.randomAttack(player, 0);
                    Bukkit.getScheduler().cancelTask(BetterWitch.this.secondary);
                } else if (this.j < 20) {
                    if (this.i > 3.0D) this.i = 0.0D;
                    this.i += 0.19634954084936207D;
                    double x = this.r * Math.cos(this.i);
                    double y = 0.5D * this.i;
                    double z = this.r * Math.sin(this.i);
                    Location loc1 = BetterWitch.this.entity.getLocation().clone().add(x, y, z);
                    Location loc2 = BetterWitch.this.entity.getLocation().clone().add(-x, y, -z);
                    Location loc3 = BetterWitch.this.entity.getLocation().clone().add(-x, y, z);
                    Location loc4 = BetterWitch.this.entity.getLocation().clone().add(x, y, -z);
                    BetterWitch.this.entity.getWorld().spawnParticle(Particle.CLOUD, loc1, 0, 0.0D, 0.0D, 0.0D, 1.0D);
                    BetterWitch.this.entity.getWorld().spawnParticle(Particle.CLOUD, loc2, 0, 0.0D, 0.0D, 0.0D, 1.0D);
                    BetterWitch.this.entity.getWorld().spawnParticle(Particle.CLOUD, loc3, 0, 0.0D, 0.0D, 0.0D, 1.0D);
                    BetterWitch.this.entity.getWorld().spawnParticle(Particle.CLOUD, loc4, 0, 0.0D, 0.0D, 0.0D, 1.0D);
                    this.j++;
                } else {
                    BetterWitch.this.entity.removePotionEffect(PotionEffectType.SLOW);
                    for (int i = 0; i < BetterWitch.this.random.nextInt(3) + 3; i++) {
                        Location location = BetterWitch.this.entity.getLocation().clone().add((BetterWitch.this.random.nextInt(6) - 3), 0.0D, ((new Random()).nextInt(6) - 3));
                        BetterWitch.this.entity.getWorld().spawnEntity(location, EntityType.ENDERMITE);
                    }
                    BetterWitch.this.randomAttack(player, MobAI.settings.configuration.getInt("Witch.EndermitesAttack.NextAttackDelay"));
                    Bukkit.getScheduler().cancelTask(BetterWitch.this.secondary);
                }
            }
        }, 0L, 2L);
    }

    public void healEffect(final Player player) {
        this.entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20000, 100, true));
        final double health = this.entity.getHealth();
        this.secondary = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
            double i = 0.0D;

            double r = 1.0D;

            int j = 0;

            public void run() {
                if (BetterWitch.this.entity.getHealth() < health || player.isDead() || !player.isOnline()) {
                    BetterWitch.this.entity.removePotionEffect(PotionEffectType.SLOW);
                    BetterWitch.this.randomAttack(player, 0);
                    Bukkit.getScheduler().cancelTask(BetterWitch.this.secondary);
                } else if (this.j < 20) {
                    if (this.i > 3.0D) this.i = 0.0D;
                    this.i += 0.19634954084936207D;
                    double x = this.r * Math.cos(this.i);
                    double y = 0.5D * this.i;
                    double z = this.r * Math.sin(this.i);
                    Location loc1 = BetterWitch.this.entity.getLocation().clone().add(x, y, z);
                    Location loc2 = BetterWitch.this.entity.getLocation().clone().add(-x, y, -z);
                    Location loc3 = BetterWitch.this.entity.getLocation().clone().add(-x, y, z);
                    Location loc4 = BetterWitch.this.entity.getLocation().clone().add(x, y, -z);
                    BetterWitch.this.entity.getWorld().spawnParticle(Particle.HEART, loc1, 0, 0.0D, 0.0D, 0.0D, 1.0D);
                    BetterWitch.this.entity.getWorld().spawnParticle(Particle.HEART, loc2, 0, 0.0D, 0.0D, 0.0D, 1.0D);
                    BetterWitch.this.entity.getWorld().spawnParticle(Particle.HEART, loc3, 0, 0.0D, 0.0D, 0.0D, 1.0D);
                    BetterWitch.this.entity.getWorld().spawnParticle(Particle.HEART, loc4, 0, 0.0D, 0.0D, 0.0D, 1.0D);
                    this.j++;
                } else {
                    BetterWitch.this.entity.removePotionEffect(PotionEffectType.SLOW);
                    BetterWitch.this.entity.setHealth(BetterWitch.this.entity.getMaxHealth());
                    Bukkit.getScheduler().cancelTask(BetterWitch.this.secondary);
                }
            }
        }, 0L, 2L);
    }

    public void trackAndKill(final Player player) {
        if (!isBusy()) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterWitch.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Witch.TrackingSpeed"), 20.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterWitch.this.entity.isDead()) {
                        BetterWitch.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterWitch.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterWitch.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Witch.TrackingSpeed"), 20.0D);
                    } else {
                        BetterWitch.this.track(BetterWitch.this.entity.getLocation(), 0.0F, 0.0D);
                        BetterWitch.this.setBusy(false);
                        BetterWitch.this.randomAttack(player, 0);
                        Bukkit.getScheduler().cancelTask(BetterWitch.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void randomAttack(Player player, int delay) {
        if (!player.isDead() && player.isOnline()) {
            setBusy(true);
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) MobAI.getInstance().getPlugin(), () -> {
                ((EntityEquipment) Objects.<EntityEquipment>requireNonNull(this.entity.getEquipment())).setItemInHand(new ItemStack(Material.BLAZE_ROD));
                WitchAttack attack = WitchAttack.values()[this.random.nextInt((WitchAttack.values()).length)];
                switch (attack) {
                    case NORMAL_ATTACK:
                        normalAttack(player);
                        return;
                    case BLACK_MAGIC_ATTACK:
                        blackMagicAttack(player);
                        return;
                    case BURN_PLAYER_ATTACK:
                        burnPlayerAttack(player);
                        return;
                    case ENDERMITES_ATTACK:
                        endermitesAttack(player);
                        return;
                    case FIRE_CIRCLE_ATTACK:
                        fireCircleAttack(player);
                        return;
                    case LAVA_ATTACK:
                        lavaAttack(player);
                        return;
                    case POISON_ATTACK:
                        poisonAttack(player);
                        return;
                    case SLOWNESS_ATTACK:
                        slownessAttack(player);
                        return;
                    case HEAL_EFFECT:
                        if (this.entity.getHealth() < this.entity.getMaxHealth()) {
                            healEffect(player);
                        } else {
                            normalAttack(player);
                        }
                        return;
                    default:
                        break;
                }
                normalAttack(player);
            }, (20 * delay));
        }
    }
}
