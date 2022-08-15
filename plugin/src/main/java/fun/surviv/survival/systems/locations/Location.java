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

package fun.surviv.survival.systems.locations;

import fun.surviv.survival.serialization.SerializableSerializer;

import java.io.Serializable;

/**
 * SurvivalSystem; fun.surviv.survival.systems.locations:Location
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 08.08.2022
 */
public interface Location extends Serializable {

    static Location newLocation(String name, String world, double x, double y, double z, float yaw, float pitch) {
        return new Impl(name, world, x, y, z, yaw, pitch);
    }

    static Location newLocation(String name, String world, double x, double y, double z) {
        return new Impl(name, world, x, y, z, 0, 0);
    }

    static String toString(Location location) {
        try {
            return SerializableSerializer.serialize(location);
        } catch (Exception ex) {
            return null;
        }
    }

    static Location fromString(String location) {
        try {
            return (Location) SerializableSerializer.deserialize(location);
        } catch (Exception ex) {
            return null;
        }
    }

    static Location load(String name) {
        try {
            return null; // TODO
        } catch (Exception ex) {
            return null;
        }
    }

    String name();

    String worldName();

    int getX();

    int getY();

    int getZ();

    double getXExact();

    double getYExact();

    double getZExact();

    float getYaw();

    float getPitch();

    class Impl implements Location {

        private String name;
        private String worldName;
        private double x;
        private double y;
        private double z;

        private float yaw;
        private float pitch;

        public Impl(String name, String worldName, double x, double y, double z) {
            this.name = name;
            this.worldName = worldName;
            this.x = x;
            this.y = y;
            this.z = z;
            this.yaw = 0f;
            this.pitch = 0f;
        }

        public Impl(String name, String worldName, double x, double y, double z, float yaw, float pitch) {
            this.name = name;
            this.worldName = worldName;
            this.x = x;
            this.y = y;
            this.z = z;
            this.yaw = yaw;
            this.pitch = pitch;
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public String worldName() {
            return worldName;
        }

        @Override
        public int getX() {
            return (int) x;
        }

        @Override
        public int getY() {
            return (int) y;
        }

        @Override
        public int getZ() {
            return (int) z;
        }

        @Override
        public double getXExact() {
            return x;
        }

        @Override
        public double getYExact() {
            return y;
        }

        @Override
        public double getZExact() {
            return z;
        }

        @Override
        public float getYaw() {
            return yaw;
        }

        @Override
        public float getPitch() {
            return pitch;
        }

    }

}
