package mvc;

public class Main {
    public static void main(String[] args) {
        
        RegisterModel model = new RegisterModel();
        RegisterView view = new RegisterView();
        RegisterController controller = new RegisterController(model, view);

        
        view.setVisible(true);
    }
}
