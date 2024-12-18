import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import model.ImageModel;
import model.ImageModelImpl;
import operations.AbstractImageOperation;
import operations.ColorCorrectOperation;

import static org.junit.Assert.assertArrayEquals;

/**
 * This file tests the ColorCorrect operation.
 */

public class ColorCorrectOperationTest {

  private ImageModel model;

  @Before
  public void setUp() {
    model = new ImageModelImpl();
  }

  @Test
  public void testColorCorrectOnSmallImage() throws IOException {
    int[][][] imageData = {
        {{255, 0, 0}, {0, 255, 0}},
        {{0, 0, 255}, {255, 255, 0}}
    };

    String imageName = "small.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new ColorCorrectOperation(imageName, "ColorCorrectImage");
    operation.performOperation(model);

    int[][][] expectedData = {{{255, 0, 0}, {0, 255, 0}}, {{0, 0, 255}, {255, 255, 0}}};

    int[][][] colorCorrectImage = model.getImage("ColorCorrectImage");
    assertArrayEquals(expectedData, colorCorrectImage);
  }

  @Test
  public void testColorCorrectOnLargeImage() throws IOException {
    int[][][] imageData = {
        {{255, 0, 0}, {0, 255, 0}, {0, 0, 255}},
        {{255, 255, 0}, {255, 0, 255}, {0, 255, 255}},
        {{128, 128, 128}, {64, 64, 64}, {192, 192, 192}}
    };
    String imageName = "largeImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new ColorCorrectOperation(imageName,
        "ColorCorrectLargeImage");
    operation.performOperation(model);

    int[][][] expectedData = {{{255, 0, 0}, {0, 255, 0}, {0, 0, 255}},
        {{255, 255, 0}, {255, 0, 255}, {0, 255, 255}},
        {{128, 128, 128}, {64, 64, 64}, {192, 192, 192}}};

    int[][][] colorCorrectImage = model.getImage("ColorCorrectLargeImage");
    assertArrayEquals(expectedData, colorCorrectImage);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testColorCorrectOnNonExistentImage() {
    // Try to flip a non-existent image, should throw an exception
    AbstractImageOperation operation = new ColorCorrectOperation("nonExistentImage",
        "ColorCorrectImage");
    operation.performOperation(model);
  }

  @Test
  public void testColorCorrectOnSinglePixelImage() throws IOException {
    int[][][] imageData = {
        {{0, 0, 0}}  // Grey
    };
    String imageName = "singlePixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new ColorCorrectOperation(imageName, "ColorCorrectImage");
    operation.performOperation(model);

    int[][][] colorCorrectredImage = model.getImage("ColorCorrectImage");
    assertArrayEquals(imageData, colorCorrectredImage);
  }


  // Test normal image flip with 3D array (RGB image)
  @Test
  public void testNormalImageColorCorrect() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
        {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
        {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
        {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}
    };
    String imageName = "NormalPixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new ColorCorrectOperation(imageName,
        "ColorCorrectNormalPixelImage");
    operation.performOperation(model);

    int[][][] expectedColorCorrect = {
        {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
        {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
        {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}
    };

    assertArrayEquals(expectedColorCorrect, model.getImage("ColorCorrectNormalPixelImage"));
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

    AbstractImageOperation operation = new ColorCorrectOperation(imageName,
        "ColorCorrectSingleRowPixelImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedColorCorrect = {
        {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {4, 4, 4}, {5, 5, 5}}    };
    assertArrayEquals(expectedColorCorrect, model.getImage("ColorCorrectSingleRowPixelImage"));
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

    AbstractImageOperation operation = new ColorCorrectOperation(imageName,
        "ColorCorrectSingleColumnPixelImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedColorCorrect = {
        {{1, 1, 1}},
        {{2, 2, 2}},
        {{3, 3, 3}},
        {{4, 4, 4}},
        {{5, 5, 5}}
    };
    assertArrayEquals(expectedColorCorrect, model.getImage("ColorCorrectSingleColumnPixelImage"));
  }


  // Test symmetric image ColorCorrect (3D)
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

    AbstractImageOperation operation = new ColorCorrectOperation(imageName,
        "ColorCorrectSymmetricImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedColorCorrect = {
        {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
        {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
        {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}}
    };
    assertArrayEquals(expectedColorCorrect, model.getImage("ColorCorrectSymmetricImage"));
  }


  /// Test non-symmetric image ColorCorrect (3D)
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

    AbstractImageOperation operation = new ColorCorrectOperation(imageName,
        "ColorCorrectNonSymmetricImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedColorCorrect = {
        {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
        {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
        {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}
    };
    assertArrayEquals(expectedColorCorrect, model.getImage("ColorCorrectNonSymmetricImage"));
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

    AbstractImageOperation operation = new ColorCorrectOperation(imageName,
        "ColorCorrectSinglePixelImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedColorCorrect = {
        {{1, 1, 1}}
    };
    assertArrayEquals(expectedColorCorrect, model.getImage("ColorCorrectSinglePixelImage"));
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

    AbstractImageOperation operation = new ColorCorrectOperation(imageName,
        "ColorCorrectEvenNumberRowImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedColorCorrect = {
        {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {4, 4, 4}},
        {{5, 5, 5}, {6, 6, 6}, {7, 7, 7}, {8, 8, 8}},
        {{9, 9, 9}, {10, 10, 10}, {11, 11, 11}, {12, 12, 12}},
        {{13, 13, 13}, {14, 14, 14}, {15, 15, 15}, {16, 16, 16}}
    };
    assertArrayEquals(expectedColorCorrect, model.getImage("ColorCorrectEvenNumberRowImage"));
  }
}