package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JSpinner;
import javax.swing.ImageIcon;
import javax.swing.SpinnerNumberModel;


/**
 * GUI implementation of the mvc.ImageView interface.
 * This class creates a window for user interaction, with input fields and display areas.
 * It includes to display area for images, dynamic updates based on model changes,
 * and event handling for user actions.
 */
public class ImageViewImpl extends JFrame implements ImageView {
  private JTextArea displayArea;
  private String userInput;
  private Scanner scanner;
  private JLabel imageLabel;
  private JLabel histogramLabel;
  private BufferedImage histogramData;
  JButton loadButton = new JButton("Load");
  JButton saveButton = new JButton("Save");
  JButton blurButton = new JButton("Blur");
  JButton sharpenButton = new JButton("Sharpen");
  JButton redButton = new JButton("Red");
  JButton greenButton = new JButton("Green");
  JButton blueButton = new JButton("Blue");
  JButton lumaButton = new JButton("Luma");
  JButton sepiaButton = new JButton("Sepia");
  JButton horizontalButton = new JButton("Horizontal Flip");
  JButton verticalButton = new JButton("Vertical Flip");
  JButton colorCorrectButton = new JButton("Color Correct");
  JButton compressButton = new JButton("Compress");
  JButton laButton = new JButton("Levels Adjust");
  JButton resizeButton = new JButton("Resize Image");
  JButton scriptButton = new JButton("Upload Script");

  /**
   * This is for testing.
   *
   * @param scanner to read an input.
   */
  public ImageViewImpl(Scanner scanner) {
    this.scanner = scanner;
  }

  /**
   * Constructs a GuiImageViewImpl instance and sets up the GUI.
   */
  public ImageViewImpl() {
    // Set up the frame
    super("IME: Image Manipulation and Enhancement");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1000, 800);
    setLayout(new BorderLayout());

    // Display area where messages will be shown
    displayArea = new JTextArea();
    displayArea.setEditable(false);
    displayArea.setLineWrap(true);
    displayArea.setWrapStyleWord(true);
    JScrollPane textScrollPane = new JScrollPane(displayArea);

    // Image display panel
    imageLabel = new JLabel();
    imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
    JScrollPane imageScrollPane = new JScrollPane(imageLabel);

    // Histogram display panel
    histogramLabel = new JLabel();
    histogramLabel.setHorizontalAlignment(SwingConstants.CENTER);
    JScrollPane histogramScrollPane = new JScrollPane(histogramLabel);

