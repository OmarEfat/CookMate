package comp3350.CookMate.persistence.HSQLDB;

public class AccountManagementException extends RuntimeException {
    public AccountManagementException(final Exception cause) {
        super(cause);
    }

    public AccountManagementException() {

    }
}

