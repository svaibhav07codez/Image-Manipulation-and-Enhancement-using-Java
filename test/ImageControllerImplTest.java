import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;
import java.io.StringReader;
import java.util.Scanner;

import controller.ImageController;
import controller.ImageControllerImpl;
import model.MockImageModelImpl;
import view.ImageMockViewImpl;
import view.ImageView;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;

import static org.junit.Assert.assertTrue;

/**
 * This class tests the Controller, if it's reading the input correctly and calling the correct
 * method in the model by using a mock model and log statements.
 */
public class ImageControllerImplTest {
  private ImageController controller;
  private ImageView view;
  private StringBuilder log;
  private ByteArrayOutputStream outputStream;

  @Before
  public void setUp() {
    log = new StringBuilder();
    outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
  }

  @Test
  public void testLoadCommand() throws Exception {
    StringReader input = new StringReader("load C:\\Users\\vaibhav\\IdeaProjects" +
        "\\assign6\\res\\img.jpeg testImage\nexit");
    view = new ImageMockViewImpl(new Scanner(input));
    controller = new ImageControllerImpl(new MockImageModelImpl(log), view);
    controller.execute();
    assertEquals("loaded testImage", log.toString());
  }

  @Test
  public void testSaveCommand() throws Exception {
    StringReader input = new StringReader("save C:\\Users\\vaibhav\\IdeaProjects" +
        "\\assign6\\res\\img-new.jpeg testImage\nexit");
    view = new ImageMockViewImpl(new Scanner(input));
    controller = new ImageControllerImpl(new MockImageModelImpl(log), view);
    controller.execute();
    assertEquals("save C:\\Users\\vaibhav\\IdeaProjects\\assign6\\res" +
        "\\img-new.jpeg testImage", log.toString());
  }

  @Test
  public void testBrightenCommand() throws Exception {
    StringReader input = new StringReader("brighten 50 testImage brightenedImage\nexit");
    view = new ImageMockViewImpl(new Scanner(input));
    controller = new ImageControllerImpl(new MockImageModelImpl(log), view);
    controller.execute();
    assertTrue(log.toString().contains("brighten 50 testImage brightenedImage"));
  }

  @Test
  public void testBlurCommand() throws Exception {
    StringReader input = new StringReader("blur testImage blurredImage\nexit");
    view = new ImageMockViewImpl(new Scanner(input));
    controller = new ImageControllerImpl(new MockImageModelImpl(log), view);
    controller.execute();
    assertTrue(log.toString().contains("blur testImage blurredImage"));
  }

  @Test
  public void testRedComponentCommand() throws Exception {
    StringReader input = new StringReader("red-component testImage redImage\nexit");
    view = new ImageMockViewImpl(new Scanner(input));
    controller = new ImageControllerImpl(new MockImageModelImpl(log), view);
    controller.execute();
    assertTrue(log.toString().contains("red-component testImage redImage"));
  }

  @Test
  public void testGreenComponentCommand() throws Exception {
    StringReader input = new StringReader("green-component testImage greenImage\nexit");
    view = new ImageMockViewImpl(new Scanner(input));
    controller = new ImageControllerImpl(new MockImageModelImpl(log), view);
    controller.execute();
    assertTrue(log.toString().contains("green-component testImage greenImage"));
  }

  @Test
  public void testBlueComponentCommand() throws Exception {
    StringReader input = new StringReader("blue-component testImage blueImage\nexit");
    view = new ImageMockViewImpl(new Scanner(input));
    controller = new ImageControllerImpl(new MockImageModelImpl(log), view);
    controller.execute();
    assertTrue(log.toString().contains("blue-component testImage blueImage"));
  }

  @Test
  public void testHorizontalFlipCommand() throws Exception {
    StringReader input = new StringReader("horizontal-flip testImage flippedImage\nexit");
    view = new ImageMockViewImpl(new Scanner(input));
    controller = new ImageControllerImpl(new MockImageModelImpl(log), view);
    controller.execute();
    assertTrue(log.toString().contains("horizontal-flip testImage flippedImage"));
  }

  @Test
  public void testVerticalFlipCommand() throws Exception {
    StringReader input = new StringReader("vertical-flip testImage flippedImage\nexit");
    view = new ImageMockViewImpl(new Scanner(input));
    controller = new ImageControllerImpl(new MockImageModelImpl(log), view);
    controller.execute();
    assertTrue(log.toString().contains("vertical-flip testImage flippedImage"));
  }

