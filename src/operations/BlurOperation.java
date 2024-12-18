package operations;

import model.ImageModel;

/**
 * Represents an image operation that applies a blur effect to an image.
 * This operation processes an image stored in the model, applies a blur filter.
 * It stores the blurred image with a new name in the model.
 */
public class BlurOperation extends AbstractImageOperation {

  /**
   * Constructs a BlurOperation with the specified source and destination image names.
   *
   * @param imageName     the name of the source image to be blurred.
   * @param destImageName the name of the resulting image after applying the blur effect.
   */
  public BlurOperation(String imageName, String destImageName) {
    super(imageName, destImageName);
  }

  /**
   * Applies the blur effect to the source image and stores the result in the model.
   * This method overrides the abstract performOperation method from AbstractImageOperation.
   * It calls the blur method on the given ImageModel to apply the blur operation.
   *
   * @param model the ImageModel that contains the image data and stores the processed image.
   */
  @Override
  public void performOperation(ImageModel model) {
    model.blur(imageName, destImageName);
  }
}
