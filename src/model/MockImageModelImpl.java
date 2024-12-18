package model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is a mock class of mvc.ImageModelImpl used solely for testing the controller.
 */
public class MockImageModelImpl implements ImageModel {

  private final StringBuilder log;
  private Map<String, int[][][]> images = new HashMap<String, int[][][]>();

  public MockImageModelImpl(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void loadImage(int[][][] filePath, String imageName) {
    log.append(String.format("loaded %s", imageName));
  }

  @Override
  public void saveImage(String filePath, int[][][] imageName) throws IOException {
    log.append(String.format("save %s testImage", filePath));
  }

  @Override
  public int[][][] getImage(String imageName) {
    int[][][] newImage = {
        {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
        {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
        {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}}
    };
    images.put(imageName, newImage);
    return images.get(imageName);
  }

  @Override
  public void brighten(int value, String imageName, String destImageName) {
    log.append(String.format("brighten %d %s %s", value, imageName, destImageName));
  }

  @Override
  public void flipHorizontal(String imageName, String destImageName) {
    log.append(String.format("horizontal-flip %s %s", imageName, destImageName));
  }

  @Override
  public void flipVertical(String imageName, String destImageName) {
    log.append(String.format("vertical-flip %s %s", imageName, destImageName));
  }

  @Override
  public void redComponent(String imageName, String destImageName) {
    log.append(String.format("red-component %s %s", imageName, destImageName));
  }

  @Override
  public void greenComponent(String imageName, String destImageName) {
    log.append(String.format("green-component %s %s", imageName, destImageName));
  }

  @Override
  public void blueComponent(String imageName, String destImageName) {
    log.append(String.format("blue-component %s %s", imageName, destImageName));
  }

  @Override
  public void valueComponent(String imageName, String destImageName) {
    log.append(String.format("value-component %s %s", imageName, destImageName));
  }

  @Override
  public void lumaComponent(String imageName, String destImageName) {
    log.append(String.format("luma-component %s %s", imageName, destImageName));
  }

  @Override
  public void intensityComponent(String imageName, String destImageName) {
    log.append(String.format("intensity-component %s %s", imageName, destImageName));
  }

  @Override
  public void rgbSplit(String imageName, String redDest, String greenDest, String blueDest) {
    log.append(String.format("rgb-split %s %s %s %s", imageName, redDest, greenDest, blueDest));
  }

  @Override
  public void rgbCombine(String destImageName, String redImageName, String greenImageName,
                         String blueImageName) {
    log.append(String.format("rgb-combine %s %s %s %s", destImageName, redImageName, greenImageName,
        blueImageName));
  }

  @Override
  public void blur(String imageName, String destImageName) {
    log.append(String.format("blur %s %s", imageName, destImageName));
  }

  @Override
  public void sharpen(String imageName, String destImageName) {
    log.append(String.format("sharpen %s %s", imageName, destImageName));
  }

  @Override
  public void sepia(String imageName, String destImageName) {
    log.append(String.format("sepia %s %s", imageName, destImageName));
  }

  @Override
  public void compress(float percentage, String imageName, String destImageName) {
    log.append(String.format("compress %f %s %s", percentage, imageName, destImageName));
  }

  @Override
  public void histogram(String imageName, String destImageName) {
    log.append(String.format("histogram %s %s", imageName, destImageName));
  }

  @Override
  public void colorCorrect(String imageName, String destImageName) {
    log.append(String.format("color-correct %s %s", imageName, destImageName));
  }

  @Override
  public void levelAdjust(String imageName, String destImageName,
                          String black, String mid, String white) {
    log.append(String.format("levels-adjust %s %s %s %s %s", black,
        mid, white, imageName, destImageName));
  }

  @Override
  public void split(String imageName, String processedImageName, float percentage) {
    log.append(String.format(" split %f %s %s", percentage, imageName, processedImageName));
  }

  @Override
  public void resizeImage(String imageName, String destImageName, float newWidth, float newHeight) {
    log.append(String.format(" resize %s %s %f %f", imageName, destImageName, newWidth, newHeight));
  }

  @Override
  public BufferedImage getCurrentImage() {
    return null;
  }

  @Override
  public BufferedImage getHistImage() {
    return null;
  }

}
