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
        public MissingMenuComponentException(String component_description, Type component_type){
            super("ERROR: The following menu component of type " + component_type + " was not found: " + component_description);
        }

    }

    public static class WrongParsingException extends ExceptionModel{
        public WrongParsingException(String string_to_parse_from, Type destination_type){
            super("ERROR: Parsing of string \"" + string_to_parse_from + "\" to type " + destination_type + " was wrong.");
        }

    }

    public static class IndexOutOfRange extends ExceptionModel{
        public IndexOutOfRange(String object_description, int max_index, int current_index, Type object_type){
            super("ERROR: Maximum index was [" + max_index + "] but [" + current_index + "] was provided in the following object of type "+ object_type +": " + object_description + ".");
        }

    }

}
