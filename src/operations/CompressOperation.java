package operations;

import model.ImageModel;

/**
 * Represents an image operation that compresses an image.
 * This operation modifies the pixel values of the specified image by a given percentage,
 * resulting in a compressed version of the image (in terms of size), which is then stored
 * with a new name.
 */
public class CompressOperation extends AbstractImageOperation {
  private final float percentage;

  /**
   * Constructs operations.CompressOperation with specified compression percentage.
   * It also creates the source image name and destination image name.
   *
   * @param percentage    the amount by which to compress the image.
   * @param imageName     the name of the source image to be brightened.
   * @param destImageName the name of the resulting image after applying the brightness increase.
   */
  public CompressOperation(float percentage, String imageName, String destImageName) {
    super(imageName, destImageName);
    this.percentage = percentage;
  }

  /**
   * Compresses the source image by the specified percentage and stores the result.
   * This method overrides the abstract performOperation method from AbstractImageOperation.
   * It calls the compress method on the given mvc.ImageModel to apply the brightness operation.
   *
   * @param model the mvc.ImageModel that contains the image data and stores the processed image.
   */
  @Override
  public void performOperation(ImageModel model) {
    model.compress(percentage, imageName, destImageName);
  }
}

