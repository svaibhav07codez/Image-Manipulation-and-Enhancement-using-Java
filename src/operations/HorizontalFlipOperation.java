package operations;

import model.ImageModel;

/**
 * Represents an image operation that performs a horizontal flip on an image.
 * This operation processes the specified image by flipping it horizontally.
 * It also stores the resulting image with a new name.
 */
public class HorizontalFlipOperation extends AbstractImageOperation {

  /**
   * Constructs a HorizontalFlipOperation with the specified source and destination image name.
   *
   * @param imageName     the name of the source image that will be flipped horizontally.
   * @param destImageName the name of the resulting image that will contain the flipped image.
   */
  public HorizontalFlipOperation(String imageName, String destImageName) {
    super(imageName, destImageName);
  }

  /**
   * Performs the horizontal flip operation on the source image and stores the result in the model.
   * This method overrides the abstract performOperation method from AbstractImageOperation.
   * It calls the flipHorizontal method on the given mvc.ImageModel to apply the flip operation.
   *
   * @param model the mvc.ImageModel that contains the image data and stores the processed image.
   */
  @Override
  public void performOperation(ImageModel model) {
    model.flipHorizontal(imageName, destImageName);
  }
}
