package units.shooter_developers;

public abstract class ExceptionModel extends Exception{
    private String message;
    public ExceptionModel(){};
    public ExceptionModel(String errMessage){
        this.message = errMessage;
    }
    @Override
    public String getMessage() {
        return message;
    }

}
