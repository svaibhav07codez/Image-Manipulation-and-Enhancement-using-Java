package operations;

import model.ImageModel;

/**
 * Represents an operation to flip an image vertically.
 * This class extends {@link AbstractImageOperation} and is responsible for processing the image.
 * It creates a vertically flipped version and saves it to a destination file.
 */
public class VerticalFlipOperation extends AbstractImageOperation {

  /**
   * Constructs a VerticalFlipOperation with the specified source and destination image names.
   *
   * @param imageName     the name of the image to be flipped vertically.
   * @param destImageName the name of the file where the vertically flipped image will be saved.
   */
  public VerticalFlipOperation(String imageName, String destImageName) {
    super(imageName, destImageName);
  }

  /**
   * Executes the operation to flip the specified image vertically.
   * This method calls the corresponding method in the mvc.ImageModel to perform the flip.
   *
   * @param model the mvc.ImageModel instance used to access image processing methods.
   */
  @Override
  public void performOperation(ImageModel model) {
    model.flipVertical(imageName, destImageName);
  }
}
