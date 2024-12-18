package controller;

import java.io.IOException;

/**
 * Interface for controlling image operations within an image processing application.
 * This interface defines the contract for any class that implements image controller functionality.
 * It allows the execution of commands that manipulate or process images.
 */
public interface ImageController {

  /**
   * Executes image processing commands by interacting with the image model.
   * It performs operations like loading, manipulating, and saving images.
   *
   * @throws IOException if an input/output error occurs during command execution.
   */
  void execute() throws IOException;

  void runScript(String scriptFilePath) throws IOException;
}
