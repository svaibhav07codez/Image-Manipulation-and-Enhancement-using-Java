package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

import model.ImageModel;
import operations.BlueComponentOperation;
import operations.BlurOperation;
import operations.BrightenOperation;
import operations.ColorCorrectOperation;
import operations.CompressOperation;
import operations.GreenComponentOperation;
import operations.HistogramOperation;
import operations.HorizontalFlipOperation;
import operations.ImageOperation;
import operations.IntensityComponentOperation;
import operations.LevelsAdjustOperation;
import operations.LoadOperation;
import operations.LumaComponentOperation;
import operations.RGBCombineOperation;
import operations.RGBSplitOperation;
import operations.RedComponentOperation;
import operations.ResizeOperation;
import operations.SaveOperation;
import operations.SepiaOperation;
import operations.SharpenOperation;
import operations.ValueComponentOperation;
import operations.VerticalFlipOperation;
import view.ImageView;

/**
 * Implementation of the mvc.ImageController interface.
 * This class handles user input and processes image commands with mvc.ImageModel and mvc.ImageView.
 * It allows for various image operations to be executed based on user commands.
 * Once execute method is called, it doesn't terminate till you give "exit", "quit", or "q" command.
 */
public class ImageControllerImpl implements controller.ImageController {
  private final ImageModel model;
  private final ImageView view;

  /**
   * Constructs an mvc.ImageControllerImpl with the specified model and view.
   *
   * @param model the mvc.ImageModel to be manipulated by this controller.
   * @param view  the mvc.ImageView to interact with the user.
   */
  public ImageControllerImpl(ImageModel model, ImageView view) {
    this.model = model;
    this.view = view;
  }

  /**
   * Executes the image processing commands based on user input.
   * This method enters a loop to continuously read user input from the view.
   * It processes each command until an exit condition is met.
   *
   * @throws IOException if an input/output error occurs while executing commands.
   */
  @Override
  public void execute() throws IOException {
    boolean cont = true;
    while (cont) {
      String commandLine = view.getUserInput();
      try {
        cont = processCommand(commandLine);
      } catch (IllegalArgumentException e) {
        view.showMessage("Error: " + e.getMessage());
      }
    }
  }

