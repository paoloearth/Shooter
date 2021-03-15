package units.shooter_developers;

public class CustomUncheckedException{
    public static class FileUrlException extends RuntimeException {
        FileUrlException(String msg) {
            super(msg);
        }
    }
    public static class FileIsEmptyException extends RuntimeException{
        FileIsEmptyException(String msg) {
            super(msg);
        }

    }
}
