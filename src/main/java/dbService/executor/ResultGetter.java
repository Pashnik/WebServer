package dbService.executor;

import dbService.NoDataToGetException;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultGetter<T> {
    T getResult(ResultSet resultSet) throws SQLException, NoDataToGetException;
}
