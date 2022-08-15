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

package fun.surviv.survival;

import fun.surviv.survival.configuration.types.DatabaseConfig;
import fun.surviv.survival.database.MongoDB;
import fun.surviv.survival.database.MongoDatabase;
import fun.surviv.survival.permissions.PermissionManager;

/**
 * SurvivalSystem; fun.surviv.survival:CoreSystemImpl
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 08.08.2022
 */
public class CoreSystemImpl implements CoreSystem {

    private MongoDatabase database;

    private PermissionManager perms;

    public CoreSystemImpl(DatabaseConfig databaseConfig) {
        database = MongoDatabase.fromMongoDatabaseConfig(databaseConfig);
    }

    @Override
    public MongoDB db() {
        return database;
    }

    @Override
    public PermissionManager perms() {
        return perms;
    }

    @Override
    public void perms(PermissionManager perms) {
        this.perms = perms;
    }


}
