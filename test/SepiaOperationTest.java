import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import model.ImageModel;
import model.ImageModelImpl;
import operations.AbstractImageOperation;
import operations.SepiaOperation;

import static org.junit.Assert.assertArrayEquals;


/**
 * This file tests the sepia operation.
 */
public class SepiaOperationTest {

  private ImageModel model;

  @Before
  public void setUp() {
    model = new ImageModelImpl();  // Initialize the image model before each test
  }

  @Test
  public void testSepiaOnSmallImage() throws IOException {
    // Load a small sample image (2x2 pixels)
    int[][][] imageData = {
            {{255, 0, 0}, {0, 255, 0}},  // Red, Green
            {{0, 0, 255}, {255, 255, 0}}  // Blue, Yellow
    };

    String imageName = "small.png";
    model.saveImage(imageName, imageData);  // Save image with predefined data

    // Perform sepia operation
    AbstractImageOperation operation = new SepiaOperation(imageName, "sepiaImage");
    operation.performOperation(model);

    int[][][] expectedData = {
            {{100, 88, 69}, {196, 174, 136}},
            {{48, 42, 33}, {255, 255, 205}}
    };

    int[][][] sepiaImage = model.getImage("sepiaImage");

    // Assert the sepia image is as expected
    assertArrayEquals(expectedData, sepiaImage);
  }


  @Test
  public void testSepiaOnLargeImage() throws IOException {
    // Load a large sample image (3x3 pixels)
    int[][][] imageData = {
            {{255, 0, 0}, {0, 255, 0}, {0, 0, 255}},  // Red, Green, Blue
            {{255, 255, 0}, {255, 0, 255}, {0, 255, 255}},  // Yellow, Magenta, Cyan
            {{128, 128, 128}, {64, 64, 64}, {192, 192, 192}}  // Grey shades
    };
    String imageName = "largeImage.png";
    model.saveImage(imageName, imageData);

    // Perform vertical flip
    AbstractImageOperation operation = new SepiaOperation(imageName, "sepiaImage");
    operation.performOperation(model);

    // Get the expected flipped image
    int[][][] expectedData = {
            {{100, 88, 69}, {196, 174, 136}, {48, 42, 33}},
            {{255, 255, 205}, {148, 131, 102}, {244, 217, 169}},
            {{172, 153, 119}, {86, 76, 59}, {255, 230, 179}}
    };
    int[][][] sepiaImage = model.getImage("sepiaImage");

    // Assert the flipped image is as expected
    assertArrayEquals(expectedData, sepiaImage);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSepiaOnNonExistentImage() {
    // Try to flip a non-existent image, should throw an exception
    AbstractImageOperation operation = new SepiaOperation("nonExistentImage", "sepiaImage");
    operation.performOperation(model);
  }

  @Test
  public void testSepiaOnSinglePixelImage() throws IOException {
    // Load a 1x1 pixel image
    int[][][] imageData = {
            {{128, 128, 128}}  // Grey
    };
    String imageName = "singlePixelImage.png";
    model.saveImage(imageName, imageData);

    // Perform vertical flip
    AbstractImageOperation operation = new SepiaOperation(imageName, "sepiaImage");
    operation.performOperation(model);

    int[][][] sepiaImage = model.getImage("sepiaImage");

    int[][][] expectedData = {{{172, 153, 119}}};
    // Assert the image is unchanged
    assertArrayEquals(expectedData, sepiaImage);
  }


  // Test normal image flip with 3D array (RGB image)
  @Test
  public void testNormalImageFlip() throws IOException {
    // Arrange
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
            {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
            {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}
    };
    String imageName = "NormalPixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new SepiaOperation(imageName, "sepiaImage");
    operation.performOperation(model);

    int[][][] sepiaImage = model.getImage("sepiaImage");

    int[][][] expectedFlipped = {
        {{1, 1, 0}, {2, 2, 1}, {4, 3, 2}},
        {{5, 4, 3}, {6, 6, 4}, {8, 7, 5}},
        {{9, 8, 6}, {10, 9, 7}, {12, 10, 8}}
    };

    assertArrayEquals(expectedFlipped, sepiaImage);
  }

  // Test single row image flip (3D)
  @Test
  public void testSingleRowImage() throws IOException {
    // Arrange
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {4, 4, 4}, {5, 5, 5}}
    };
    String imageName = "SingleRowPixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new SepiaOperation(imageName, "sepiaImage");
    operation.performOperation(model);

    int[][][] sepiaImage = model.getImage("sepiaImage");

    int[][][] expectedFlipped = {{{1, 1, 0}, {2, 2, 1},
            {4, 3, 2}, {5, 4, 3}, {6, 6, 4}}};

    assertArrayEquals(expectedFlipped, sepiaImage);
  }

  // Test single column image flip (3D)
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

    AbstractImageOperation operation = new SepiaOperation(imageName, "sepiaImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedFlipped = {{{1, 1, 0}}, {{2, 2, 1}}, {{4, 3, 2}}, {{5, 4, 3}}, {{6, 6, 4}}};

    int[][][] sepiaImage = model.getImage("sepiaImage");

    assertArrayEquals(expectedFlipped, sepiaImage);
  }


  // Test symmetric image flip (3D)
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

    AbstractImageOperation operation = new SepiaOperation(imageName, "sepiaImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedFlipped = {
            {{1, 1, 0}, {2, 2, 1}, {4, 3, 2}},
            {{5, 4, 3}, {6, 6, 4}, {8, 7, 5}},
            {{1, 1, 0}, {2, 2, 1}, {4, 3, 2}}
    };

    int[][][] sepiaImage = model.getImage("sepiaImage");

    assertArrayEquals(expectedFlipped, sepiaImage);
  }

  // Test non-symmetric image flip (3D)
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

    AbstractImageOperation operation = new SepiaOperation(imageName, "sepiaImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedFlipped = {
            {{1, 1, 0}, {2, 2, 1}, {4, 3, 2}},
            {{5, 4, 3}, {6, 6, 4}, {8, 7, 5}},
            {{9, 8, 6}, {10, 9, 7}, {12, 10, 8}}
    };

    int[][][] sepiaImage = model.getImage("sepiaImage");

    assertArrayEquals(expectedFlipped, sepiaImage);
  }


  // Test one-pixel image flip (3D)
  @Test
  public void testOnePixelImage() throws IOException {
    // Arrange
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
            {{1, 1, 1}}
    };
    String imageName = "SinglePixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new SepiaOperation(imageName, "sepiaImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedFlipped = {
            {{1, 1, 0}}  // Should remain the same
    };

    int[][][] sepiaImage = model.getImage("sepiaImage");

    assertArrayEquals(expectedFlipped, sepiaImage);
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

    AbstractImageOperation operation = new SepiaOperation(imageName, "sepiaImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedFlipped = {
            {{1, 1, 0}, {2, 2, 1}, {4, 3, 2}, {5, 4, 3}},
            {{6, 6, 4}, {8, 7, 5}, {9, 8, 6}, {10, 9, 7}},
            {{12, 10, 8}, {13, 12, 9}, {14, 13, 10}, {16, 14, 11}},
            {{17, 15, 12}, {18, 16, 13}, {20, 18, 14}, {21, 19, 14}}
    };


    int[][][] sepiaImage = model.getImage("sepiaImage");

    assertArrayEquals(expectedFlipped, sepiaImage);
  }

}