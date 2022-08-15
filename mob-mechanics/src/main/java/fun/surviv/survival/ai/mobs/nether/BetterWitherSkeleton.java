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
import fun.surviv.survival.ai.mobs.attacks.WitherSkeletonAttack;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

/**
 * SurvivalSystem; fun.surviv.survival.ai.mobs.nether:BetterWitherSkeleton
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.08.2022
 */
public class BetterWitherSkeleton extends BetterMob {
    private int scheduler;

    private int secondary;

    public BetterWitherSkeleton(Skeleton skeleton) {
        super((LivingEntity) skeleton);
    }

    public void normalAttack(final Player player) {
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterWitherSkeleton.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("WitherSkeleton.NormalAttack.Speed"), 1.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterWitherSkeleton.this.entity.isDead()) {
                        BetterWitherSkeleton.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterWitherSkeleton.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterWitherSkeleton.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("WitherSkeleton.NormalAttack.Speed"), 1.0D);
                    } else {
                        if (!player.isDead() && player.isOnline()) {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 0));
                            player.damage(MobAI.settings.configuration.getDouble("WitherSkeleton.NormalAttack.Damage"));
                            BetterWitherSkeleton.this.randomAttack(player, MobAI.settings.configuration.getInt("WitherSkeleton.NormalAttack.NextAttackDelay"));
                        } else {
                            BetterWitherSkeleton.this.setBusy(false);
                        }
                        Bukkit.getScheduler().cancelTask(BetterWitherSkeleton.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void swordAttack(final Player player) {
        if (MobAI.settings.configuration.getBoolean("WitherSkeleton.SwordAttack.Disable")) {
            normalAttack(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterWitherSkeleton.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("WitherSkeleton.SwordAttack.Speed"), 1.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterWitherSkeleton.this.entity.isDead()) {
                        BetterWitherSkeleton.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterWitherSkeleton.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterWitherSkeleton.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("WitherSkeleton.SwordAttack.Speed"), 1.0D);
                    } else {
                        if (!player.isDead() && player.isOnline()) {
                            BetterWitherSkeleton.this.throwSword(player);
                        } else {
                            BetterWitherSkeleton.this.setBusy(false);
                        }
                        Bukkit.getScheduler().cancelTask(BetterWitherSkeleton.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    private void throwSword(Player player) {
        if (MobAI.settings.configuration.getBoolean("WitherSkeleton.ThrowSwordAttack.Disable")) {
            normalAttack(player);
            return;
        }
        Item item = this.entity.getWorld().dropItem(this.entity.getEyeLocation().clone().add(0.0D, 0.2D, 0.0D), new ItemStack(Material.GOLDEN_SWORD));
        Vector vector = player.getLocation().subtract(this.entity.getLocation()).toVector();
        item.setVelocity(vector.multiply(0.5D));
        this.secondary = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), () -> {
            if (item.isOnGround()) {
                item.remove();
                Bukkit.getScheduler().cancelTask(this.secondary);
                return;
            }
            for (Entity entity : item.getNearbyEntities(0.2D, 0.2D, 0.2D)) {
                if (entity.getType().equals(EntityType.PLAYER)) {
                    item.remove();
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 0));
                    player.damage(MobAI.settings.configuration.getDouble("WitherSkeleton.ThrowSwordAttack.Damage"));
                    randomAttack(player, MobAI.settings.configuration.getInt("WitherSkeleton.ThrowSwordAttack.NextAttackDelay"));
                    Bukkit.getScheduler().cancelTask(this.secondary);
                    break;
                }
            }
        }, 0L, 1L);
    }

    public void witherSkullAttack(final Player player) {
        if (MobAI.settings.configuration.getBoolean("WitherSkeleton.WitherSkullAttack.Disable")) {
            normalAttack(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterWitherSkeleton.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("WitherSkeleton.WitherSkullAttack.Speed"), 10.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterWitherSkeleton.this.entity.isDead()) {
                        BetterWitherSkeleton.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterWitherSkeleton.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterWitherSkeleton.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("WitherSkeleton.WitherSkullAttack.Speed"), 10.0D);
                    } else {
                        if (!player.isDead() && player.isOnline()) {
                            Vector vector = player.getLocation().subtract(BetterWitherSkeleton.this.entity.getLocation()).toVector();
                            WitherSkull skull = (WitherSkull) BetterWitherSkeleton.this.entity.getWorld().spawnEntity(BetterWitherSkeleton.this.entity.getEyeLocation().clone().add(0.0D, 0.2D, 0.0D), EntityType.WITHER_SKULL);
                            skull.setGlowing(true);
                            skull.setDirection(vector);
                        } else {
                            BetterWitherSkeleton.this.setBusy(false);
                        }
                        Bukkit.getScheduler().cancelTask(BetterWitherSkeleton.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void trackAndKill(final Player player) {
        if (!isBusy()) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterWitherSkeleton.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("WitherSkeleton.TrackingSpeed"), 10.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterWitherSkeleton.this.entity.isDead()) {
                        BetterWitherSkeleton.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterWitherSkeleton.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterWitherSkeleton.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("WitherSkeleton.TrackingSpeed"), 10.0D);
                    } else {
                        BetterWitherSkeleton.this.track(BetterWitherSkeleton.this.entity.getLocation(), 0.0F, 0.0D);
                        BetterWitherSkeleton.this.setBusy(false);
                        BetterWitherSkeleton.this.randomAttack(player, 0);
                        Bukkit.getScheduler().cancelTask(BetterWitherSkeleton.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void randomAttack(Player player, int delay) {
        setBusy(true);
        WitherSkeletonAttack attack = WitherSkeletonAttack.values()[this.random.nextInt((WitherSkeletonAttack.values()).length)];
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) MobAI.getInstance().getPlugin(), () -> {
            switch (attack) {
                case NORMAL_ATTACK:
                    normalAttack(player);
                    return;
                case WITHER_SKULL_ATTACK:
                    witherSkullAttack(player);
                    return;
                case SWORD_THROW_ATTACK:
                    swordAttack(player);
                    return;
                default:
                    break;
            }
            normalAttack(player);
        }, (20 * delay));
    }
}
