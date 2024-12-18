import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import model.ImageModel;
import model.ImageModelImpl;
import operations.AbstractImageOperation;
import operations.HorizontalFlipOperation;

import static org.junit.Assert.assertArrayEquals;


/**
 * This file tests the horizontal-flip operation.
 */
public class HorizontalFlipOperationTest {

  private ImageModel model;

  @Before
  public void setUp() {
    model = new ImageModelImpl();  // Initialize the image model before each test
  }

  @Test
  public void testHorizontalFlipOnSmallImage() throws IOException {
    // Load a small sample image (2x2 pixels)
    int[][][] imageData = {
            {{255, 0, 0}, {0, 255, 0}},  // Red, Green
            {{0, 0, 255}, {255, 255, 0}}  // Blue, Yellow
    };

    String imageName = "small.png";
    model.saveImage("small.png", imageData);  // Mock load image with predefined data

    // Perform vertical flip
    AbstractImageOperation operation = new HorizontalFlipOperation(imageName, "flippedImage");
    operation.performOperation(model);

    // Get the expected flipped image
    int[][][] expectedData = {
            {{0, 255, 0}, {255, 0, 0}},
            {{255, 255, 0}, {0, 0, 255}}
    };
    int[][][] flippedImage = model.getImage("flippedImage");
    // Assert the flipped image is as expected
    assertArrayEquals(expectedData, flippedImage);
  }

  @Test
  public void testHorizontalFlipOnLargeImage() throws IOException {
    // Load a large sample image (3x3 pixels)
    int[][][] imageData = {
            {{255, 0, 0}, {0, 255, 0}, {0, 0, 255}},  // Red, Green, Blue
            {{255, 255, 0}, {255, 0, 255}, {0, 255, 255}},  // Yellow, Magenta, Cyan
            {{128, 128, 128}, {64, 64, 64}, {192, 192, 192}}  // Grey shades
    };
    String imageName = "largeImage.png";
    model.saveImage(imageName, imageData);

    // Perform horizontal flip
    AbstractImageOperation operation = new HorizontalFlipOperation(imageName, "flippedLargeImage");
    operation.performOperation(model);

    // Get the expected flipped image (horizontally flipped)
    int[][][] expectedData = {
            {{0, 0, 255}, {0, 255, 0}, {255, 0, 0}},  // Flipped Row 0: Blue, Green, Red
            {{0, 255, 255}, {255, 0, 255}, {255, 255, 0}},  // Flipped Row 1: Cyan, Magenta, Yellow
            {{192, 192, 192}, {64, 64, 64}, {128, 128, 128}} // Flipped Row 2: Grey shades
    };

    // Retrieve the flipped image
    int[][][] flippedImage = model.getImage("flippedLargeImage");

    // Assert the flipped image is as expected
    assertArrayEquals(expectedData, flippedImage);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testHorizontalFlipOnNonExistentImage() {
    // Try to flip a non-existent image, should throw an exception
    AbstractImageOperation operation = new HorizontalFlipOperation("nonExistentImage",
        "flippedImage");
    operation.performOperation(model);
  }

  @Test
  public void testHorizontalFlipOnSinglePixelImage() throws IOException {
    // Load a 1x1 pixel image
    int[][][] imageData = {
            {{128, 128, 128}}  // Grey
    };
    String imageName = "singlePixelImage.png";
    model.saveImage(imageName, imageData);

    // Perform vertical flip
    AbstractImageOperation operation = new HorizontalFlipOperation(imageName,
        "flippedSinglePixelImage");
    operation.performOperation(model);

    // The flipped image should be identical to the original in this case
    int[][][] flippedImage = model.getImage("flippedSinglePixelImage");

    // Assert the image is unchanged
    assertArrayEquals(imageData, flippedImage);
  }


  // Test normal image flip with 3D array (RGB image)
  @Test
  public void testNormalImageFlip() throws IOException {
    // Arrange
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},  // Row 0: 1s, 2s, 3s
            {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},  // Row 1: 4s, 5s, 6s
            {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}   // Row 2: 7s, 8s, 9s
    };
    String imageName = "NormalPixelImage.png";
    model.saveImage(imageName, imageData);

    // Perform horizontal flip
    AbstractImageOperation operation = new HorizontalFlipOperation(imageName,
        "flippedNormalPixelImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedFlipped = {
            {{3, 3, 3}, {2, 2, 2}, {1, 1, 1}},  // Flipped Row 0
            {{6, 6, 6}, {5, 5, 5}, {4, 4, 4}},  // Flipped Row 1
            {{9, 9, 9}, {8, 8, 8}, {7, 7, 7}}   // Flipped Row 2
    };

