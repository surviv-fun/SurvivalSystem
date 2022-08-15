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

package fun.surviv.survival.players.options;

import fun.surviv.survival.Core;
import fun.surviv.survival.CoreSystem;
import fun.surviv.survival.players.CachedPlayer;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * SurvivalSystem; fun.surviv.survival.players.options:PlayerOptionsImpl
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 08.08.2022
 */
public class PlayerOptionsImpl implements PlayerOptions {

    private final CachedPlayer cachedPlayer;
    private final CoreSystem core;

    private Document optionsDocument;

    private Map<String, PlayerOptionsEntry> entries;

    public PlayerOptionsImpl(CachedPlayer cachedPlayer) {
        this.cachedPlayer = cachedPlayer;
        this.core = Core.system();
        this.entries = new HashMap<>();
        this.optionsDocument = core.db().getDocument("playerOptions", cachedPlayer.uuid().toString());
        if (this.optionsDocument == null) {
            this.optionsDocument = core.db().buildDocument(cachedPlayer.uuid().toString(), new Object[][]{});
            core.db().insertDocument("playerOptions", this.optionsDocument);
        }
        for (String key : this.optionsDocument.keySet()) {
            if (key.equalsIgnoreCase("documentName") || key.equalsIgnoreCase("_id")) {
                continue;
            }
            this.entries.put(key, new PlayerOptionsEntryImpl(this.optionsDocument.getString(key)));
        }
    }

    private void update() {
        for (String key : entries.keySet()) {
            this.optionsDocument.put(key, this.entries.get(key).asString());
        }
        core.db().replaceDocument("playerOptions", cachedPlayer.uuid().toString(), this.optionsDocument);
    }

    @Override
    public void set(String key, String value) {
        this.entries.put(key, new PlayerOptionsEntryImpl(value));
        update();
    }

    @Override
    public void set(String key, boolean value) {
        this.entries.put(key, new PlayerOptionsEntryImpl(Boolean.toString(value)));
        update();
    }

    @Override
    public void set(String key, int value) {
        this.entries.put(key, new PlayerOptionsEntryImpl(Integer.toString(value)));
        update();
    }

    @Override
    public void set(String key, long value) {
        this.entries.put(key, new PlayerOptionsEntryImpl(Long.toString(value)));
        update();
    }

    @Override
    public void set(String key, double value) {
        this.entries.put(key, new PlayerOptionsEntryImpl(Double.toString(value)));
        update();
    }

    @Override
    public void set(String key, float value) {
        this.entries.put(key, new PlayerOptionsEntryImpl(Float.toString(value)));
        update();
    }

    @Override
    public void remove(String key) {
        this.entries.remove(key);
        update();
    }

    @Override
    public boolean existEntry(String key) {
        return this.entries.containsKey(key);
    }

    @Override
    public PlayerOptionsEntry getEntry(String key) {
        return this.entries.get(key);
    }

}
