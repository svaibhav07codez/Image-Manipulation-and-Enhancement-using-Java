package operations;

import model.ImageModel;

/**
 * An abstract class representing a generic image operation.
 * Subclasses must implement the specific operation logic by overriding the performOperation method.
 * This class defines the structure for image operations that modify an image.
 *
 */
public abstract class AbstractImageOperation implements ImageOperation {
  protected String imageName;
  protected String destImageName;

  /**
   * Constructs an operations.AbstractImageOperation with the specified
   * source and destination image names.
   *
   * @param imageName     the name of the source image to be processed.
   * @param destImageName the name of the resulting image after applying the operation.
   */
  public AbstractImageOperation(String imageName, String destImageName) {
    this.imageName = imageName;
    this.destImageName = destImageName;
  }

  /**
   * Defines the specific operation to be performed on the image.
   * This method must be implemented by subclasses to specify how the operation is applied.
   *
   * @param model the image model containing the image data.
   */
  @Override
  public abstract void performOperation(ImageModel model);

  /**
   * Executes the image operation by calling the performOperation method.
   * This method is part of the operations.ImageOperation and
   * is used to trigger the processing of the image.
   *
   * @param model the image model containing the image data.
   * @param args  additional arguments required for the operation (unused in this implementation).
   */
  @Override
  public void execute(ImageModel model, String[] args) {
    performOperation(model);
  }
}