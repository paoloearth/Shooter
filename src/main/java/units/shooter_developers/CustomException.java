package units.shooter_developers;

public class CustomException{

    public static class IncorrectFileName extends ExceptionModel{
        IncorrectFileName(String msg){
            super(msg);
        }
    }

    public static class NegativeNumber extends ExceptionModel{
        NegativeNumber(String msg){super(msg);}
    }

    public static class MyException extends ExceptionModel{
        MyException(String msg){super(msg);}
    }

}