    // SplitPane to divide the image and histogram panels horizontally
    JSplitPane imageHistogramPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
        imageScrollPane, histogramScrollPane);
    imageHistogramPane.setDividerLocation(500);
    imageHistogramPane.setResizeWeight(0.5);

    // Main SplitPane to divide text area and image-histogram pane vertically
    JSplitPane mainPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, textScrollPane,
        imageHistogramPane);
    mainPane.setDividerLocation(200);
    mainPane.setResizeWeight(0.3);
    add(mainPane, BorderLayout.CENTER);

    // Create Load and Save buttons
    JPanel buttonPanel = new JPanel();

    buttonPanel.add(loadButton);
    buttonPanel.add(scriptButton);
    buttonPanel.add(saveButton);
    buttonPanel.add(blurButton);
    buttonPanel.add(sharpenButton);
    buttonPanel.add(redButton);
    buttonPanel.add(greenButton);
    buttonPanel.add(blueButton);
    buttonPanel.add(lumaButton);
    buttonPanel.add(sepiaButton);
    buttonPanel.add(horizontalButton);
    buttonPanel.add(verticalButton);
    buttonPanel.add(colorCorrectButton);
    buttonPanel.add(compressButton);
    buttonPanel.add(laButton);
    buttonPanel.add(resizeButton);

    // Use GridLayout with two rows
    buttonPanel.setLayout(new GridLayout(2, 6, 5, 5));
    add(buttonPanel, BorderLayout.NORTH);

    // Initially disable all buttons except the Load button
    enableButtons(false);
    loadButton.setEnabled(true);
    scriptButton.setEnabled(true);

    loadButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showFileDialogLoad();
      }
    });

    saveButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showFileDialogSave();
      }
    });

    blurButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showFileDialog("blur");
      }
    });

    sharpenButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showFileDialog("sharpen");
      }
    });

    redButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showFileDialog("red-component");
      }
    });

    greenButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showFileDialog("green-component");
      }
    });

    blueButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showFileDialog("blue-component");
      }
    });

    lumaButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showFileDialog("luma-component");
      }
    });

    sepiaButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showFileDialog("sepia");
      }
    });

    horizontalButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showFileDialogFlip("horizontal-flip");
      }
    });

    verticalButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showFileDialogFlip("vertical-flip");
      }
    });

    colorCorrectButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showFileDialog("color-correct");
      }
    });

    compressButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showFileDialogCompress();
      }
    });

    laButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showFileDialogLevelsAdjust();
      }
    });

    resizeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showFileDialogResize();
      }
    });

    scriptButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showFileDialogScript();
      }
    });

    setVisible(true);
  }

  /**
   * Waits for and retrieves user input entered in the GUI input field.
   *
   * @return the trimmed input string provided by the user.
   */
  @Override
  public synchronized String getUserInput() {
    if (scanner == null) {
      try {
        wait(); // Wait until notified.
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
      return userInput;
    } else {
      System.out.print("Type here: ");
      if (scanner.hasNextLine()) {
        return scanner.nextLine();
      } else {
        return scanner.nextLine().trim();
      }
    }
  }

  /**
   * Displays a message to the user in the display area.
   * This method communicates information, such as operation results or error messages.
   *
   * @param message the message to be displayed to the user.
   */
  @Override
  public void showMessage(String message) {
    if (displayArea != null) {
      if (message != null && message.startsWith("Error:")) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
      } else {
        displayArea.append(message + "\n");
      }
    } else {
      System.out.println(message);
    }
  }

  /**
   * Displays the image on the right side of the window.
   *
   * @param image The BufferedImage to display.
   */
  @Override
  public void displayImage(BufferedImage image, BufferedImage histogramImage) {
    if (displayArea != null) {
      if (image == null) {
        showMessage("Image is null, cannot display.");
        return;
      }
      if (histogramImage == null) {
        showMessage("Histogram is null, cannot display.");
        return;
      }

      setImage(image);
      setHistImage(histogramImage);
      enableButtons(true);
    }
  }

  private void setImage(BufferedImage image) {
    ImageIcon imageIcon = new ImageIcon(image);
    imageLabel.setIcon(imageIcon);
    imageLabel.revalidate();
    imageLabel.repaint();
  }

  private void setHistImage(BufferedImage histogramImage) {
    ImageIcon histogramIcon = new ImageIcon(histogramImage);
    histogramLabel.setIcon(histogramIcon);
    histogramLabel.revalidate();
    histogramLabel.repaint();
  }

  private void showFileDialogLoad() {
    JTextField nameField = new JTextField();

    JButton browseButton = new JButton("Choose an image:");
    JTextField pathField = new JTextField();
    pathField.setEditable(false); // Make the path field non-editable

    // Add action listener for the button to open a file chooser
    browseButton.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      int returnValue = fileChooser.showOpenDialog(this);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
        pathField.setText(fileChooser.getSelectedFile().getAbsolutePath());
      }
    });

    // Create a panel to organize the file chooser components
    JPanel fileChooserPanel = new JPanel(new BorderLayout());
    fileChooserPanel.add(browseButton, BorderLayout.WEST); // Button on the left
    fileChooserPanel.add(pathField, BorderLayout.CENTER);  // Path field on the right

    Object[] dialogMessage = {
        "Image:", fileChooserPanel,
        "Image Name:", nameField
    };

    int option = JOptionPane.showConfirmDialog(this, dialogMessage,
        "load".substring(0, 1).toUpperCase() + "load".substring(1) + " Image",
        JOptionPane.OK_CANCEL_OPTION);

    if (option == JOptionPane.OK_OPTION) {
      String path = pathField.getText().trim();
      String name = nameField.getText().trim();
      if (!path.isEmpty() && !name.isEmpty()) {
        userInput = "load" + " " + path + " " + name;
        synchronized (this) {
          notify(); // Notify that input is available
        }
      } else {
        showMessage("Both fields are required.");
      }
    }
  }

  private void showFileDialogSave() {

    JButton browseButton = new JButton("Select Folder:");
    JTextField pathField = new JTextField();
    pathField.setEditable(false); // Make the path field non-editable
    JTextField nameField = new JTextField();
    JTextField existingNameField = new JTextField();

    // Add an action listener to the browse button to open a folder chooser
    browseButton.addActionListener(e -> {
      JFileChooser folderChooser = new JFileChooser();
      folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      int returnValue = folderChooser.showOpenDialog(this);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
        pathField.setText(folderChooser.getSelectedFile().getAbsolutePath());
      }
    });

    // Create a panel for folder selection
    JPanel folderChooserPanel = new JPanel(new BorderLayout());
    folderChooserPanel.add(browseButton, BorderLayout.WEST); // Add the button on the left
    folderChooserPanel.add(pathField, BorderLayout.CENTER);  // Add the path field on the right

    Object[] message = {
        "Folder to Save Image:", folderChooserPanel,
        "Name to save the image as with file extension:", nameField,
        "Image Name:", existingNameField
    };

    int option = JOptionPane.showConfirmDialog(this, message,
        "save".substring(0, 1).toUpperCase() + "save".substring(1) + " Image",
        JOptionPane.OK_CANCEL_OPTION);


    if (option == JOptionPane.OK_OPTION) {
      String newName = nameField.getText().trim();
      String path = pathField.getText().trim() + File.separator + newName;
      String name = existingNameField.getText().trim();
      if (!name.isEmpty()) {
        userInput = "save" + " " + path + " " + name;
        synchronized (this) {
          notify(); // Notify that input is available
        }
      } else {
        showMessage("Both fields are required.");
      }
    }
  }


  private void showFileDialog(String command) {
    JTextField pathField = new JTextField();
    JTextField nameField = new JTextField();
    SpinnerNumberModel sModel = new SpinnerNumberModel(0, 0, 100, 1);
    JSpinner sSpinner = new JSpinner(sModel);

    Object[] message = {
        "Image Name:", pathField,
        "Destination Image Name:", nameField,
        "Split:", sSpinner
    };

    int option = JOptionPane.showConfirmDialog(this, message,
        command.substring(0, 1).toUpperCase() + command.substring(1) + " Image",
        JOptionPane.OK_CANCEL_OPTION);

    if (option == JOptionPane.OK_OPTION) {
      String path = pathField.getText().trim();
      String name = nameField.getText().trim();
      int s = (int) sSpinner.getValue();

      if (!path.isEmpty() && !name.isEmpty()) {
        if (s == 0) {
          userInput = command + " " + path + " " + name;
        } else {
          userInput = command + " " + path + " " + name + " " + "split" + " " + s;
        }

        synchronized (this) {
          notify();
        }
      } else {
        showMessage("Both fields are required.");
      }
    }
  }

  private void showFileDialogScript() {
    JTextField pathField = new JTextField();
    Object[] message = {
        "Absolute Path:", pathField
    };

    int option = JOptionPane.showConfirmDialog(this, message,
        "-file".substring(0, 1).toUpperCase() + "-file".substring(1) + " Image",
        JOptionPane.OK_CANCEL_OPTION);

    if (option == JOptionPane.OK_OPTION) {
      String path = pathField.getText().trim();
      if (!path.isEmpty()) {
        userInput = "-file" + " " + path;
        synchronized (this) {
          notify();
        }
      } else {
        showMessage("Input field is required.");
      }
    }
  }

  private void showFileDialogFlip(String command) {
    JTextField pathField = new JTextField();
    JTextField nameField = new JTextField();

    Object[] message = {
        "Image Name:", pathField,
        "Destination Image Name:", nameField,
    };

    int option = JOptionPane.showConfirmDialog(this, message,
        command.substring(0, 1).toUpperCase() + command.substring(1) + " Image",
        JOptionPane.OK_CANCEL_OPTION);

    if (option == JOptionPane.OK_OPTION) {
      String path = pathField.getText().trim();
      String name = nameField.getText().trim();

      if (!path.isEmpty() && !name.isEmpty()) {
        userInput = command + " " + path + " " + name;
        synchronized (this) {
          notify();
        }
      } else {
        showMessage("Both fields are required.");
      }
    }
  }


  private void showFileDialogCompress() {
    JTextField pathField = new JTextField();
    JTextField nameField = new JTextField();
    JTextField percentage = new JTextField();

    Object[] message = {
        "Compression Percentage (1-100):", percentage,
        "Image Name:", pathField,
        "Destination Image Name:", nameField
    };

    int option = JOptionPane.showConfirmDialog(this, message,
        "compress".substring(0, 1).toUpperCase() + "compress".substring(1) + " Image",
        JOptionPane.OK_CANCEL_OPTION);

    if (option == JOptionPane.OK_OPTION) {
      try {
        float percent = Float.parseFloat(percentage.getText().trim());
        if (percent < 1 || percent > 100) {
          showMessage("Error: Enter a percentage between 1 and 100.");
          return;
        }
        String path = pathField.getText().trim();
        String name = nameField.getText().trim();
        if (!path.isEmpty() && !name.isEmpty()) {
          userInput = "compress" + " " + percent + " " + path + " " + name;
          synchronized (this) {
            notify();
          }
        } else {
          showMessage("Error: Both fields are required.");
        }
      } catch (NumberFormatException e) {
        showMessage("Error: Please enter a valid number for the percentage.");
      }
    }
  }

  private void showFileDialogResize() {
    JTextField pathField = new JTextField();
    JTextField nameField = new JTextField();
    JTextField width = new JTextField();
    JTextField height = new JTextField();

    Object[] message = {
        "Image Name:", pathField,
        "Destination Image Name:", nameField,
        "Width:", width,
        "Height:", height
    };

    int option = JOptionPane.showConfirmDialog(this, message,
        "resize".substring(0, 1).toUpperCase() + "resize".substring(1) + " Image",
        JOptionPane.OK_CANCEL_OPTION);

    if (option == JOptionPane.OK_OPTION) {
      try {
        float wid = width.getText().trim().isEmpty() ? 0 : Float.parseFloat(width.getText().trim());
        float hei = height.getText().trim().isEmpty() ? 0 :
            Float.parseFloat(height.getText().trim());

        if (wid < 0 || hei < 0) {
          showMessage("Error: Please enter a value above 0.");
          return;
        }
        String path = pathField.getText().trim();
        String name = nameField.getText().trim();
        if (!path.isEmpty() && !name.isEmpty()) {
          userInput = "resize" + " " + path + " " + name + " " + wid + " " + hei;
          synchronized (this) {
            notify();
          }
        } else {
          showMessage("Error: Both fields are required.");
        }
      } catch (NumberFormatException e) {
        showMessage("Error: Please enter a value above 0.");
      }
    }
  }


  private void showFileDialogLevelsAdjust() {
    SpinnerNumberModel bModel = new SpinnerNumberModel(0, 0, 253, 1);
    SpinnerNumberModel mModel = new SpinnerNumberModel(138, 1, 254, 1);
    SpinnerNumberModel wModel = new SpinnerNumberModel(255, 2, 255, 1);
    SpinnerNumberModel sModel = new SpinnerNumberModel(0, 0, 100, 1);


    JTextField pathField = new JTextField();
    JTextField nameField = new JTextField();

    JSpinner bSpinner = new JSpinner(bModel);
    JSpinner mSpinner = new JSpinner(mModel);
    JSpinner wSpinner = new JSpinner(wModel);
    JSpinner sSpinner = new JSpinner(sModel);


    // Synchronize the ranges of b, m, and w
    bSpinner.addChangeListener(e -> mModel.setMinimum((int) bSpinner.getValue() + 1));
    mSpinner.addChangeListener(e -> {
      bModel.setMaximum((int) mSpinner.getValue() - 1);
      wModel.setMinimum((int) mSpinner.getValue() + 1);
    });
    wSpinner.addChangeListener(e -> mModel.setMaximum((int) wSpinner.getValue() - 1));

    Object[] message = {
        "Black Level:", bSpinner,
        "Mid Level:", mSpinner,
        "White Level:", wSpinner,
        "Image Name:", pathField,
        "Destination Image Name:", nameField,
        "Split:", sSpinner
    };

    int option = JOptionPane.showConfirmDialog(this, message,
        "levels-adjust".substring(0, 1).toUpperCase() +
            "levels-adjust".substring(1) + " Adjustment",
        JOptionPane.OK_CANCEL_OPTION);

    if (option == JOptionPane.OK_OPTION) {
      int b = (int) bSpinner.getValue();
      int m = (int) mSpinner.getValue();
      int w = (int) wSpinner.getValue();
      String path = pathField.getText().trim();
      String name = nameField.getText().trim();
      int s = (int) sSpinner.getValue();

      if (s == 0) {
        userInput = "levels-adjust" + " " + b + " " + m + " " + w + " " + path + " " + name;
      } else {
        userInput = "levels-adjust" + " " + b + " " + m + " " + w + " " +
            path + " " + name + " " + "split" + " " + s;
      }

      synchronized (this) {
        notify();
      }
    }
  }

  private void enableButtons(boolean enable) {
    saveButton.setEnabled(enable);
    blurButton.setEnabled(enable);
    sharpenButton.setEnabled(enable);
    redButton.setEnabled(enable);
    greenButton.setEnabled(enable);
    blueButton.setEnabled(enable);
    lumaButton.setEnabled(enable);
    sepiaButton.setEnabled(enable);
    horizontalButton.setEnabled(enable);
    verticalButton.setEnabled(enable);
    colorCorrectButton.setEnabled(enable);
    compressButton.setEnabled(enable);
    laButton.setEnabled(enable);
    resizeButton.setEnabled(enable);
  }

}