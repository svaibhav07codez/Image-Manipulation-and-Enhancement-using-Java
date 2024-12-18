package operations;

import model.ImageModel;

/**
 * An interface for image operations that can be executed on an mvc.ImageModel.
 * Implementing classes should define specific image transformations or manipulations.
 */
public interface ImageOperation {

  /**
   * This method calls the implementation in the model.
   *
   * @param model the image model containing the image data.
   */
  void performOperation(ImageModel model);

  /**
   * Executes the image operation on the given model.
   * This method is called to perform the operation and process the image data.
   *
   * @param model the image model containing the image data.
   * @param args  additional arguments required for the operation.
   */
  void execute(ImageModel model, String[] args);
}