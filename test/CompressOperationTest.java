import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import model.ImageModel;
import model.ImageModelImpl;
import operations.AbstractImageOperation;
import operations.CompressOperation;

import static org.junit.Assert.assertArrayEquals;

/**
 * This file tests the compress operation.
 */

public class CompressOperationTest {

  private ImageModel model;

  @Before
  public void setUp() {
    model = new ImageModelImpl();
  }

  @Test
  public void testCompressOnSmallImage() throws IOException {
    int[][][] imageData = {
        {{255, 0, 0}, {0, 255, 0}},    // Red and Green pixels
        {{0, 0, 255}, {255, 255, 0}}   // Blue and Yellow pixels
    };

    String imageName = "small.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new CompressOperation(50, imageName,
        "CompressImage");
    operation.performOperation(model);

    int[][][] expectedData = {
        {{255, 0, 0}}
    };

    int[][][] compressImage = model.getImage("CompressImage");

    assertArrayEquals(expectedData, compressImage);
  }


  @Test
  public void testCompressOnLargeImage() throws IOException {
    int[][][] imageData = {
        {{255, 0, 0}, {0, 255, 0}, {0, 0, 255}},     // Red, Green, Blue
        {{255, 255, 0}, {255, 0, 255}, {0, 255, 255}}, // Yellow, Magenta, Cyan
        {{128, 128, 128}, {64, 64, 64}, {192, 192, 192}} // Various shades of gray
    };

    String imageName = "largeImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new CompressOperation(50, imageName,
        "CompressLargeImage");
    operation.performOperation(model);

    int[][][] expectedData = {
        {{255, 0, 0}}
    };

    int[][][] compressImage = model.getImage("CompressLargeImage");
    assertArrayEquals(expectedData, compressImage);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testCompressOnNonExistentImage() {
    // Try to flip a non-existent image, should throw an exception
    AbstractImageOperation operation = new CompressOperation(50,
        "nonExistentImage", "CompressImage");
    operation.performOperation(model);
  }

  @Test
  public void testCompressOnSinglePixelImage() throws IOException {
    int[][][] imageData = {
        {{0, 0, 0}}  // Black
    };
    String imageName = "singlePixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new CompressOperation(50, imageName,
        "CompressImage");
    operation.performOperation(model);

    int[][][] expectedData = {
        {{0, 0, 0}}  // Should Compress to (50, 50, 50)
    };
    assertArrayEquals(expectedData, model.getImage("CompressImage"));
  }

  // Test normal image Compressing with 3D array (RGB image)
  @Test
  public void testNormalImageCompress() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
        {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
        {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
        {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}
    };
    String imageName = "NormalPixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new CompressOperation(50, imageName,
        "CompressNormalPixelImage");
    operation.performOperation(model);

    int[][][] expectedCompress = {
        {{1, 1, 1}}
    };
    assertArrayEquals(expectedCompress, model.getImage("CompressNormalPixelImage"));
  }

  @Test
  public void testSingleRowImage() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
        {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {4, 4, 4}, {5, 5, 5}}
    };
    String imageName = "SingleRowPixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new CompressOperation(50, imageName,
        "CompressSingleRowPixelImage");
    operation.performOperation(model);

    int[][][] expectedCompress = {
        {{1, 1, 1}, {3, 3, 3}}
    };

    assertArrayEquals(expectedCompress, model.getImage("CompressSingleRowPixelImage"));
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

    AbstractImageOperation operation = new CompressOperation(50, imageName,
        "CompressSingleColumnPixelImage");
    operation.performOperation(model);

    int[][][] expectedCompress = {
        {{1, 1, 1}}, {{3, 3, 3}}
    };
    assertArrayEquals(expectedCompress, model.getImage("CompressSingleColumnPixelImage"));
  }


  // Test symmetric image Compress (3D)
  @Test
  public void testSymmetricImage() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
        {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
        {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
        {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}}
    };
    String imageName = "SymmetricImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new CompressOperation(50, imageName,
        "CompressSymmetricImage");
    operation.performOperation(model);

    int[][][] expectedCompress = {
        {{1,1,1}}
    };
    assertArrayEquals(expectedCompress, model.getImage("CompressSymmetricImage"));
  }

  // Test non-symmetric image Compress (3D)
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

    AbstractImageOperation operation = new CompressOperation(50, imageName,
        "CompressNonSymmetricImage");
    operation.performOperation(model);

    int[][][] expectedCompress = {
        {{1,1,1}}
    };
    assertArrayEquals(expectedCompress, model.getImage("CompressNonSymmetricImage"));
  }

  @Test
  public void testOnePixelImage() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
        {{1, 1, 1}}  // Single pixel
    };
    String imageName = "SinglePixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new CompressOperation(50, imageName,
        "CompressSinglePixelImage");
    operation.performOperation(model);

    int[][][] expectedCompress = {
        {{1,1,1}}
    };
    assertArrayEquals(expectedCompress, model.getImage("CompressSinglePixelImage"));
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

    AbstractImageOperation operation = new CompressOperation(50, imageName,
        "CompressEvenNumberRowImage");
    operation.performOperation(model);

    int[][][] expectedCompress = {
        {{1, 1, 1}, {3, 3, 3}},
        {{9, 9, 9}, {11, 11, 11}},
    };

    assertArrayEquals(expectedCompress, model.getImage("CompressEvenNumberRowImage"));
  }

  @Test
  public void testNormalImage() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
        {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
        {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
        {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}
    };
    String imageName = "NormalPixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new CompressOperation(50, imageName,
        "darkenNormalPixelImage");
    operation.performOperation(model);

    int[][][] expectedDarken = {
        {{1, 1, 1}}
    };

    assertArrayEquals(expectedDarken, model.getImage("darkenNormalPixelImage"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNormalImageLessThan0() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
        {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
        {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
        {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}
    };
    String imageName = "NormalPixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new CompressOperation(-20, imageName,
        "darkenNormalPixelImage");
    operation.performOperation(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNormalImageGreaterThan100() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
        {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
        {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
        {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}
    };
    String imageName = "NormalPixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new CompressOperation(110, imageName,
        "darkenNormalPixelImage");
    operation.performOperation(model);
  }

}

