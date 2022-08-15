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
import fun.surviv.survival.ai.mobs.attacks.GhastAttack;
import net.minecraft.world.entity.Entity;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftGhast;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * SurvivalSystem; fun.surviv.survival.ai.mobs.nether:BetterGhast
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.08.2022
 */
public class BetterGhast extends BetterMob {
    private int scheduler;

    public BetterGhast(Ghast ghast) {
        super((LivingEntity) ghast);
    }

    public void moveTo(Location location, float speed) {
        ((CraftGhast) this.entity).getHandle().D().a(location.getX(), location.getY() + 1.0D, location.getZ(), speed);
    }

    public void normalAttack(final Player player) {
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterGhast.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Ghast.NormalAttack.Speed"), 20.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterGhast.this.entity.isDead()) {
                        BetterGhast.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterGhast.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterGhast.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Ghast.NormalAttack.Speed"), 20.0D);
                    } else {
                        if (!player.isDead() && player.isOnline()) {
                            ((CraftGhast) BetterGhast.this.entity).getHandle().a(((CraftPlayer) player).getHandle());
                            BetterGhast.this.randomAttack(player, MobAI.settings.configuration.getInt("Ghast.NormalAttack.NextAttackDelay"));
                        } else {
                            BetterGhast.this.setBusy(false);
                        }
                        Bukkit.getScheduler().cancelTask(BetterGhast.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void highSpeedAttack(final Player player) {
        if (MobAI.settings.configuration.getBoolean("Ghast.HighSpeedAttack.Disable")) {
            normalAttack(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterGhast.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Ghast.HighSpeedAttack.Speed"), 20.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterGhast.this.entity.isDead()) {
                        BetterGhast.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterGhast.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterGhast.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Ghast.HighSpeedAttack.Speed"), 20.0D);
                    } else {
                        if (!player.isDead() && player.isOnline()) {
                            ((CraftGhast) BetterGhast.this.entity).getHandle().a((Entity) ((CraftPlayer) player).getHandle(), 1.0F, 1.0F);
                            BetterGhast.this.randomAttack(player, MobAI.settings.configuration.getInt("Ghast.HighSpeedAttack.NextAttackDelay"));
                        } else {
                            BetterGhast.this.setBusy(false);
                        }
                        Bukkit.getScheduler().cancelTask(BetterGhast.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void multiAttack(final Player player) {
        if (MobAI.settings.configuration.getBoolean("Ghast.MultiAttack.Disable")) {
            normalAttack(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterGhast.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Ghast.MultiAttack.Speed"), 20.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterGhast.this.entity.isDead()) {
                        BetterGhast.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterGhast.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterGhast.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Ghast.MultiAttack.Speed"), 20.0D);
                    } else {
                        if (!player.isDead() && player.isOnline()) {
                            for (int i = 0; i < 4; i++)
                                ((CraftGhast) BetterGhast.this.entity).getHandle().a(((CraftPlayer) player).getHandle());
                            BetterGhast.this.randomAttack(player, MobAI.settings.configuration.getInt("Ghast.MultiAttack.NextAttackDelay"));
                        } else {
                            BetterGhast.this.setBusy(false);
                        }
                        Bukkit.getScheduler().cancelTask(BetterGhast.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void trackAndKill(final Player player) {
        if (!isBusy()) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterGhast.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Ghast.TrackingSpeed"), 30.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterGhast.this.entity.isDead()) {
                        BetterGhast.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterGhast.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterGhast.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("Ghast.TrackingSpeed"), 30.0D);
                    } else {
                        BetterGhast.this.track(BetterGhast.this.entity.getLocation(), 0.0F, 0.0D);
                        BetterGhast.this.setBusy(false);
                        BetterGhast.this.randomAttack(player, 0);
                        Bukkit.getScheduler().cancelTask(BetterGhast.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void randomAttack(Player player, int delay) {
        setBusy(true);
        GhastAttack attack = GhastAttack.values()[this.random.nextInt((GhastAttack.values()).length)];
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) MobAI.getInstance().getPlugin(), () -> {
            switch (attack) {
                case NORMAL_ATTACK:
                    normalAttack(player);
                    return;
                case HIGH_SPEED_FIRE_BALL_ATTACK:
                    highSpeedAttack(player);
                    return;
                case MULTY_FIRE_BALL_ATTACK:
                    multiAttack(player);
                    return;
                default:
                    break;
            }
            normalAttack(player);
        }, (20 * delay));
    }
}
