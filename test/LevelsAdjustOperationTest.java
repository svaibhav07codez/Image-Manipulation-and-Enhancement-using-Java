import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import model.ImageModel;
import model.ImageModelImpl;
import operations.AbstractImageOperation;
import operations.LevelsAdjustOperation;

import static org.junit.Assert.assertArrayEquals;

/**
 * This file tests the LevelsAdjust operation.
 */

public class LevelsAdjustOperationTest {

  private ImageModel model;

  @Before
  public void setUp() {
    model = new ImageModelImpl();
  }

  @Test
  public void testLevelsAdjustOnSmallImage() throws IOException {
    int[][][] imageData = {
        {{255, 0, 0}, {0, 255, 0}},    // Red and Green pixels
        {{0, 0, 255}, {255, 255, 0}}   // Blue and Yellow pixels
    };

    String imageName = "small.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new LevelsAdjustOperation("30", "138", "255", imageName,
        "LevelsAdjustImage");
    operation.performOperation(model);

    int[][][] expectedData = {{{238, 0, 0}, {0, 143, 0}}, {{0, 0, 143}, {238, 143, 0}}};

    int[][][] levelsAdjustImage = model.getImage("LevelsAdjustImage");

    assertArrayEquals(expectedData, levelsAdjustImage);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testLevelsAdjustOnNonExistentImage() {
    // Try to flip a non-existent image, should throw an exception
    AbstractImageOperation operation = new LevelsAdjustOperation("30", "138", "255",
        "nonExistentImage", "LevelsAdjustImage");
    operation.performOperation(model);
  }

  @Test
  public void testLevelsAdjustOnSinglePixelImage() throws IOException {
    int[][][] imageData = {
        {{0, 0, 0}}  // Black
    };
    String imageName = "singlePixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new LevelsAdjustOperation("30", "138", "255", imageName,
        "LevelsAdjustImage");
    operation.performOperation(model);

    int[][][] expectedData = {
        {{0, 0, 0}}  // Should LevelsAdjust to (50, 50, 50)
    };
    assertArrayEquals(expectedData, model.getImage("LevelsAdjustImage"));
  }

  // Test normal image LevelsAdjusting with 3D array (RGB image)
  @Test
  public void testNormalImageLevelsAdjust() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
        {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
        {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
        {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}
    };
    String imageName = "NormalPixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new LevelsAdjustOperation("30", "138", "255", imageName,
        "LevelsAdjustNormalPixelImage");
    operation.performOperation(model);

    int[][][] expectedLevelsAdjust = {{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
        {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}, {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}};


    assertArrayEquals(expectedLevelsAdjust, model.getImage("LevelsAdjustNormalPixelImage"));
  }

  @Test
  public void testSingleRowImage() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
        {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {4, 4, 4}, {5, 5, 5}}
    };
    String imageName = "SingleRowPixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new LevelsAdjustOperation("30", "138", "255", imageName,
        "LevelsAdjustSingleRowPixelImage");
    operation.performOperation(model);

    int[][][] expectedLevelsAdjust = {{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}}};


    assertArrayEquals(expectedLevelsAdjust, model.getImage("LevelsAdjustSingleRowPixelImage"));
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

    AbstractImageOperation operation = new LevelsAdjustOperation("30", "138", "255", imageName,
        "LevelsAdjustSingleColumnPixelImage");
    operation.performOperation(model);

    int[][][] expectedLevelsAdjust = {{{0, 0, 0}}, {{0, 0, 0}},
        {{0, 0, 0}}, {{0, 0, 0}}, {{0, 0, 0}}};

    assertArrayEquals(expectedLevelsAdjust, model.getImage("LevelsAdjustSingleColumnPixelImage"));
  }


  // Test symmetric image LevelsAdjust (3D)
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

    AbstractImageOperation operation = new LevelsAdjustOperation("30", "138", "255", imageName,
        "LevelsAdjustSymmetricImage");
    operation.performOperation(model);

    int[][][] expectedLevelsAdjust = {{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
        {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}, {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}};

    assertArrayEquals(expectedLevelsAdjust, model.getImage("LevelsAdjustSymmetricImage"));
  }

  // Test non-symmetric image LevelsAdjust (3D)
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

    AbstractImageOperation operation = new LevelsAdjustOperation("30", "138", "255", imageName,
        "LevelsAdjustNonSymmetricImage");
    operation.performOperation(model);

    int[][][] expectedLevelsAdjust = {{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
        {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}, {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}};


    assertArrayEquals(expectedLevelsAdjust, model.getImage("LevelsAdjustNonSymmetricImage"));
  }

  @Test
  public void testOnePixelImage() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
        {{1, 1, 1}}  // Single pixel
    };
    String imageName = "SinglePixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new LevelsAdjustOperation("30", "138", "255", imageName,
        "LevelsAdjustSinglePixelImage");
    operation.performOperation(model);

    int[][][] expectedLevelsAdjust = {
        {{0, 0, 0}}
    };

    assertArrayEquals(expectedLevelsAdjust, model.getImage("LevelsAdjustSinglePixelImage"));
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

    AbstractImageOperation operation = new LevelsAdjustOperation("30", "138", "255", imageName,
        "LevelsAdjustEvenNumberRowImage");
    operation.performOperation(model);

    int[][][] expectedLevelsAdjust = {{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
        {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
        {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
        {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}}};


    assertArrayEquals(expectedLevelsAdjust, model.getImage("LevelsAdjustEvenNumberRowImage"));
  }

  @Test
  public void testNormalImageDarken() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
        {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
        {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
        {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}
    };
    String imageName = "NormalPixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new LevelsAdjustOperation("30", "138", "255", imageName,
        "darkenNormalPixelImage");
    operation.performOperation(model);

    int[][][] expectedDarken = {{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
        {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}, {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}};


    assertArrayEquals(expectedDarken, model.getImage("darkenNormalPixelImage"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNormalImageBOutside() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
        {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
        {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
        {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}
    };
    String imageName = "NormalPixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new LevelsAdjustOperation("-10", "138", "255", imageName,
        "darkenNormalPixelImage");
    operation.performOperation(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNormalImageBMOutside() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
        {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
        {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
        {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}
    };
    String imageName = "NormalPixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new LevelsAdjustOperation("-10", "266", "255", imageName,
        "darkenNormalPixelImage");
    operation.performOperation(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNormalImageBMWOutside() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
        {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
        {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
        {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}
    };
    String imageName = "NormalPixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new LevelsAdjustOperation("-10", "266", "256", imageName,
        "darkenNormalPixelImage");
    operation.performOperation(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNormalImageBMWOutOfOrder() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
        {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
        {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
        {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}
    };
    String imageName = "NormalPixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new LevelsAdjustOperation("240", "20", "110", imageName,
        "darkenNormalPixelImage");
    operation.performOperation(model);
  }

}

