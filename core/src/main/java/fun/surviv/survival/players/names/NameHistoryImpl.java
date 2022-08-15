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

package fun.surviv.survival.players.names;

import fun.surviv.survival.Core;
import fun.surviv.survival.CoreSystem;
import fun.surviv.survival.players.CachedPlayer;
import fun.surviv.survival.serialization.SerializableSerializer;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * SurvivalSystem; fun.surviv.survival.players.names:NameHistoryImpl
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 08.08.2022
 */
public class NameHistoryImpl implements NameHistory {

    private final CachedPlayer cachedPlayer;
    private final CoreSystem core;

    private List<NameHistoryEntry> entries;

    private Document nameHistoryDocument;

    public NameHistoryImpl(CachedPlayer cachedPlayer) {
        this.cachedPlayer = cachedPlayer;
        this.core = Core.system();
        this.entries = new ArrayList<>();
        this.nameHistoryDocument = core.db().getDocument("nameHistory", cachedPlayer.uuid().toString());
        if (this.nameHistoryDocument == null) {
            this.nameHistoryDocument = core.db().buildDocument(cachedPlayer.uuid().toString(), new Object[][]{
                    {"entries", Arrays.asList(SerializableSerializer.serialize(new NameHistoryEntryImpl(cachedPlayer.username())))}
            });
            core.db().insertDocument("nameHistory", this.nameHistoryDocument);
        }
        List<String> history = this.nameHistoryDocument.getList("entries", String.class);
        for (String entry : history) {
            this.entries.add((NameHistoryEntry) SerializableSerializer.deserialize(entry));
        }
    }

    @Override
    public UUID uuid() {
        return cachedPlayer.uuid();
    }

    @Override
    public String current() {
        return null;
    }

    @Override
    public List<NameHistoryEntry> entries() {
        return entries;
    }

    @Override
    public NameHistoryEntry currentEntry() {
        return null;
    }

    @Override
    public NameHistoryEntry firstJoined() {
        return null;
    }

    @Override
    public boolean addEntry(String name) {
        try {
            List<String> history = new ArrayList<>();
            for (NameHistoryEntry entry : entries) {
                history.add(SerializableSerializer.serialize(entry));
            }
            history.add(SerializableSerializer.serialize(new NameHistoryEntryImpl(name)));
            this.nameHistoryDocument.put("entries", history);
            core.db().replaceDocument("nameHistory", cachedPlayer.uuid().toString(), this.nameHistoryDocument);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
