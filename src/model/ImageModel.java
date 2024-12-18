package model;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * This interface is for the image model, defining methods for manipulating images.
 * This interface provides various operations for loading, saving, and modifying images.
 * It uses different image processing techniques such as color extraction, flipping, and filtering.
 * Images are represented as a 3D array where each pixel is defined by its RGB values.
 * The first dimension represents rows, second represents columns and third are the color channels.
 * The code handles different image formats to load pixel data.
 * For PPM pixel values are read in text format.
 * Whereas, for PNG/JPG, RGB values are extracted from packed integers.
 */
public interface ImageModel {

  /**
   * Loads an image from the specified file path into the model under the given image name.
   *
   * @param filePath  the path to the image file to be loaded.
   * @param imageName the name to assign to the loaded image within the model.
   * @throws IOException if an error occurs while reading the file.
   */
  void loadImage(int[][][] filePath, String imageName) throws IOException;

  /**
   * Saves the specified image to the given file path.
   *
   * @param filePath  the path where the image should be saved.
   * @param imageName the name of the image to be saved.
   * @throws IOException if an error occurs while writing the file.
   */
  void saveImage(String filePath, int[][][] imageName) throws IOException;

  /**
   * Provides a deep copy 3D integer matrix of the specified image.
   *
   * @param imageName the name of the image to return.
   * @return the 3D integer matrix.
   */
  int[][][] getImage(String imageName);

  /**
   * Brightens the specified image by a given value and stores the result under a new name.
   *
   * @param value         the amount to brighten the image.
   * @param imageName     the name of the image to brighten.
   * @param destImageName the name to assign to the brightened image.
   */
  void brighten(int value, String imageName, String destImageName);

  /**
   * Flips the specified image horizontally and stores the result under a new name.
   *
   * @param imageName     the name of the image to flip.
   * @param destImageName the name to assign to the horizontally flipped image.
   */
  void flipHorizontal(String imageName, String destImageName);

  /**
   * Flips the specified image vertically and stores the result under a new name.
   *
   * @param imageName     the name of the image to flip.
   * @param destImageName the name to assign to the vertically flipped image.
   */
  void flipVertical(String imageName, String destImageName);

  /**
   * Extracts the red component of the specified image and stores it under a new name.
   *
   * @param imageName     the name of the image to extract the red component from.
   * @param destImageName the name to assign to the red component image.
   */
  void redComponent(String imageName, String destImageName);

  /**
   * Extracts the green component of the specified image and stores it under a new name.
   *
   * @param imageName     the name of the image to extract the green component from.
   * @param destImageName the name to assign to the green component image.
   */
  void greenComponent(String imageName, String destImageName);

  /**
   * Extracts the blue component of the specified image and stores it under a new name.
   *
   * @param imageName     the name of the image to extract the blue component from.
   * @param destImageName the name to assign to the blue component image.
   */
  void blueComponent(String imageName, String destImageName);

  /**
   * Extracts the value component of the specified image and stores it under a new name.
   *
   * @param imageName     the name of the image to extract the value component from.
   * @param destImageName the name to assign to the value component image.
   */
  void valueComponent(String imageName, String destImageName);

  /**
   * Extracts the luma component of the specified image and stores it under a new name.
   *
   * @param imageName     the name of the image to extract the luma component from.
   * @param destImageName the name to assign to the luma component image.
   */
  void lumaComponent(String imageName, String destImageName);

  /**
   * Extracts the intensity component of the specified image and stores it under a new name.
   *
   * @param imageName     the name of the image to extract the intensity component from.
   * @param destImageName the name to assign to the intensity component image.
   */
  void intensityComponent(String imageName, String destImageName);

  /**
   * Splits the RGB components of the specified image and stores them under new names.
   *
   * @param imageName the name of the image to split.
   * @param redDest   the name to assign to the red component image.
   * @param greenDest the name to assign to the green component image.
   * @param blueDest  the name to assign to the blue component image.
   */
  void rgbSplit(String imageName, String redDest, String greenDest, String blueDest);

  /**
   * Combines the RGB components into a single image and stores it under a new name.
   *
   * @param destImageName  the name to assign to the combined image.
   * @param redImageName   the name of the image containing the red component.
   * @param greenImageName the name of the image containing the green component.
   * @param blueImageName  the name of the image containing the blue component.
   */
  void rgbCombine(String destImageName, String redImageName, String greenImageName,
                  String blueImageName);

  /**
   * Blurs the specified image and stores the result under a new name.
   *
   * @param imageName     the name of the image to blur.
   * @param destImageName the name to assign to the blurred image.
   */
  void blur(String imageName, String destImageName);

  /**
   * Sharpens the specified image and stores the result under a new name.
   *
   * @param imageName     the name of the image to sharpen.
   * @param destImageName the name to assign to the sharpened image.
   */
  void sharpen(String imageName, String destImageName);

  /**
   * Applies a sepia tone to the specified image and stores the result under a new name.
   *
   * @param imageName     the name of the image to apply the sepia effect to.
   * @param destImageName the name to assign to the sepia-toned image.
   */
  void sepia(String imageName, String destImageName);

  /**
   * Compresses the given image as per the percentage given and stores the result under a new name.
   *
   * @param percentage    the percentage of compression to be done.
   * @param imageName     the name of the image to be compressed.
   * @param destImageName the name to assign to the compressed image.
   */
  void compress(float percentage, String imageName, String destImageName);

  /**
   * Visualises the histogram of the corresponding given image.
   *
   * @param imageName     the name of the image to get the histogram.
   * @param destImageName the name to assign the histogram.
   */
  void histogram(String imageName, String destImageName);

  /**
   * Implements color correction on an image by aligning the meaningful peaks of its histogram.
   *
   * @param imageName     the name of the image to get the color correction.
   * @param destImageName the name to assign the color correction.
   */
  void colorCorrect(String imageName, String destImageName);

  /**
   * Adjusts the levels of an image by modifying its black-tone, mid-tone, and white-tone levels.
   *
   * @param imageName     the name of the image to be level adjusted.
   * @param destImageName the name to assign the level adjustment.
   * @param black         black the black level adjustment.
   * @param mid           mid the black level adjustment.
   * @param white         white the black level adjustment.
   */
  void levelAdjust(String imageName, String destImageName, String black, String mid, String white);

  /**
   * This method splits the image based on the percentage.
   *
   * @param imageName          The original image.
   * @param processedImageName The image gotten after processing.
   * @param percentage         the percentage of image to be split.
   */
  void split(String imageName, String processedImageName, float percentage);

  /**
   * This method resizes the image based on the width and height given as input.
   *
   * @param imageName     The original image.
   * @param destImageName The image to be saved as in map.
   * @param newWidth      desired width of the image.
   * @param newHeight     desired height of the image.
   */
  void resizeImage(String imageName, String destImageName, float newWidth, float newHeight);

  /**
   * To get the current image to load in GUI.
   *
   * @return the current image as a BufferedImage.
   */
  BufferedImage getCurrentImage();

  /**
   * To get the histogram of the current image.
   *
   * @return the histogram as a BufferedImage.
   */
  BufferedImage getHistImage();
}
