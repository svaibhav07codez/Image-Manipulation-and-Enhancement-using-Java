package view;

import java.awt.image.BufferedImage;

/**
 * Interface for the image view, defining methods for user interaction.
 * This interface represents the communication between the system and the user.
 * This can be an image manipulation application, providing methods to display messages.
 */
public interface ImageView {

  /**
   * Retrieves user input as a string.
   * This method is intended to capture any input provided by the user,
   * such as file paths, commands, or parameters for image manipulation.
   *
   * @return the input string provided by the user.
   */
  String getUserInput() ;

  /**
   * Displays a message to the user.
   * This method is used to inform the user about the status of operations or errors.
   *
   * @param message the message to be displayed to the user.
   */
  void showMessage(String message);

  /**
   * Displays the image on the right side of the window.
   *
   * @param image The BufferedImage to display.
   */
  void displayImage(BufferedImage image, BufferedImage histogram);
}
