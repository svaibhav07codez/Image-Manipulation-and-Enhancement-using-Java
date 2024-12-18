package operations;

import model.ImageModel;

/**
 * Represents an image operation that extracts the blue component from an image.
 * This operation processes an image stored in the model and creates a new image.
 * Only the blue component is preserved, and stores the result with a new name.
 */
public class BlueComponentOperation extends AbstractImageOperation {

  /**
   * Constructs a BlueComponentOperation with the specified source and destination image names.
   *
   * @param imageName     the name of the source image to be processed for the blue component.
   * @param destImageName the name of the resulting image after extracting the blue component.
   */
  public BlueComponentOperation(String imageName, String destImageName) {
    super(imageName, destImageName);
  }

  /**
   * Extracts the blue component from the source image and stores the result in the model.
   * This method overrides the abstract performOperation method from AbstractImageOperation.
   * It applies the blueComponent operation on the given mvc.ImageModel.
   *
   * @param model the ImageModel that contains the image data and stores the processed image.
   */
  @Override
  public void performOperation(ImageModel model) {
    model.blueComponent(imageName, destImageName);
  }
}
