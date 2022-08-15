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

package fun.surviv.survival.bosses.entities;

import fun.surviv.survival.bosses.AbstractSurvivBossEntity;
import fun.surviv.survival.bosses.SurvivBoss;
import fun.surviv.survival.serialization.ComponentSerializer;
import org.bukkit.Location;

/**
 * SurvivalSystem; fun.surviv.survival.bosses.entities:Zombum
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 12.08.2022
 */
public class Zombum extends SurvivBoss {

    public static final int ID = 1;

    public Zombum() {
        super(ID);
    }

    @Override
    public synchronized AbstractSurvivBossEntity spawnBoss(final Location location) {
        super.spawnBoss(location);
        setInvulnerable(true);
        setCanPickupItems(true);
        setLeftHanded(true);
        setMaximumAir(999999999);
        customName(customName().append(ComponentSerializer.etAndHEX.deserialize(" &5ACHTUNG ZOMBUM &5" + getHealth() + " &b" + isInvulnerable())));
        return this;
    }

    @Override
    public void tick(final long tick) {

    }

}
