package operations;

import model.ImageModel;

/**
 * Represents an operation to extract the red component from an image.
 */
public class RedComponentOperation extends AbstractImageOperation {

  /**
   * Constructs a operations.RedComponentOperation with the specified image names.
   *
   * @param imageName     the name of the source image from which the red component is extracted.
   * @param destImageName the name of the destination image where the red component will be stored.
   */
  public RedComponentOperation(String imageName, String destImageName) {
    super(imageName, destImageName);
  }

  /**
   * Executes the operation to extract the red component from the source image.
   * This method calls the corresponding method in the ImageModel to perform the actual extraction.
   *
   * @param model the mvc.ImageModel instance used to access image processing methods.
   */
  @Override
  public void performOperation(ImageModel model) {
    model.redComponent(imageName, destImageName);
  }
}
