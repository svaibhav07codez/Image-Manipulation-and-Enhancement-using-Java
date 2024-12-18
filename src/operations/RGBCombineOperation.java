package operations;

import model.ImageModel;

/**
 * Represents an operation to combine red, green, and blue components into a single RGB image.
 * This class extends {@link AbstractImageOperation}.
 * It implements the logic to combine the specified color component images into a destination image.
 */
public class RGBCombineOperation extends AbstractImageOperation {

  private String redImage;
  private String greenImage;
  private String blueImage;

  /**
   * Constructs an RGBCombineOperation with the specified component images and destination name.
   *
   * @param destImageName the name of the destination image where the combined RGB image.
   * @param redImage      the name of the image containing the red component.
   * @param greenImage    the name of the image containing the green component.
   * @param blueImage     the name of the image containing the blue component.
   */
  public RGBCombineOperation(String destImageName, String redImage, String greenImage,
                             String blueImage) {
    super(null, destImageName);
    this.redImage = redImage;
    this.greenImage = greenImage;
    this.blueImage = blueImage;
  }

  /**
   * Executes the operation to combine the red, green, and blue component images into single image.
   * This method calls the corresponding method in the ImageModel to perform the RGB combination.
   *
   * @param model the mvc.ImageModel instance used to access image processing methods.
   */
  @Override
  public void performOperation(ImageModel model) {
    model.rgbCombine(destImageName, redImage, greenImage, blueImage);
  }
}
