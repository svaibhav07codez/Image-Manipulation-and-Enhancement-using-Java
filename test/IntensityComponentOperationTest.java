import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import model.ImageModel;
import model.ImageModelImpl;
import operations.AbstractImageOperation;
import operations.IntensityComponentOperation;

import static org.junit.Assert.assertArrayEquals;

/**
 * This file tests the intensity component operation.
 */
public class IntensityComponentOperationTest {

  private ImageModel model;

  @Before
  public void setUp() {
    model = new ImageModelImpl();
  }

  @Test
  public void testIntensityOnSmallImage() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
            {{255, 0, 0}, {0, 255, 0}},   // Red and Green
            {{0, 0, 255}, {255, 255, 0}}  // Blue and Yellow (Red + Green)
    };

    String imageName = "small.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new IntensityComponentOperation(imageName, "IntensityImage");
    operation.performOperation(model);

    // Calculate expected intensity values
    int[][][] expectedData = {{{85, 85, 85}, {85, 85, 85}}, {{85, 85, 85}, {170, 170, 170}}};

    int[][][] intensityImage = model.getImage("IntensityImage");

    assertArrayEquals(expectedData, intensityImage);
  }


  @Test
  public void testIntensityOnLargeImage() throws IOException {
    int[][][] imageData = {
            {{255, 0, 0}, {0, 255, 0}, {0, 0, 255}},
            {{255, 255, 0}, {255, 0, 255}, {0, 255, 255}},
            {{128, 128, 128}, {64, 64, 64}, {192, 192, 192}}
    };
    String imageName = "largeImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new IntensityComponentOperation(imageName,
        "IntensityLargeImage");
    operation.performOperation(model);

    int[][][] expectedData = {
            {{85, 85, 85}, {85, 85, 85}, {85, 85, 85}},
            {{170, 170, 170}, {170, 170, 170}, {170, 170, 170}},
            {{128, 128, 128}, {64, 64, 64}, {192, 192, 192}}
    };

    int[][][] intensityImage = model.getImage("IntensityLargeImage");

    assertArrayEquals(expectedData, intensityImage);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIntensityOnNonExistentImage() {
    // Try to flip a non-existent image, should throw an exception
    AbstractImageOperation operation = new IntensityComponentOperation("nonExistentImage",
        "IntensityImage");
    operation.performOperation(model);
  }

  @Test
  public void testIntensityOnSinglePixelImage() throws IOException {
    int[][][] imageData = {
            {{0, 0, 0}}  // Grey
    };
    String imageName = "singlePixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new IntensityComponentOperation(imageName, "IntensityImage");
    operation.performOperation(model);

    int[][][] intensityImage = model.getImage("IntensityImage");

    assertArrayEquals(imageData, intensityImage);
  }


  @Test
  public void testNormalImageIntensity() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
            {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
            {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}
    };
    String imageName = "NormalPixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new IntensityComponentOperation(imageName,
        "IntensityNormalPixelImage");
    operation.performOperation(model);

    int[][][] expectedIntensity = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
            {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
            {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}
    };
    int[][][] intensityImage = model.getImage("IntensityNormalPixelImage");

    assertArrayEquals(expectedIntensity, intensityImage);
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

    AbstractImageOperation operation = new IntensityComponentOperation(imageName,
        "IntensitySingleRowPixelImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedIntensity = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {4, 4, 4}, {5, 5, 5}}  // Expected Intensity output
    };

    int[][][] intensityImage = model.getImage("IntensitySingleRowPixelImage");

    assertArrayEquals(expectedIntensity, intensityImage);
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

    AbstractImageOperation operation = new IntensityComponentOperation(imageName,
        "IntensitySingleColumnPixelImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedIntensity = {
            {{1, 1, 1}},
            {{2, 2, 2}},
            {{3, 3, 3}},
            {{4, 4, 4}},
            {{5, 5, 5}}  // Corrected expected output
    };
    int[][][] intensityImage = model.getImage("IntensitySingleColumnPixelImage");

    assertArrayEquals(expectedIntensity, intensityImage);
  }


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

    AbstractImageOperation operation = new IntensityComponentOperation(imageName,
        "IntensitySymmetricImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedIntensity = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
            {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}}  // Corrected expected output
    };
    int[][][] intensityImage = model.getImage("IntensitySymmetricImage");

    assertArrayEquals(expectedIntensity, intensityImage);
  }

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

    AbstractImageOperation operation = new IntensityComponentOperation(imageName,
        "IntensityNonSymmetricImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedIntensity = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
            {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
            {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}  // Corrected expected output
    };
    int[][][] intensityImage = model.getImage("IntensityNonSymmetricImage");

    assertArrayEquals(expectedIntensity, intensityImage);
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

    AbstractImageOperation operation = new IntensityComponentOperation(imageName,
        "IntensitySinglePixelImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedIntensity = {
            {{1, 1, 1}}  // Should change to Intensity value
    };
    int[][][] intensityImage = model.getImage("IntensitySinglePixelImage");

    assertArrayEquals(expectedIntensity, intensityImage);
  }

  @Test
  public void testEvenNumberOfRows() throws IOException {
    // Arrange
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {4, 4, 4}},
            {{5, 5, 5}, {6, 6, 6}, {7, 7, 7}, {8, 8, 8}},
            {{9, 9, 9}, {10, 10, 10}, {11, 11, 11}, {12, 12, 12}},
            {{13, 13, 13}, {14, 14, 14}, {15, 15, 15}, {16, 16, 16}}
    };
    String imageName = "EvenNumberRowImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new IntensityComponentOperation(imageName,
        "IntensityEvenNumberRowImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedIntensity = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {4, 4, 4}},
            {{5, 5, 5}, {6, 6, 6}, {7, 7, 7}, {8, 8, 8}},
            {{9, 9, 9}, {10, 10, 10}, {11, 11, 11}, {12, 12, 12}},
            {{13, 13, 13}, {14, 14, 14}, {15, 15, 15}, {16, 16, 16}}   // Corrected expected output
    };
    int[][][] intensityImage = model.getImage("IntensityEvenNumberRowImage");

    assertArrayEquals(expectedIntensity, intensityImage);
  }
}