  @Test
  public void testSepiaCommand() throws Exception {
    StringReader input = new StringReader("sepia testImage sepiaImage\nexit");
    view = new ImageMockViewImpl(new Scanner(input));
    controller = new ImageControllerImpl(new MockImageModelImpl(log), view);
    controller.execute();
    assertTrue(log.toString().contains("sepia testImage sepiaImage"));
  }

  @Test
  public void testSharpenCommand() throws Exception {
    StringReader input = new StringReader("sharpen testImage sharpenImage\nexit");
    view = new ImageMockViewImpl(new Scanner(input));
    controller = new ImageControllerImpl(new MockImageModelImpl(log), view);
    controller.execute();
    assertTrue(log.toString().contains("sharpen testImage sharpenImage"));
  }

  @Test
  public void testIntensityCommand() throws Exception {
    StringReader input = new StringReader("intensity-component testImage intensityImage\nexit");
    view = new ImageMockViewImpl(new Scanner(input));
    controller = new ImageControllerImpl(new MockImageModelImpl(log), view);
    controller.execute();
    assertTrue(log.toString().contains("intensity-component testImage intensityImage"));
  }

  @Test
  public void testLumaCommand() throws Exception {
    StringReader input = new StringReader("luma-component testImage lumaImage\nexit");
    view = new ImageMockViewImpl(new Scanner(input));
    controller = new ImageControllerImpl(new MockImageModelImpl(log), view);
    controller.execute();
    assertTrue(log.toString().contains("luma-component testImage lumaImage"));
  }

  @Test
  public void testValueCommand() throws Exception {
    StringReader input = new StringReader("value-component testImage valueImage\nexit");
    view = new ImageMockViewImpl(new Scanner(input));
    controller = new ImageControllerImpl(new MockImageModelImpl(log), view);
    controller.execute();
    assertTrue(log.toString().contains("value-component testImage valueImage"));
  }

  @Test
  public void testRGBSplitCommand() throws Exception {
    StringReader input = new StringReader("rgb-split testImage rImage gImage bImage \nexit");
    view = new ImageMockViewImpl(new Scanner(input));
    controller = new ImageControllerImpl(new MockImageModelImpl(log), view);
    controller.execute();
    assertTrue(log.toString().contains("rgb-split testImage rImage gImage bImage"));
  }

  @Test
  public void testRGBCombineCommand() throws Exception {
    StringReader input = new StringReader("rgb-combine testImage rImage gImage bImage \nexit");
    view = new ImageMockViewImpl(new Scanner(input));
    controller = new ImageControllerImpl(new MockImageModelImpl(log), view);
    controller.execute();
    assertTrue(log.toString().contains("rgb-combine testImage rImage gImage bImage"));
  }

  @Test
  public void testCompressCommand() throws Exception {
    StringReader input = new StringReader("compress 20 testImage compressedImage \nexit");
    view = new ImageMockViewImpl(new Scanner(input));
    controller = new ImageControllerImpl(new MockImageModelImpl(log), view);
    controller.execute();
    assertTrue(log.toString().contains("compress 20.000000 testImage compressedImage"));
  }

  @Test
  public void testColorCorrectCommand() throws Exception {
    StringReader input = new StringReader("color-correct testImage colorCorrectedImage \nexit");
    view = new ImageMockViewImpl(new Scanner(input));
    controller = new ImageControllerImpl(new MockImageModelImpl(log), view);
    controller.execute();
    assertTrue(log.toString().contains("color-correct testImage colorCorrectedImage"));
  }

  @Test
  public void testHistogramCommand() throws Exception {
    StringReader input = new StringReader("histogram testImage histogramImage \nexit");
    view = new ImageMockViewImpl(new Scanner(input));
    controller = new ImageControllerImpl(new MockImageModelImpl(log), view);
    controller.execute();
    assertTrue(log.toString().contains("histogram testImage histogramImage"));
  }

  @Test
  public void testLevelsAdjustCommand() throws Exception {
    StringReader input
        = new StringReader("levels-adjust 10 10 10 testImage levelsAdjustImage\nexit");
    view = new ImageMockViewImpl(new Scanner(input));
    controller = new ImageControllerImpl(new MockImageModelImpl(log), view);
    controller.execute();
    assertTrue(log.toString().contains("levels-adjust 10 10 10 testImage levelsAdjustImage"));
  }

