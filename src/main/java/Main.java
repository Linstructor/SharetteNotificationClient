import controler.Controler;
import model.Model;
import view.View;

public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        View view = new View();
        Controler controler = new Controler(model, view);
    }
}