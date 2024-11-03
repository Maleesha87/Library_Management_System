package mvc;

public class RegisterModel {
    private String name;
    private String username;
    private String email;
    private String password;

    public void setname(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean validate() {
        return validateEmail() && validatePassword();
    }

    private boolean validateEmail() {
        return email != null && email.matches("^[\\w-\\.]+@gmail\\.com$");
    }

    private boolean validatePassword() {
        return password != null && password.length() >= 6;
    }
}
