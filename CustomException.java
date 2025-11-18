class InvalidAgeException extends Exception{
    public InvalidAgeException(String message){
        super(message);
    }
}

public class CustomException {
    public static void checkAge(int age) throws InvalidAgeException{
        if (age < 18) {
            throw new InvalidAgeException("Age must be 18 or older");
        }else{
            System.out.println("Age is valid  " + age );
        }
    }
    public static void main(String[] args) {
        try{
            checkAge(25);
            checkAge(15);
        }catch(InvalidAgeException e){
            System.out.println("invalid age: " + e.getMessage());
        }
        }
}