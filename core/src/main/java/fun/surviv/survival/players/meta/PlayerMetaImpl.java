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

package fun.surviv.survival.players.meta;

import fun.surviv.survival.Core;
import fun.surviv.survival.players.CachedPlayer;

/**
 * SurvivalSystem; fun.surviv.survival.players.meta:PlayerMetaImpl
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 08.08.2022
 */
public class PlayerMetaImpl implements PlayerMeta {

    private final CachedPlayer cachedPlayer;

    public PlayerMetaImpl(CachedPlayer cachedPlayer) {
        this.cachedPlayer = cachedPlayer;
    }

    @Override
    public void update() {
        Core.system().perms().loadUser(cachedPlayer.uuid());
    }

    @Override
    public String prefix() {
        return Core.system().perms().getPrefix(cachedPlayer.uuid());
    }

    @Override
    public String suffix() {
        return Core.system().perms().getSuffix(cachedPlayer.uuid());
    }

    @Override
    public String prefixes() {
        return Core.system().perms().getPrefixes(cachedPlayer.uuid());
    }

    @Override
    public String suffixes() {
        return Core.system().perms().getSuffixes(cachedPlayer.uuid());
    }

}
