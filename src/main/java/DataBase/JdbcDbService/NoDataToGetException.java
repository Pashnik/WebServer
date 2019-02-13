package DataBase.JdbcDbService;

public class NoDataToGetException extends Exception {
    public NoDataToGetException(String message) {
        super(message);
    }
    public NoDataToGetException() {super();}
}
