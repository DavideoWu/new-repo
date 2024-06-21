import java.awt.*;
import java.io.InputStreamReader;
import java.io.StringReader;

import javax.swing.*;

import controller.Controller;
import controller.Controller2;
import controller.ControllerInterface;
import model.Model;
import model.ModelImp;
import view.JFrameView;
import view.View;
import view.ViewImp;
import view.ViewMockImp;

public class GUI {

  private JButton calculateButton;


  public static void main(String[] args) {
    Model model = new ModelImp();
    //View view = new ViewImp(System.out);
    JFrameView jFrameView = new JFrameView("Stock/Portfolio info");
    //Readable rd = new StringReader("create-portfolio");
    //Readable rd = null;
    Readable rd = new InputStreamReader(System.in);


    Controller2 controller2 = new Controller2(model, jFrameView);
  }
}
