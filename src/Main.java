import java.util.Scanner;

import controller.ImageController;
import controller.ImageControllerImpl;
import model.ImageModel;
import model.ImageModelImpl;
import view.ImageView;
import view.ImageViewImpl;

/**
 * The Main class serves as the entry point for the image processing application.
 * It handles the initialization of the model, view, and controller components.
 * The application follows the MVC (Model-View-Controller) design pattern.
 * The model handles the logic and data manipulation.
 * The view is responsible for displaying the data.
 * The controller mediates between the model and view to process user commands.
 */
public class Main {

  /**
   * The main method that serves as the entry point of the application.
   * It initializes the components necessary for the application.
   * Then starts the image processing execution.
   *
   * @param args command-line arguments.
   */
  public static void main(String[] args) {
    try {
      if (args.length == 0) {
        // GUI Mode
        ImageView view = new ImageViewImpl();
        ImageModel model = new ImageModelImpl();
        ImageController controller = new ImageControllerImpl(model, view);
        controller.execute();

      } else if (args.length == 2 && "-file".equals(args[0])) {
        // Script Mode
        ImageView view = new ImageViewImpl(new Scanner(System.in));
        ImageModel model = new ImageModelImpl();
        ImageController controller = new ImageControllerImpl(model, view);
        controller.runScript(args[1]);
      } else if (args.length == 1 && "-text".equals(args[0])) {
        // Interactive Text Mode
        ImageView view = new ImageViewImpl(new Scanner(System.in));
        ImageModel model = new ImageModelImpl();
        ImageController controller = new ImageControllerImpl(model, view);
        controller.execute();
      } else {
        System.err.println("Invalid arguments. Usage:");
        System.err.println("java -jar Program.jar");
        System.err.println("java -jar Program.jar -file <script-file>");
        System.err.println("java -jar Program.jar -text");
      }
    } catch (Exception e) {
      System.err.println("An error occurred: " + e.getMessage());
    }
  }
}