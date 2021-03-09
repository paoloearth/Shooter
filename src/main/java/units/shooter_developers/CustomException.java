package units.shooter_developers;

public class CustomException{

    public static class IncorrectFileName extends ExceptionModel{
        IncorrectFileName(String msg){
            super(msg);
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

}
