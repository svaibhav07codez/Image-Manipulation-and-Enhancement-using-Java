package operations;

import model.ImageModel;

/**
 * Represents an image operation where the image is resized according to the width and height.
 */
public class ResizeOperation extends AbstractImageOperation {

  private float width;
  private float height;

  /**
   * Constructs a ResizeOperation with the specified source and destination image names.
   *
   * @param imageName the name of the source image to be processed.
   * @param destImage the name of the resulting image after applying the operation.
   * @param width     the width of the image.
   * @param height    the height of the image.
   */
  public ResizeOperation(String imageName, String destImage, String width, String height) {
    super(imageName, destImage);
    this.width = Float.parseFloat(width);
    this.height = Float.parseFloat(height);
  }

  /**
   * Executes the resize operation from the source image and stores the result in the model.
   *
   * @param model the image model containing the image data.
   */
  @Override
  public void performOperation(ImageModel model) {
    model.resizeImage(imageName, destImageName, width, height);
  }
}
