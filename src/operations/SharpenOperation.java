package operations;

import model.ImageModel;

/**
 * Represents an operation to sharpen an image.
 * This class extends {@link AbstractImageOperation} and implements the logic.
 * It enhances the edges and details of the specified image.
 */
public class SharpenOperation extends AbstractImageOperation {

  /**
   * Constructs a operations.SharpenOperation with the specified source and destination image names.
   *
   * @param imageName     the name of the image to be sharpened.
   * @param destImageName the name of the file where the sharpened image will be saved.
   */
  public SharpenOperation(String imageName, String destImageName) {
    super(imageName, destImageName);
  }

  /**
   * Executes the operation to sharpen the specified image.
   * This method calls the corresponding method in the ImageModel to perform sharpening operation.
   *
   * @param model the mvc.ImageModel instance used to access image processing methods.
   */
  @Override
  public void performOperation(ImageModel model) {
    model.sharpen(imageName, destImageName);
  }
}
