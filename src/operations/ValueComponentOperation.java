package operations;

import model.ImageModel;

/**
 * Represents an operation to extract the value component from an image.
 * This class extends {@link AbstractImageOperation} and is responsible processing the image.
 * It isolates and save its value component to a destination file.
 */
public class ValueComponentOperation extends AbstractImageOperation {

  /**
   * Constructs a ValueComponentOperation with the specified source and destination image names.
   *
   * @param imageName     the name of the image from which the value component will be extracted.
   * @param destImageName the name of the file where extracted value component image will be saved.
   */
  public ValueComponentOperation(String imageName, String destImageName) {
    super(imageName, destImageName);
  }

  /**
   * Executes the operation to extract the value component from the specified image.
   * This method calls the corresponding method in the mvc.ImageModel to perform the extraction.
   *
   * @param model the mvc.ImageModel instance used to access image processing methods.
   */
  @Override
  public void performOperation(ImageModel model) {
    model.valueComponent(imageName, destImageName);
  }
}
