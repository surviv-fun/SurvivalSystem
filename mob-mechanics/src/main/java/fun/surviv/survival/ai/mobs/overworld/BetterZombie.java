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
import fun.surviv.survival.ai.mobs.attacks.ZombieAttack;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftZombie;
import org.bukkit.entity.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * SurvivalSystem; fun.surviv.survival.ai.mobs.overworld:BetterZombie
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.08.2022
 */
public class BetterZombie extends BetterMob {

    private int scheduler;

    public BetterZombie(Zombie zombie) {
        super((LivingEntity) zombie);
    }

    public void normalAttack(final Player player) {
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterZombie.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Zombie.NormalAttack.Speed"), 1.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterZombie.this.entity.isDead()) {
                        BetterZombie.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterZombie.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterZombie.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Zombie.NormalAttack.Speed"), 1.0D);
                    } else {
                        if (!player.isDead() && player.isOnline()) {
                            player.damage(MobAI.settings.configuration.getDouble("Zombie.NormalAttack.Damage"));
                            BetterZombie.this.randomAttack(player, MobAI.settings.configuration.getInt("Zombie.NormalAttack.NextAttackDelay"));
                        } else {
                            BetterZombie.this.setBusy(false);
                        }
                        Bukkit.getScheduler().cancelTask(BetterZombie.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void bloodRushAttack(final Player player) {
        if (MobAI.settings.configuration.getBoolean("Zombie.BloodRushAttack.Disable")) {
            normalAttack(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterZombie.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Zombie.BloodRushAttack.Speed"), 1.0D);

                double i = 0.0D;

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterZombie.this.entity.isDead()) {
                        BetterZombie.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterZombie.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterZombie.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Zombie.BloodRushAttack.Speed"), 1.0D);
                        this.i += 0.19634954084936207D;
                        for (double j = 0.0D; j <= 6.283185307179586D; j += 0.19634954084936207D) {
                            for (int k = 0; k <= 1; k++) {
                                double x = 0.3D * (6.283185307179586D - j) * 0.5D * Math.cos(j + this.i + k * Math.PI);
                                double y = 0.5D * j;
                                double z = 0.3D * (6.283185307179586D - j) * 0.5D * Math.sin(j + this.i + k * Math.PI);
                                Location location = BetterZombie.this.entity.getLocation().clone().add(x, y, z);
                                Particle.DustOptions dustOptions = new Particle.DustOptions(Color.RED, 1.0F);
                                ((World) Objects.<World>requireNonNull(location.getWorld())).spawnParticle(Particle.REDSTONE, location, 0, 0.0D, 0.0D, 1.0D, dustOptions);
                            }
                        }
                    } else {
                        if (!player.isDead() && player.isOnline()) {
                            player.damage(MobAI.settings.configuration.getDouble("Zombie.BloodRushAttack.Damage"));
                            BetterZombie.this.randomAttack(player, MobAI.settings.configuration.getInt("Zombie.BloodRushAttack.NextAttackDelay"));
                        } else {
                            BetterZombie.this.setBusy(false);
                        }
                        Bukkit.getScheduler().cancelTask(BetterZombie.this.scheduler);
                    }
                }
            }, 0L, 2L);
        }
    }

    public void minionsAttack(final Player player) {
        if (MobAI.settings.configuration.getBoolean("Zombie.MinionsAttack.Disable")) {
            normalAttack(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterZombie.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Zombie.MinionsAttack.Speed"), 10.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterZombie.this.entity.isDead()) {
                        BetterZombie.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterZombie.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterZombie.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Zombie.MinionsAttack.Speed"), 10.0D);
                    } else {
                        if (!player.isDead() && player.isOnline()) {
                            for (int i = 0; i < BetterZombie.this.random.nextInt(3) + 3; i++) {
                                Location location = BetterZombie.this.entity.getLocation().clone().add((BetterZombie.this.random.nextInt(6) - 3), 0.0D, ((new Random()).nextInt(6) - 3));
                                Zombie zombie = (Zombie) BetterZombie.this.entity.getWorld().spawnEntity(location, EntityType.ZOMBIE);
                                ((CraftZombie) zombie).setBaby(true);
                            }
                            BetterZombie.this.setBusy(false);
                            BetterZombie.this.entity.remove();
                        } else {
                            BetterZombie.this.setBusy(false);
                        }
                        Bukkit.getScheduler().cancelTask(BetterZombie.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void vampireAttack(final Player player) {
        if (MobAI.settings.configuration.getBoolean("Zombie.VampireAttack.Disable")) {
            normalAttack(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            final List<Bat> bats = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Location location = this.entity.getLocation().clone().add((this.random.nextInt(6) - 3), 0.0D, ((new Random()).nextInt(6) - 3));
                Bat bat = (Bat) this.entity.getWorld().spawnEntity(location, EntityType.BAT);
                bat.setNoDamageTicks(400);
                bats.add(bat);
            }
            this.entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 1, true));
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterZombie.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Zombie.VampireAttack.Speed"), 1.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterZombie.this.entity.isDead()) {
                        BetterZombie.this.setBusy(false);
                        BetterZombie.this.entity.removePotionEffect(PotionEffectType.INVISIBILITY);
                        Bukkit.getScheduler().cancelTask(BetterZombie.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterZombie.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Zombie.VampireAttack.Speed"), 1.0D);
                    } else {
                        BetterZombie.this.entity.removePotionEffect(PotionEffectType.INVISIBILITY);
                        for (int i = 0; i < 5; i++) {
                            ((Bat) bats.get(i)).damage(100000.0D);
                            Location location = BetterZombie.this.entity.getLocation().clone().add((BetterZombie.this.random.nextInt(6) - 3), 0.0D, ((new Random()).nextInt(6) - 3));
                            Bat bat = (Bat) BetterZombie.this.entity.getWorld().spawnEntity(location, EntityType.BAT);
                            bat.setNoDamageTicks(400);
                        }
                        if (!player.isDead() && player.isOnline()) {
                            player.damage(MobAI.settings.configuration.getDouble("Zombie.VampireAttack.Damage"));
                            BetterZombie.this.randomAttack(player, MobAI.settings.configuration.getInt("Zombie.VampireAttack.NextAttackDelay"));
                        } else {
                            BetterZombie.this.setBusy(false);
                        }
                        Bukkit.getScheduler().cancelTask(BetterZombie.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void trackAndKill(final Player player) {
        if (!isBusy()) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterZombie.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Zombie.TrackingSpeed"), 10.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterZombie.this.entity.isDead()) {
                        BetterZombie.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterZombie.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterZombie.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Zombie.TrackingSpeed"), 10.0D);
                    } else {
                        BetterZombie.this.track(BetterZombie.this.entity.getLocation(), 0.0F, 0.0D);
                        BetterZombie.this.setBusy(false);
                        BetterZombie.this.randomAttack(player, 0);
                        Bukkit.getScheduler().cancelTask(BetterZombie.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void randomAttack(Player player, int delay) {
        setBusy(true);
        ZombieAttack attack = ZombieAttack.values()[this.random.nextInt((ZombieAttack.values()).length)];
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) MobAI.getInstance().getPlugin(), () -> {
            switch (attack) {
                case NORMAL_ATTACK:
                    normalAttack(player);
                    return;
                case BLOOD_RUSH_ATTACK:
                    bloodRushAttack(player);
                    return;
                case MINIONS_ATTACK:
                    minionsAttack(player);
                    return;
                case VAMPIRE_ATTACK:
                    vampireAttack(player);
                    return;
                default:
                    break;
            }
            normalAttack(player);
        }, (20 * delay));
    }

}
