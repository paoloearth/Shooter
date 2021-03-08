package units.shooter_developers;

public abstract class ExceptionModel extends Exception{
    private final String message;
    public ExceptionModel(String errMessage){
        this.message = errMessage;
    }
    @Override
    public String getMessage() {
        return message;
    }

}
