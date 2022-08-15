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

package fun.surviv.survival.ai.mobs.end;

import fun.surviv.survival.ai.MobAI;
import fun.surviv.survival.ai.mobs.BetterMob;
import fun.surviv.survival.ai.mobs.attacks.EndermanAttack;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * SurvivalSystem; fun.surviv.survival.ai.mobs.end:BetterEnderman
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.08.2022
 */
public class BetterEnderman extends BetterMob {
    private int scheduler;

    private int secondary;

    public BetterEnderman(Enderman enderman) {
        super((LivingEntity) enderman);
    }

    public void normalAttack(final Player player) {
        final Location location = this.entity.getLocation();
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterEnderman.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Enderman.NormalAttack.Speed"), 10.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterEnderman.this.entity.isDead()) {
                        BetterEnderman.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterEnderman.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterEnderman.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Enderman.NormalAttack.Speed"), 10.0D);
                    } else {
                        BetterEnderman.this.track(BetterEnderman.this.entity.getLocation(), 1.0F, 1.0D);
                        BetterEnderman.this.entity.teleport(player.getLocation().clone().add(0.5D, 0.0D, 0.5D));
                        player.damage(MobAI.settings.configuration.getDouble("Enderman.NormalAttack.Damage"));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * MobAI.settings.configuration.getInt("Enderman.NormalAttack.ConfusionTime"), 1));
                        BetterEnderman.this.entity.teleport(location);
                        BetterEnderman.this.randomAttack(player, MobAI.settings.configuration.getInt("Enderman.NormalAttack.NextAttackDelay"));
                        Bukkit.getScheduler().cancelTask(BetterEnderman.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void jumperAttack(final Player player) {
        if (MobAI.settings.configuration.getBoolean("Creeper.JumperAttack.Disable")) {
            normalAttack(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterEnderman.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Enderman.JumperAttack.Speed"), 15.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterEnderman.this.entity.isDead()) {
                        BetterEnderman.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterEnderman.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterEnderman.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Enderman.JumperAttack.Speed"), 15.0D);
                    } else {
                        BetterEnderman.this.track(BetterEnderman.this.entity.getLocation(), 1.0F, 1.0D);
                        BetterEnderman.this.jumpTowardsPlayer(player);
                        Bukkit.getScheduler().cancelTask(BetterEnderman.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    private void jumpTowardsPlayer(final Player player) {
        final Location location = this.entity.getLocation();
        this.secondary = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
            double x = BetterEnderman.this.entity.getLocation().getX();

            double z = BetterEnderman.this.entity.getLocation().getZ();

            int i = 0;

            public void run() {
                if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterEnderman.this.entity.isDead()) {
                    BetterEnderman.this.setBusy(false);
                    Bukkit.getScheduler().cancelTask(BetterEnderman.this.scheduler);
                    return;
                }
                double xPortion = player.getLocation().getX() - BetterEnderman.this.entity.getLocation().getX() / this.i;
                double zPortion = player.getLocation().getZ() - BetterEnderman.this.entity.getLocation().getZ() / this.i;
                if (this.i < 6) {
                    BetterEnderman.this.entity.teleport(new Location(player.getWorld(), this.x + xPortion, player.getLocation().getY(), this.z + zPortion));
                    BetterEnderman.this.entity.getWorld().playSound(BetterEnderman.this.entity.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F);
                    this.i++;
                } else {
                    BetterEnderman.this.entity.teleport(player.getLocation().clone().add(0.5D, 0.0D, 0.5D));
                    player.damage(MobAI.settings.configuration.getDouble("Enderman.JumperAttack.Damage"));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * MobAI.settings.configuration.getInt("Enderman.JumperAttack.Damage"), 1));
                    BetterEnderman.this.entity.teleport(location);
                    BetterEnderman.this.randomAttack(player, MobAI.settings.configuration.getInt("Enderman.JumperAttack.NextAttackDelay"));
                    Bukkit.getScheduler().cancelTask(BetterEnderman.this.secondary);
                }
            }
        }, 0L, 5L);
    }

    public void trackAndKill(final Player player) {
        if (!isBusy()) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterEnderman.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Enderman.TrackingSpeed"), 15.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterEnderman.this.entity.isDead()) {
                        BetterEnderman.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterEnderman.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterEnderman.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Enderman.TrackingSpeed"), 15.0D);
                    } else {
                        BetterEnderman.this.track(BetterEnderman.this.entity.getLocation(), 0.0F, 0.0D);
                        BetterEnderman.this.setBusy(false);
                        BetterEnderman.this.randomAttack(player, 0);
                        Bukkit.getScheduler().cancelTask(BetterEnderman.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void randomAttack(Player player, int delay) {
        setBusy(true);
        EndermanAttack attack = EndermanAttack.values()[this.random.nextInt((EndermanAttack.values()).length)];
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) MobAI.getInstance().getPlugin(), () -> {
            switch (attack) {
                case NORMAL_ATTACK:
                    normalAttack(player);
                    return;
                case JUMPER_ATTACK:
                    jumperAttack(player);
                    return;
                default:
                    break;
            }
            normalAttack(player);
        }, (20 * delay));
    }
}
