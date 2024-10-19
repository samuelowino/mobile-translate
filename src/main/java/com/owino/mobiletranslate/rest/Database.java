package com.owino.mobiletranslate.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class Database {
    private static final java.lang.String DB_URL="jdbc:sqlite:auth.db";
    private static Logger logger=Logger.getLogger(Database.class.getSimpleName());
    public  static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS users");
            stmt.execute("DROP TABLE IF EXISTS api_keys");
            // Enable foreign key support
            stmt.execute("PRAGMA foreign_keys = ON");
            //stmt.execute("DROP TABLE IF EXISTS users");

            stmt.execute("CREATE TABLE users " +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT UNIQUE COLLATE NOCASE , " +
                    "email TEXT UNIQUE COLLATE NOCASE," +
                    "password TEXT, " +
                    "created_at TEXT DEFAULT (datetime('now')))");

            stmt.execute("CREATE TABLE IF NOT EXISTS api_keys " +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "user_id INTEGER, " +
                    "api_key TEXT UNIQUE, " +
                    "created_at TEXT DEFAULT (datetime('now')), " +
                    "expires_at TEXT, " +
                    "is_active INTEGER DEFAULT 1, " +
                    "FOREIGN KEY (user_id) REFERENCES users(id))");

            // Create index on user_id in api_keys table
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_api_keys_user_id ON api_keys(user_id)");

            logger.info("Created tables users and api_keys with foreign key constraint and index");

        } catch (SQLException e) {
            logger.info("Error initializing database: " + e.getMessage());
            throw new RuntimeException("Could not initialize database", e);
        }
    }
}
