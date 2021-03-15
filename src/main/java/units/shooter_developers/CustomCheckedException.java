package units.shooter_developers;

import java.lang.reflect.Type;

public class CustomCheckedException {

    public static class FileManagementException extends Exception {
        public FileManagementException(String file_name){
            super("ERROR: There was a problem by using the file: " + file_name);
        }
    }

    public static class MissingMenuComponentException extends Exception {
        public MissingMenuComponentException(String component_descriptor, Type component_type){
            super("ERROR: The following menu component of type " + component_type + " was not found: " + component_descriptor);
        }

    }

    public static class WrongParsingException extends Exception {
        public WrongParsingException(String string_to_parse_from, Type destination_type){
            super("ERROR: Parsing of string \"" + string_to_parse_from + "\" to type " + destination_type + " was wrong.");
        }

    }

}
