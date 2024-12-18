package operations;

import model.ImageModel;

/**
 * Represents an operation to split RGB image into its individual red, green, and blue components.
 * This class extends {@link AbstractImageOperation} and implements the logic.
 */
public class RGBSplitOperation extends AbstractImageOperation {

  private String redDest;
  private String greenDest;
  private String blueDest;

  /**
   * Constructs an RGBSplitOperation with the specified source image and destination names.
   *
   * @param imageName the name of the RGB image to be split.
   * @param redDest   the name of the destination image where the red component will be stored.
   * @param greenDest the name of the destination image where the green component will be stored.
   * @param blueDest  the name of the destination image where the blue component will be stored.
   */
  public RGBSplitOperation(String imageName, String redDest, String greenDest, String blueDest) {
    super(imageName, null);
    this.redDest = redDest;
    this.greenDest = greenDest;
    this.blueDest = blueDest;
  }

  /**
   * Executes the operation to split the RGB image into its red, green, and blue component images.
   * This method calls the corresponding method in the ImageModel to perform actual RGB splitting.
   *
   * @param model the mvc.ImageModel instance used to access image processing methods.
   */
  @Override
  public void performOperation(ImageModel model) {
    model.rgbSplit(imageName, redDest, greenDest, blueDest);
  }
}
