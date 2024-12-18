package operations;

import model.ImageModel;

/**
 * Class for the intensity component operation on an image.
 * This operation extracts the intensity component from a specified image.
 * and stores it in a destination image.
 */
public class IntensityComponentOperation extends AbstractImageOperation {

  /**
   * Constructs an IntensityComponentOperation instance with the specified source and destination.
   *
   * @param imageName     the name of the source image from which the intensity
   *                      component will be extracted.
   * @param destImageName the name of the destination image where the
   *                      intensity component will be saved.
   */
  public IntensityComponentOperation(String imageName, String destImageName) {
    super(imageName, destImageName);
  }

  /**
   * Performs the intensity component operation on the given image model.
   * This method extracts the intensity component from the source image .
   * It then saves it to the destination image.
   *
   * @param model the image model that contains the image data and
   *              implements the operation logic.
   */
  @Override
  public void performOperation(ImageModel model) {
    model.intensityComponent(imageName, destImageName);
  }
}
