package units.shooter_developers.customs;

public class CustomUncheckedException{
    public static class FileUrlException extends RuntimeException {
        public FileUrlException(String msg) {
            super(msg);
        }
    }
    public static class FileIsEmptyException extends RuntimeException{
        public FileIsEmptyException(String msg) {
            super(msg);
        }

    }
}
