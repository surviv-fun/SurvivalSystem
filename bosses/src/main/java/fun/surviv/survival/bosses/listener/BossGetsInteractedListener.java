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

package fun.surviv.survival.bosses.listener;

import fun.surviv.survival.bosses.BossManager;
import fun.surviv.survival.bosses.SurvivBoss;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;

/**
 * SurvivalSystem; fun.surviv.survival.bosses.listener:BossGetsInteractedListener
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 12.08.2022
 */
public class BossGetsInteractedListener implements Listener {

    private final BossManager manager;

    public BossGetsInteractedListener(BossManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void bossInteraction(EntityInteractEvent event) {
        if (!(event.getEntity() instanceof SurvivBoss)) {
            return;
        }

        SurvivBoss boss = (SurvivBoss) event.getEntity();
    }

}
