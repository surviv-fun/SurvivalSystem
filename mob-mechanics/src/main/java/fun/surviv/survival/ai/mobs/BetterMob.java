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

package fun.surviv.survival.ai.mobs;

import fun.surviv.survival.ai.MobAI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * SurvivalSystem; fun.surviv.survival.ai.mob:BetterMob
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.08.2022
 */
public class BetterMob implements CraftBetterMob {

    public static Map<UUID, Boolean> busyEntities = new HashMap<>();
    public static Map<UUID, Boolean> protectedEntities = new HashMap<>();

    protected LivingEntity entity;
    protected Random random;

    private int protect;

    public BetterMob(LivingEntity entity) {
        this.entity = entity;
        this.random = new Random();
    }

    public static void setBusy(Entity entity, boolean busy) {
        busyEntities.put(entity.getUniqueId(), Boolean.valueOf(busy));
    }

    public static boolean isBusy(Entity entity) {
        if (busyEntities.containsKey(entity.getUniqueId())) return busyEntities.get(entity.getUniqueId()).booleanValue();
        return false;
    }

    public void trackAndKill(Player player) {
    }

    public boolean isBusy() {
        if (busyEntities.containsKey(this.entity.getUniqueId())) return busyEntities.get(this.entity.getUniqueId()).booleanValue();
        return false;
    }

    public void setBusy(boolean busy) {
        busyEntities.put(this.entity.getUniqueId(), Boolean.valueOf(busy));
    }

    @Override
    public Entity entity() {
        return entity;
    }

    public boolean track(Location location, float speed, double distance) {
        if (!this.entity.isDead()) {
            moveTo(location, speed);
            double distance2D = Math.sqrt(Math.pow(location.getX() - this.entity.getLocation().getX(), 2.0D) + Math.pow(location.getZ() - this.entity.getLocation().getZ(), 2.0D));
            return (distance2D <= distance);
        }
        return true;
    }

    public void randomAttack(Player player, int delay) {
        (new BukkitRunnable() {
            public void run() {
                entity.attack(player);
            }
        }).runTaskLater(MobAI.getInstance().getPlugin(), delay);
    }

    public void protect() {
        if (protectedEntities.containsKey(this.entity.getUniqueId())) {
            if (!((Boolean) protectedEntities.get(this.entity.getUniqueId())).booleanValue()) {
                protectedEntities.put(this.entity.getUniqueId(), Boolean.TRUE);
                doProtection();
            }
        } else {
            protectedEntities.put(this.entity.getUniqueId(), Boolean.TRUE);
            doProtection();
        }
    }

    public void doProtection() {
        final List<BetterMob> entities = new ArrayList<>();
        final Map<UUID, double[]> offset = (Map) new HashMap<>();
        for (Entity e : this.entity.getNearbyEntities(10.0D, 10.0D, 10.0D)) {
            if (e instanceof LivingEntity) {
                if ((e.getType().equals(EntityType.ZOMBIE) || e.getType().equals(EntityType.BLAZE) || e.getType().equals(EntityType.ZOMBIFIED_PIGLIN))) {
                    BetterMob betterMob = new BetterMob((LivingEntity) e);
                    if (!betterMob.isBusy() && entities.size() <= 15) {
                        betterMob.setBusy(true);
                        entities.add(betterMob);
                        offset.put(e.getUniqueId(), null);
                    }
                }
            }
        }
        this.protect = Bukkit.getScheduler().scheduleSyncRepeatingTask(MobAI.getInstance().getPlugin(), new Runnable() {
            double p = 0.0D;
            double r = 5.0D;
            int k = 0;

            public void run() {
                if (entities.size() > 2) for (int i = 0; i < entities.size(); i++) {
                    double x1 = 3.0D * Math.cos(((i + 1 + this.k) * 2) * Math.PI / entities.size());
                    double x3 = 3.0D * Math.sin(((i + 1 + this.k) * 2) * Math.PI / entities.size());
                    offset.remove(entities.get(i).entity.getUniqueId());
                    offset.put(entities.get(i).entity.getUniqueId(), new double[]{x1, x3});
                }
                if (entities.size() > 2) {
                    this.k++;
                    if (this.k > entities.size()) this.k = 0;
                }
                if (!BetterMob.this.entity.isDead() && entities.size() > 2) {
                    this.p += 0.3141592653589793D;
                    for (double theta = 0.0D; theta < 6.283185307179586D; theta += 0.07853981633974483D) {
                        double x = this.r * Math.cos(theta) * Math.sin(this.p);
                        double y = this.r * Math.cos(this.p) + 1.5D;
                        double z = this.r * Math.sin(theta) * Math.sin(this.p);
                        BetterMob.this.entity.getWorld().spawnParticle(Particle.FLAME, BetterMob.this.entity.getLocation().clone().add(x, y, z), 0, 0.0D, 0.0D, 0.0D, 1.0D);
                    }
                    Iterator<BetterMob> iterator = entities.iterator();
                    while (iterator.hasNext()) {
                        BetterMob betterMob = iterator.next();
                        if (betterMob.entity.isDead()) {
                            iterator.remove();
                            offset.remove(betterMob.entity.getUniqueId());
                            continue;
                        }
                        double x1 = ((double[]) offset.get(betterMob.entity.getUniqueId()))[0];
                        double x3 = ((double[]) offset.get(betterMob.entity.getUniqueId()))[1];
                        Location location = BetterMob.this.entity.getLocation().clone().add(x1, 0.0D, x3);
                        if (location.getWorld() != null) {
                            location = location.getWorld().getHighestBlockAt(location).getLocation();
                            if (!betterMob.track(location, 2.0F, 8.0D)) betterMob.entity.teleport(location);
                            for (Entity e : betterMob.entity.getNearbyEntities(0.7D, 0.7D, 0.7D)) {
                                if (e.getType().equals(EntityType.PLAYER)) ((Player) e).damage(8.0D);
                            }
                        }
                    }
                } else {
                    BetterMob.protectedEntities.put(BetterMob.this.entity.getUniqueId(), Boolean.FALSE);
                    Bukkit.getScheduler().cancelTask(BetterMob.this.protect);
                }
            }
        }, 0L, 4L);
    }

}
