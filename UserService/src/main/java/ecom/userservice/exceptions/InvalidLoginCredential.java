package ecom.userservice.exceptions;

public class InvalidLoginCredential extends Exception{
    public InvalidLoginCredential(String message){
        super(message);
    }
}
