package com.moneytransfer.repository;

import org.apache.commons.dbutils.DbUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class H2RepositoryUtil {
    private static final Properties properties;
    private static final String h2_driver;
    private static final String h2_connection_url;
    private static final String h2_user;
    private static final String h2_password;
    private static final Logger logger = Logger.getLogger(H2RepositoryUtil.class.getName());


    static {
        properties = new Properties();
        loadConfig();
        h2_password = properties.getProperty("h2_password");
        h2_user = properties.getProperty("h2_user");
        h2_connection_url = properties.getProperty("h2_connection_url");
        h2_driver = properties.getProperty("h2_driver");
        DbUtils.loadDriver(h2_driver);
        initializeDatabase();
    }

    private static void initializeDatabase() {
        Connection connection = null;
        try {
            connection = H2RepositoryUtil.getConnection();
            final PreparedStatement createAccount = connection.prepareStatement("create table account_tbl(accountId varchar primary key,balance numeric,currencyCode varchar)");
            createAccount.execute();
            final PreparedStatement insertAccount1 = connection.prepareStatement("insert into account_tbl values('1',1000, 'GBP')");
            insertAccount1.execute();
            final PreparedStatement insertAccount2 = connection.prepareStatement("insert into account_tbl values('2',1000, 'GBP')");
            insertAccount2.execute();
            final PreparedStatement createTransaction = connection.prepareStatement("create table transaction_tbl(requestId numeric  primary key, timeStamp numeric,sourceAccountId varchar, destinationAccountId varchar, amount numeric, currencyCode varchar, reference varchar, status numeric, foreign key(sourceAccountId) references account_tbl(accountId), foreign key(destinationAccountId) references account_tbl(accountId))");
            createTransaction.execute();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error occurred while initializing database", e);
            throw new RuntimeException(e);
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(h2_connection_url, h2_user, h2_password);

    }

    private static void loadConfig() {
        String fileName = "application.properties";
        try {
            final InputStream fis = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            properties.load(fis);
        } catch (FileNotFoundException fne) {
            logger.log(Level.SEVERE, "loadConfig(): file name not found " + fileName, fne);
        } catch (IOException ioe) {
            logger.log(Level.SEVERE, "loadConfig(): error when reading the config " + fileName, ioe);
        }
    }

}