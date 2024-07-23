public class Main {
    public static void main(String[] args) {
        Register register= new Register(null);
        User  user = register.user;
        if(user != null){
            System.out.println("Successful registration of: " + user.getName());
        } else{
            System.out.println("Registration canceled");
        }
    }
}