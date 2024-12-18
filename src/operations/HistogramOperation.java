package operations;

import model.ImageModel;

/**
 * Represents an operation to produce an image that gives histogram for the given image.
 * This class extends {@link AbstractImageOperation} and implements the logic.
 * It produces the histogram of the given image.
 */
public class HistogramOperation extends AbstractImageOperation {

  /**
   * Constructs a HistogramOperation with the specified source and destination image names.
   *
   * @param imageName     the name of the image to get the histogram.
   * @param destImageName the name to assign the histogram.
   */
  public HistogramOperation(String imageName, String destImageName) {
    super(imageName, destImageName);
  }

  /**
   * Executes the operation to generate the histogram for the specified image.
   * This method calls the corresponding method in the ImageModel to perform histogram operation.
   *
   * @param model model the mvc.ImageModel instance used to access image processing methods.
   */
  @Override
  public void performOperation(ImageModel model) {
    model.histogram(imageName, destImageName);
  }
}
