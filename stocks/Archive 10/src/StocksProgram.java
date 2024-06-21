import java.io.InputStreamReader;

import controller.ControllerInterface;
import model.ModelImp;
import model.Model;
import controller.Controller;
import view.ViewImp;
import view.View;

/**
 * How the output will work.
 */
public class StocksProgram {
  /**
   * The main method.
   * @param args The arguments provided by the user.
   */
  public static void main(String[] args) {
    Model model = new ModelImp();
    View view = new ViewImp(System.out);
    Readable rd = new InputStreamReader(System.in);

    ControllerInterface controller = new Controller(model, view, rd);
    controller.control();
  }
}
