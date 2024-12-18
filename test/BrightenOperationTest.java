import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import model.ImageModel;
import model.ImageModelImpl;
import operations.AbstractImageOperation;
import operations.BrightenOperation;

import static org.junit.Assert.assertArrayEquals;

/**
 * This file tests the brighten operation.
 */

public class BrightenOperationTest {

  private ImageModel model;

  @Before
  public void setUp() {
    model = new ImageModelImpl();
  }

  @Test
  public void testBrightenOnSmallImage() throws IOException {
    int[][][] imageData = {
            {{255, 0, 0}, {0, 255, 0}},    // Red and Green pixels
            {{0, 0, 255}, {255, 255, 0}}   // Blue and Yellow pixels
    };

    String imageName = "small.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new BrightenOperation(50, imageName, "BrightenImage");
    operation.performOperation(model);

    int[][][] expectedData = {
            {{255, 50, 50}, {50, 255, 50}},  // Brightened Red and Green pixels
            {{50, 50, 255}, {255, 255, 50}}   // Brightened Blue and Yellow pixels
    };

    int[][][] brightenImage = model.getImage("BrightenImage");
    assertArrayEquals(expectedData, brightenImage);
  }


  @Test
  public void testBrightenOnLargeImage() throws IOException {
    int[][][] imageData = {
            {{255, 0, 0}, {0, 255, 0}, {0, 0, 255}},     // Red, Green, Blue
            {{255, 255, 0}, {255, 0, 255}, {0, 255, 255}}, // Yellow, Magenta, Cyan
            {{128, 128, 128}, {64, 64, 64}, {192, 192, 192}} // Various shades of gray
    };

    String imageName = "largeImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new BrightenOperation(50, imageName, "BrightenLargeImage");
    operation.performOperation(model);

    int[][][] expectedData = {
            {{255, 50, 50}, {50, 255, 50}, {50, 50, 255}},  // Brightened Red, Green, Blue
            {{255, 255, 50}, {255, 50, 255}, {50, 255, 255}}, // Brightened Yellow, Magenta, Cyan
            {{178, 178, 178}, {114, 114, 114}, {242, 242, 242}} // Brightened shades of gray
    };

    int[][][] brightenImage = model.getImage("BrightenLargeImage");
    assertArrayEquals(expectedData, brightenImage);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testBrightenOnNonExistentImage() {
    // Try to flip a non-existent image, should throw an exception
    AbstractImageOperation operation = new BrightenOperation(50,
        "nonExistentImage", "BrightenImage");
    operation.performOperation(model);
  }

  @Test
  public void testBrightenOnSinglePixelImage() throws IOException {
    int[][][] imageData = {
            {{0, 0, 0}}  // Black
    };
    String imageName = "singlePixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new BrightenOperation(50, imageName,
        "BrightenImage");
    operation.performOperation(model);

    int[][][] expectedData = {
            {{50, 50, 50}}  // Should brighten to (50, 50, 50)
    };
    assertArrayEquals(expectedData, model.getImage("BrightenImage"));
  }

