package org.nuvito.spring.data.sqlite;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcOperations;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SqliteDialectProviderTest {
    private SqliteDialectProvider sut;

    @BeforeEach
    void setUp() {
        sut = new SqliteDialectProvider();
    }

    @Test
    void givenSqliteProduct_sqliteDialectShouldBeCreated() throws SQLException {
        // given
        JdbcOperations operations = givenJdbcConnectionConnectedWithDatabaseProduct("SQLite-Database");

        // when
        Optional<Dialect> optionalDialect = sut.getDialect(operations);

        // then
        assertThat(optionalDialect).containsInstanceOf(SqliteDialect.class);
    }

    @Test
    void givenOtherProduct_noDialectShouldBeCreated() throws Exception {
        // given
        JdbcOperations operations = givenJdbcConnectionConnectedWithDatabaseProduct("my-other-database");

        // when
        Optional<Dialect> optionalDialect = sut.getDialect(operations);

        // then
        assertThat(optionalDialect).isEmpty();
    }

    private JdbcOperations givenJdbcConnectionConnectedWithDatabaseProduct(String productName) throws SQLException {
        Connection connection = givenConnectionFor(productName);
        return givenJdbcOperationsExecutesCallbackWithMock(connection);
    }

    private Connection givenConnectionFor(String productName) throws SQLException {
        DatabaseMetaData metaData = mock(DatabaseMetaData.class);
        when(metaData.getDatabaseProductName()).thenReturn(productName);

        Connection connection = mock(Connection.class);
        when(connection.getMetaData()).thenReturn(metaData);

        return connection;
    }

    private JdbcOperations givenJdbcOperationsExecutesCallbackWithMock(Connection connection) {
        JdbcOperations operations = mock(JdbcOperations.class);
        doAnswer(invocationOnMock -> {
            ConnectionCallback<Dialect> callback = invocationOnMock.getArgument(0);
            return callback.doInConnection(connection);
        }).when(operations).execute(ArgumentMatchers.<ConnectionCallback<Dialect>>any());
        return operations;
    }

}
