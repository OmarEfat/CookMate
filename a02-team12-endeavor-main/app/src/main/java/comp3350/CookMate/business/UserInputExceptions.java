package comp3350.CookMate.business;

public class UserInputExceptions extends IllegalArgumentException {

    //register input
    //when user id, user name, password are empty
    public static class UserInfoIsEmptyException extends IllegalArgumentException {
        public UserInfoIsEmptyException() {
            super("User ID, User name, and password cannot be empty");
        }
    }

    //register input, login input
    //when user id and password contain space
    public static class UserIdPwdContainSpaceException extends IllegalArgumentException {
        public UserIdPwdContainSpaceException() {
            super("User ID and password cannot contain space");
        }
    }

    //register input, update input
    //when password is less than 6 digit
    public static class PwdLengthLessThanSixException extends IllegalArgumentException {
        public PwdLengthLessThanSixException() {
            super("Password cannot less than 6 digit");
        }
    }

    //login input
    // when user id and pwd are empty
    public static class UserLoginInfoEmptyException extends IllegalArgumentException {
        public UserLoginInfoEmptyException() {
            super("User ID and password cannot be empty");
        }
    }

    //update input
    //when user didn't make any change
    public static class NothingToUpdateException extends IllegalArgumentException {
        public NothingToUpdateException() {
            super("You didn't update anything!");
        }
    }

    //update input
    //when user include space in the new password
    public static class PwdContainSpaceException extends IllegalArgumentException {
        public PwdContainSpaceException() {
            super("Password cannot contain space");
        }
    }

    public static class RecipeDuplicate extends IllegalArgumentException{
        public RecipeDuplicate(){super("Recipe already exists!, Please remove the existing one to create a new one!");}
    }


}
