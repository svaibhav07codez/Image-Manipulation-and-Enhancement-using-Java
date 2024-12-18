package operations;

import model.ImageModel;

/**
 * Represents an image operation that extracts the green component from an image.
 * This operation processes the specified image to isolate the green color channel.
 * It also stores the resulting image with a new name.
 */
public class GreenComponentOperation extends AbstractImageOperation {

  /**
   * Constructs a GreenComponentOperation with the specified source destination image name.
   *
   * @param imageName     the name of source image from which the green component will be extracted.
   * @param destImageName the name of resulting image that will contain only the green component.
   */
  public GreenComponentOperation(String imageName, String destImageName) {
    super(imageName, destImageName);
  }

  /**
   * Extracts the green component from the source image and stores the result in the model.
   * This method overrides the abstract performOperation method from AbstractImageOperation.
   * It calls the greenComponent method on the given ImageModel to apply the extraction operation.
   *
   * @param model the mvc.ImageModel that contains the image data and stores the processed image.
   */
  @Override
  public void performOperation(ImageModel model) {
    model.greenComponent(imageName, destImageName);
  }
}
