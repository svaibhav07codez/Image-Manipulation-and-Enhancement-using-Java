import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import model.ImageModel;
import model.ImageModelImpl;
import operations.AbstractImageOperation;
import operations.GreenComponentOperation;

import static org.junit.Assert.assertArrayEquals;


/**
 * This file tests the extraction of green component.
 */
public class GreenComponentOperationTest {
  private ImageModel model;

  @Before
  public void setUp() {
    model = new ImageModelImpl();  // Initialize the image model before each test
  }

  @Test
  public void testGreenOnSmallImage() throws IOException {
    int[][][] imageData = {
            {{255, 0, 0}, {0, 255, 0}},  // Red, Green
            {{0, 0, 255}, {255, 255, 0}}  // Green, Yellow
    };

    String imageName = "small.png";
    model.saveImage(imageName, imageData);  // Save image with predefined data

    // Perform Green operation
    AbstractImageOperation operation = new GreenComponentOperation(imageName, "greenImage");
    operation.performOperation(model);

    int[][][] expectedData = {{{0, 0, 0}, {255, 255, 255}}, {{0, 0, 0}, {255, 255, 255}}};

    int[][][] greenImage = model.getImage("greenImage");

    // Assert the Green image is as expected
    assertArrayEquals(expectedData, greenImage);
  }


  @Test
  public void testGreenOnLargeImage() throws IOException {
    // Load a large sample image (3x3 pixels)
    int[][][] imageData = {
            {{255, 0, 0}, {0, 255, 0}, {0, 0, 255}},  // Red, Green, Green
            {{255, 255, 0}, {255, 0, 255}, {0, 255, 255}},  // Yellow, Magenta, Cyan
            {{128, 128, 128}, {64, 64, 64}, {192, 192, 192}}  // Grey shades
    };
    String imageName = "largeImage.png";
    model.saveImage(imageName, imageData);

    // Perform vertical flip
    AbstractImageOperation operation = new GreenComponentOperation(imageName, "greenImage");
    operation.performOperation(model);

    // Get the expected flipped image
    int[][][] expectedData = {
            {{0, 0, 0}, {255, 255, 255}, {0, 0, 0}},
            {{255, 255, 255}, {0, 0, 0}, {255, 255, 255}},
            {{128, 128, 128}, {64, 64, 64}, {192, 192, 192}}
    };

    int[][][] greenImage = model.getImage("greenImage");

    // Assert the flipped image is as expected
    assertArrayEquals(expectedData, greenImage);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGreenOnNonExistentImage() {
    // Try to flip a non-existent image, should throw an exception
    AbstractImageOperation operation = new GreenComponentOperation("nonExistentImage",
        "greenImage");
    operation.performOperation(model);
  }

  @Test
  public void testGreenOnSinglePixelImage() throws IOException {
    // Load a 1x1 pixel image
    int[][][] imageData = {
            {{128, 128, 128}}  // Grey
    };
    String imageName = "singlePixelImage.png";
    model.saveImage(imageName, imageData);

    // Perform vertical flip
    AbstractImageOperation operation = new GreenComponentOperation(imageName, "greenImage");
    operation.performOperation(model);

    int[][][] greenImage = model.getImage("greenImage");

    int[][][] expectedData = {
            {{128, 128, 128}}  // Grey
    };
    // Assert the image is unchanged
    assertArrayEquals(expectedData, greenImage);
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

    AbstractImageOperation operation = new GreenComponentOperation(imageName, "greenImage");
    operation.performOperation(model);

    int[][][] greenImage = model.getImage("greenImage");

    int[][][] expectedFlipped = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
            {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
            {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}
    };

    assertArrayEquals(expectedFlipped, greenImage);
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

    AbstractImageOperation operation = new GreenComponentOperation(imageName, "greenImage");
    operation.performOperation(model);

    int[][][] greenImage = model.getImage("greenImage");

    int[][][] expectedFlipped = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {4, 4, 4}, {5, 5, 5}}
    };

    assertArrayEquals(expectedFlipped, greenImage);
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

    AbstractImageOperation operation = new GreenComponentOperation(imageName, "greenImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedFlipped = {
            {{1, 1, 1}},
            {{2, 2, 2}},
            {{3, 3, 3}},
            {{4, 4, 4}},
            {{5, 5, 5}}
    };


    int[][][] greenImage = model.getImage("greenImage");

    assertArrayEquals(expectedFlipped, greenImage);
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

    AbstractImageOperation operation = new GreenComponentOperation(imageName, "greenImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedFlipped = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
            {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}}
    };

    int[][][] greenImage = model.getImage("greenImage");

    assertArrayEquals(expectedFlipped, greenImage);
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

    AbstractImageOperation operation = new GreenComponentOperation(imageName, "greenImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedFlipped = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
            {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
            {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}
    };

    int[][][] greenImage = model.getImage("greenImage");

    assertArrayEquals(expectedFlipped, greenImage);
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

    AbstractImageOperation operation = new GreenComponentOperation(imageName, "greenImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedFlipped = {
            {{1, 1, 1}}  // Should remain the same
    };

    int[][][] greenImage = model.getImage("greenImage");

    assertArrayEquals(expectedFlipped, greenImage);
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

    AbstractImageOperation operation = new GreenComponentOperation(imageName, "greenImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedFlipped = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {4, 4, 4}},
            {{5, 5, 5}, {6, 6, 6}, {7, 7, 7}, {8, 8, 8}},
            {{9, 9, 9}, {10, 10, 10}, {11, 11, 11}, {12, 12, 12}},
            {{13, 13, 13}, {14, 14, 14}, {15, 15, 15}, {16, 16, 16}}
    };

    int[][][] greenImage = model.getImage("greenImage");

    assertArrayEquals(expectedFlipped, greenImage);
  }

}