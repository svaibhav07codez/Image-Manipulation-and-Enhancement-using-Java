package operations;

import model.ImageModel;

/**
 * Represents an operation to apply a sepia tone effect to an image.
 * This class extends {@link AbstractImageOperation} and implements the logic.
 * It transforms the specified image into a sepia-toned version.
 */
public class SepiaOperation extends AbstractImageOperation {

  /**
   * Constructs a operations.SepiaOperation with the specified source and destination image names.
   *
   * @param imageName     the name of the image to be transformed into sepia tone.
   * @param destImageName the name of the file where the sepia-toned image will be saved.
   */
  public SepiaOperation(String imageName, String destImageName) {
    super(imageName, destImageName);
  }

  /**
   * Executes the operation to apply the sepia effect to the specified image.
   * This method calls the corresponding method in the ImageModel to perform the sepia operation.
   *
   * @param model the mvc.ImageModel instance used to access image processing methods.
   */
  @Override
  public void performOperation(ImageModel model) {
    model.sepia(imageName, destImageName);
  }
}
