package operations;

import model.ImageModel;

/**
 * Represents an operation to correct the color meaningfully in an image.
 * This class extends {@link AbstractImageOperation} and implements the logic.
 * It transforms the specified image into a color corrected version.
 */
public class ColorCorrectOperation extends AbstractImageOperation {

  /**
   * Constructs a ColorCorrectOperation with the specified source and destination image names.
   *
   * @param imageName     the name of the image to be transformed into color corrected version.
   * @param destImageName the name of the file where the color corrected image will be saved.
   */
  public ColorCorrectOperation(String imageName, String destImageName) {
    super(imageName, destImageName);
  }

  /**
   * Executes the operation to correct the color meaningfully in the specified image.
   * This method calls the corresponding method in mvc.ImageModel to perform the
   * color-correct operation.
   *
   * @param model the mvc.ImageModel instance used to access image processing methods.
   */
  @Override
  public void performOperation(ImageModel model) {
    model.colorCorrect(imageName, destImageName);
  }
}
