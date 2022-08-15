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

import org.jetbrains.annotations.NotNull;

/**
 * SurvivalSystem; fun.surviv.survival.systems.locations:CachedLocationImpl
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 08.08.2022
 */
public class CachedLocationImpl implements CachedLocation {

    private Location location;

    public CachedLocationImpl(@NotNull Location location) {
        this.location = location;
    }

    @Override
    public void reload() {
        // TODO:
    }

    @Override
    public void save() {
        // TODO:
    }

    @Override
    public void delete() {
        // TODO:
    }

    @Override
    public void edit(Location newLocation) {
        this.location = newLocation;
    }

    @Override
    public String name() {
        return location.name();
    }

    @Override
    public String worldName() {
        return location.worldName();
    }

    @Override
    public int getX() {
        return location.getX();
    }

    @Override
    public int getY() {
        return location.getY();
    }

    @Override
    public int getZ() {
        return location.getZ();
    }

    @Override
    public double getXExact() {
        return location.getXExact();
    }

    @Override
    public double getYExact() {
        return location.getYExact();
    }

    @Override
    public double getZExact() {
        return location.getZExact();
    }

    @Override
    public float getYaw() {
        return location.getYaw();
    }

    @Override
    public float getPitch() {
        return location.getPitch();
    }

}