  // Test normal image brightening with 3D array (RGB image)
  @Test
  public void testNormalImageBrighten() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
            {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
            {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}
    };
    String imageName = "NormalPixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new BrightenOperation(50,
        imageName, "BrightenNormalPixelImage");
    operation.performOperation(model);

    int[][][] expectedBrighten = {
            {{51, 51, 51}, {52, 52, 52}, {53, 53, 53}},
            {{54, 54, 54}, {55, 55, 55}, {56, 56, 56}},
            {{57, 57, 57}, {58, 58, 58}, {59, 59, 59}}
    };

    assertArrayEquals(expectedBrighten, model.getImage("BrightenNormalPixelImage"));
  }

  @Test
  public void testSingleRowImage() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {4, 4, 4}, {5, 5, 5}}
    };
    String imageName = "SingleRowPixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new BrightenOperation(50,
        imageName, "BrightenSingleRowPixelImage");
    operation.performOperation(model);

    int[][][] expectedBrighten = {
            {{51, 51, 51}, {52, 52, 52}, {53, 53, 53}, {54, 54, 54}, {55, 55, 55}}
    };
    assertArrayEquals(expectedBrighten, model.getImage("BrightenSingleRowPixelImage"));
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

    AbstractImageOperation operation = new BrightenOperation(50, imageName,
        "BrightenSingleColumnPixelImage");
    operation.performOperation(model);

    int[][][] expectedBrighten = {
            {{51, 51, 51}},
            {{52, 52, 52}},
            {{53, 53, 53}},
            {{54, 54, 54}},
            {{55, 55, 55}}
    };
    assertArrayEquals(expectedBrighten, model.getImage("BrightenSingleColumnPixelImage"));
  }


  // Test symmetric image Brighten (3D)
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

    AbstractImageOperation operation = new BrightenOperation(50, imageName,
        "BrightenSymmetricImage");
    operation.performOperation(model);

    int[][][] expectedBrighten = {
            {{51, 51, 51}, {52, 52, 52}, {53, 53, 53}},
            {{54, 54, 54}, {55, 55, 55}, {56, 56, 56}},
            {{51, 51, 51}, {52, 52, 52}, {53, 53, 53}}
    };
    assertArrayEquals(expectedBrighten, model.getImage("BrightenSymmetricImage"));
  }

  // Test non-symmetric image Brighten (3D)
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

    AbstractImageOperation operation = new BrightenOperation(50, imageName,
        "BrightenNonSymmetricImage");
    operation.performOperation(model);

    int[][][] expectedBrighten = {
            {{51, 51, 51}, {52, 52, 52}, {53, 53, 53}},
            {{54, 54, 54}, {55, 55, 55}, {56, 56, 56}},
            {{57, 57, 57}, {58, 58, 58}, {59, 59, 59}}
    };
    assertArrayEquals(expectedBrighten, model.getImage("BrightenNonSymmetricImage"));
  }

  @Test
  public void testOnePixelImage() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
            {{1, 1, 1}}  // Single pixel
    };
    String imageName = "SinglePixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new BrightenOperation(50, imageName,
        "BrightenSinglePixelImage");
    operation.performOperation(model);

    int[][][] expectedBrighten = {
            {{51, 51, 51}}  // Should brighten to (51, 51, 51)
    };
    assertArrayEquals(expectedBrighten, model.getImage("BrightenSinglePixelImage"));
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

    AbstractImageOperation operation = new BrightenOperation(50, imageName,
        "BrightenEvenNumberRowImage");
    operation.performOperation(model);

    int[][][] expectedBrighten = {
            {{51, 51, 51}, {52, 52, 52}, {53, 53, 53}, {54, 54, 54}},
            {{55, 55, 55}, {56, 56, 56}, {57, 57, 57}, {58, 58, 58}},
            {{59, 59, 59}, {60, 60, 60}, {61, 61, 61}, {62, 62, 62}},
            {{63, 63, 63}, {64, 64, 64}, {65, 65, 65}, {66, 66, 66}}
    };
    assertArrayEquals(expectedBrighten, model.getImage("BrightenEvenNumberRowImage"));
  }

  @Test
  public void testDarken() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
        {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {4, 4, 4}},
        {{5, 5, 5}, {6, 6, 6}, {7, 7, 7}, {8, 8, 8}},
        {{9, 9, 9}, {10, 10, 10}, {11, 11, 11}, {12, 12, 12}},
        {{13, 13, 13}, {14, 14, 14}, {15, 15, 15}, {16, 16, 16}}
    };
    String imageName = "EvenNumberRowImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new BrightenOperation(-2, imageName,
        "DarkenImage");
    operation.performOperation(model);

    int[][][] expectedBrighten = {
        {{0, 0, 0}, {0, 0, 0}, {1, 1, 1}, {2, 2, 2}},
        {{3, 3, 3}, {4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
        {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}, {10, 10, 10}},
        {{11, 11, 11}, {12, 12, 12}, {13, 13, 13}, {14, 14, 14}}
    };

    int[][][] image = model.getImage("DarkenImage");
    assertArrayEquals(expectedBrighten, image);
  }

}
