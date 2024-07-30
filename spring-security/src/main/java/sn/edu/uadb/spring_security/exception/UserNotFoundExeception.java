package sn.edu.uadb.spring_security.exception;

public class UserNotFoundExeception extends RuntimeException {
    public UserNotFoundExeception(String message){
        super(message);
    }
}