  /**
   * Processes a single command entered by the user.
   * This method parses the command, validates its format,
   * creates the appropriate operations.ImageOperation.
   * It is based on the command type.
   * It then executes the operation using the provided model.
   *
   * @param command the command entered by the user.
   * @throws IllegalArgumentException if the command format is invalid or the command is unknown.
   */
  private boolean processCommand(String command) {
    String[] tokens = command.split(" ");
    int startIndex = -1;
    boolean containsSplit = false;
    boolean splitEligibile = false;
    String[] splitTokens = new String[0];

    List<String> list = Arrays.asList(tokens);
    if (list.contains("split")) {
      startIndex = list.indexOf("split");
      containsSplit = true;
    }

    if (startIndex != -1) {
      splitTokens = Arrays.copyOfRange(tokens, startIndex, tokens.length);
    }

    ImageOperation operation = null;
    String fileExtension;

    try {
      switch (tokens[0].toLowerCase()) {
        case "load":
          if (tokens.length != 3) {
            throw new IllegalArgumentException("Invalid load command format.");
          }
          fileExtension = getFileExtension(tokens[1]).toLowerCase();
          int[][][] image;
          switch (fileExtension) {
            case "ppm":
              image = loadPpmImage(tokens[1]);
              break;
            case "png":
            case "jpg":
            case "jpeg":
              image = loadRasterImage(tokens[1]);
              break;
            default:
              throw new IllegalArgumentException("Unsupported image format: " + fileExtension);
          }

          operation = new LoadOperation(image, tokens[2]);
          view.showMessage("Image is Loaded Successfully! - " + tokens[2]);
          break;

        case "save":
          if (tokens.length != 3) {
            throw new IllegalArgumentException("Invalid save command format.");
          }

          fileExtension = getFileExtension(tokens[1]).toLowerCase();

          int[][][] saveImage = model.getImage(tokens[2]);
          switch (fileExtension) {
            case "ppm":
              savePpmImage(tokens[1], saveImage);
              break;
            case "png":
            case "jpg":
            case "jpeg":
              saveRasterImage(tokens[1], saveImage);
              break;
            default:
              throw new IllegalArgumentException("Unsupported image format: " + fileExtension);
          }

          operation = new SaveOperation(saveImage, tokens[1]);
          view.showMessage("Image is Saved Successfully!");
          break;

        case "brighten":
          if (tokens.length < 4) {
            throw new IllegalArgumentException("Invalid brighten command format.");
          }
          operation = new BrightenOperation(Integer.parseInt(tokens[1]), tokens[2], tokens[3]);
          view.showMessage(tokens[2] + " is Brightened Successfully! - " + tokens[3]);
          break;

        case "blur":
          if (tokens.length < 3) {
            throw new IllegalArgumentException("Invalid blur command format.");
          }
          operation = new BlurOperation(tokens[1], tokens[2]);
          splitEligibile = true;
          view.showMessage(tokens[1] + " is blurred Successfully! - " + tokens[2]);
          break;

        case "red-component":
          if (tokens.length < 3) {
            throw new IllegalArgumentException("Invalid red-component command format.");
          }
          operation = new RedComponentOperation(tokens[1], tokens[2]);
          splitEligibile = true;
          view.showMessage("Red Component of " + tokens[1] + " is extracted Successfully! - "
              + tokens[2]);
          break;

        case "green-component":
          if (tokens.length < 3) {
            throw new IllegalArgumentException("Invalid green-component command format.");
          }
          operation = new GreenComponentOperation(tokens[1], tokens[2]);
          splitEligibile = true;
          view.showMessage("Green Component of " + tokens[1] + " is extracted Successfully! - "
              + tokens[2]);
          break;

        case "blue-component":
          if (tokens.length < 3) {
            throw new IllegalArgumentException("Invalid blue-component command format.");
          }
          operation = new BlueComponentOperation(tokens[1], tokens[2]);
          splitEligibile = true;
          view.showMessage("Blue Component of " + tokens[1] + " is extracted Successfully! - "
              + tokens[2]);
          break;

        case "value-component":
          if (tokens.length < 3) {
            throw new IllegalArgumentException("Invalid value-component command format.");
          }
          operation = new ValueComponentOperation(tokens[1], tokens[2]);
          splitEligibile = true;
          view.showMessage("Value Component of " + tokens[1] + " is extracted Successfully! - "
              + tokens[2]);
          break;

        case "luma-component":
          if (tokens.length < 3) {
            throw new IllegalArgumentException("Invalid luma-component command format.");
          }
          operation = new LumaComponentOperation(tokens[1], tokens[2]);
          splitEligibile = true;
          view.showMessage("Luma Component of " + tokens[1] + " is extracted Successfully! - "
              + tokens[2]);
          break;

        case "intensity-component":
          if (tokens.length < 3) {
            throw new IllegalArgumentException("Invalid intensity-component command format.");
          }
          operation = new IntensityComponentOperation(tokens[1], tokens[2]);
          splitEligibile = true;
          view.showMessage("Intensity Component of " + tokens[1] + " is extracted Successfully! - "
              + tokens[2]);
          break;

        case "horizontal-flip":
          if (tokens.length != 3) {
            throw new IllegalArgumentException("Invalid horizontal-flip command format.");
          }
          operation = new HorizontalFlipOperation(tokens[1], tokens[2]);
          view.showMessage(tokens[1] + " is flipped Horizontally! - " + tokens[2]);
          break;

        case "vertical-flip":
          if (tokens.length != 3) {
            throw new IllegalArgumentException("Invalid vertical-flip command format.");
          }
          operation = new VerticalFlipOperation(tokens[1], tokens[2]);
          view.showMessage(tokens[1] + " is flipped Vertically - " + tokens[2]);
          break;

        case "rgb-split":
          if (tokens.length != 5) {
            throw new IllegalArgumentException("Invalid rgb-split command format.");
          }
          operation = new RGBSplitOperation(tokens[1], tokens[2], tokens[3], tokens[4]);
          view.showMessage(tokens[1] + " is split Successfully! - "
              + tokens[2] + " " + tokens[3] + " " + tokens[4]);
          break;

        case "rgb-combine":
          if (tokens.length != 5) {
            throw new IllegalArgumentException("Invalid rgb-combine command format.");
          }
          operation = new RGBCombineOperation(tokens[1], tokens[2], tokens[3], tokens[4]);
          view.showMessage(tokens[2] + " " + tokens[3] + " " + tokens[4]
              + " are combined Successfully! - " + tokens[1]);
          break;

        case "sharpen":
          if (tokens.length < 3) {
            throw new IllegalArgumentException("Invalid sharpen command format.");
          }
          operation = new SharpenOperation(tokens[1], tokens[2]);
          splitEligibile = true;
          view.showMessage(tokens[1] + " is sharpened Successfully! - " + tokens[2]);
          break;

        case "sepia":
          if (tokens.length < 3) {
            throw new IllegalArgumentException("Invalid sepia command format.");
          }
          operation = new SepiaOperation(tokens[1], tokens[2]);
          splitEligibile = true;
          view.showMessage("Sepia filter is applied on " + tokens[1] + " Successfully! - "
              + tokens[2]);
          break;

        case "compress":
          if (tokens.length != 4) {
            throw new IllegalArgumentException("Invalid compress command format.");
          }
          operation = new CompressOperation(Float.parseFloat(tokens[1]), tokens[2], tokens[3]);
          view.showMessage(tokens[2] + " is compressed by " + Float.parseFloat(tokens[1])
              + "% of " + tokens[3] + " Successfully!");
          break;

        case "histogram":
          if (tokens.length != 3) {
            throw new IllegalArgumentException("Invalid histogram command format.");
          }
          operation = new HistogramOperation(tokens[1], tokens[2]);
          view.showMessage("Histogram is generated for " + tokens[1]);
          break;

        case "color-correct":
          if (tokens.length < 3) {
            throw new IllegalArgumentException("Invalid Color correct command format.");
          }
          operation = new ColorCorrectOperation(tokens[1], tokens[2]);
          splitEligibile = true;
          view.showMessage("Color of " + tokens[1] + " is corrected - " + tokens[2]);
          break;

        case "levels-adjust":
          if (tokens.length < 6) {
            throw new IllegalArgumentException("Invalid levels-adjust command format.");
          }
          operation = new LevelsAdjustOperation(tokens[1], tokens[2], tokens[3],
              tokens[4], tokens[5]);
          splitEligibile = true;
          view.showMessage(tokens[4] + " is Adjusted Successfully! - " + tokens[5]);
          break;

        case "resize":
          if (tokens.length < 5) {
            throw new IllegalArgumentException("Invalid resize command format.");
          }
          operation = new ResizeOperation(tokens[1], tokens[2], tokens[3], tokens[4]);
          view.showMessage(tokens[1] + " is Resized Successfully! - " + tokens[2]);
          break;

        case "-file":
        case "run":
          if (tokens.length < 2) {
            throw new IllegalArgumentException("Invalid run command format.");
          }
          runScript(tokens[1]);
          view.showMessage("Script executed.");
          break;

        case "exit":
        case "quit":
        case "q":
          return false;

        default:
          throw new IllegalArgumentException("Unknown command.");
      }
    } catch (Exception e) {
      view.showMessage("Error: " + e.getMessage());
    }


    if (operation != null) {
      operation.execute(model, tokens);
    }

    if (containsSplit && splitTokens.length == 2) {
      if (splitEligibile) {
        if (!tokens[0].equals("levels-adjust")) {
          model.split(tokens[1], tokens[2], Float.parseFloat(splitTokens[1]));
        } else {
          model.split(tokens[4], tokens[5], Float.parseFloat(splitTokens[1]));
        }
        view.showMessage("Image is split at " + Float.parseFloat(splitTokens[1]) + "%.");
      } else {
        view.showMessage("Error: Split operation is unsupported.");
      }
    }

    BufferedImage curr_image = model.getCurrentImage();
    BufferedImage hist_image = model.getHistImage();
    if (curr_image == null || hist_image == null) {
      view.showMessage("Error: Unable to display image.");
    } else {
      view.displayImage(curr_image, hist_image);
    }

    return true;
  }

