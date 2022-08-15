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
import fun.surviv.survival.ai.mobs.attacks.ZombiePigmanAttack;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

/**
 * SurvivalSystem; fun.surviv.survival.ai.mobs.nether:BetterZombiePigman
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.08.2022
 */
public class BetterZombiePigman extends BetterMob {
    private int scheduler;

    private int secondary;

    public BetterZombiePigman(PigZombie pigZombie) {
        super((LivingEntity) pigZombie);
    }

    public void normalAttack(final Player player) {
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterZombiePigman.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("PigmanZombie.NormalAttack.Speed"), 1.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterZombiePigman.this.entity.isDead()) {
                        BetterZombiePigman.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterZombiePigman.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterZombiePigman.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("PigmanZombie.NormalAttack.Speed"), 1.0D);
                    } else {
                        if (!player.isDead() && player.isOnline()) {
                            player.damage(MobAI.settings.configuration.getDouble("PigmanZombie.NormalAttack.Damage"));
                            BetterZombiePigman.this.randomAttack(player, MobAI.settings.configuration.getInt("PigmanZombie.NormalAttack.NextAttackDelay"));
                        } else {
                            BetterZombiePigman.this.setBusy(false);
                        }
                        Bukkit.getScheduler().cancelTask(BetterZombiePigman.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void lightningAttack(final Player player) {
        if (MobAI.settings.configuration.getBoolean("PigmanZombie.LightningAttack.Disable")) {
            normalAttack(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterZombiePigman.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("PigmanZombie.LightningAttack.Speed"), 1.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterZombiePigman.this.entity.isDead()) {
                        BetterZombiePigman.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterZombiePigman.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterZombiePigman.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("PigmanZombie.LightningAttack.Speed"), 1.0D);
                    } else {
                        if (!player.isDead() && player.isOnline()) {
                            BetterZombiePigman.this.entity.getWorld().strikeLightning(BetterZombiePigman.this.entity.getLocation());
                            player.damage(MobAI.settings.configuration.getDouble("PigmanZombie.LightningAttack.Damage"));
                            BetterZombiePigman.this.randomAttack(player, MobAI.settings.configuration.getInt("PigmanZombie.LightningAttack.NextAttackDelay"));
                        } else {
                            BetterZombiePigman.this.setBusy(false);
                        }
                        Bukkit.getScheduler().cancelTask(BetterZombiePigman.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void swordAttack(final Player player) {
        if (MobAI.settings.configuration.getBoolean("PigmanZombie.SwordAttack.Disable")) {
            normalAttack(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterZombiePigman.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("PigmanZombie.SwordAttack.Speed"), 10.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterZombiePigman.this.entity.isDead()) {
                        BetterZombiePigman.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterZombiePigman.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterZombiePigman.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("PigmanZombie.SwordAttack.Speed"), 10.0D);
                    } else {
                        if (!player.isDead() && player.isOnline()) {
                            BetterZombiePigman.this.throwSword(player);
                        } else {
                            BetterZombiePigman.this.setBusy(false);
                        }
                        Bukkit.getScheduler().cancelTask(BetterZombiePigman.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    private void throwSword(Player player) {
        if (MobAI.settings.configuration.getBoolean("PigmanZombie.ThrowSwordAttack.Disable")) {
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
                    player.damage(MobAI.settings.configuration.getDouble("PigmanZombie.ThrowSwordAttack.Damage"));
                    randomAttack(player, MobAI.settings.configuration.getInt("PigmanZombie.ThrowSwordAttack.NextAttackDelay"));
                    Bukkit.getScheduler().cancelTask(this.secondary);
                    break;
                }
            }
        }, 0L, 1L);
    }

    public void trackAndKill(final Player player) {
        if (!isBusy()) {
            setBusy(true);
            this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) MobAI.getInstance().getPlugin(), new Runnable() {
                boolean b = BetterZombiePigman.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("PigmanZombie.TrackingSpeed"), 10.0D);

                public void run() {
                    if ((!player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) || !player.isOnline() || BetterZombiePigman.this.entity.isDead()) {
                        BetterZombiePigman.this.setBusy(false);
                        Bukkit.getScheduler().cancelTask(BetterZombiePigman.this.scheduler);
                        return;
                    }
                    if (!this.b) {
                        this.b = BetterZombiePigman.this.track(player.getLocation(), (float) MobAI.settings.configuration.getDouble("PigmanZombie.TrackingSpeed"), 10.0D);
                    } else {
                        BetterZombiePigman.this.track(BetterZombiePigman.this.entity.getLocation(), 0.0F, 0.0D);
                        BetterZombiePigman.this.setBusy(false);
                        BetterZombiePigman.this.randomAttack(player, 0);
                        Bukkit.getScheduler().cancelTask(BetterZombiePigman.this.scheduler);
                    }
                }
            }, 0L, 5L);
        }
    }

    public void randomAttack(Player player, int delay) {
        setBusy(true);
        ZombiePigmanAttack attack = ZombiePigmanAttack.values()[this.random.nextInt((ZombiePigmanAttack.values()).length)];
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) MobAI.getInstance().getPlugin(), () -> {
            switch (attack) {
                case NORMAL_ATTACK:
                    normalAttack(player);
                    return;
                case LIGHTNING_ATTACK:
                    lightningAttack(player);
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