    assertArrayEquals(expectedFlipped, model.getImage("flippedNormalPixelImage"));
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

    // Perform horizontal flip
    AbstractImageOperation operation = new HorizontalFlipOperation(imageName,
        "flippedSingleRowPixelImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedFlipped = {
            {{5, 5, 5}, {4, 4, 4}, {3, 3, 3}, {2, 2, 2}, {1, 1, 1}}  // Flipped order
    };

    // Assert that the flipped image matches the expected output
    assertArrayEquals(expectedFlipped, model.getImage("flippedSingleRowPixelImage"));
  }


  // Test symmetric image flip (3D)
  @Test
  public void testSymmetricImage() throws IOException {
    // Arrange
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}}, // Row 0
            {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}}, // Row 1
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}}  // Row 2
    };
    String imageName = "SymmetricImage.png";
    model.saveImage(imageName, imageData);

    // Perform horizontal flip
    AbstractImageOperation operation = new HorizontalFlipOperation(imageName,
        "flippedSymmetricImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedFlipped = {
            {{3, 3, 3}, {2, 2, 2}, {1, 1, 1}}, // Flipped Row 0
            {{6, 6, 6}, {5, 5, 5}, {4, 4, 4}}, // Flipped Row 1
            {{3, 3, 3}, {2, 2, 2}, {1, 1, 1}}  // Flipped Row 2
    };

    // Assert that the flipped image matches the expected output
    assertArrayEquals(expectedFlipped, model.getImage("flippedSymmetricImage"));
  }


  // Test non-symmetric image flip (3D)
  @Test
  public void testNonSymmetricImage() throws IOException {
    // Arrange
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}}, // Row 0
            {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}}, // Row 1
            {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}  // Row 2
    };
    String imageName = "NonSymmetricImage.png";
    model.saveImage(imageName, imageData);

    // Perform horizontal flip
    AbstractImageOperation operation = new HorizontalFlipOperation(imageName,
        "flippedNonSymmetricImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedFlipped = {
            {{3, 3, 3}, {2, 2, 2}, {1, 1, 1}}, // Flipped Row 0
            {{6, 6, 6}, {5, 5, 5}, {4, 4, 4}}, // Flipped Row 1
            {{9, 9, 9}, {8, 8, 8}, {7, 7, 7}}  // Flipped Row 2
    };

    // Assert that the flipped image matches the expected output
    assertArrayEquals(expectedFlipped, model.getImage("flippedNonSymmetricImage"));
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

    AbstractImageOperation operation = new HorizontalFlipOperation(imageName,
        "flippedSinglePixelImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedFlipped = {
            {{1, 1, 1}}  // Should remain the same
    };
    assertArrayEquals(expectedFlipped, model.getImage("flippedSinglePixelImage"));
  }

  // Test even number of rows (3D)
  @Test
  public void testEvenNumberOfRows() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {4, 4, 4}},   // Row 0
            {{5, 5, 5}, {6, 6, 6}, {7, 7, 7}, {8, 8, 8}},   // Row 1
            {{9, 9, 9}, {10, 10, 10}, {11, 11, 11}, {12, 12, 12}}, // Row 2
            {{13, 13, 13}, {14, 14, 14}, {15, 15, 15}, {16, 16, 16}}  // Row 3
    };
    String imageName = "EvenNumberRowImage.png";
    model.saveImage(imageName, imageData);

    // Perform horizontal flip
    AbstractImageOperation operation = new HorizontalFlipOperation(imageName,
        "EvenNumberRowPixelImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedFlipped = {
            {{4, 4, 4}, {3, 3, 3}, {2, 2, 2}, {1, 1, 1}},   // Flipped Row 0
            {{8, 8, 8}, {7, 7, 7}, {6, 6, 6}, {5, 5, 5}},   // Flipped Row 1
            {{12, 12, 12}, {11, 11, 11}, {10, 10, 10}, {9, 9, 9}}, // Flipped Row 2
            {{16, 16, 16}, {15, 15, 15}, {14, 14, 14}, {13, 13, 13}}  // Flipped Row 3
    };

    // Assert that the flipped image matches the expected output
    assertArrayEquals(expectedFlipped, model.getImage("EvenNumberRowPixelImage"));
  }


}