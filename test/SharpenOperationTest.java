import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import model.ImageModel;
import model.ImageModelImpl;
import operations.AbstractImageOperation;
import operations.SharpenOperation;

import static org.junit.Assert.assertArrayEquals;


/**
 * This file tests the sharpen operation.
 */
public class SharpenOperationTest {
  private ImageModel model;

  @Before
  public void setUp() {
    model = new ImageModelImpl();  // Initialize the image model before each test
  }

  @Test
  public void testSharpenOnSmallImage() throws IOException {
    // Load a small sample image (2x2 pixels)
    int[][][] imageData = {
            {{255, 0, 0}, {0, 255, 0}},  // Sharpen, Sharpen
            {{0, 0, 255}, {255, 255, 0}}  // Sharpen, Yellow
    };

    String imageName = "small.png";
    model.saveImage(imageName, imageData);  // Save image with Sharp data

    // Perform Sharpen operation
    AbstractImageOperation operation = new SharpenOperation(imageName,
            "sharpenImage");
    operation.performOperation(model);

    int[][][] expectedData = {{{0, 0, 0}, {0, 0, 0}}, {{0, 0, 0}, {0, 0, 0}}};

    int[][][] sharpenImage = model.getImage("sharpenImage");
     

    // Assert the Sharpen image is as expected
    assertArrayEquals(expectedData, sharpenImage);
  }


  @Test
  public void testSharpenOnLargeImage() throws IOException {
    // Load a large sample image (3x3 pixels)
    int[][][] imageData = {
            {{255, 0, 0}, {0, 255, 0}, {0, 0, 255}},  // Sharpen, Sharpen, Sharpen
            {{255, 255, 0}, {255, 0, 255}, {0, 255, 255}},  // Yellow, Magenta, Cyan
            {{128, 128, 128}, {64, 64, 64}, {192, 192, 192}}  // Grey shades
    };
    String imageName = "largeImage.png";
    model.saveImage(imageName, imageData);

    // Perform vertical flip
    AbstractImageOperation operation = new SharpenOperation(imageName,
            "sharpenImage");
    operation.performOperation(model);

    // Get the expected flipped image
    int[][][] expectedData = {
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };

    int[][][] sharpenImage = model.getImage("sharpenImage");
     

    // Assert the flipped image is as expected
    assertArrayEquals(expectedData, sharpenImage);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSharpenOnNonExistentImage() {
    // Try to flip a non-existent image, should throw an exception
    AbstractImageOperation operation = new SharpenOperation("nonExistentImage",
            "sharpenImage");
    operation.performOperation(model);
  }

  @Test
  public void testSharpenOnSinglePixelImage() throws IOException {
    // Load a 1x1 pixel image
    int[][][] imageData = {
            {{128, 128, 128}}  // Grey
    };
    String imageName = "singlePixelImage.png";
    model.saveImage(imageName, imageData);

    // Perform vertical flip
    AbstractImageOperation operation = new SharpenOperation(imageName,
            "sharpenImage");
    operation.performOperation(model);

    int[][][] sharpenImage = model.getImage("sharpenImage");
     

    int[][][] expectedData = {
            {{0, 0, 0}}  // Grey
    };
    // Assert the image is unchanged
    assertArrayEquals(expectedData, sharpenImage);
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

    AbstractImageOperation operation = new SharpenOperation(imageName,
            "sharpenImage");
    operation.performOperation(model);

    int[][][]sharpenImage = model.getImage("sharpenImage");

    int[][][] expectedFlipped = {
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };

    assertArrayEquals(expectedFlipped, sharpenImage);
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

    AbstractImageOperation operation = new SharpenOperation(imageName,
            "sharpenImage");
    operation.performOperation(model);

    int[][][] sharpenImage = model.getImage("sharpenImage");

    int[][][] expectedFlipped = {
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };

    assertArrayEquals(expectedFlipped, sharpenImage);
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

    AbstractImageOperation operation = new SharpenOperation(imageName, "sharpenImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedFlipped = {
            {{0, 0, 0}},
            {{0, 0, 0}},
            {{0, 0, 0}},
            {{0, 0, 0}},
            {{0, 0, 0}}
    };

    int[][][] sharpenImage = model.getImage("sharpenImage");

    assertArrayEquals(expectedFlipped, sharpenImage);
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

    AbstractImageOperation operation = new SharpenOperation(imageName,
            "sharpenImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedFlipped = {
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };

    int[][][] sharpenImage = model.getImage("sharpenImage");
     

    assertArrayEquals(expectedFlipped, sharpenImage);
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

    AbstractImageOperation operation = new SharpenOperation(imageName, "sharpenImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedFlipped = {
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };

    int[][][] sharpenImage = model.getImage("sharpenImage");
     

    assertArrayEquals(expectedFlipped, sharpenImage);
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

    AbstractImageOperation operation = new SharpenOperation(imageName, "sharpenImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedFlipped = {{{0, 0, 0}}};

    int[][][] sharpenImage = model.getImage("sharpenImage");
     

    assertArrayEquals(expectedFlipped, sharpenImage);
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

    AbstractImageOperation operation = new SharpenOperation(imageName,
            "sharpenImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedFlipped = {
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };

    int[][][] sharpenImage = model.getImage("sharpenImage");

    assertArrayEquals(expectedFlipped, sharpenImage);
  }

}