package com.moneytransfer.repository.transfer;

import com.moneytransfer.domain.TransactionStatus;
import com.moneytransfer.domain.Transfer;
import com.moneytransfer.domain.TransferBuilder;
import com.moneytransfer.domain.TransferRequest;
import com.moneytransfer.exception.GetTransferException;
import com.moneytransfer.exception.InsufficientBalanceException;
import com.moneytransfer.exception.TransferException;
import com.moneytransfer.repository.H2RepositoryUtil;
import com.moneytransfer.repository.SqlConstant;
import org.apache.commons.dbutils.DbUtils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.apache.commons.dbutils.DbUtils.closeQuietly;

public class H2TransferRepository implements TransferRepository {
    private static Logger logger = Logger.getLogger(H2TransferRepository.class.getName());

    /**
     * Transfer method transfers money between accounts of the same bank.
     *
     * @param transferRequest Source, Destination and Amount details
     * @return {@link Transfer} Transaction detail
     */
    @Override
    public synchronized Transfer transfer(TransferRequest transferRequest) {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            startTransaction(transferRequest, connection);
            checkBalance(transferRequest, connection);
            debitAccount(transferRequest, connection);
            creditAccount(transferRequest, connection);
            finishTransaction(transferRequest, connection);
            connection.commit();
            return getTransferUsingRequestId(transferRequest.requestId, connection);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error occurred while transfer operation.", e);
            rollBackQuietly(connection);
            throw new TransferException("Error occurred during transfer operation.");
        } catch (InsufficientBalanceException e) {
            logger.log(Level.SEVERE, "Insufficient balance available for transfer operation.", e);
            try {
                updateTransactionStatus(transferRequest, TransactionStatus.DECLINED, connection);
                connection.commit();
            } catch (SQLException ex) {
                throw new TransferException("Error occurred during transfer operation.");
            }
            throw e;
        } finally {
            closeQuietly(connection);
        }
    }

    private void startTransaction(TransferRequest transferRequest, Connection connection) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(SqlConstant.INSERT_TRANSACTION);
        stmt.setString(1, transferRequest.requestId);
        stmt.setLong(2, System.currentTimeMillis());
        stmt.setString(3, transferRequest.sourceAccountId);
        stmt.setString(4, transferRequest.destinationAccountId);
        stmt.setBigDecimal(5, transferRequest.amount);
        stmt.setString(6, transferRequest.currencyCode);
        stmt.setString(7, transferRequest.reference);
        stmt.setInt(8, TransactionStatus.PENDING.getValue());

        stmt.executeUpdate();
    }

    private void checkBalance(TransferRequest transferRequest, Connection connection) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(SqlConstant.GET_ACCOUNT_BALANCE);
        stmt.setString(1, transferRequest.sourceAccountId);
        BigDecimal balance = BigDecimal.ZERO;

        final ResultSet resultSet = stmt.executeQuery();
        if (resultSet.next()) {
            balance = resultSet.getBigDecimal(1);
        }

        if (balance.compareTo(transferRequest.amount) < 0) {
            updateTransactionStatus(transferRequest, TransactionStatus.DECLINED, connection);
            throw new InsufficientBalanceException("Insufficient balance transaction declined.");
        }

    }

    private void debitAccount(TransferRequest transferRequest, Connection connection) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(SqlConstant.DEBIT_ACCOUNT_BALANCE);
        stmt.setBigDecimal(1, transferRequest.amount);
        stmt.setString(2, transferRequest.sourceAccountId);
        stmt.executeUpdate();
    }

    private void creditAccount(TransferRequest transferRequest, Connection connection) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(SqlConstant.CREDIT_ACCOUNT_BALANCE);
        stmt.setBigDecimal(1, transferRequest.amount);
        stmt.setString(2, transferRequest.sourceAccountId);
        stmt.executeUpdate();

    }

    private void finishTransaction(TransferRequest transferRequest, Connection connection) throws SQLException {
        updateTransactionStatus(transferRequest, TransactionStatus.FINISHED, connection);
    }

    private void updateTransactionStatus(TransferRequest transferRequest, TransactionStatus status, Connection connection) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(SqlConstant.UPDATE_TRANSACTION_STATUS);
        stmt.setInt(1, status.getValue());
        stmt.setString(2, transferRequest.requestId);
        stmt.executeUpdate();
    }

    private void rollBackQuietly(Connection connection) {
        try {
            DbUtils.rollback(connection);
        } catch (SQLException ex) {
            throw new TransferException("Error occurred during transfer operation");
        }
    }

    private Transfer getTransferUsingRequestId(String requestId, Connection connection) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(SqlConstant.GET_TRANSACTION_USING_REQUEST_ID);
        stmt.setString(1, requestId);
        final ResultSet resultSet = stmt.executeQuery();
        if (resultSet.next()) {
            return buildTransfer(resultSet);
        }

        return null;
    }

    @Override
    public List<Transfer> getTransfers(String accountId) {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(SqlConstant.GET_TRANSACTION_USING_ACCOUNT_ID);
            stmt.setString(1, accountId);
            final ResultSet resultSet = stmt.executeQuery();
            List<Transfer> transfers = new ArrayList<>();
            while (resultSet.next()) {
                transfers.add(buildTransfer(resultSet));
            }
            return transfers;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error occurred while get transfer operation.", e);
            throw new GetTransferException("Error occurred during get transfer operation");
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    private Transfer buildTransfer(ResultSet resultSet) throws SQLException {
        return new TransferBuilder()
                .withRequestId(resultSet.getString(1))
                .withTimeStamp(resultSet.getLong(2))
                .withSourceAccountId(resultSet.getString(3))
                .withDestinationAccountId(resultSet.getString(4))
                .withAmount(resultSet.getBigDecimal(5))
                .withCurrencyCode(resultSet.getString(6))
                .withReference(resultSet.getString(7))
                .withStatus(TransactionStatus.getValue(resultSet.getInt(8)))
                .build();
    }

    @Override
    public List<Transfer> getTransfers() {
        Connection connection = null;
        try {
            List<Transfer> transfers = new ArrayList<>();
            connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(SqlConstant.GET_TRANSACTIONS);
            final ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                transfers.add(buildTransfer(resultSet));
            }
            return transfers;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error occurred while transfer operation.", e);
            throw new GetTransferException("Error occurred during get transfer operation");
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    private Connection getConnection() throws SQLException {
        return H2RepositoryUtil.getConnection();
    }
}