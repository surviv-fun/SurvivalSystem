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

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import org.bson.Document;

/**
 * SurvivalSystem; fun.surviv.survival.database:MongoDB
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 07.08.2022
 */
public interface MongoDB {

    /**
     * Build a Document insertable to a collection
     *
     * @param documentName       name of the document which you want to get
     * @param keyValueCollection a collection of keys and values which to append to the document
     * @return document /%/
     */
    Document buildDocument(String documentName, Object[][] keyValueCollection);

    /**
     * Add a Document to given collection
     *
     * @param collection collections name
     * @param document   the document to insert
     */
    void insertDocument(String collection, Document document);

    /**
     * Replace a Document in given collection with a new Document
     *
     * @param collection   collections name
     * @param documentName /%/
     * @param newDocument  /%/
     */
    void replaceDocument(String collection, String documentName, Document newDocument);

    /**
     * Add a property to a specified document in a specified collection
     *
     * @param collection      collections name
     * @param documentName    /%/
     * @param propertiesToAdd /%/
     */
    void addPropertyToDocument(String collection, String documentName, Object[][] propertiesToAdd);

    /**
     * Replace document property with a new value
     *
     * @param collection   collections name
     * @param documentName /%/
     * @param property     /%/
     * @param newvalue     /%/
     */
    void replaceProperty(String collection, String documentName, String property, Object newvalue);

    /**
     * Get a new Document object with updated Property
     *
     * @param oldDocument /%/
     * @param property    /%/
     * @param newvalue    /%/
     * @return newDocument /%/
     */
    Document replaceProperty(Document oldDocument, String property, Object newvalue);

    /**
     * Get all Documents in a collection
     *
     * @param collectionName collections name
     * @return documents  /%/
     */
    FindIterable<Document> getAllDocuments(String collectionName);

    /**
     * Get the count of all documents in a Collection
     *
     * @param collectionName collections name
     * @return long DocumentCount
     */
    @Deprecated
    long allDocumentsCount(String collectionName);

    /**
     * Get a collection out of the database
     *
     * @param collectionName collections name
     * @return collection  /%/
     */
    MongoCollection<Document> getCollection(String collectionName);

    /**
     * Create a collection in a specified database / collection
     *
     * @param collectionname collections name
     * @param options        /%/
     */
    void createCollection(String collectionname, CreateCollectionOptions options);


    /**
     * Get a document out in a specified collection
     *
     * @param collection   collections name
     * @param documentName /%/
     * @return document /%/
     */
    Document getDocument(String collection, String documentName);

    /**
     * Get a document out in a specified collection by a given key where the value match whereValue
     *
     * @param collection collections name
     * @param byKey      /%/
     * @param whereValue /%/
     * @return document /%/
     */
    Document getDocument(String collection, String byKey, Object whereValue);

    /**
     * delete a document in a specified collection
     *
     * @param collection   collections name
     * @param documentName /%/
     * @return bool /%/
     */
    boolean deleteOne(String collection, String documentName);

    /**
     * delete a document in a specified collection by a given key where the value match whereValue
     *
     * @param collection collections name
     * @param key        /%/
     * @param whereValue /%/
     * @return bool
     */
    boolean deleteOne(String collection, String key, Object whereValue);

    /**
     * Check if the client is Connected to the database server
     *
     * @return boolean /%/
     */
    boolean isConnected();

    /**
     * Let the mongoClient connect to database per given mongoUri
     *
     * @param connectionString the MongoDB connectionString
     */
    void connect(String connectionString);

    /**
     * Get a database in MongoDB server
     *
     * @param dbName database name
     * @return MongoDatabase
     */
    MongoDatabase getDatabase(String dbName);

    /**
     * close the active session
     */
    void closeSession();

}
