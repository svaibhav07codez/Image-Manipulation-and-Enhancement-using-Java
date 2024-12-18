# Image Manipulation and Enhancement using Java  

## Project Overview  

**GRIME: Graphical Image Manipulation and Enhancement**  
This project was developed as part of an assignment for the Programming Design Paradigm course during the Masters in Computer Science program by Vaibhav Sankaran and Ishwarya Rajkumar.  

The application follows the **Model-View-Controller (MVC)** architecture, where:  
- **Model**: Handles the logic and data manipulation for image processing.  
- **View**: Provides a graphical user interface (GUI) for user interaction.  
- **Controller**: Processes user commands, interacts with the model, and updates the view.  

## Changes Across Assignments  

### Assignment 4 to Assignment 5  
1. **Model Enhancements**: Added operations:  
   - Compress  
   - Histogram  
   - Color Correction  
   - Levels Adjustment  
   - Split View  
2. **Controller Updates**: The `ImageControllerImpl` was updated to handle new operations.  

### Assignment 5 to Assignment 6  
1. **Model Enhancements**: Added image downscaling functionality.  
2. **Controller Updates**: Updated `ImageControllerImpl` to support image resizing.  
3. **View Enhancements**: Added GUI functionality, including:  
   - New resize button  
   - Input fields for resizing dimensions  
   - Event handling for image downscaling  

## Project Structure  

### Main Class  
`Main.java`: Entry point of the application. Initializes the model, view, and controller components and launches the GUI.  

### Packages  

#### Model  
- **ImageModel.java**: Interface defining methods for image manipulation.  
- **ImageModelImpl.java**: Implementation of `ImageModel`, handling image operations like loading, saving, flipping, filtering, and resizing. Supports multiple image formats (PPM, PNG, JPG).  
- **MockImageModelImpl.java**: Mock implementation of `ImageModel` for testing the controller.  

#### View  
- **ImageView.java**: Interface defining methods for user interaction.  
- **ImageViewImpl.java**: GUI implementation of `ImageView`, featuring:  
  - Buttons for operations  
  - Input fields for specific operations  
  - Display areas for images and histograms  

#### Controller  
- **ImageController.java**: Interface for controlling image operations.  
- **ImageControllerImpl.java**: Processes user commands, interacts with the model, and updates the view.  

#### Operations  
- **AbstractImageOperation.java**: Abstract class defining the structure of image operations.  
- **Specific Operations**: Each operation extends `AbstractImageOperation`, implementing its unique logic:  
  - **LoadOperation**: Load an image from a file.  
  - **SaveOperation**: Save an image to a file.  
  - **BlurOperation**: Apply a blur effect.  
  - **BrightenOperation**: Brighten or darken an image.  
  - **Red/Green/BlueComponentOperation**: Extract individual RGB components.  
  - **HorizontalFlipOperation**: Flip an image horizontally.  
  - **VerticalFlipOperation**: Flip an image vertically.  
  - **SharpenOperation**: Sharpen an image.  
  - **SepiaOperation**: Apply a sepia tone effect.  
  - **RGBSplitOperation**: Split an image into red, green, and blue components.  
  - **RGBCombineOperation**: Combine RGB components into a single image.  
  - **ColorCorrectOperation**: Adjust colors based on histogram peaks.  
  - **CompressOperation**: Compress an image by reducing pixel values.  
  - **HistogramOperation**: Generate an image histogram.  
  - **LevelsAdjustOperation**: Adjust black, mid, and white levels.  
  - **ResizeOperation**: Downscale an image to a specified size.  

## Features  

### Available in GUI and Command Line  
- Load  
- Save  
- Blur  
- Red/Green/Blue Component Extraction  
- Luma Component Extraction  
- Sepia  
- Horizontal Flip  
- Vertical Flip  
- Color Correction  
- Compress  
- Levels Adjustment  
- Sharpen  
- Resize  