  /**
   * Runs a script of commands from a specified file.
   * This method reads commands from the provided script file path and executes each command.
   * Any errors encountered during execution are reported to the console.
   *
   * @param scriptFilePath the path to the script file containing commands to be executed.
   * @throws FileNotFoundException if the specified script file does not exist.
   */
  @Override
  public void runScript(String scriptFilePath) throws IOException {
    Scanner sc = new Scanner(new File(scriptFilePath));
    while (sc.hasNextLine()) {
      String command = sc.nextLine().trim();
      if (command.isEmpty() || command.startsWith("#")) {
        continue;
      }

      view.showMessage("Executing: " + command);
      try {
        processCommand(command);
      } catch (Exception e) {
        view.showMessage("Error: Invalid Command: " + command);
      }
    }
    sc.close();
  }


  private int[][][] loadPpmImage(String filename) {
    int[][][] image = null;
    try (Scanner scanner = new Scanner(new File(filename))) {
      // Check the file format (should be "P3" for PPM)
      String format = scanner.next();
      if (!format.equals("P3")) {
        throw new IllegalArgumentException("Invalid PPM format: " + format);
      }

      // Skip any comment lines
      while (scanner.hasNext("#")) {
        scanner.nextLine();
      }

      int width = scanner.nextInt();
      int height = scanner.nextInt();
      int maxColorValue = scanner.nextInt();

      image = new int[height][width][3];

      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          int red = scanner.nextInt();
          int green = scanner.nextInt();
          int blue = scanner.nextInt();

          red = (red * 255) / maxColorValue;
          green = (green * 255) / maxColorValue;
          blue = (blue * 255) / maxColorValue;

          image[y][x][0] = red;   // Red
          image[y][x][1] = green; // Green
          image[y][x][2] = blue;  // Blue
        }
      }
    } catch (FileNotFoundException e) {
      System.err.println("File not found: " + e.getMessage());
    } catch (InputMismatchException e) {
      System.err.println("Invalid input in PPM file: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("An error occurred: " + e.getMessage());
    }

    return image;
  }

  private int[][][] loadRasterImage(String filePath) throws IOException {
    BufferedImage bufferedImage = ImageIO.read(new File(filePath));

    return bufferedToInt3d(bufferedImage);
  }

  private String getFileExtension(String filePath) {
    int lastIndexOfDot = filePath.lastIndexOf('.');
    if (lastIndexOfDot == -1 || lastIndexOfDot == filePath.length() - 1) {
      return "";
    }
    return filePath.substring(lastIndexOfDot + 1);
  }

  private void savePpmImage(String filePath, int[][][] image) throws IOException {
    FileWriter fw = new FileWriter(new File(filePath));
    fw.write("P3\n" + image[0].length + " " + image.length + "\n255\n");
    for (int row = 0; row < image.length; row++) {
      for (int col = 0; col < image[0].length; col++) {
        fw.write(image[row][col][0] + " "); // Red
        fw.write(image[row][col][1] + " "); // Green
        fw.write(image[row][col][2] + "\n"); // Blue
      }
    }

    fw.close();
  }

  private void saveRasterImage(String filePath, int[][][] image) throws IOException {
    if (image.length == 0 || image[0].length == 0) {
      throw new IllegalArgumentException("Image data cannot be empty.");
    }

    BufferedImage bufferedImage = int3dToBufferedImage(image);

    ImageIO.write(bufferedImage, getFileExtension(filePath), new File(filePath));
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

}
