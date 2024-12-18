import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import model.ImageModel;
import model.ImageModelImpl;
import operations.AbstractImageOperation;
import operations.BlurOperation;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * This file tests the blur operation.
 */

public class BlurOperationTest {

  private ImageModel model;

  @Before
  public void setUp() {
    model = new ImageModelImpl();
  }

  @Test
  public void testBlurOnSmallImage() throws IOException {
    int[][][] imageData = {
            {{255, 0, 0}, {0, 255, 0}},
            {{0, 0, 255}, {255, 255, 0}}
    };

    String imageName = "small.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new BlurOperation(imageName, "blurredImage");
    operation.performOperation(model);

    int[][][] expectedData = {{{0, 0, 0}, {0, 0, 0}}, {{0, 0, 0}, {0, 0, 0}}};

    int[][][] blurredImage = model.getImage("blurredImage");
    assertArrayEquals(expectedData, blurredImage);
  }

  @Test
  public void testBlurOnLargeImage() throws IOException {
    int[][][] imageData = {
            {{255, 0, 0}, {0, 255, 0}, {0, 0, 255}},
            {{255, 255, 0}, {255, 0, 255}, {0, 255, 255}},
            {{128, 128, 128}, {64, 64, 64}, {192, 192, 192}}
    };
    String imageName = "largeImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new BlurOperation(imageName, "blurredLargeImage");
    operation.performOperation(model);

    int[][][] expectedData = {
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}};
    int[][][] blurredImage = model.getImage("blurredLargeImage");
    assertArrayEquals(expectedData, blurredImage);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testBlurOnNonExistentImage() {
    // Try to flip a non-existent image, should throw an exception
    AbstractImageOperation operation = new BlurOperation("nonExistentImage", "blurImage");
    operation.performOperation(model);
  }

  @Test
  public void testBlurOnSinglePixelImage() throws IOException {
    int[][][] imageData = {
            {{0, 0, 0}}  // Grey
    };
    String imageName = "singlePixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new BlurOperation(imageName, "blurImage");
    operation.performOperation(model);

    int[][][] blurredImage = model.getImage("blurImage");
    assertArrayEquals(imageData, blurredImage);
  }


  // Test normal image flip with 3D array (RGB image)
  @Test
  public void testNormalImageBlur() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
            {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
            {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}
    };
    String imageName = "NormalPixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new BlurOperation(imageName, "blurredNormalPixelImage");
    operation.performOperation(model);

    int[][][] expectedBlurred = {
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };

