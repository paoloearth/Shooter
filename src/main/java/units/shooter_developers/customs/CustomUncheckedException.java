package units.shooter_developers.customs;

public class CustomUncheckedException{
    public static class FileUrlException extends RuntimeException {
        public FileUrlException(String msg) {
            super(msg);
        }
    }
    public static class EmptyFileException extends RuntimeException{
        public EmptyFileException(String msg) { super(msg); }
    }
    public static class MapFileFormatException extends RuntimeException{
        public MapFileFormatException(String msg) { super(msg); }
    }
}
