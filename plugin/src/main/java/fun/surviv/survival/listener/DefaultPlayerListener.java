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

package fun.surviv.survival.listener;

import fun.surviv.survival.Constants;
import fun.surviv.survival.SurvivalSystem;
import fun.surviv.survival.players.SurvivalPlayer;
import fun.surviv.survival.players.SurvivalPlayerImpl;
import fun.surviv.survival.serialization.ComponentSerializer;
import fun.surviv.survival.utils.ChatUtil;
import fun.surviv.survival.utils.LocationUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * SurvivalSystem; fun.surviv.survival.listener:DefaultPlayerListener
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 08.08.2022
 */
public class DefaultPlayerListener implements Listener {

    private final SurvivalSystem plugin;

    public DefaultPlayerListener(SurvivalSystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerJoin(AsyncPlayerPreLoginEvent event) {
        if (!SurvivalSystem.LOADED) {
            event.kickMessage(ComponentSerializer.etOnly.deserialize(Constants.PREFIX + " &7Der &3Server &7ist noch nicht fertig geladen."));
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void playerJoin(PlayerJoinEvent event) {
        event.joinMessage(Component.empty());
        SurvivalPlayer survivalPlayer = new SurvivalPlayerImpl(event.getPlayer().getUniqueId());
        plugin.getPlayerProvider().getSurvivalPlayers().put(survivalPlayer.uuid(), survivalPlayer);
        plugin.getPlayerProvider().getCachedPlayers().put(survivalPlayer.uuid(), survivalPlayer);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void playerQuit(PlayerQuitEvent event) {
        event.quitMessage(Component.empty());
        plugin.getPlayerProvider().getSurvivalPlayers().remove(event.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    void onBedEnter(PlayerBedEnterEvent event) {
        if (event.getBedEnterResult().equals(PlayerBedEnterEvent.BedEnterResult.OK)) {
            ChatUtil.replySenderComponent(event.getPlayer(), "&7Die Nacht kann nicht übersprungen werden >:(");
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    void onBedEnter(PlayerDeathEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (event.getPlayer() == null) {
            return;
        }
        Player player = event.getPlayer();
        Location deathLocation = player.getLocation();
        String locationReadable = LocationUtils.locationAsString(deathLocation);
        ChatUtil.reply(player, "&7Aww du bist gestorben. Deine Position war &l[&r&3" + locationReadable + "&l&7]&r.");
    }

}
