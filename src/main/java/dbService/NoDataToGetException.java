package dbService;

public class NoDataToGetException extends Exception {
    public NoDataToGetException(String message) {
        super(message);
    }
    public NoDataToGetException() {super();}
}
