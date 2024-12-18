import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import model.ImageModel;
import model.ImageModelImpl;
import operations.AbstractImageOperation;
import operations.VerticalFlipOperation;

import static org.junit.Assert.assertArrayEquals;

/**
 * This file tests the vertical-flip operation.
 */
public class VerticalFlipOperationTest {

  private ImageModel model;

  @Before
  public void setUp() {
    model = new ImageModelImpl();  // Initialize the image model before each test
  }

  @Test
  public void testVerticalFlipOnSmallImage() throws IOException {
    // Load a small sample image (2x2 pixels)
    int[][][] imageData = {
            {{255, 0, 0}, {0, 255, 0}},  // Red, Green
            {{0, 0, 255}, {255, 255, 0}}  // Blue, Yellow
    };

    String imageName = "small.png";
    model.saveImage("small.png", imageData);  // Mock load image with predefined data

    // Perform vertical flip
    AbstractImageOperation operation = new VerticalFlipOperation(imageName, "flippedImage");
    operation.performOperation(model);

    // Get the expected flipped image
    int[][][] expectedData = {
            {{0, 0, 255}, {255, 255, 0}},  // Blue, Yellow
            {{255, 0, 0}, {0, 255, 0}}     // Red, Green
    };
    int[][][] flippedImage = model.getImage("flippedImage");

    // Assert the flipped image is as expected
    assertArrayEquals(expectedData, flippedImage);
  }

  @Test
  public void testVerticalFlipOnLargeImage() throws IOException {
    // Load a large sample image (3x3 pixels)
    int[][][] imageData = {
            {{255, 0, 0}, {0, 255, 0}, {0, 0, 255}},  // Red, Green, Blue
            {{255, 255, 0}, {255, 0, 255}, {0, 255, 255}},  // Yellow, Magenta, Cyan
            {{128, 128, 128}, {64, 64, 64}, {192, 192, 192}}  // Grey shades
    };
    String imageName = "largeImage.png";
    model.saveImage(imageName, imageData);

    // Perform vertical flip
    AbstractImageOperation operation = new VerticalFlipOperation(imageName, "flippedLargeImage");
    operation.performOperation(model);

    // Get the expected flipped image
    int[][][] expectedData = {
            {{128, 128, 128}, {64, 64, 64}, {192, 192, 192}},  // Grey shades
            {{255, 255, 0}, {255, 0, 255}, {0, 255, 255}},     // Yellow, Magenta, Cyan
            {{255, 0, 0}, {0, 255, 0}, {0, 0, 255}}            // Red, Green, Blue
    };
    int[][][] flippedImage = model.getImage("flippedLargeImage");

    // Assert the flipped image is as expected
    assertArrayEquals(expectedData, flippedImage);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testVerticalFlipOnNonExistentImage() {
    // Try to flip a non-existent image, should throw an exception
    AbstractImageOperation operation = new VerticalFlipOperation("nonExistentImage",
            "flippedImage");
    operation.performOperation(model);
  }

  @Test
  public void testVerticalFlipOnSinglePixelImage() throws IOException {
    // Load a 1x1 pixel image
    int[][][] imageData = {
            {{128, 128, 128}}  // Grey
    };
    String imageName = "singlePixelImage.png";
    model.saveImage(imageName, imageData);

    // Perform vertical flip
    AbstractImageOperation operation = new VerticalFlipOperation(imageName,
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
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
            {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
            {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}
    };
    String imageName = "NormalPixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new VerticalFlipOperation(imageName,
            "flippedNormalPixelImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedFlipped = {
            {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}},
            {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}}
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

    AbstractImageOperation operation = new VerticalFlipOperation(imageName,
            "flippedSingleRowPixelImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedFlipped = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {4, 4, 4}, {5, 5, 5}}  // Should remain the same
    };
    assertArrayEquals(expectedFlipped, model.getImage("flippedSingleRowPixelImage"));
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

    AbstractImageOperation operation = new VerticalFlipOperation(imageName,
            "flippedSingleColumnPixelImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedFlipped = {
            {{5, 5, 5}},
            {{4, 4, 4}},
            {{3, 3, 3}},
            {{2, 2, 2}},
            {{1, 1, 1}}
    };
    assertArrayEquals(expectedFlipped, model.getImage("flippedSingleColumnPixelImage"));
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

    AbstractImageOperation operation = new VerticalFlipOperation(imageName,
            "flippedSymmetricImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedFlipped = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
            {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}}
    };
    assertArrayEquals(expectedFlipped, model.getImage("flippedSymmetricImage"));
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

    AbstractImageOperation operation = new VerticalFlipOperation(imageName,
            "flippedNonSymmetricImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedFlipped = {
            {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}},
            {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}}
    };
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

    AbstractImageOperation operation = new VerticalFlipOperation(imageName,
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
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {4, 4, 4}},
            {{5, 5, 5}, {6, 6, 6}, {7, 7, 7}, {8, 8, 8}},
            {{9, 9, 9}, {10, 10, 10}, {11, 11, 11}, {12, 12, 12}},
            {{13, 13, 13}, {14, 14, 14}, {15, 15, 15}, {16, 16, 16}}
    };
    String imageName = "EvenNumberRowImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new VerticalFlipOperation(imageName,
            "EvenNumberRowPixelImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedFlipped = {
            {{13, 13, 13}, {14, 14, 14}, {15, 15, 15}, {16, 16, 16}},
            {{9, 9, 9}, {10, 10, 10}, {11, 11, 11}, {12, 12, 12}},
            {{5, 5, 5}, {6, 6, 6}, {7, 7, 7}, {8, 8, 8}},
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {4, 4, 4}}
    };
    assertArrayEquals(expectedFlipped, model.getImage("EvenNumberRowPixelImage"));
  }
}


