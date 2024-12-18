package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of the mvc.ImageModel interface.
 * It manages image loading,saving, and manipulation operations.
 * Images are represented as a 3D array where each pixel is defined by its RGB values.
 * The first dimension represents rows, second represents columns and third are the color channels.
 * The code handles different image formats to load pixel data.
 * For PPM pixel values are read in text format.
 * Whereas, for PNG/JPG, RGB values are extracted from packed integers.
 */

public class ImageModelImpl implements ImageModel {

  private Map<String, int[][][]> images;
  private BufferedImage currentImage;
  private BufferedImage histImage;

  public ImageModelImpl() {
    images = new HashMap<>();
  }

  // Load an image and associate it with a given name
  @Override
  public void loadImage(int[][][] image, String imageName) throws IOException {
    images.put(imageName, image);
    currentImage = int3dToBufferedImage(image);
    histogram(imageName, "Histogram");
  }

  private int[][][] bufferedToInt3d(BufferedImage bufferedImage) {
    int width = bufferedImage.getWidth();
    int height = bufferedImage.getHeight();
    int[][][] image = new int[height][width][3];

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int rgb = bufferedImage.getRGB(col, row);
        image[row][col][0] = (rgb >> 16) & 0xFF; // Red
        image[row][col][1] = (rgb >> 8) & 0xFF;  // Green
        image[row][col][2] = rgb & 0xFF;           // Blue
      }
    }

    return image;
  }

  private BufferedImage int3dToBufferedImage(int[][][] image) {
    int height = image.length;
    int width = image[0].length;
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int r = image[row][col][0];
        int g = image[row][col][1];
        int b = image[row][col][2];
        int rgb = (r << 16) | (g << 8) | b;
        bufferedImage.setRGB(col, row, rgb);
      }
    }

    return bufferedImage;
  }

  @Override
  public void saveImage(String filePath, int[][][] imageSave) throws IOException {
    images.put(filePath, imageSave);
    currentImage = int3dToBufferedImage(imageSave);

    histogram(filePath, "Histogram");
  }


  @Override
  public void brighten(int value, String imageName, String destImageName) {
    int[][][] image = getImage(imageName);
    int[][][] brightenedImage = new int[image.length][image[0].length][3];

    for (int row = 0; row < image.length; row++) {
      for (int col = 0; col < image[0].length; col++) {
        for (int ch = 0; ch < 3; ch++) {
          brightenedImage[row][col][ch] = clamp(image[row][col][ch] + value);
        }
      }
    }
    images.put(destImageName, brightenedImage);
    currentImage = int3dToBufferedImage(brightenedImage);
    histogram(destImageName, "Histogram");
  }

  @Override
  public void flipHorizontal(String imageName, String destImageName) {
    int[][][] image = getImage(imageName);
    int[][][] flippedImage = new int[image.length][image[0].length][3];

    for (int row = 0; row < image.length; row++) {
      for (int col = 0; col < image[0].length; col++) {
        flippedImage[row][col] = image[row][image[0].length - 1 - col];
      }
    }
    images.put(destImageName, flippedImage);
    currentImage = int3dToBufferedImage(flippedImage);
    histogram(destImageName, "Histogram");
  }

  @Override
  public void flipVertical(String imageName, String destImageName) {
    int[][][] image = getImage(imageName);
    int[][][] flippedImage = new int[image.length][image[0].length][3];

    for (int row = 0; row < image.length; row++) {
      flippedImage[row] = image[image.length - 1 - row];
    }
    images.put(destImageName, flippedImage);
    currentImage = int3dToBufferedImage(flippedImage);
    histogram(destImageName, "Histogram");
  }

  @Override
  public void redComponent(String imageName, String destImageName) {
    extractComponent(0, imageName, destImageName);
  }

  @Override
  public void greenComponent(String imageName, String destImageName) {
    extractComponent(1, imageName, destImageName);
  }

  @Override
  public void blueComponent(String imageName, String destImageName) {
    extractComponent(2, imageName, destImageName);
  }

  private void extractComponent(int componentIndex, String imageName, String destImageName) {
    int[][][] image = getImage(imageName);
    int[][][] componentImage = new int[image.length][image[0].length][3];

    for (int row = 0; row < image.length; row++) {
      for (int col = 0; col < image[0].length; col++) {
        int value = image[row][col][componentIndex];
        componentImage[row][col][0] = value;
        componentImage[row][col][1] = value;
        componentImage[row][col][2] = value;
      }
    }
    images.put(destImageName, componentImage);

    currentImage = int3dToBufferedImage(componentImage);
    histogram(destImageName, "Histogram");
  }

  @Override
  public void valueComponent(String imageName, String destImageName) {
    applyComponentTransformation(imageName, destImageName, 1);
  }

  @Override
  public void lumaComponent(String imageName, String destImageName) {
    applyComponentTransformation(imageName, destImageName, 2);
  }

  @Override
  public void intensityComponent(String imageName, String destImageName) {
    applyComponentTransformation(imageName, destImageName, 3);
  }

  private void applyComponentTransformation(String imageName, String destImageName, int mode) {
    int[][][] image = getImage(imageName);
    int[][][] transformedImage = new int[image.length][image[0].length][3];

    for (int row = 0; row < image.length; row++) {
      for (int col = 0; col < image[0].length; col++) {
        int componentValue;
        switch (mode) {
          case 1: // Value component (maximum of RGB)
            componentValue = Math.max(image[row][col][0],
                Math.max(image[row][col][1], image[row][col][2]));
            break;
          case 2: // Luma component (weighted average of RGB)
            componentValue = (int) (0.2126 * image[row][col][0]
                + 0.7152 * image[row][col][1]
                + 0.0722 * image[row][col][2]);
            break;
          case 3: // Intensity component (average of RGB)
            componentValue = (image[row][col][0] + image[row][col][1] + image[row][col][2]) / 3;
            break;
          default:
            throw new IllegalArgumentException("Invalid mode for component transformation.");
        }
        transformedImage[row][col][0] = componentValue;
        transformedImage[row][col][1] = componentValue;
        transformedImage[row][col][2] = componentValue;
      }
    }
    images.put(destImageName, transformedImage);

    currentImage = int3dToBufferedImage(transformedImage);
    histogram(destImageName, "Histogram");
  }

  @Override
  public int[][][] getImage(String imageName) {
    if (!images.containsKey(imageName)) {
      throw new IllegalArgumentException("Image not found: " + imageName);
    }

    int[][][] originalImage = images.get(imageName);
    int[][][] copiedImage = new int[originalImage.length][originalImage[0].length][3];

    for (int i = 0; i < originalImage.length; i++) {
      for (int j = 0; j < originalImage[i].length; j++) {
        for (int k = 0; k < 3; k++) {
          copiedImage[i][j][k] = originalImage[i][j][k]; //deep copy
        }
      }
    }

    currentImage = int3dToBufferedImage(copiedImage);
    return copiedImage;
  }

  @Override
  public void rgbSplit(String imageName, String redDest, String greenDest, String blueDest) {
    int[][][] image = getImage(imageName);

    int[][][] redImage = new int[image.length][image[0].length][3];
    int[][][] greenImage = new int[image.length][image[0].length][3];
    int[][][] blueImage = new int[image.length][image[0].length][3];

    for (int row = 0; row < image.length; row++) {
      for (int col = 0; col < image[0].length; col++) {
        int red = image[row][col][0];
        redImage[row][col][0] = red;
        redImage[row][col][1] = red;
        redImage[row][col][2] = red;

        int green = image[row][col][1];
        greenImage[row][col][0] = green;
        greenImage[row][col][1] = green;
        greenImage[row][col][2] = green;

        int blue = image[row][col][2];
        blueImage[row][col][0] = blue;
        blueImage[row][col][1] = blue;
        blueImage[row][col][2] = blue;
      }
    }

    images.put(redDest, redImage);
    images.put(greenDest, greenImage);
    images.put(blueDest, blueImage);
  }

  @Override
  public void rgbCombine(String destImageName, String redImageName, String greenImageName,
                         String blueImageName) {
    int[][][] redImage = getImage(redImageName);
    int[][][] greenImage = getImage(greenImageName);
    int[][][] blueImage = getImage(blueImageName);

    int[][][] combinedImage = new int[redImage.length][redImage[0].length][3];
    for (int row = 0; row < redImage.length; row++) {
      for (int col = 0; col < redImage[0].length; col++) {
        combinedImage[row][col][0] = redImage[row][col][0];
        combinedImage[row][col][1] = greenImage[row][col][1];
        combinedImage[row][col][2] = blueImage[row][col][2];
      }
    }
    images.put(destImageName, combinedImage);
    currentImage = int3dToBufferedImage(combinedImage);
  }

  @Override
  public void blur(String imageName, String destImageName) {
    int[][][] image = getImage(imageName);
    int[][][] blurredImage = new int[image.length][image[0].length][3];
    float[] blurKernel = {
        1 / 256f, 4 / 256f, 6 / 256f, 4 / 256f, 1 / 256f,
        4 / 256f, 16 / 256f, 24 / 256f, 16 / 256f, 4 / 256f,
        6 / 256f, 24 / 256f, 36 / 256f, 24 / 256f, 6 / 256f,
        4 / 256f, 16 / 256f, 24 / 256f, 16 / 256f, 4 / 256f,
        1 / 256f, 4 / 256f, 6 / 256f, 4 / 256f, 1 / 256f};

    applyKernel(image, blurredImage, blurKernel);
    images.put(destImageName, blurredImage);

    currentImage = int3dToBufferedImage(blurredImage);
    histogram(destImageName, "Histogram");
  }

  @Override
  public void sharpen(String imageName, String destImageName) {
    int[][][] image = getImage(imageName);
    int[][][] sharpenedImage = new int[image.length][image[0].length][3];
    float[] sharpenKernel = {-1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f, 2 / 8f, 2 / 8f,
        2 / 8f, -1 / 8f, -1 / 8f, 2 / 8f, 4 / 8f, 2 / 8f, -1 / 8f, -1 / 8f, 2 / 8f, 2 / 8f,
        2 / 8f, -1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f};
    applyKernel(image, sharpenedImage, sharpenKernel);
    images.put(destImageName, sharpenedImage);
    currentImage = int3dToBufferedImage(sharpenedImage);
    histogram(destImageName, "Histogram");
  }

  @Override
  public void sepia(String imageName, String destImageName) {
    int[][][] image = getImage(imageName);
    int[][][] sepiaImage = new int[image.length][image[0].length][3];

    for (int row = 0; row < image.length; row++) {
      for (int col = 0; col < image[0].length; col++) {
        int red = image[row][col][0];
        int green = image[row][col][1];
        int blue = image[row][col][2];

        int newRed = clamp((int) (0.393 * red + 0.769 * green + 0.189 * blue));
        int newGreen = clamp((int) (0.349 * red + 0.686 * green + 0.168 * blue));
        int newBlue = clamp((int) (0.272 * red + 0.534 * green + 0.131 * blue));

        sepiaImage[row][col][0] = newRed;
        sepiaImage[row][col][1] = newGreen;
        sepiaImage[row][col][2] = newBlue;
      }
    }
    images.put(destImageName, sepiaImage);
    currentImage = int3dToBufferedImage(sepiaImage);
    histogram(destImageName, "Histogram");
  }

  @Override
  public void compress(float percentage, String imageName, String destImageName) {
    if (percentage <= 0 || percentage > 100) {
      throw new IllegalArgumentException("Compression percentage should be between 0 and 100.");
    }

    float new_percentage = 100 - percentage;

    int[][][] originalImage = getImage(imageName);
    int originalHeight = originalImage.length;
    int originalWidth = originalImage[0].length;

    // Calculate the new dimensions
    int newWidth = Math.max(1, (int) (originalWidth * (new_percentage / 100)));
    int newHeight = Math.max(1, (int) (originalHeight * (new_percentage / 100)));
    int[][][] compressedImage = new int[newHeight][newWidth][3];

    // Apply nearest-neighbor compression
    for (int row = 0; row < newHeight; row++) {
      for (int col = 0; col < newWidth; col++) {
        // Find the corresponding pixel in the original image
        int origRow = (int) (row * ((float) originalHeight / newHeight));
        int origCol = (int) (col * ((float) originalWidth / newWidth));

        // Copy the RGB values
        compressedImage[row][col] = originalImage[origRow][origCol];
      }
    }

    // Store the compressed image in the map
    images.put(destImageName, compressedImage);
    currentImage = int3dToBufferedImage(compressedImage);
    histogram(destImageName, "Histogram");

  }

  @Override
  public void histogram(String imageName, String destImageName) {
    int[][][] image = getImage(imageName);
    if (image == null) {
      throw new IllegalArgumentException("Image not found: " + imageName);
    }

    // Initialize histograms for RGB channels with 256 bins each
    int[] redHistogram = new int[256];
    int[] greenHistogram = new int[256];
    int[] blueHistogram = new int[256];

    // Populate histograms by counting pixel values for each channel
    for (int[][] row : image) {
      for (int[] pixel : row) {
        int r = pixel[0];
        int g = pixel[1];
        int b = pixel[2];
        redHistogram[r]++;
        greenHistogram[g]++;
        blueHistogram[b]++;
      }
    }

    // Create an image to display the histograms
    int width = 512;  // Width of histogram image
    int height = 256; // Height of the histogram area
    BufferedImage histogramImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    // Draw histograms on the image
    drawCombinedHistogram(histogramImage, redHistogram, greenHistogram, blueHistogram, height);
    images.put(destImageName, bufferedToInt3d(histogramImage));

    histImage = histogramImage;
  }

  /**
   * Draws combined color histograms on the given BufferedImage.
   *
   * @param image          the image to draw on
   * @param redHistogram   the histogram array for red
   * @param greenHistogram the histogram array for green
   * @param blueHistogram  the histogram array for blue
   * @param height         the height of the histogram area
   */
  private void drawCombinedHistogram(BufferedImage image, int[] redHistogram,
                                     int[] greenHistogram, int[] blueHistogram, int height) {
    int maxFrequency = Math.max(Math.max(Arrays.stream(redHistogram).max().orElse(1),
            Arrays.stream(greenHistogram).max().orElse(1)),
        Arrays.stream(blueHistogram).max().orElse(1));

    for (int i = 0; i < redHistogram.length - 1; i++) {
      int x1 = i * 2; // Scale up to fit the width
      int y1Red = height - (redHistogram[i] * height / maxFrequency);
      int y1Green = height - (greenHistogram[i] * height / maxFrequency);
      int y1Blue = height - (blueHistogram[i] * height / maxFrequency);
      int x2 = (i + 1) * 2;
      int y2Red = height - (redHistogram[i + 1] * height / maxFrequency);
      int y2Green = height - (greenHistogram[i + 1] * height / maxFrequency);
      int y2Blue = height - (blueHistogram[i + 1] * height / maxFrequency);

      // Draw lines for each color histogram
      drawLine(image, x1, y1Red, x2, y2Red, Color.RED);
      drawLine(image, x1, y1Green, x2, y2Green, Color.GREEN);
      drawLine(image, x1, y1Blue, x2, y2Blue, Color.BLUE);

    }
  }

  /**
   * Draws a line between two points on a BufferedImage.
   *
   * @param image the image to draw on
   * @param x1    the x-coordinate of the starting point
   * @param y1    the y-coordinate of the starting point
   * @param x2    the x-coordinate of the ending point
   * @param y2    the y-coordinate of the ending point
   * @param color the color of the line
   */
  private void drawLine(BufferedImage image, int x1, int y1, int x2, int y2, Color color) {
    int dx = Math.abs(x2 - x1);
    int sx = x1 < x2 ? 1 : -1;
    int dy = -Math.abs(y2 - y1);
    int sy = y1 < y2 ? 1 : -1;
    int err = dx + dy;

    while (true) {
      if (x1 >= 0 && x1 < image.getWidth() && y1 >= 0 && y1 < image.getHeight()) {
        image.setRGB(x1, y1, color.getRGB());
      }
      if (x1 == x2 && y1 == y2) {
        break;
      }
      int e2 = 2 * err;
      if (e2 >= dy) {
        err += dy;
        x1 += sx;
      }
      if (e2 <= dx) {
        err += dx;
        y1 += sy;
      }
    }
  }

  @Override
  public void colorCorrect(String imageName, String destImageName) {
    // Retrieve the image data for processing
    int[][][] image = getImage(imageName);
    if (image == null) {
      throw new IllegalArgumentException("Image not found: " + imageName);
    }

    // Initialize histograms for RGB channels with 256 bins each
    int[] redHistogram = new int[256];
    int[] greenHistogram = new int[256];
    int[] blueHistogram = new int[256];

    // Populate histograms by counting pixel values for each channel
    for (int[][] row : image) {
      for (int[] pixel : row) {
        int r = pixel[0];
        int g = pixel[1];
        int b = pixel[2];
        redHistogram[r]++;
        greenHistogram[g]++;
        blueHistogram[b]++;
      }
    }

    // Find the peaks for each channel
    Peak redPeak = findPeak(redHistogram);
    Peak greenPeak = findPeak(greenHistogram);
    Peak bluePeak = findPeak(blueHistogram);

    // Calculate the average peak position
    int averagePeak = (redPeak.position + greenPeak.position + bluePeak.position) / 3;

    // Calculate offsets based on the average peak
    int redOffset = averagePeak - redPeak.position;
    int greenOffset = averagePeak - greenPeak.position;
    int blueOffset = averagePeak - bluePeak.position;

    // Apply the offsets to the image data
    int[][][] newImage = new int[image.length][image[0].length][3];

    for (int y = 0; y < image.length; y++) {
      for (int x = 0; x < image[y].length; x++) {
        int r = image[y][x][0];
        int g = image[y][x][1];
        int b = image[y][x][2];

        // Apply offsets and clamp values
        r = clamp(r + redOffset);
        g = clamp(g + greenOffset);
        b = clamp(b + blueOffset);

        // Update the pixel values
        newImage[y][x][0] = r;
        newImage[y][x][1] = g;
        newImage[y][x][2] = b;
      }
    }

    // Store the color corrected image
    images.put(destImageName, newImage);
    currentImage = int3dToBufferedImage(newImage);
    histogram(destImageName, "Histogram");

  }

  @Override
  public void levelAdjust(String imageName, String destImageName,
                          String black, String mid, String white) {
    // Parse the input levels
    int b = Integer.parseInt(black);
    int m = Integer.parseInt(mid);
    int w = Integer.parseInt(white);

    // Ensure levels are in a valid range
    if (b < 0 || b > 255 || m < 0 || m > 255 || w < 0 || w > 255 || b >= m || m >= w) {
      throw new IllegalArgumentException("Levels must be between 0 and 255 and b < m < w");
    }

    // Retrieve the source image
    int[][][] sourceImage = images.get(imageName);
    if (sourceImage == null) {
      throw new IllegalArgumentException("Image not found: " + imageName);
    }

    int height = sourceImage.length;
    int width = sourceImage[0].length;

    // Prepare the output image
    int[][][] levelAdjustImage = new int[height][width][3];

    // Calculate coefficients A, B, C for the quadratic function
    double a1 = (128.0 - 255.0 * (m - b) / (w - b)) / ((m - b) * (m - w));
    double b1 = -2 * a1 * b + 255.0 / (w - b);
    double c1 = 0;  // Ensuring curve passes through (b, 0)

    // Loop through each pixel and apply the levels adjustment
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        for (int channel = 0; channel < 3; channel++) {
          int originalValue = sourceImage[y][x][channel];
          double adjustedValue;

          double v = a1 * (originalValue - b) * (originalValue - b) + b1 * (originalValue - b) + c1;
          if (channel == 0) { // Red channel: apply full adjustment
            if (originalValue < b) {
              adjustedValue = 0; // Clamp to black for shadows
            } else if (originalValue > w) {
              adjustedValue = 255; // Clamp to white for highlights
            } else {
              // Apply quadratic transformation for red channel
              adjustedValue = v;
            }
          } else { // Green and Blue channels: apply scaled down adjustment
            if (originalValue < b) {
              adjustedValue = 0;
            } else if (originalValue > w) {
              adjustedValue = 255;
            } else {
              // Apply a lower scaling factor for green and blue
              adjustedValue = 0.6 * v;
            }
          }

          // Clamp the value to be between 0 and 255
          adjustedValue = Math.max(0, Math.min(255, adjustedValue));

          // Set the new value in the destination image
          levelAdjustImage[y][x][channel] = (int) adjustedValue;
        }
      }
    }

    // Store the adjusted image
    images.put(destImageName, levelAdjustImage);
    currentImage = int3dToBufferedImage(levelAdjustImage);
    histogram(destImageName, "Histogram");

  }

  @Override
  public void split(String imageName, String processedImageName, float percentage) {
    // Retrieve original and processed (transformed) images
    int[][][] originalImage = getImage(imageName);
    int[][][] transformedImage = getImage(processedImageName);

    // Validate inputs
    if (originalImage == null || transformedImage == null) {
      throw new IllegalArgumentException("Invalid image names provided.");
    }
    if (percentage < 0 || percentage > 100) {
      throw new IllegalArgumentException("Percentage must be between 0 and 100.");
    }

    // Calculate the vertical split point
    int width = originalImage[0].length;
    int splitPoint = (int) (width * (percentage / 100.0));

    // Combine original and transformed images
    int[][][] combinedImage = new int[originalImage.length][originalImage[0].length][3];
    for (int row = 0; row < originalImage.length; row++) {
      for (int col = 0; col < width; col++) {
        // Copy from the transformed image on the left side, and from the original on the right
        if (col < splitPoint) {
          combinedImage[row][col] = transformedImage[row][col];
        } else {
          combinedImage[row][col] = originalImage[row][col];
        }
      }
    }

    // Save the combined split image
    images.put(processedImageName+"^Split", combinedImage);
    currentImage = int3dToBufferedImage(combinedImage);
    histogram(processedImageName+"^Split", "Histogram");
  }


  // Helper method to find the peak in the histogram
  private Peak findPeak(int[] channel) {
    int peakPos = -1;
    int peakHeight = 0;

    for (int i = 10; i < 246; i++) { // Ignore values < 10 and > 245
      if (channel[i] > peakHeight) {
        peakHeight = channel[i];
        peakPos = i;
      }
    }
    return new Peak(peakPos, peakHeight);
  }

  private int clamp(int val) {
    return Math.max(0, Math.min(val, 255));
  }

  // Peak class to hold position and height
  private static class Peak {
    int position;
    int height;

    public Peak(int position, int height) {
      this.position = position;
      this.height = height;
    }
  }

  private void applyKernel(int[][][] image, int[][][] resultImage, float[] kernel) {
    int kernelSize = (int) Math.sqrt(kernel.length);
    int kernelOffset = kernelSize / 2;

    for (int row = kernelOffset; row < image.length - kernelOffset; row++) {
      for (int col = kernelOffset; col < image[0].length - kernelOffset; col++) {
        for (int ch = 0; ch < 3; ch++) {
          float newVal = 0.0f;
          for (int kr = -kernelOffset; kr <= kernelOffset; kr++) {
            for (int kc = -kernelOffset; kc <= kernelOffset; kc++) {
              newVal += image[row + kr][col + kc][ch] * kernel[(kr + kernelOffset) * kernelSize
                  + (kc + kernelOffset)];
            }
          }
          resultImage[row][col][ch] = clamp((int) newVal);
        }
      }
    }
  }

  @Override
  public void resizeImage(String imageName, String destImageName, float newWidth, float newHeight) {
    // Read the input image
    int[][][] image = getImage(imageName);
    BufferedImage originalImage = int3dToBufferedImage(image);

    if (newWidth == 0) {
      newWidth = image[0].length;
    }

    if (newHeight == 0) {
      newHeight = image.length;
    }

    // Create a new image for the downsized version
    BufferedImage resizedImage = new BufferedImage((int) newWidth, (int) newHeight,
        originalImage.getType());

    // Loop through each pixel in the resized image and apply bilinear interpolation
    for (int y = 0; y < newHeight; y++) {
      for (int x = 0; x < newWidth; x++) {
        double origX = (double) x * originalImage.getWidth() / newWidth;
        double origY = (double) y * originalImage.getHeight() / newHeight;

        int x1 = (int) Math.floor(origX);
        int x2 = Math.min((int) Math.ceil(origX), originalImage.getWidth() - 1);
        int y1 = (int) Math.floor(origY);
        int y2 = Math.min((int) Math.ceil(origY), originalImage.getHeight() - 1);

        Color c00 = new Color(originalImage.getRGB(x1, y1));
        Color c01 = new Color(originalImage.getRGB(x2, y1));
        Color c10 = new Color(originalImage.getRGB(x1, y2));
        Color c11 = new Color(originalImage.getRGB(x2, y2));

        int red = bilinearInterpolate(origX, origY, c00.getRed(), c01.getRed(), c10.getRed(),
            c11.getRed());
        int green = bilinearInterpolate(origX, origY, c00.getGreen(), c01.getGreen(),
            c10.getGreen(), c11.getGreen());
        int blue = bilinearInterpolate(origX, origY, c00.getBlue(), c01.getBlue(),
            c10.getBlue(), c11.getBlue());

        // Set the pixel in the resized image
        Color newColor = new Color(red, green, blue);
        resizedImage.setRGB(x, y, newColor.getRGB());
      }
    }

    images.put(destImageName, bufferedToInt3d(resizedImage));
    currentImage = resizedImage;
    histogram(destImageName, "Histogram");
  }

  private static int bilinearInterpolate(double x, double y, int c00, int c01, int c10, int c11) {
    // Perform bilinear interpolation for one color channel (R, G, or B)
    double fx = x - Math.floor(x);
    double fy = y - Math.floor(y);
    return (int) ((1 - fx) * (1 - fy) * c00 + fx * (1 - fy) * c01 + (1 - fx)
        * fy * c10 + fx * fy * c11);
  }

  @Override
  public BufferedImage getCurrentImage() {
    return currentImage;
  }

  @Override
  public BufferedImage getHistImage() {
    return histImage;
  }

}