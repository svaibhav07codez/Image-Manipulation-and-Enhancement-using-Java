package operations;

import model.ImageModel;

/**
 * Class for extracting the luma component from an image.
 * This operation takes an image and generates a new image.
 * It contains only the luma component.
 */
public class LumaComponentOperation extends AbstractImageOperation {

  /**
   * Constructs a LumaComponentOperation instance with the specified source and destination name.
   *
   * @param imageName     the name of the image from which to extract the luma component.
   * @param destImageName the name under which the luma component image will be stored.
   */
  public LumaComponentOperation(String imageName, String destImageName) {
    super(imageName, destImageName);
  }

  /**
   * Performs the luma component extraction operation on the given image model.
   * This method calls the appropriate method in the image model to extract the luma component.
   *
   * @param model the image model that will be used to perform the operation.
   */
  @Override
  public void performOperation(ImageModel model) {
    model.lumaComponent(imageName, destImageName);
  }
}
