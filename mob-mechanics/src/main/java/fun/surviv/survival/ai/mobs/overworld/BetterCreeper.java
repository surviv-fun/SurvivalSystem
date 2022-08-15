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
import fun.surviv.survival.ai.mobs.attacks.CreeperAttack;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.Random;

/**
 * SurvivalSystem; fun.surviv.survival.ai.mobs.overworld:BetterCreeper
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.08.2022
 */
public class BetterCreeper extends BetterMob {
    private int scheduler;

    public BetterCreeper(Creeper creeper) {
        super((LivingEntity) creeper);
    }

    public void normalAttack(final Player player) {
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterCreeper.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Creeper.NormalAttack.Speed"), 4.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterCreeper.this.entity.isDead()) {
                        BetterCreeper.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterCreeper.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterCreeper.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Creeper.NormalAttack.Speed"), 4.0D);
                    } else {
                        Bukkit.getScheduler().cancelTask(BetterCreeper.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void implosionExplosionAttack(final Player player) {
        if (MobAI.settings.configuration.getBoolean("Creeper.ImplosionExplosionAttack.Disable")) {
            normalAttack(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterCreeper.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Creeper.ImplosionExplosionAttack.Speed"), 4.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterCreeper.this.entity.isDead()) {
                        BetterCreeper.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterCreeper.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterCreeper.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Creeper.ImplosionExplosionAttack.Speed"), 4.0D);
                    } else {
                        int j = MobAI.settings.configuration.getInt("Creeper.ImplosionExplosionAttack.ImplosionRadius");
                        for (Entity e : BetterCreeper.this.entity.getNearbyEntities(j, j, j)) {
                            if (e instanceof LivingEntity) {
                                Vector vector = BetterCreeper.this.entity.getLocation().subtract(e.getLocation()).toVector();
                                e.setVelocity(vector);
                            }
                        }
                        Bukkit.getScheduler().cancelTask(BetterCreeper.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void chargedCreeperAttack(final Player player) {
        if (MobAI.settings.configuration.getBoolean("Creeper.ChargedCreeperAttack.Disable")) {
            normalAttack(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.entity.getWorld().strikeLightning(this.entity.getLocation());
            ((Creeper) this.entity).setPowered(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterCreeper.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Creeper.ChargedCreeperAttack.Speed"), 4.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterCreeper.this.entity.isDead()) {
                        BetterCreeper.this.setBusy(false);
                        ((Creeper) BetterCreeper.this.entity).setPowered(false);
                        Bukkit.getScheduler().cancelTask(BetterCreeper.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterCreeper.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Creeper.ChargedCreeperAttack.Speed"), 4.0D);
                    } else {
                        Bukkit.getScheduler().cancelTask(BetterCreeper.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void chargedSuperCreeperAttack(final Player player) {
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.entity.getWorld().strikeLightning(this.entity.getLocation());
            ((Creeper) this.entity).setPowered(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterCreeper.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Creeper.ChargedSuperCreeperAttack.Speed"), 4.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterCreeper.this.entity.isDead()) {
                        BetterCreeper.this.setBusy(false);
                        ((Creeper) BetterCreeper.this.entity).setPowered(false);
                        Bukkit.getScheduler().cancelTask(BetterCreeper.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterCreeper.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Creeper.ChargedSuperCreeperAttack.Speed"), 4.0D);
                    } else {
                        for (int i = 0; i < BetterCreeper.this.random.nextInt(3) + 3; i++) {
                            Location location = BetterCreeper.this.entity.getLocation().clone().add((BetterCreeper.this.random.nextInt(6) - 3), 0.0D, ((new Random()).nextInt(6) - 3));
                            Creeper creeper = (Creeper) BetterCreeper.this.entity.getWorld().spawnEntity(location, EntityType.CREEPER);
                            (new BetterCreeper(creeper)).normalAttack(player);
                        }
                        Bukkit.getScheduler().cancelTask(BetterCreeper.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void trackAndKill(final Player player) {
        if (!isBusy()) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterCreeper.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Creeper.TrackingSpeed"), 7.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterCreeper.this.entity.isDead()) {
                        BetterCreeper.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterCreeper.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterCreeper.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Creeper.TrackingSpeed"), 7.0D);
                    } else {
                        BetterCreeper.this.track(BetterCreeper.this.entity.getLocation(), 0.0F, 0.0D);
                        BetterCreeper.this.setBusy(false);
                        BetterCreeper.this.randomAttack(player, 0);
                        Bukkit.getScheduler().cancelTask(BetterCreeper.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void randomAttack(Player player, int delay) {
        setBusy(true);
        CreeperAttack attack = CreeperAttack.values()[this.random.nextInt((CreeperAttack.values()).length)];
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) MobAI.getInstance().getPlugin(), () -> {
            switch (attack) {
                case NORMAL_ATTACK:
                    normalAttack(player);
                    return;
                case CHARGED_CREEPER_ATTACK:
                    chargedCreeperAttack(player);
                    return;
                case CHARGED_SUPER_CREEPER_ATTACK:
                    chargedSuperCreeperAttack(player);
                    return;
                case IMPLOSION_EXPLOSION_ATTACK:
                    implosionExplosionAttack(player);
                    return;
                default:
                    break;
            }
            normalAttack(player);
        }, (20 * delay));
    }
}
