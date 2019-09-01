package com.moneytransfer.repository.account;

import com.moneytransfer.domain.Account;
import com.moneytransfer.domain.AccountBuilder;
import com.moneytransfer.exception.AddAccountException;
import com.moneytransfer.exception.DeleteAccountException;
import com.moneytransfer.exception.GetAccountException;
import com.moneytransfer.exception.UpdateAccountException;
import com.moneytransfer.repository.H2RepositoryUtil;
import com.moneytransfer.repository.SqlConstant;
import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class H2AccountRepository implements AccountRepository {
    private static Logger logger = Logger.getLogger(H2AccountRepository.class.getName());

    @Override
    public List<Account> getAllAccounts() {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(SqlConstant.GET_ALL_ACCOUNTS);
            final ResultSet resultSet = stmt.executeQuery();
            List<Account> accounts = new ArrayList<>();
            while (resultSet.next()) {
                accounts.add(buildAccount(resultSet));
            }
            return accounts;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error occurred while get all accounts operation.", e);
            throw new GetAccountException("Error occurred during get all accounts operation");
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    private Account buildAccount(ResultSet resultSet) throws SQLException {
        return new AccountBuilder()
                .withAccountId(resultSet.getString(1))
                .withBalance(resultSet.getBigDecimal(2))
                .withCurrencyCode(resultSet.getString(3))
                .build();
    }

    @Override
    public Account getAccount(String accountId) {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(SqlConstant.GET_ACCOUNT);
            stmt.setString(1, accountId);
            final ResultSet resultSet = stmt.executeQuery();
            List<Account> accounts = new ArrayList<>();
            if (resultSet.next()) {
                return buildAccount(resultSet);
            }
            return null;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error occurred while get all accounts operation.", e);
            throw new GetAccountException("Error occurred during get all accounts operation");
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    @Override
    public Account addAccount(Account account) {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(SqlConstant.INSERT_ACCOUNT);
            stmt.setString(1, account.accountId);
            stmt.setBigDecimal(2, account.balance);
            stmt.setString(3, account.currencyCode);
            stmt.executeUpdate();
            return account;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error occurred while add account operation.", e);
            throw new AddAccountException("Error occurred during add account operation");
        }
    }

    @Override
    public Account updateAccount(Account account) {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(SqlConstant.UPDATE_ACCOUNT);
            stmt.setBigDecimal(1, account.balance);
            stmt.setString(2, account.currencyCode);
            stmt.setString(3, account.accountId);
            stmt.executeUpdate();
            return account;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error occurred while update account operation.", e);
            throw new UpdateAccountException("Error occurred during update account operation");
        }
    }

    @Override
    public void deleteAccount(String accountId) {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(SqlConstant.DELETE_ACCOUNT);
            stmt.setString(1, accountId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error occurred while delete account operation.", e);
            throw new DeleteAccountException("Error occurred during delete account operation");
        }
    }

    @Override
    public boolean isAccountExists(String accountId) {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(SqlConstant.GET_ACCOUNT);
            stmt.setString(1, accountId);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error occurred while get account operation.", e);
            throw new GetAccountException("Error occurred while get account operation.");
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    private Connection getConnection() throws SQLException {
        return H2RepositoryUtil.getConnection();
    }
}