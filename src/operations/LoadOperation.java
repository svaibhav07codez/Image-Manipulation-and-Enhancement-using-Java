package operations;

import model.ImageModel;

/**
 * Class for loading an image from a specified file into an image model.
 * This operation reads the image data from a file and stores it in the image model.
 */
public class LoadOperation extends AbstractImageOperation {

  int[][][] image;

  /**
   * Constructs a LoadOperation instance with the specified source and destination image names.
   *
   * @param image         the 3D int image to be loaded into the model.
   * @param destImageName the name under which the loaded image will be stored.
   */
  public LoadOperation(int[][][] image, String destImageName) {
    super(null, destImageName);
    this.image = image;
  }

  /**
   * Performs the load operation on the given image model.
   * This method attempts to load an image from the specified file path.
   * It then stores it in the image model. If an error occurs during the loading process.
   *
   * @param model the image model that will be updated with the loaded image.
   */
  @Override
  public void performOperation(ImageModel model) {
    try {
      model.loadImage(image, destImageName);
    } catch (Exception e) {
      System.out.println("Give a valid Input!");
    }
  }
}
