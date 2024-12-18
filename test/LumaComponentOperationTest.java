import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import model.ImageModel;
import model.ImageModelImpl;
import operations.AbstractImageOperation;
import operations.LumaComponentOperation;

import static org.junit.Assert.assertArrayEquals;

/**
 * This file tests the luma component operation.
 */
public class LumaComponentOperationTest {

  private ImageModel model;

  @Before
  public void setUp() {
    model = new ImageModelImpl();
  }

  @Test
  public void testLumaOnSmallImage() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
            {{255, 0, 0}, {0, 255, 0}},
            {{0, 0, 255}, {255, 255, 0}}
    };

    String imageName = "small.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new LumaComponentOperation(imageName, "lumaImage");
    operation.performOperation(model);

    int[][][] expectedData = {
            {{54, 54, 54}, {182, 182, 182}},
            {{18, 18, 18}, {236, 236, 236}}
    };

    int[][][] lumaImage = model.getImage("lumaImage");

    assertArrayEquals(expectedData, lumaImage);
  }


  @Test
  public void testLumaOnLargeImage() throws IOException {
    int[][][] imageData = {
            {{255, 0, 0}, {0, 255, 0}, {0, 0, 255}},
            {{255, 255, 0}, {255, 0, 255}, {0, 255, 255}},
            {{128, 128, 128}, {64, 64, 64}, {192, 192, 192}}
    };
    String imageName = "largeImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new LumaComponentOperation(imageName, "LumaLargeImage");
    operation.performOperation(model);

    int[][][] expectedData = {
            {{54, 54, 54}, {182, 182, 182}, {18, 18, 18}},
            {{236, 236, 236}, {72, 72, 72}, {200, 200, 200}},
            {{128, 128, 128}, {64, 64, 64}, {192, 192, 192}}
    };

    int[][][] lumaImage = model.getImage("LumaLargeImage");

    assertArrayEquals(expectedData, lumaImage);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testLumaOnNonExistentImage() {
    // Try to flip a non-existent image, should throw an exception
    AbstractImageOperation operation = new LumaComponentOperation("nonExistentImage", "lumaImage");
    operation.performOperation(model);
  }

  @Test
  public void testLumaOnSinglePixelImage() throws IOException {
    int[][][] imageData = {
            {{0, 0, 0}}  // Grey
    };
    String imageName = "singlePixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new LumaComponentOperation(imageName, "lumaImage");
    operation.performOperation(model);

    int[][][] lumaImage = model.getImage("lumaImage");

    assertArrayEquals(imageData, lumaImage);
  }


  @Test
  public void testNormalImageLuma() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
            {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
            {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}
    };
    String imageName = "NormalPixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new LumaComponentOperation(imageName,
        "LumaNormalPixelImage");
    operation.performOperation(model);

    int[][][] expectedLuma = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
            {{4, 4, 4}, {4, 4, 4}, {6, 6, 6}},
            {{6, 6, 6}, {8, 8, 8}, {9, 9, 9}}
    };
    int[][][] lumaImage = model.getImage("LumaNormalPixelImage");

    assertArrayEquals(expectedLuma, lumaImage);
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

    AbstractImageOperation operation = new LumaComponentOperation(imageName,
        "LumaSingleRowPixelImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedLuma = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {4, 4, 4}, {4, 4, 4}}  // Expected Luma output
    };

    int[][][] lumaImage = model.getImage("LumaSingleRowPixelImage");

    assertArrayEquals(expectedLuma, lumaImage);
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

    AbstractImageOperation operation = new LumaComponentOperation(imageName,
        "LumaSingleColumnPixelImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedLuma = {
            {{1, 1, 1}},
            {{2, 2, 2}},
            {{3, 3, 3}},
            {{4, 4, 4}},
            {{4, 4, 4}}
    };
    int[][][] lumaImage = model.getImage("LumaSingleColumnPixelImage");

    assertArrayEquals(expectedLuma, lumaImage);
  }


  // Test symmetric image Luma (3D)
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

    AbstractImageOperation operation = new LumaComponentOperation(imageName,
        "LumaSymmetricImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedLuma = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
            {{4, 4, 4}, {4, 4, 4}, {6, 6, 6}},
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}}
    };
    int[][][] lumaImage = model.getImage("LumaSymmetricImage");

    assertArrayEquals(expectedLuma, lumaImage);
  }


  /// Test non-symmetric image Luma (3D)
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

    AbstractImageOperation operation = new LumaComponentOperation(imageName,
        "LumaNonSymmetricImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedLuma = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
            {{4, 4, 4}, {4, 4, 4}, {6, 6, 6}},
            {{6, 6, 6}, {8, 8, 8}, {9, 9, 9}}
    };
    int[][][] lumaImage = model.getImage("LumaNonSymmetricImage");

    assertArrayEquals(expectedLuma, lumaImage);
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

    AbstractImageOperation operation = new LumaComponentOperation(imageName,
        "LumaSinglePixelImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedLuma = {
            {{1, 1, 1}}  // Should change to Luma value
    };
    int[][][] lumaImage = model.getImage("LumaSinglePixelImage");

    assertArrayEquals(expectedLuma, lumaImage);
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

    AbstractImageOperation operation = new LumaComponentOperation(imageName,
        "LumaEvenNumberRowImage");
    operation.performOperation(model);

    // Assert
    int[][][] expectedLuma = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {4, 4, 4}},
            {{4, 4, 4}, {6, 6, 6}, {6, 6, 6}, {8, 8, 8}},
            {{9, 9, 9}, {9, 9, 9}, {11, 11, 11}, {12, 12, 12}},
            {{12, 12, 12}, {13, 13, 13}, {15, 15, 15}, {16, 16, 16}}
    };
    int[][][] lumaImage = model.getImage("LumaEvenNumberRowImage");

    assertArrayEquals(expectedLuma, lumaImage);
  }
}