import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import model.ImageModel;
import model.ImageModelImpl;
import operations.AbstractImageOperation;
import operations.RGBCombineOperation;

import static org.junit.Assert.assertArrayEquals;

/**
 * This file tests the RGB Combine operation.
 */
public class RGBCombineOperationTest {

  private ImageModel model;

  @Before
  public void setUp() {
    model = new ImageModelImpl();
  }

  @Test
  public void testRGBCombineOnSmallImage() throws IOException {
    ImageModel model = new ImageModelImpl();

    int[][][] expectedRedData = {{{255, 255, 255}, {0, 0, 0}}, {{0, 0, 0}, {255, 255, 255}}};

    int[][][] expectedGreenData = {{{0, 0, 0}, {255, 255, 255}}, {{0, 0, 0}, {255, 255, 255}}};

    int[][][] expectedBlueData = {{{0, 0, 0}, {0, 0, 0}}, {{255, 255, 255}, {0, 0, 0}}};

    model.saveImage("redDest.png", expectedRedData);
    model.saveImage("greenDest.png", expectedGreenData);
    model.saveImage("blueDest.png", expectedBlueData);

    AbstractImageOperation operation = new RGBCombineOperation("imageDestName",
            "redDest.png", "greenDest.png", "blueDest.png");
    operation.performOperation(model);

    // Retrieve the split images
    int[][][] imageData = {
            {{255, 0, 0}, {0, 255, 0}},   // Red and Green
            {{0, 0, 255}, {255, 255, 0}}  // Blue and Yellow (Red + Green)
    };

    int[][][] res = model.getImage("imageDestName");
    // Validate the results
    assertArrayEquals(imageData, res);
  }

  @Test
  public void testRGBCombineOnSinglePixelImage() throws IOException {
    int[][][] expectedRed = {{{0, 0, 0}}};   // Red channel
    int[][][] expectedGreen = {{{0, 0, 0}}}; // Green channel
    int[][][] expectedBlue = {{{0, 0, 0}}};  // Blue channel

    model.saveImage("redDest.png", expectedRed);
    model.saveImage("greenDest.png", expectedGreen);
    model.saveImage("blueDest.png", expectedBlue);

    AbstractImageOperation operation = new RGBCombineOperation("imageDestName",
            "redDest.png", "greenDest.png", "blueDest.png");
    operation.performOperation(model);

    // Expected split result for a black pixel

    int[][][] imageData = {
            {{0, 0, 0}}  // Black (R=0, G=0, B=0)
    };

    // Get the split images
    int[][][] resImage = model.getImage("imageDestName");

    assertArrayEquals(imageData, resImage);

  }

  @Test
  public void testOnePixelImage() throws IOException {
    // Arrange
    ImageModel model = new ImageModelImpl();

    int[][][] expectedRedSplit = {
            {{1, 1, 1}}  // Should change to RGBCombine value
    };
    int[][][] expectedGreenSplit = {
            {{1, 1, 1}} // Should change to RGBCombine value
    };
    int[][][] expectedBlueSplit = {
            {{1, 1, 1}}  // Should change to RGBCombine value
    };

    model.saveImage("redDest.png", expectedRedSplit);
    model.saveImage("greenDest.png", expectedGreenSplit);
    model.saveImage("blueDest.png", expectedBlueSplit);

    AbstractImageOperation operation = new RGBCombineOperation("imageDestName",
            "redDest.png", "greenDest.png", "blueDest.png");
    operation.performOperation(model);

    int[][][] imageData = {
            {{1, 1, 1}}
    };

    int[][][] redSplitImage = model.getImage("imageDestName");

    assertArrayEquals(imageData, redSplitImage);
  }
}
