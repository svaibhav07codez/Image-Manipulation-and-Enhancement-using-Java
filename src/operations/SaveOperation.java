package operations;

import model.ImageModel;

/**
 * Represents an operation to save an image to a specified file.
 * This class extends {@link AbstractImageOperation} and implements the logic.
 * It saves the image identified by the source image name to the destination file name.
 */
public class SaveOperation extends AbstractImageOperation {

  String filePath;
  int[][][] imageName;

  /**
   * Constructs a operations.SaveOperation with the specified source and destination image names.
   *
   * @param imageName the name of the image to be saved.
   * @param filePath  the path of the file where the image will be saved.
   */
  public SaveOperation(int[][][] imageName, String filePath) {
    super(null, filePath);
    this.filePath = filePath;
    this.imageName = imageName;
  }

  /**
   * Executes the operation to save the image to the specified destination.
   * This method calls the corresponding method in the ImageModel to perform the saving operation.
   * If an exception occurs during the save operation, an error message is printed to the console.
   *
   * @param model the mvc.ImageModel instance used to access image processing methods.
   */
  @Override
  public void performOperation(ImageModel model) {
    try {
      model.saveImage(filePath, imageName);
    } catch (Exception e) {
      System.out.println("Give a valid Input!");
    }
  }
}
