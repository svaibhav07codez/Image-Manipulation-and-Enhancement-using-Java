package operations;

import model.ImageModel;

/**
 * Represents an image operation that increases the brightness of an image.
 * This operation modifies the pixel values of the specified image by a given increment,
 * resulting in a brighter version of the image, which is then stored with a new name.
 */
public class BrightenOperation extends AbstractImageOperation {
  private int increment;

  /**
   * Constructs operations.BrightenOperation with specified brightness increment.
   * It also creates the source image name and destination image name.
   *
   * @param increment     the amount by which to increase the brightness of the image.
   * @param imageName     the name of the source image to be brightened.
   * @param destImageName the name of the resulting image after applying the brightness increase.
   */
  public BrightenOperation(int increment, String imageName, String destImageName) {
    super(imageName, destImageName);
    this.increment = increment;
  }

  /**
   * Increases the brightness of the source image by the specified increment and stores the result.
   * This method overrides the abstract performOperation method from AbstractImageOperation.
   * It calls the brighten method on the given mvc.ImageModel to apply the brightness operation.
   *
   * @param model the mvc.ImageModel that contains the image data and stores the processed image.
   */
  @Override
  public void performOperation(ImageModel model) {
    model.brighten(increment, imageName, destImageName);
  }
}
