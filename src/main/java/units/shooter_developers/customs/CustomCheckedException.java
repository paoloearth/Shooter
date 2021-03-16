package units.shooter_developers.customs;

import java.lang.reflect.Type;

public class CustomCheckedException {

    public static class FileManagementException extends Exception {
        public FileManagementException(String file_name){
            super("ERROR: There was a problem by using the file: " + file_name + ".");
        }
    }

    public static class MissingMenuComponentException extends Exception{
        public MissingMenuComponentException(String component_description, Type component_type){
            super("ERROR: The following menu component of type " + component_type + " was not found: " + component_description);
        }

    }

    public static class WrongParsingException extends Exception {
        public WrongParsingException(String string_to_parse_from, Type destination_type){
            super("ERROR: Parsing of string \"" + string_to_parse_from + "\" to type " + destination_type + " was wrong.");
        }

    }

    public static class IndexOutOfRangeException extends Exception{
        public IndexOutOfRangeException(String object_description, int max_index, int current_index, Type object_type){
            super("ERROR: Maximum index was [" + max_index + "] but [" + current_index + "] was provided in the following object of type "+ object_type +": " + object_description + ".");
        }

    }

}
