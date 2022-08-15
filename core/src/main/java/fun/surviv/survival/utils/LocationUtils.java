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

package fun.surviv.survival.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * SurvivalSystem; fun.surviv.survival.utils:LocationUtils
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 13.08.2022
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationUtils {

    public static boolean teleportPlayerRandomOverworld(
            Player player, World world, int minx, int minz, int maxx,
            int maxz
    ) {
        try {
            int maxX = maxx;
            int minX = minx;
            int X = (int) Math.round(Math.random() * (double) (maxX - minX + 1) + (double) minX);
            double maxZ = maxz;
            int minZ = minz;
            int Z = (int) Math.round(Math.random() * (double) (maxZ - minZ + 1) + (double) minZ);
            Location loc = new Location(world, X, 65, Z);
            Block b = world.getHighestBlockAt(loc);
            loc.setY((double) b.getLocation().getBlockY());
            loc.setWorld(world);
            player.teleport(loc);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean teleportPlayerRandomOverworld(
            Player player, String world, int minx, int minz, int maxx,
            int maxz
    ) {
        try {
            int maxX = maxx;
            int minX = minx;
            int X = (int) Math.round(Math.random() * (double) (maxX - minX + 1) + (double) minX);
            double maxZ = maxz;
            int minZ = minz;
            int Z = (int) Math.round(Math.random() * (double) (maxZ - minZ + 1) + (double) minZ);
            World tpWorld = Bukkit.getWorld(world);
            Location loc = new Location(tpWorld, X, 65, Z);
            Block b = tpWorld.getHighestBlockAt(loc);
            loc.setY((double) b.getLocation().getBlockY());
            loc.setWorld(tpWorld);
            player.teleport(loc);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean teleportPlayerRandomNether(
            Player player, World world, int minx, int minz, int maxx,
            int maxz
    ) {
        try {
            int x = 0;
            int z = 0;
            Location destiny = new Location(world, x, 100, z);
            Location idestiny = new Location(world, x, 100, z);
            Block block = destiny.getWorld().getBlockAt(x, 100, z);
            Block iblock = block;
            do {
                x = randomCoord(minx, maxx, 0);
                z = randomCoord(minz, maxz, 0);
                block = destiny.getWorld().getBlockAt(x, 100, z);
                int i = 0;
                for (iblock = block; (iblock.getType() != Material.AIR && iblock.getY() > 0); i++) {
                    iblock = block.getRelative(0, -i, 0);
                }
                if (iblock.getY() > 0) {
                    for (iblock = block; (iblock.getType() == Material.AIR && iblock.getY() > 0); i--) {
                        iblock = block.getRelative(0, i, 0);
                    }
                }
            } while (dangerBlocks(iblock.getType()) || airExist(iblock));
            idestiny = new Location(world, x + 0.5, (iblock.getY() + 1), z + 0.5);
            player.teleport(idestiny);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean teleportPlayerRandomNether(
            Player player, String wName, int minx, int minz, int maxx,
            int maxz
    ) {
        try {
            World world = Bukkit.getWorld(wName);
            int x = 0;
            int z = 0;
            Location destiny = new Location(world, x, 100, z);
            Location idestiny = new Location(world, x, 100, z);
            Block block = destiny.getWorld().getBlockAt(x, 100, z);
            Block iblock = block;
            do {
                x = randomCoord(minx, maxx, 0);
                z = randomCoord(minz, maxz, 0);
                block = destiny.getWorld().getBlockAt(x, 100, z);
                int i = 0;
                for (iblock = block; (iblock.getType() != Material.AIR && iblock.getY() > 0); i++) {
                    iblock = block.getRelative(0, -i, 0);
                }
                if (iblock.getY() > 0) {
                    for (iblock = block; (iblock.getType() == Material.AIR && iblock.getY() > 0); i--) {
                        iblock = block.getRelative(0, i, 0);
                    }
                }
            } while (dangerBlocks(iblock.getType()) || airExist(iblock));
            idestiny = new Location(world, x + 0.5, (iblock.getY() + 1), z + 0.5);
            player.teleport(idestiny);
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    public static boolean dangerBlocks(Material block) {
        List<Material> materials = new ArrayList<Material>();
        materials.add(Material.BEDROCK);
        materials.add(Material.LAVA);
        materials.add(Material.MAGMA_BLOCK);
        if (materials.contains(block)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean airExist(Block iblock) {
        if (iblock.getRelative(0, 1, 0).getType() != Material.AIR
                || iblock.getRelative(0, 2, 0).getType() != Material.AIR) {
            return true;
        }
        return false;
    }

    public static int randomCoord(int minimum, int maximum, int center) {
        int randomCoord = 0;
        int randCoord = maximum;
        do {
            randomCoord = new Double(Math.random() * 2 * maximum).intValue();
            randomCoord = randomCoord - randCoord + center;
        } while (Math.abs(randomCoord) < (minimum + center));
        return randomCoord;

    }

    public static String location5dToString(Location location) {
        String worldName = location.getWorld().getName();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();
        return new String(worldName + Seperator.SEPLOC.getCharacter() + x + Seperator.SEPLOC.getCharacter() + y
                + Seperator.SEPLOC.getCharacter() + z + Seperator.SEPLOC.getCharacter() + yaw
                + Seperator.SEPLOC.getCharacter() + pitch);
    }

    public static Location location5dFromString(String locStr) {
        String[] args = locStr.split(Seperator.SEPLOC.getCharacter());
        return new Location(Bukkit.getWorld(args[0]), Double.valueOf(args[1]), Double.valueOf(args[2]),
                Double.valueOf(args[3]), Float.valueOf(args[4]), Float.valueOf(args[5])
        );
    }

    public static String location3dToString(Location location) {
        String worldName = location.getWorld().getName();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        return new String(worldName + Seperator.SEPLOC.getCharacter() + x + Seperator.SEPLOC.getCharacter() + y
                + Seperator.SEPLOC.getCharacter() + z);
    }

    public static Location location3dFromString(String locStr) {
        String[] args = locStr.split(Seperator.SEPLOC.getCharacter());
        return new Location(Bukkit.getWorld(args[0]), Double.valueOf(args[1]), Double.valueOf(args[2]),
                Double.valueOf(args[3])
        );
    }

    public static String locationAsString(Location location) {
        String x = Double.toString(MathUtils.round2Decimals(location.getX()));
        String y = Double.toString(MathUtils.round2Decimals(location.getY()));
        String z = Double.toString(MathUtils.round2Decimals(location.getZ()));
        return x + ", " + y + ", " + z;
    }

    @AllArgsConstructor
    public enum Seperator {
        SEPLOC(";");
        @Getter
        String character;
    }

}
