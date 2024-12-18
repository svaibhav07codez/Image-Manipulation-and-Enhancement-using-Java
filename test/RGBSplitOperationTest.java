import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import model.ImageModel;
import model.ImageModelImpl;
import operations.AbstractImageOperation;
import operations.RGBSplitOperation;

import static org.junit.Assert.assertArrayEquals;

/**
 * This file tests the RGB Split operation.
 */

public class RGBSplitOperationTest {

  private ImageModel model;

  @Before
  public void setUp() {
    model = new ImageModelImpl();
  }

  @Test
  public void testRGBSplitOnSmallImage() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
            {{255, 0, 0}, {0, 255, 0}},   // Red and Green
            {{0, 0, 255}, {255, 255, 0}}  // Blue and Yellow (Red + Green)
    };

    String imageName = "small.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new RGBSplitOperation(imageName, "redDest",
            "greenDest", "blueDest");
    operation.performOperation(model);

    // Expected data for the red, green, and blue components
    int[][][] expectedRedData = {{{255, 255, 255}, {0, 0, 0}}, {{0, 0, 0}, {255, 255, 255}}};

    int[][][] expectedGreenData = {{{0, 0, 0}, {255, 255, 255}}, {{0, 0, 0}, {255, 255, 255}}};

    int[][][] expectedBlueData = {{{0, 0, 0}, {0, 0, 0}}, {{255, 255, 255}, {0, 0, 0}}};

    // Retrieve the split images
    int[][][] redImage = model.getImage("redDest");
    int[][][] greenImage = model.getImage("greenDest");
    int[][][] blueImage = model.getImage("blueDest");

    // Validate the results
    assertArrayEquals(expectedRedData, redImage);
    assertArrayEquals(expectedGreenData, greenImage);
    assertArrayEquals(expectedBlueData, blueImage);
  }

  @Test
  public void testRGBSplitOnLargeImage() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
            {{255, 0, 0}, {0, 255, 0}, {0, 0, 255}},
            {{255, 255, 0}, {255, 0, 255}, {0, 255, 255}},
            {{128, 128, 128}, {64, 64, 64}, {192, 192, 192}}
    };
    String imageName = "largeImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new RGBSplitOperation(imageName, "redDest",
            "greenDest", "blueDest");
    operation.performOperation(model);

    // Expected data for the red, green, and blue components
    int[][][] expectedRedData = {
            {{255, 255, 255}, {0, 0, 0}, {0, 0, 0}},
            {{255, 255, 255}, {255, 255, 255}, {0, 0, 0}},
            {{128, 128, 128}, {64, 64, 64}, {192, 192, 192}}
    };

    int[][][] expectedGreenData = {
            {{0, 0, 0}, {255, 255, 255}, {0, 0, 0}},
            {{255, 255, 255}, {0, 0, 0}, {255, 255, 255}},
            {{128, 128, 128}, {64, 64, 64}, {192, 192, 192}}
    };


    int[][][] expectedBlueData = {
            {{0, 0, 0}, {0, 0, 0}, {255, 255, 255}},
            {{0, 0, 0}, {255, 255, 255}, {255, 255, 255}},
            {{128, 128, 128}, {64, 64, 64}, {192, 192, 192}}
    };


    // Retrieve the split images
    int[][][] redImage = model.getImage("redDest");
    int[][][] greenImage = model.getImage("greenDest");
    int[][][] blueImage = model.getImage("blueDest");

    // Validate the results
    assertArrayEquals(expectedRedData, redImage);
    assertArrayEquals(expectedGreenData, greenImage);
    assertArrayEquals(expectedBlueData, blueImage);
  }


  @Test
  public void testRGBSplitOnSinglePixelImage() throws IOException {
    int[][][] imageData = {
            {{0, 0, 0}}  // Black (R=0, G=0, B=0)
    };
    String imageName = "singlePixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new RGBSplitOperation(imageName, "redDest",
            "greenDest", "blueDest");
    operation.performOperation(model);

    // Expected split result for a black pixel
    int[][][] expectedRed = {{{0, 0, 0}}};   // Red channel
    int[][][] expectedGreen = {{{0, 0, 0}}}; // Green channel
    int[][][] expectedBlue = {{{0, 0, 0}}};  // Blue channel

    // Get the split images
    int[][][] redImage = model.getImage("redDest");
    int[][][] greenImage = model.getImage("greenDest");
    int[][][] blueImage = model.getImage("blueDest");

    assertArrayEquals(expectedRed, redImage);
    assertArrayEquals(expectedGreen, greenImage);
    assertArrayEquals(expectedBlue, blueImage);
  }

  @Test
  public void testNormalImageRGBSplit() throws IOException {
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}, // RGB values
            {{10, 11, 12}, {13, 14, 15}, {16, 17, 18}},
            {{19, 20, 21}, {22, 23, 24}, {25, 26, 27}}
    };
    String imageName = "NormalPixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new RGBSplitOperation(imageName, "redDest",
            "greenDest", "blueDest");
    operation.performOperation(model);

    // Expected split results
    int[][][] expectedRed = {
            {{1, 1, 1}, {4, 4, 4}, {7, 7, 7}},
            {{10, 10, 10}, {13, 13, 13}, {16, 16, 16}},
            {{19, 19, 19}, {22, 22, 22}, {25, 25, 25}}
    };

    int[][][] expectedGreen = {
            {{2, 2, 2}, {5, 5, 5}, {8, 8, 8}},
            {{11, 11, 11}, {14, 14, 14}, {17, 17, 17}},
            {{20, 20, 20}, {23, 23, 23}, {26, 26, 26}}
    };


    int[][][] expectedBlue = {
            {{3, 3, 3}, {6, 6, 6}, {9, 9, 9}},
            {{12, 12, 12}, {15, 15, 15}, {18, 18, 18}},
            {{21, 21, 21}, {24, 24, 24}, {27, 27, 27}}
    };

    // Get the split images
    int[][][] redImage = model.getImage("redDest");
    int[][][] greenImage = model.getImage("greenDest");
    int[][][] blueImage = model.getImage("blueDest");

    assertArrayEquals(expectedRed, redImage);
    assertArrayEquals(expectedGreen, greenImage);
    assertArrayEquals(expectedBlue, blueImage);
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

    AbstractImageOperation operation = new RGBSplitOperation(imageName, "redDest",
            "greenDest", "blueDest");
    operation.performOperation(model);

    // Assert
    int[][][] expectedRedSplit = {{{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {4, 4, 4}, {5, 5, 5}}};

    int[][][] expectedGreenSplit = {{{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {4, 4, 4}, {5, 5, 5}}};

    int[][][] expectedBlueSplit = {{{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {4, 4, 4}, {5, 5, 5}}};

    // Retrieve RGB split images
    int[][][] redSplitImage = model.getImage("redDest");
    int[][][] greenSplitImage = model.getImage("greenDest");
    int[][][] blueSplitImage = model.getImage("blueDest");

    // Assert each color channel split
    assertArrayEquals(expectedRedSplit, redSplitImage);
    assertArrayEquals(expectedGreenSplit, greenSplitImage);
    assertArrayEquals(expectedBlueSplit, blueSplitImage);
  }


  @Test
  public void testSingleColumnImage() throws IOException {
    // Arrange
    ImageModel model = new ImageModelImpl();
    int[][][] imageData = {
            {{1, 1, 1}},  // RGB (1, 1, 1)
            {{2, 2, 2}},  // RGB (2, 2, 2)
            {{3, 3, 3}},  // RGB (3, 3, 3)
            {{4, 4, 4}},  // RGB (4, 4, 4)
            {{5, 5, 5}}   // RGB (5, 5, 5)
    };
    String imageName = "SingleColumnPixelImage.png";
    model.saveImage(imageName, imageData);

    AbstractImageOperation operation = new RGBSplitOperation(imageName, "redDest",
            "greenDest", "blueDest");
    operation.performOperation(model);

    // Assert
    int[][][] expectedRedSplit = {{{1, 1, 1}}, {{2, 2, 2}}, {{3, 3, 3}}, {{4, 4, 4}}, {{5, 5, 5}}};

    int[][][] expectedGreenSplit = {
        {{1, 1, 1}}, {{2, 2, 2}}, {{3, 3, 3}}, {{4, 4, 4}}, {{5, 5, 5}}
    };

    int[][][] expectedBlueSplit = {
        {{1, 1, 1}},
        {{2, 2, 2}},
        {{3, 3, 3}},
        {{4, 4, 4}},
        {{5, 5, 5}}
    };

    // Fetch the split images
    int[][][] redSplitImage = model.getImage("redDest");
    int[][][] greenSplitImage = model.getImage("greenDest");
    int[][][] blueSplitImage = model.getImage("blueDest");


    // Assert the expected outputs for each split
    assertArrayEquals(expectedRedSplit, redSplitImage);
    assertArrayEquals(expectedGreenSplit, greenSplitImage);
    assertArrayEquals(expectedBlueSplit, blueSplitImage);
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

    AbstractImageOperation operation = new RGBSplitOperation(imageName, "redDest",
            "greenDest", "blueDest");
    operation.performOperation(model);

    // Assert
    int[][][] expectedRedSplit = {{{1, 1, 1}, {2, 2, 2}, {3, 3, 3}}, {{4, 4, 4}, {5, 5, 5},
            {6, 6, 6}}, {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}}};

    int[][][] expectedGreenSplit = {{{1, 1, 1}, {2, 2, 2}, {3, 3, 3}}, {{4, 4, 4}, {5, 5, 5},
            {6, 6, 6}}, {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}}};

    int[][][] expectedBlueSplit = {{{1, 1, 1}, {2, 2, 2}, {3, 3, 3}}, {{4, 4, 4}, {5, 5, 5},
            {6, 6, 6}}, {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}}};

    int[][][] redSplitImage = model.getImage("redDest");
    int[][][] greenSplitImage = model.getImage("greenDest");
    int[][][] blueSplitImage = model.getImage("blueDest");

    assertArrayEquals(expectedRedSplit, redSplitImage);
    assertArrayEquals(expectedGreenSplit, greenSplitImage);
    assertArrayEquals(expectedBlueSplit, blueSplitImage);
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

    AbstractImageOperation operation = new RGBSplitOperation(imageName, "redDest",
            "greenDest", "blueDest");
    operation.performOperation(model);

    // Assert
    int[][][] expectedRedSplit = {{{1, 1, 1}, {2, 2, 2}, {3, 3, 3}}, {{4, 4, 4}, {5, 5, 5},
            {6, 6, 6}}, {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}};
    int[][][] expectedGreenSplit = {{{1, 1, 1}, {2, 2, 2}, {3, 3, 3}}, {{4, 4, 4}, {5, 5, 5},
            {6, 6, 6}}, {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}};

    int[][][] expectedBlueSplit = {{{1, 1, 1}, {2, 2, 2}, {3, 3, 3}}, {{4, 4, 4}, {5, 5, 5},
            {6, 6, 6}}, {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}};


    int[][][] redSplitImage = model.getImage("redDest");
    int[][][] greenSplitImage = model.getImage("greenDest");
    int[][][] blueSplitImage = model.getImage("blueDest");

    assertArrayEquals(expectedRedSplit, redSplitImage);
    assertArrayEquals(expectedGreenSplit, greenSplitImage);
    assertArrayEquals(expectedBlueSplit, blueSplitImage);
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

    AbstractImageOperation operation = new RGBSplitOperation(imageName, "redDest",
            "greenDest", "blueDest");
    operation.performOperation(model);

    // Assert
    int[][][] expectedRedSplit = {{{1, 1, 1}}};
    int[][][] expectedGreenSplit = {{{1, 1, 1}}};
    int[][][] expectedBlueSplit = {{{1, 1, 1}}};

    int[][][] redSplitImage = model.getImage("redDest");
    int[][][] greenSplitImage = model.getImage("greenDest");
    int[][][] blueSplitImage = model.getImage("blueDest");

    assertArrayEquals(expectedRedSplit, redSplitImage);
    assertArrayEquals(expectedGreenSplit, greenSplitImage);
    assertArrayEquals(expectedBlueSplit, blueSplitImage);
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

    AbstractImageOperation operation = new RGBSplitOperation(imageName, "redDest",
            "greenDest", "blueDest");
    operation.performOperation(model);

    // Assert
    int[][][] expectedRedSplit = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {4, 4, 4}},
            {{5, 5, 5}, {6, 6, 6}, {7, 7, 7}, {8, 8, 8}},
            {{9, 9, 9}, {10, 10, 10}, {11, 11, 11}, {12, 12, 12}},
            {{13, 13, 13}, {14, 14, 14}, {15, 15, 15}, {16, 16, 16}}
    };
    int[][][] expectedGreenSplit = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {4, 4, 4}},
            {{5, 5, 5}, {6, 6, 6}, {7, 7, 7}, {8, 8, 8}},
            {{9, 9, 9}, {10, 10, 10}, {11, 11, 11}, {12, 12, 12}},
            {{13, 13, 13}, {14, 14, 14}, {15, 15, 15}, {16, 16, 16}}
    };
    int[][][] expectedBlueSplit = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {4, 4, 4}},
            {{5, 5, 5}, {6, 6, 6}, {7, 7, 7}, {8, 8, 8}},
            {{9, 9, 9}, {10, 10, 10}, {11, 11, 11}, {12, 12, 12}},
            {{13, 13, 13}, {14, 14, 14}, {15, 15, 15}, {16, 16, 16}}
    };

    int[][][] redSplitImage = model.getImage("redDest");
    int[][][] greenSplitImage = model.getImage("greenDest");
    int[][][] blueSplitImage = model.getImage("blueDest");

    assertArrayEquals(expectedRedSplit, redSplitImage);
    assertArrayEquals(expectedGreenSplit, greenSplitImage);
    assertArrayEquals(expectedBlueSplit, blueSplitImage);
  }
}