    assertArrayEquals(expectedBlurred, model.getImage("blurredNormalPixelImage"));
  }


  @Test
  public void testSingleRowImage() throws IOException {
    // Arrange
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {4, 4, 4}, {5, 5, 5}}
    };
    String imageName = "SingleRowPixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new BlurOperation(imageName, "blurredSingleRowPixelImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedBlurred = {
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}}  // Expected blurred output
    };
    assertArrayEquals(expectedBlurred, model.getImage("blurredSingleRowPixelImage"));
  }


  @Test
  public void testSingleColumnImage() throws IOException {
    // Arrange
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
            {{1, 1, 1}},
            {{2, 2, 2}},
            {{3, 3, 3}},
            {{4, 4, 4}},
            {{5, 5, 5}}
    };
    String imageName = "SingleColumnPixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new BlurOperation(imageName,
        "blurredSingleColumnPixelImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedBlurred = {
            {{0, 0, 0}},
            {{0, 0, 0}},
            {{0, 0, 0}},
            {{0, 0, 0}},
            {{0, 0, 0}}
    };
    assertArrayEquals(expectedBlurred, model.getImage("blurredSingleColumnPixelImage"));
  }


  // Test symmetric image blur (3D)
  @Test
  public void testSymmetricImage() throws IOException {
    // Arrange
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
            {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}}
    };
    String imageName = "SymmetricImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new BlurOperation(imageName, "blurredSymmetricImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedBlurred = {
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };
    assertArrayEquals(expectedBlurred, model.getImage("blurredSymmetricImage"));
  }


  /// Test non-symmetric image blur (3D)
  @Test
  public void testNonSymmetricImage() throws IOException {
    // Arrange
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
            {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
            {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}
    };
    String imageName = "NonSymmetricImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new BlurOperation(imageName, "blurredNonSymmetricImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedBlurred = {
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };
    assertArrayEquals(expectedBlurred, model.getImage("blurredNonSymmetricImage"));
  }

  @Test
  public void testOnePixelImage() throws IOException {
    // Arrange
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
            {{1, 1, 1}}
    };
    String imageName = "SinglePixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new BlurOperation(imageName, "blurredSinglePixelImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedBlurred = {
            {{0, 0, 0}}  // Should change to blurred value
    };
    assertArrayEquals(expectedBlurred, model.getImage("blurredSinglePixelImage"));
  }

  // Test even number of rows (3D)
  @Test
  public void testEvenNumberOfRows() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {4, 4, 4}},
            {{5, 5, 5}, {6, 6, 6}, {7, 7, 7}, {8, 8, 8}},
            {{9, 9, 9}, {10, 10, 10}, {11, 11, 11}, {12, 12, 12}},
            {{13, 13, 13}, {14, 14, 14}, {15, 15, 15}, {16, 16, 16}}
    };
    String imageName = "EvenNumberRowImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new BlurOperation(imageName, "blurredEvenNumberRowImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedBlurred = {
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };
    assertArrayEquals(expectedBlurred, model.getImage("blurredEvenNumberRowImage"));
  }

  @Test
  public void testBlurSplit() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
        {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {4, 4, 4}},
        {{5, 5, 5}, {6, 6, 6}, {7, 7, 7}, {8, 8, 8}},
        {{9, 9, 9}, {10, 10, 10}, {11, 11, 11}, {12, 12, 12}},
        {{13, 13, 13}, {14, 14, 14}, {15, 15, 15}, {16, 16, 16}}
    };
    String imageName = "EvenNumberRowImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new BlurOperation(imageName,
        "blurredEvenNumberRowImage");
    operation.performOperation(model);

    model.split(imageName, "blurredEvenNumberRowImage", 50);

    // Assert
    int[][][] expectedBlurred = {
        {{0, 0, 0}, {0, 0, 0}, {3, 3, 3}, {4, 4, 4}},
        {{0, 0, 0}, {0, 0, 0}, {7, 7, 7}, {8, 8, 8}},
        {{0, 0, 0}, {0, 0, 0}, {11, 11, 11}, {12, 12, 12}},
        {{0, 0, 0}, {0, 0, 0}, {15, 15, 15}, {16, 16, 16}}

    };
    assertEquals(Arrays.deepToString(expectedBlurred),
        Arrays.deepToString(model.getImage("blurredEvenNumberRowImage^Split")));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBlurSplitPercentageOutside() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
        {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {4, 4, 4}},
        {{5, 5, 5}, {6, 6, 6}, {7, 7, 7}, {8, 8, 8}},
        {{9, 9, 9}, {10, 10, 10}, {11, 11, 11}, {12, 12, 12}},
        {{13, 13, 13}, {14, 14, 14}, {15, 15, 15}, {16, 16, 16}}
    };
    String imageName = "EvenNumberRowImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new BlurOperation(imageName,
        "blurredEvenNumberRowImage");
    operation.performOperation(model);

    model.split(imageName, "blurredEvenNumberRowImage", 109);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBlurSplitPercentageLess() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
        {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {4, 4, 4}},
        {{5, 5, 5}, {6, 6, 6}, {7, 7, 7}, {8, 8, 8}},
        {{9, 9, 9}, {10, 10, 10}, {11, 11, 11}, {12, 12, 12}},
        {{13, 13, 13}, {14, 14, 14}, {15, 15, 15}, {16, 16, 16}}
    };
    String imageName = "EvenNumberRowImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new BlurOperation(imageName,
        "blurredEvenNumberRowImage");
    operation.performOperation(model);

    model.split(imageName, "blurredEvenNumberRowImage", -5);
  }

}