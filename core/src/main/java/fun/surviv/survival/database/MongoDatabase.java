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

package fun.surviv.survival.database;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.CreateCollectionOptions;
import fun.surviv.survival.configuration.types.DatabaseConfig;
import lombok.Getter;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * SurvivalSystem; fun.surviv.survival.database:MongoDatabase
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 07.08.2022
 */
public class MongoDatabase implements MongoDB {

    @Getter
    private String databaseName;
    private MongoClient mongoClient;

    /**
     * Create default MongoDBHandler instance
     *
     * @param connectionString the MongoDB connectionString
     * @param databaseName     the databases name you want to use
     */
    public MongoDatabase(String connectionString, String databaseName) {
        this.databaseName = databaseName;
        connect(connectionString);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!isConnected()) {
                    try {
                        closeSession();
                    } catch (Exception ex) {
                    }
                    connect(connectionString);
                }
            }
        }, 0, 60000 * 3);
    }

    /**
     * Returns a new MongoDatabaseHandler instance by given MongoDatabaseConfig
     *
     * @param config
     * @return mongoDatabaseHandler
     */
    public static MongoDatabase fromMongoDatabaseConfig(DatabaseConfig config) {
        return new MongoDatabase(config.getConnectionString(), config.getDatabaseName());
    }

    @Override
    public Document buildDocument(String documentName, Object[][] keyValueCollection) {
        Document document = new Document("documentName", documentName);
        for (Object[] append : keyValueCollection) {
            document = document.append((String) append[0], append[1]);
        }
        return document;
    }

    @Override
    public void insertDocument(String collection, Document document) {
        mongoClient.getDatabase(databaseName).getCollection(collection).insertOne(document);
    }

    @Override
    public void replaceDocument(String collection, String documentName, Document newDocument) {
        getCollection(collection).deleteOne(getDocument(collection, documentName));
        getCollection(collection).insertOne(newDocument);
    }

    @Override
    public void addPropertyToDocument(String collection, String documentName, Object[][] propertiesToAdd) {
        Document toUpdateDocument = getDocument(collection, documentName);
        for (Object[] append : propertiesToAdd) {
            toUpdateDocument.append((String) append[0], append[1]);
        }
        replaceDocument(collection, documentName, toUpdateDocument);
    }

    @Override
    public void replaceProperty(String collection, String documentName, String property, Object newvalue) {
        Document document = getDocument(collection, documentName);
        Bson filter = document;
        Document newDocument = new Document("documentName", documentName);
        for (Map.Entry<String, Object> entry : document.entrySet()) {
            if (entry.getKey().equals(property)) {
                if (entry.getKey().equals(document.get("_id"))) {
                    continue;
                }
                newDocument.append(entry.getKey(), newvalue);
            } else {
                if (entry.getKey().equals(document.get("_id"))) {
                    continue;
                }
                newDocument.append(entry.getKey(), entry.getValue());
            }
        }
        getCollection(collection).deleteOne(filter);
        getCollection(collection).insertOne((newDocument));
    }

    @Override
    public Document replaceProperty(Document oldDocument, String property, Object newvalue) {
        Document newDocument = new Document("documentName", oldDocument.getString("documentName"));
        for (Map.Entry<String, Object> entry : oldDocument.entrySet()) {
            if (entry.getKey().equals(property)) {
                if (entry.getKey().equals(oldDocument.get("_id"))) {
                    continue;
                }
                newDocument.append(entry.getKey(), newvalue);
            } else {
                if (entry.getKey().equals(oldDocument.get("_id"))) {
                    continue;
                }
                newDocument.append(entry.getKey(), entry.getValue());
            }
        }
        return newDocument;
    }

    @Override
    public FindIterable<Document> getAllDocuments(String collectionName) {
        return getCollection(collectionName).find();
    }

    @Override
    public long allDocumentsCount(String collectionName) {
        return getCollection(collectionName).countDocuments();
    }

    @Override
    public MongoCollection<Document> getCollection(String collectionName) {
        if (mongoClient.getDatabase(databaseName).getCollection(collectionName) != null) {
            return mongoClient.getDatabase(databaseName).getCollection(collectionName);
        }
        return null;
    }

    @Override
    public void createCollection(String collectionname, CreateCollectionOptions options) {
        mongoClient.getDatabase(databaseName).createCollection(collectionname, options);
    }

    @Override
    public Document getDocument(String collection, String documentName) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("documentName", documentName);
        FindIterable<Document> cursor = mongoClient.getDatabase(databaseName).getCollection(collection).find(whereQuery);
        return cursor.first();
    }

    @Override
    public Document getDocument(String collection, String byKey, Object whereValue) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put(byKey, whereValue);
        FindIterable<Document> cursor = mongoClient.getDatabase(databaseName).getCollection(collection).find(whereQuery);
        return cursor.first();
    }

    @Override
    public boolean deleteOne(String collection, String documentName) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("documentName", documentName);
        FindIterable<Document> cursor = mongoClient.getDatabase(databaseName).getCollection(collection).find(whereQuery);

        getCollection(collection).deleteOne(cursor.first());
        return true;
    }

    @Override
    public boolean deleteOne(String collection, String key, Object whereValue) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put(key, whereValue);
        FindIterable<Document> cursor = mongoClient.getDatabase(databaseName).getCollection(collection).find(whereQuery);

        getCollection(collection).deleteOne(cursor.first());
        return true;
    }

    @Override
    public boolean isConnected() {
        return getDatabase(databaseName) != null;
    }

    @Override
    public void connect(String connectionString) {
        try {
            mongoClient = new MongoClient(new MongoClientURI(connectionString));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public com.mongodb.client.MongoDatabase getDatabase(String dbName) {
        return mongoClient.getDatabase(dbName);
    }

    @Override
    public void closeSession() {
        try {
            mongoClient.close();
        } catch (Exception e) {
        }
    }

}
