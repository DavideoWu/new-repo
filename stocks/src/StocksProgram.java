import java.io.InputStreamReader;

import Controller.ControllerInterface;
import Model.ModelImp;
import Model.Model;
import Controller.Controller;
import View.ViewImp;
import View.View;

public class StocksProgram {
  public static void main(String []args) {
    Model model = new ModelImp();
    View view = new ViewImp(System.out);
    Readable rd = new InputStreamReader(System.in);

    ControllerInterface controller = new Controller(model, view, rd);
    controller.go();
  }
}
