package database.JdbcDbService.executor;

import database.JdbcDbService.NoDataToGetException;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultGetter<T> {
    T getResult(ResultSet resultSet) throws SQLException, NoDataToGetException;
}