### GUI-Only Features  
- Resize Image (Image Downscaling)  

### Command-Line-Only Features  
- Intensity Component Extraction  
- Value Component Extraction  
- RGB Split/Combine  
- Brighten  
- Histogram Generation  

### Split View Support  
Preview the original and manipulated images side-by-side for the following operations:  
- Blur  
- Sharpen  
- Sepia  
- RGB Component Extraction  
- Luma/Intensity/Value Components  
- Color Correction  
- Levels Adjustment  

## How to Run the Code  

### Using an IDE  
1. Open the project in IntelliJ or any Java IDE.  
2. Run `Main.java` to launch the GUI.  
3. Load an image using the "Load" button.  
4. Perform manipulations using the available buttons.  
5. Save the final image.  

### Using JAR File  

1. Open a terminal and navigate to the JAR file folder.  
2. Run the following commands:  

#### Option 1: GUI Mode  

 java -jar Program.jar

 - This launches the GUI.
 - Load an image using the "Load" button.
 - Perform manipulations and save the final image.

#### Option 2: Script-file Mode

 java-jar [Program.jar-file] [path-of-script-file]

 - This will now automatically run all the commands in the script file.
 - If any error occurs while executing any command, a message starting with “Error: “ will be displayed in the terminal.

#### Option 3: Text Mode

 java-jar [Program.jar-text]

 - This will now ask for input from the user.
 - Follow the rules below to use the available operations.


## Rules for Commands

- load image-path image-name: Load an image from the specified path and refer to it to henceforth in the program by the given image name.

- save image-path image-name: Save the image with the given name to the specified path which should include the name of the file.

- red-component image-name dest-image-name: Create an image with the red-component of the image with the given name, and refer to it henceforth in the program by the given destination name. Similar commands for green, blue, value, luma, and intensity components should be supported. Note that the value, luma, and intensity images will be greyscale.

- horizontal-flip image-name dest-image-name: Flip an image horizontally to create a new image, referred to henceforth by the given destination name.
  
- vertical-flip image-name dest-image-name: Flip an image vertically to create a new image, referred to henceforth by the given destination name.

- brighten increment image-name dest-image-name: brighten the image by the given increment to create a new image, referred to henceforth by the given destination name. The increment may be positive (brightening) or negative (darkening).

- rgb-split image-name dest-image-name-red dest-image-name-green dest-image-name-blue: split the given image into three images containing its red, green, and blue components respectively. These would be the same images that would be individually produced with the red-component, green-component, and blue-component commands.

- rgb-combine image-name red-image green-image blue-image: Combine the three images that are individually red, green, and blue into a single image that gets its red, green, and blue components from the three images respectively.

- blur image-name dest-image-name: blur the given image and store the result in another image with the given name.

- sharpen image-name dest-image-name: sharpen the given image and store the result in another image with the given name.

- sepia image-name dest-image-name: produce a sepia-toned version of the given image and store the result in another image with the given name.

- compress percentage image-name dest-image-name: modifies the pixel values of the specified image by a given percentage, resulting in a compressed version of the image (in terms of size), which is then stored with a new name.
  
 ● color-correct image-name dest-image-name: color corrects an image by aligning the meaningful peaks of its histogram.

 ● histogram image-name dest-image-name: produces an image that gives a histogram for the given image.
 
 ● levels-adjust b m w image-name dest-image-name: adjusts the color meaningfully in an image where b, m, and w are the three relevant black, mid, and white values respectively. The values should be in ascending order.
 
 ● Operations Preview: For certain operations, a split view of the image can be seen. The operated image is on the left and the original image is on the right. blur, sharpen, sepia, greyscale (red component, green component, blue component, luma component, value component, intensity component), color correction, and levels adjustment. To use this feature, give the command as mentioned earlier followed by “split percentage_value”.


 ## Citation

  Images used in the /res folder are our own and I authorize their use in this project and not anywhere else.
