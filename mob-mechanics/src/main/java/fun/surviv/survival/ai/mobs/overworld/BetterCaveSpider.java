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
import fun.surviv.survival.ai.mobs.attacks.SpiderAttack;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftSpider;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * SurvivalSystem; fun.surviv.survival.ai.mobs.overworld:BetterCaveSpider
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.08.2022
 */
public class BetterCaveSpider extends BetterMob {
    private int scheduler;

    public BetterCaveSpider(CaveSpider spider) {
        super(spider);
    }

    public void normalAttack(final Player player) {
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance(), new Runnable() {
                boolean b = BetterCaveSpider.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("CaveSpider.NormalAttack.Speed"), 3.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterCaveSpider.this.entity.isDead()) {
                        BetterCaveSpider.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterCaveSpider.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterCaveSpider.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("CaveSpider.NormalAttack.Speed"), 3.0D);
                    } else {
                        BetterCaveSpider.this.track(BetterCaveSpider.this.entity.getLocation(), 0.0F, 0.0D);
                        ((CraftSpider) BetterCaveSpider.this.entity).getHandle().a(((CraftPlayer) player).getHandle());
                        player.damage(MobAI.settings.configuration.getDouble("CaveSpider.NormalAttack.Damage"));
                        BetterCaveSpider.this.randomAttack(player, MobAI.settings.configuration.getInt("CaveSpider.NormalAttack.NextAttackDelay"));
                        Bukkit.getScheduler().cancelTask(BetterCaveSpider.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void poisonAttack(final Player player) {
        if (MobAI.settings.configuration.getBoolean("CaveSpider.PoisonAttack.Disable")) {
            normalAttack(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance(), new Runnable() {
                boolean b = BetterCaveSpider.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("CaveSpider.PoisonAttack.Speed"), 3.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterCaveSpider.this.entity.isDead()) {
                        BetterCaveSpider.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterCaveSpider.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterCaveSpider.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("CaveSpider.PoisonAttack.Speed"), 3.0D);
                    } else {
                        BetterCaveSpider.this.track(BetterCaveSpider.this.entity.getLocation(), 0.0F, 0.0D);
                        ((CraftSpider) BetterCaveSpider.this.entity).getHandle().a(((CraftPlayer) player).getHandle());
                        player.damage(MobAI.settings.configuration.getDouble("CaveSpider.PoisonAttack.Damage"));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * MobAI.settings.configuration.getInt("CaveSpider.PoisonAttack.PoisoningTime"), 1));
                        BetterCaveSpider.this.randomAttack(player, MobAI.settings.configuration.getInt("CaveSpider.PoisonAttack.NextAttackDelay"));
                        Bukkit.getScheduler().cancelTask(BetterCaveSpider.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void skyAttack(final Player player) {
        if (MobAI.settings.configuration.getBoolean("CaveSpider.SkyAttack.Disable")) {
            normalAttack(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance(), new Runnable() {
                boolean b = BetterCaveSpider.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("CaveSpider.SkyAttack.Speed"), 3.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterCaveSpider.this.entity.isDead()) {
                        BetterCaveSpider.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterCaveSpider.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterCaveSpider.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("CaveSpider.SkyAttack.Speed"), 3.0D);
                    } else {
                        BetterCaveSpider.this.track(BetterCaveSpider.this.entity.getLocation(), 0.0F, 0.0D);
                        BetterCaveSpider.this.entity.teleport(BetterCaveSpider.this.entity.getLocation().clone().add(0.0D, 20.0D, 0.0D));
                        BetterCaveSpider.this.entity.setNoDamageTicks(10);
                        ((CraftSpider) BetterCaveSpider.this.entity).getHandle().a(((CraftPlayer) player).getHandle());
                        player.damage(MobAI.settings.configuration.getDouble("CaveSpider.SkyAttack.Damage"));
                        BetterCaveSpider.this.randomAttack(player, MobAI.settings.configuration.getInt("CaveSpider.SkyAttack.NextAttackDelay"));
                        Bukkit.getScheduler().cancelTask(BetterCaveSpider.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void webAttack(final Player player) {
        if (MobAI.settings.configuration.getBoolean("CaveSpider.WebAttack.Disable")) {
            normalAttack(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance(), new Runnable() {
                boolean b = BetterCaveSpider.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("CaveSpider.WebAttack.Speed"), 3.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterCaveSpider.this.entity.isDead()) {
                        BetterCaveSpider.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterCaveSpider.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterCaveSpider.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("CaveSpider.WebAttack.Speed"), 3.0D);
                    } else {
                        BetterCaveSpider.this.track(BetterCaveSpider.this.entity.getLocation(), 0.0F, 0.0D);
                        Snowball snowball = (Snowball) BetterCaveSpider.this.entity.getWorld().spawnEntity(BetterCaveSpider.this.entity.getLocation().clone().add(0.0D, 0.2D, 0.0D), EntityType.SNOWBALL);
                        snowball.setVelocity(BetterCaveSpider.this.entity.getEyeLocation().clone().add(0.0D, 1.0D, 0.0D).subtract(player.getLocation().clone()).toVector().multiply(3));
                        player.getLocation().clone().add(0.0D, 1.0D, 0.0D).getBlock().setType(Material.COBWEB);
                        BetterCaveSpider.this.randomAttack(player, MobAI.settings.configuration.getInt("CaveSpider.WebAttack.NextAttackDelay"));
                        Bukkit.getScheduler().cancelTask(BetterCaveSpider.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void trackAndKill(final Player player) {
        if (!isBusy()) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance(), new Runnable() {
                boolean b = BetterCaveSpider.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("CaveSpider.TrackingSpeed"), 10.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterCaveSpider.this.entity.isDead()) {
                        BetterCaveSpider.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterCaveSpider.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterCaveSpider.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("CaveSpider.TrackingSpeed"), 10.0D);
                    } else {
                        BetterCaveSpider.this.track(BetterCaveSpider.this.entity.getLocation(), 0.0F, 0.0D);
                        BetterCaveSpider.this.setBusy(false);
                        BetterCaveSpider.this.randomAttack(player, 0);
                        Bukkit.getScheduler().cancelTask(BetterCaveSpider.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void randomAttack(Player player, int delay) {
        setBusy(true);
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) MobAI.getInstance(), () -> {
            SpiderAttack attack = SpiderAttack.values()[this.random.nextInt((SpiderAttack.values()).length)];
            switch (attack) {
                case NORMAL_ATTACK:
                    normalAttack(player);
                    return;
                case POISON_ATTACK:
                    poisonAttack(player);
                    return;
                case SKY_ATTACK:
                    skyAttack(player);
                    return;
                case WEB_ATTACK:
                    webAttack(player);
                    return;
                default:
                    break;
            }
            normalAttack(player);
        }, (20 * delay));
    }
}
