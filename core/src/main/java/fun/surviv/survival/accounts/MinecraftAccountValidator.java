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

package fun.surviv.survival.accounts;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

/**
 * SurvivalSystem; fun.surviv.survival.accounts:MinecraftAccountValidator
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 08.08.2022
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MinecraftAccountValidator {

    /**
     * Validate a players account by a given name ( Checks api.mojang.com api if exists )
     *
     * @param playerName
     * @return isValid
     */
    public static boolean isValidPlayer(String playerName) {
        try {
            String urlS = "https://api.mojang.com/users/profiles/minecraft/" + playerName;
            URL url = new URL(urlS);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject jsonProfile = root.getAsJsonObject();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    /**
     * Resolve the uuid of a player by given username ( returns null if not existing )
     *
     * @param playerName
     * @return uuid
     */
    public static UUID resolveUUID(String playerName) {
        try {
            String uuid = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(new URL("https://api.mojang.com/users/profiles/minecraft/" + playerName).openStream()));
            uuid = (((JsonObject) new JsonParser().parse(in)).get("id")).toString().replaceAll("\"", "");
            uuid = uuid.replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5");
            in.close();
            return UUID.fromString(uuid);
        } catch (Exception e) {
            return null;
        }
    }

}