  @Test
  public void testSplitLevelsAdjustCommand() throws Exception {
    StringReader input
        = new StringReader("levels-adjust 10 10 10 testImage levelsAdjustImage split 50\nexit");
    view = new ImageMockViewImpl(new Scanner(input));
    controller = new ImageControllerImpl(new MockImageModelImpl(log), view);
    controller.execute();
    assertTrue(log.toString().contains("levels-adjust 10 10 10 testImage levelsAdjustImage" +
        " split 50.000000 testImage levelsAdjustImage"));
  }

  @Test
  public void testSplitSepiaCommand() throws Exception {
    StringReader input = new StringReader("sepia testImage sepiaImage split 50\nexit");
    view = new ImageMockViewImpl(new Scanner(input));
    controller = new ImageControllerImpl(new MockImageModelImpl(log), view);
    controller.execute();
    assertTrue(log.toString().contains("sepia testImage sepiaImage" +
        " split 50.000000 testImage sepiaImage"));
  }

  @Test
  public void testRunScript() throws Exception {
    view = new ImageMockViewImpl();
    controller = new ImageControllerImpl(new MockImageModelImpl(log), view);
    controller.runScript("C:\\Users\\vaibhav\\IdeaProjects\\assign6\\res\\run.txt");
    String output = outputStream.toString().trim();
    String message = "Executing: load img.jpeg img\r\n" +
        "Image is Loaded Successfully! - img\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: brighten 50 img img-brighter\r\n" +
        "img is Brightened Successfully! - img-brighter\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: blur img img-blur\r\n" +
        "img is blurred Successfully! - img-blur\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: sharpen img img-sharpen\r\n" +
        "img is sharpened Successfully! - img-sharpen\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: red-component img img-red\r\n" +
        "Red Component of img is extracted Successfully! - img-red\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: green-component img img-green\r\n" +
        "Green Component of img is extracted Successfully! - img-green\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: blue-component img img-blue\r\n" +
        "Blue Component of img is extracted Successfully! - img-blue\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: value-component img img-value\r\n" +
        "Value Component of img is extracted Successfully! - img-value\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: luma-component img img-luma\r\n" +
        "Luma Component of img is extracted Successfully! - img-luma\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: intensity-component img img-intensity\r\n" +
        "Intensity Component of img is extracted Successfully! - img-intensity\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: vertical-flip img img-vertical\r\n" +
        "img is flipped Vertically - img-vertical\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: horizontal-flip img img-horizontal\r\n" +
        "img is flipped Horizontally! - img-horizontal\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: sepia img img-sepia\r\n" +
        "Sepia filter is applied on img Successfully! - img-sepia\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: horizontal-flip img-vertical img-vertical-horizontal\r\n" +
        "img-vertical is flipped Horizontally! - img-vertical-horizontal\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: value-component img img-greyscale\r\n" +
        "Value Component of img is extracted Successfully! - img-greyscale\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: save img-brighter.png img-brighter\r\n" +
        "Image is Saved Successfully!\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: save img-gs.jpeg img-greyscale\r\n" +
        "Image is Saved Successfully!\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: load img1.png img\r\n" +
        "Error: Can't read input file!\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: rgb-split img img-red img-green img-blue\r\n" +
        "img is split Successfully! - img-red img-green img-blue\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: brighten 50 img-red img-red\r\n" +
        "img-red is Brightened Successfully! - img-red\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: rgb-combine img-red-tint img-red img-green img-blue\r\n" +
        "img-red img-green img-blue are combined Successfully! - img-red-tint\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: save img-red-tint.jpeg img-red-tint\r\n" +
        "Image is Saved Successfully!\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: compress 20 img img-compressed\r\n" +
        "img is compressed by 20.0% of img-compressed Successfully!\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: save img-compressed.jpeg img-compressed\r\n" +
        "Image is Saved Successfully!\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: histogram img img-histogram\r\n" +
        "Histogram is generated for img\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: save img-histogram.jpeg img-histogram\r\n" +
        "Image is Saved Successfully!\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: color-correct img img-colored\r\n" +
        "Color of img is corrected - img-colored\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: save img-colored.jpeg img-colored\r\n" +
        "Image is Saved Successfully!\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: levels-adjust 20 130 255 img img-adjusted\r\n" +
        "img is Adjusted Successfully! - img-adjusted\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: save img-adjusted.jpeg img-adjusted\r\n" +
        "Image is Saved Successfully!\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: blur img img-split split 70\r\n" +
        "img is blurred Successfully! - img-split\r\n" +
        "Image is split at 70.0%.\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: save img-split.jpeg img-split\r\n" +
        "Image is Saved Successfully!\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: blur img img-blurSplit split 70\r\n" +
        "img is blurred Successfully! - img-blurSplit\r\n" +
        "Image is split at 70.0%.\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: save img-blurSplit.jpeg img-blurSplit\r\n" +
        "Image is Saved Successfully!\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: sharpen img img-sharpenSplit split 70\r\n" +
        "img is sharpened Successfully! - img-sharpenSplit\r\n" +
        "Image is split at 70.0%.\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: save img-sharpenSplit.jpeg img-sharpenSplit\r\n" +
        "Image is Saved Successfully!\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: brighten 50 img img-brightenSplit split 70\r\n" +
        "img is Brightened Successfully! - img-brightenSplit\r\n" +
        "Error: Split operation is unsupported.\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: save img-brightenSplit.jpeg img-brightenSplit\r\n" +
        "Image is Saved Successfully!\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: blue-component img img-blueSplit split 70\r\n" +
        "Blue Component of img is extracted Successfully! - img-blueSplit\r\n" +
        "Image is split at 70.0%.\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: save img-blueSplit.jpeg img-blueSplit\r\n" +
        "Image is Saved Successfully!\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: green-component img img-greenSplit split 70\r\n" +
        "Green Component of img is extracted Successfully! - img-greenSplit\r\n" +
        "Image is split at 70.0%.\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: save img-greenSplit.jpeg img-greenSplit\r\n" +
        "Image is Saved Successfully!\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: red-component img img-redSplit split 70\r\n" +
        "Red Component of img is extracted Successfully! - img-redSplit\r\n" +
        "Image is split at 70.0%.\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: save img-redSplit.jpeg img-redSplit\r\n" +
        "Image is Saved Successfully!\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: luma-component img img-lumaSplit split 70\r\n" +
        "Luma Component of img is extracted Successfully! - img-lumaSplit\r\n" +
        "Image is split at 70.0%.\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: save img-lumaSplit.jpeg img-lumaSplit\r\n" +
        "Image is Saved Successfully!\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: intensity-component img img-intensitySplit split 70\r\n" +
        "Intensity Component of img is extracted Successfully! - img-intensitySplit\r\n" +
        "Image is split at 70.0%.\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: save img-intensitySplit.jpeg img-intensitySplit\r\n" +
        "Image is Saved Successfully!\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: value-component img img-valueSplit split 70\r\n" +
        "Value Component of img is extracted Successfully! - img-valueSplit\r\n" +
        "Image is split at 70.0%.\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: save img-valueSplit.jpeg img-valueSplit\r\n" +
        "Image is Saved Successfully!\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: sepia img img-sepiaSplit split 70\r\n" +
        "Sepia filter is applied on img Successfully! - img-sepiaSplit\r\n" +
        "Image is split at 70.0%.\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: save img-sepiaSplit.jpeg img-sepiaSplit\r\n" +
        "Image is Saved Successfully!\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: color-correct img img-ccSplit split 70\r\n" +
        "Color of img is corrected - img-ccSplit\r\n" +
        "Image is split at 70.0%.\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: save img-colorCorrectSplit.jpeg img-ccSplit\r\n" +
        "Image is Saved Successfully!\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: levels-adjust 30 138 255 img img-laSplit split 70\r\n" +
        "img is Adjusted Successfully! - img-laSplit\r\n" +
        "Image is split at 70.0%.\r\n" +
        "Error: Unable to display image.\r\n" +
        "Executing: save img-laSplit.jpeg img-laSplit\r\n" +
        "Image is Saved Successfully!\r\n" +
        "Error: Unable to display image.";
    assertEquals("Message displayed should match the expected output", message, output);
  }

  @Test
  public void testInvalidSplit() throws Exception {
    StringReader input = new StringReader("horizontal-flip testImage sepiaImage split 50\nexit");
    view = new ImageMockViewImpl(new Scanner(input));
    controller = new ImageControllerImpl(new MockImageModelImpl(log), view);
    controller.execute();
    assertTrue(log.toString().isEmpty());
  }

  @Test
  public void testUnknownCommand() throws Exception {
    StringReader input = new StringReader("unknown-command testImage\nexit");
    view = new ImageMockViewImpl(new Scanner(input));
    controller = new ImageControllerImpl(new MockImageModelImpl(log), view);
    try {
      controller.execute();
    } catch (IllegalArgumentException e) {
      assertTrue(e.getMessage().contains("Unknown command"));
    }
  }
}