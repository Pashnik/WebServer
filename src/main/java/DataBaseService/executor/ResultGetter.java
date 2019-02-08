package DataBaseService.executor;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultGetter<T> {
    T getResult(ResultSet resultSet) throws SQLException;
}
