package units.shooter_developers;

import java.lang.reflect.Type;

public class CustomException{

    public static class FileManagementException extends ExceptionModel{
        public FileManagementException(String file_name){
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

    public static class MissingMenuComponentException extends ExceptionModel{
        public MissingMenuComponentException(String component_descriptor, Type component_type){
            super("ERROR: The following menu component of type " + component_type + " was not found: " + component_descriptor);
        }

    }

    public static class WrongParsingException extends ExceptionModel{
        public WrongParsingException(String string_to_parse_from, Type destination_type){
            super("ERROR: Parsing of string \"" + string_to_parse_from + "\" to type " + destination_type + " was wrong.");
        }

    }

}
