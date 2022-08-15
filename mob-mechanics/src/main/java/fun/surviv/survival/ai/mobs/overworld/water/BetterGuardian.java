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

package fun.surviv.survival.ai.mobs.overworld.water;

import fun.surviv.survival.ai.MobAI;
import fun.surviv.survival.ai.mobs.BetterMob;
import fun.surviv.survival.ai.mobs.attacks.GuardianAttack;
import net.minecraft.world.entity.Entity;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftGuardian;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * SurvivalSystem; fun.surviv.survival.ai.mobs.overworld.water:BetterGuardian
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.08.2022
 */
public class BetterGuardian extends BetterMob {
    private int scheduler;

    public BetterGuardian(Guardian guardian) {
        super((LivingEntity) guardian);
    }

    public void normalAttack(final Player player) {
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterGuardian.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Guardian.NormalAttack.Speed"), 10.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterGuardian.this.entity.isDead()) {
                        BetterGuardian.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterGuardian.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterGuardian.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Guardian.NormalAttack.Speed"), 10.0D);
                    } else {
                        if (!player.isDead() && player.isOnline()) {
                            ((CraftGuardian) BetterGuardian.this.entity).getHandle().a(((CraftPlayer) player).getHandle());
                            BetterGuardian.this.randomAttack(player, MobAI.settings.configuration.getInt("Guardian.NormalAttack.NextAttackDelay"));
                        } else {
                            BetterGuardian.this.setBusy(false);
                        }
                        Bukkit.getScheduler().cancelTask(BetterGuardian.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void instantLaserAttack(final Player player) {
        if (MobAI.settings.configuration.getBoolean("Guardian.InstantAttack.Disable")) {
            normalAttack(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterGuardian.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Guardian.InstantAttack.Speed"), 10.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterGuardian.this.entity.isDead()) {
                        BetterGuardian.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterGuardian.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterGuardian.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Guardian.InstantAttack.Speed"), 10.0D);
                    } else {
                        if (!player.isDead() && player.isOnline()) {
                            ((CraftGuardian) BetterGuardian.this.entity).getHandle().a((Entity) ((CraftPlayer) player).getHandle(), 0.0F, 0.0F);
                            BetterGuardian.this.randomAttack(player, MobAI.settings.configuration.getInt("Guardian.InstantAttack.NextAttackDelay"));
                        } else {
                            BetterGuardian.this.setBusy(false);
                        }
                        Bukkit.getScheduler().cancelTask(BetterGuardian.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void suffocationAttack(final Player player) {
        if (MobAI.settings.configuration.getBoolean("Guardian.SuffocationVibesAttack.Disable")) {
            normalAttack(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterGuardian.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Guardian.SuffocationVibesAttack.Speed"), 10.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterGuardian.this.entity.isDead()) {
                        BetterGuardian.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterGuardian.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterGuardian.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Guardian.SuffocationVibesAttack.Speed"), 10.0D);
                    } else {
                        if (!player.isDead() && player.isOnline()) {
                            player.setRemainingAir(0);
                            BetterGuardian.this.randomAttack(player, MobAI.settings.configuration.getInt("Guardian.SuffocationVibesAttack.NextAttackDelay"));
                        } else {
                            BetterGuardian.this.setBusy(false);
                        }
                        Bukkit.getScheduler().cancelTask(BetterGuardian.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void nailingAttack(final Player player) {
        if (MobAI.settings.configuration.getBoolean("Guardian.NailingVibesAttack.Disable")) {
            normalAttack(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterGuardian.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Guardian.NailingVibesAttack.Speed"), 10.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterGuardian.this.entity.isDead()) {
                        BetterGuardian.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterGuardian.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterGuardian.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Guardian.NailingVibesAttack.Speed"), 10.0D);
                    } else {
                        if (!player.isDead() && player.isOnline()) {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * MobAI.settings.configuration.getInt("Guardian.NailingVibesAttack.EffectLength"), 4));
                            BetterGuardian.this.randomAttack(player, MobAI.settings.configuration.getInt("Guardian.NailingVibesAttack.NextAttackDelay"));
                        } else {
                            BetterGuardian.this.setBusy(false);
                        }
                        Bukkit.getScheduler().cancelTask(BetterGuardian.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void trackAndKill(final Player player) {
        if (!isBusy()) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterGuardian.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Guardian.TrackingSpeed"), 10.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterGuardian.this.entity.isDead()) {
                        BetterGuardian.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterGuardian.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterGuardian.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Guardian.TrackingSpeed"), 10.0D);
                    } else {
                        BetterGuardian.this.track(BetterGuardian.this.entity.getLocation(), 0.0F, 0.0D);
                        BetterGuardian.this.setBusy(false);
                        BetterGuardian.this.randomAttack(player, 0);
                        Bukkit.getScheduler().cancelTask(BetterGuardian.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void randomAttack(Player player, int delay) {
        setBusy(true);
        GuardianAttack attack = GuardianAttack.values()[this.random.nextInt((GuardianAttack.values()).length)];
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) MobAI.getInstance().getPlugin(), () -> {
            switch (attack) {
                case NORMAL_ATTACK:
                    normalAttack(player);
                    return;
                case INSTANT_LASER_ATTACK:
                    instantLaserAttack(player);
                    return;
                case NAILING_VIBES_ATTACK:
                    suffocationAttack(player);
                    return;
                case SUFFOCATION_VIBES_ATTACK:
                    nailingAttack(player);
                    return;
                default:
                    break;
            }
            normalAttack(player);
        }, (20 * delay));
    }
}
