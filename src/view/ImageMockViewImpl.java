package view;

import java.awt.image.BufferedImage;
import java.util.Scanner;

/**
 * This mock view is used to test the methods. It's not intended for actual image manipulation.
 */
public class ImageMockViewImpl implements ImageView {
  private Scanner scanner;

  /**
   * Constructs an instance and initializes the scanner for reading user input from console.
   */
  public ImageMockViewImpl() {
    scanner = new Scanner(System.in);
  }

  /**
   * Single Parameter constructor to accept other forms of input.
   *
   * @param scanner to accept any input.
   */
  public ImageMockViewImpl(Scanner scanner) {
    this.scanner = scanner;
  }

  /**
   * Prompts the user for input and retrieves the input as a trimmed string.
   * This method displays a prompt to the user and waits for their response.
   *
   * @return the trimmed input string provided by the user.
   */
  @Override
  public String getUserInput() {
    System.out.print("Type here: ");
    if (scanner.hasNextLine()) {
      return scanner.nextLine().trim();
    } else {
      return scanner.nextLine().trim();
    }
  }

  /**
   * Displays a message to the user in the console.
   * This method communicates information, such as operation results error messages, to the user.
   *
   * @param message the message to be displayed to the user.
   */
  @Override
  public void showMessage(String message) {
    System.out.println(message);
  }

  /**
   * This method mocks the displayImage method in ImageViewImpl.java.
   *
   * @param image     The BufferedImage to display.
   * @param histogram The histogram of the image to be displayed.
   */
  @Override
  public void displayImage(BufferedImage image, BufferedImage histogram) {
    System.out.println("Image and Histogram as displayed");
  }
}
