package units.shooter_developers;

public class CustomException{

    public static class FileManageException extends ExceptionModel{
        FileManageException(String file_name){
            super("ERROR: There was a problem by using the file: " + file_name);
        }
    }

    public static class NegativeNumberException extends ExceptionModel{
        NegativeNumberException(String msg){
            super(msg);
        }

    }

    public static class MyException extends ExceptionModel{
        MyException(String msg){super(msg);}
    }

    public static class MenuComponentNotFound extends ExceptionModel{
        MenuComponentNotFound(String component_descriptor){
            super("ERROR: The following menu component was not found: " + component_descriptor);
        }

    }

}
