package operations;

import model.ImageModel;

/**
 * Represents an operation to adjust the color meaningfully in an image.
 * This class extends {@link AbstractImageOperation} and implements the logic.
 */
public class LevelsAdjustOperation extends AbstractImageOperation {
  private String black;
  private String mid;
  private String white;

  /**
   * Constructs a LevelsAdjustOperation with the specified black, midtone, and white level.
   *
   * @param imageName     the name of the source image to be adjusted.
   * @param destImageName the name of the destination image after level adjustment.
   * @param black         black the level adjustment for the darkest tones in the image.
   * @param mid           mid the level adjustment for the midtones in the image.
   * @param white         white the level adjustment for the lightest tones in the image.
   */
  public LevelsAdjustOperation(String black, String mid, String white,
                               String imageName, String destImageName) {
    super(imageName, destImageName);
    this.black = black;
    this.mid = mid;
    this.white = white;
  }

  /**
   * Executes the level adjustment operation on the specified image.
   * This method calls the corresponding level adjustment method in the mvc.ImageModel.
   *
   * @param model the mvc.ImageModel instance used to access image processing methods.
   */
  @Override
  public void performOperation(ImageModel model) {
    model.levelAdjust(imageName, destImageName, black, mid, white);
  }
}