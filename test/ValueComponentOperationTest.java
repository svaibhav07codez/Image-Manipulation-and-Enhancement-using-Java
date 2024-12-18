import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import model.ImageModel;
import model.ImageModelImpl;
import operations.AbstractImageOperation;
import operations.ValueComponentOperation;

import static org.junit.Assert.assertArrayEquals;

/**
 * This file tests the value component operation.
 */

public class ValueComponentOperationTest {

  private ImageModel model;

  @Before
  public void setUp() {
    model = new ImageModelImpl();  // Initialize the image model before each test
  }

  @Test
  public void testValueComponentOnSmallImage() throws IOException {
    int[][][] imageData = {
            {{255, 0, 0}, {0, 255, 0}},  // Red, Green
            {{0, 0, 255}, {255, 255, 0}}  // Blue, Yellow
    };

    String imageName = "small.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new ValueComponentOperation(imageName, "valueImage");
    operation.performOperation(model);

    int[][][] expectedData = {
            {{255, 255, 255}, {255, 255, 255}},
            {{255, 255, 255}, {255, 255, 255}}
    };

    int[][][] valueImage = model.getImage("valueImage");

    assertArrayEquals(expectedData, valueImage);
  }


  @Test
  public void testValueComponentOnLargeImage() throws IOException {
    int[][][] imageData = {
            {{255, 0, 0}, {0, 255, 0}, {0, 0, 255}},  // Red, Green, Blue
            {{255, 255, 0}, {255, 0, 255}, {0, 255, 255}},  // Yellow, Magenta, Cyan
            {{128, 128, 128}, {64, 64, 64}, {192, 192, 192}}  // Grey shades
    };

    String imageName = "largeImage.png";
    model.saveImage(imageName, imageData);

    // Perform the value component extraction operation
    AbstractImageOperation operation = new ValueComponentOperation(imageName, "valueLargeImage");
    operation.performOperation(model);

    // Corrected expected value (brightness) component of each pixel
    int[][][] expectedData = {
            {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}},  // Max values for RGB
            {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}},  // Max values for RGB
            {{128, 128, 128}, {64, 64, 64}, {192, 192, 192}}       // Grey shades
    };

    // Retrieve the value component image from the model
    int[][][] valueImage = model.getImage("valueLargeImage");

    // Assert the extracted value component matches the expected output
    assertArrayEquals(expectedData, valueImage);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testValueComponentOnNonExistentImage() {
    // Attempt to extract the value component from a non-existent image
    AbstractImageOperation operation = new ValueComponentOperation("nonExistentImage",
            "ValueImage");
    operation.performOperation(model);
  }


  @Test
  public void testValueComponentOnSinglePixelImage() throws IOException {
    int[][][] imageData = {
            {{128, 128, 128}}
    };
    String imageName = "singlePixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new ValueComponentOperation(imageName,
            "valueSinglePixelImage");
    operation.performOperation(model);

    int[][][] expectedData = {
            {{128, 128, 128}}
    };
    int[][][] valueImage = model.getImage("valueSinglePixelImage");

    assertArrayEquals(expectedData, valueImage);
  }


  @Test
  public void testNormalImageFlip() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
            {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
            {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}
    };
    String imageName = "NormalPixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new ValueComponentOperation(imageName,
            "valueImage");
    operation.performOperation(model);


    int[][][] expectedFlipped = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
            {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
            {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}
    };


    assertArrayEquals(expectedFlipped, model.getImage("valueImage"));
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

    AbstractImageOperation operation = new ValueComponentOperation(imageName,
            "valueImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedFlipped = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {4, 4, 4}, {5, 5, 5}}  // Should remain the same
    };
    assertArrayEquals(expectedFlipped, model.getImage("valueImage"));
  }

  @Test
  public void testSingleColumnImage() throws IOException {
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

    AbstractImageOperation operation = new ValueComponentOperation(imageName,
            "valueImage");
    operation.performOperation(model);

    int[][][] expectedFlipped = {
            {{1, 1, 1}},
            {{2, 2, 2}},
            {{3, 3, 3}},
            {{4, 4, 4}},
            {{5, 5, 5}}
    };
    assertArrayEquals(expectedFlipped, model.getImage("valueImage"));
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

    AbstractImageOperation operation = new ValueComponentOperation(imageName,
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
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
            {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
            {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}
    };
    String imageName = "NonSymmetricImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new ValueComponentOperation(imageName,
            "flippedNonSymmetricImage");
    operation.performOperation(model);

    int[][][] expectedFlipped = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
            {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
            {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}
    };
    assertArrayEquals(expectedFlipped, model.getImage("flippedNonSymmetricImage"));
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

    AbstractImageOperation operation = new ValueComponentOperation(imageName,
            "valueImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedFlipped = {
            {{1, 1, 1}}  // Should remain the same
    };
    assertArrayEquals(expectedFlipped, model.getImage("valueImage"));
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

    AbstractImageOperation operation = new ValueComponentOperation(imageName,
            "EvenNumberRowPixelImage");
    operation.performOperation(model);

    int[][][] expectedFlipped = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {4, 4, 4}},
            {{5, 5, 5}, {6, 6, 6}, {7, 7, 7}, {8, 8, 8}},
            {{9, 9, 9}, {10, 10, 10}, {11, 11, 11}, {12, 12, 12}},
            {{13, 13, 13}, {14, 14, 14}, {15, 15, 15}, {16, 16, 16}}
    };
    assertArrayEquals(expectedFlipped, model.getImage("EvenNumberRowPixelImage"));
  }

}