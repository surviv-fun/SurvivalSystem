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

package fun.surviv.survival.ai.mobs.nether;

import fun.surviv.survival.ai.MobAI;
import fun.surviv.survival.ai.mobs.BetterMob;
import fun.surviv.survival.ai.mobs.attacks.BlazeAttack;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftBlaze;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * SurvivalSystem; fun.surviv.survival.ai.mobs.nether:BetterBlaze
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.08.2022
 */
public class BetterBlaze extends BetterMob {
    private int scheduler;

    public BetterBlaze(Blaze blaze) {
        super((LivingEntity) blaze);
    }

    public void normalAttack(final Player player) {
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterBlaze.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Blaze.NormalAttack.Speed"), 10.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterBlaze.this.entity.isDead()) {
                        BetterBlaze.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterBlaze.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterBlaze.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Blaze.NormalAttack.Speed"), 10.0D);
                    } else {
                        if (!player.isDead() && player.isOnline()) {
                            ((CraftBlaze) BetterBlaze.this.entity).getHandle().a(((CraftPlayer) player).getHandle());
                            BetterBlaze.this.randomAttack(player, MobAI.settings.configuration.getInt("Blaze.NormalAttack.NextAttackDelay"));
                        } else {
                            BetterBlaze.this.setBusy(false);
                        }
                        Bukkit.getScheduler().cancelTask(BetterBlaze.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void heatAttack(final Player player) {
        if (MobAI.settings.configuration.getBoolean("Blaze.HeatAttack.Disable")) {
            normalAttack(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterBlaze.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Blaze.HeatAttack.Speed"), 5.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterBlaze.this.entity.isDead()) {
                        BetterBlaze.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterBlaze.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterBlaze.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Blaze.HeatAttack.Speed"), 5.0D);
                    } else {
                        if (!player.isDead() && player.isOnline()) {
                            BetterBlaze.this.entity.setFireTicks(20 * MobAI.settings.configuration.getInt("Blaze.HeatAttack.EffectLength"));
                            player.setFireTicks(20 * MobAI.settings.configuration.getInt("Blaze.HeatAttack.EffectLength"));
                            BetterBlaze.this.randomAttack(player, MobAI.settings.configuration.getInt("Blaze.HeatAttack.NextAttackDelay"));
                        } else {
                            BetterBlaze.this.setBusy(false);
                        }
                        Bukkit.getScheduler().cancelTask(BetterBlaze.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void smokeAttack(final Player player) {
        if (MobAI.settings.configuration.getBoolean("Blaze.SmokeAttack.Disable")) {
            normalAttack(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterBlaze.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Blaze.SmokeAttack.Speed"), 5.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterBlaze.this.entity.isDead()) {
                        BetterBlaze.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterBlaze.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterBlaze.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Blaze.SmokeAttack.Speed"), 5.0D);
                    } else {
                        if (!player.isDead() && player.isOnline()) {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * MobAI.settings.configuration.getInt("Blaze.SmokeAttack.EffectLength"), 5));
                            BetterBlaze.this.randomAttack(player, MobAI.settings.configuration.getInt("Blaze.SmokeAttack.NextAttackDelay"));
                        } else {
                            BetterBlaze.this.setBusy(false);
                        }
                        Bukkit.getScheduler().cancelTask(BetterBlaze.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void trackAndKill(final Player player) {
        if (!isBusy()) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterBlaze.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Blaze.TrackingSpeed"), 15.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterBlaze.this.entity.isDead()) {
                        BetterBlaze.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterBlaze.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterBlaze.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Blaze.TrackingSpeed"), 15.0D);
                    } else {
                        BetterBlaze.this.track(BetterBlaze.this.entity.getLocation(), 0.0F, 0.0D);
                        BetterBlaze.this.setBusy(false);
                        BetterBlaze.this.randomAttack(player, 0);
                        Bukkit.getScheduler().cancelTask(BetterBlaze.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void randomAttack(Player player, int delay) {
        setBusy(true);
        BlazeAttack attack = BlazeAttack.values()[this.random.nextInt((BlazeAttack.values()).length)];
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) MobAI.getInstance().getPlugin(), () -> {
            switch (attack) {
                case NORMAL_ATTACK:
                    normalAttack(player);
                    return;
                case HEAT_ATTACK:
                    heatAttack(player);
                    return;
                case SMOKE_ATTACK:
                    smokeAttack(player);
                    return;
                default:
                    break;
            }
            normalAttack(player);
        }, (20 * delay));
    }
}
