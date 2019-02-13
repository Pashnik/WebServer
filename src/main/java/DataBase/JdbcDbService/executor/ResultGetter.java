package DataBase.JdbcDbService.executor;

import DataBase.JdbcDbService.NoDataToGetException;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultGetter<T> {
    T getResult(ResultSet resultSet) throws SQLException, NoDataToGetException;
}
