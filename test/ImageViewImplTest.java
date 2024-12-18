import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import view.ImageView;
import view.ImageViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * This class tests the view.
 */
public class ImageViewImplTest {

  private ImageView imageView;
  private ByteArrayOutputStream outputStream;
  private PrintStream originalSystemOut;

  @Before
  public void setUp() {
    // Redirecting System.out to capture the output of showMessage
    originalSystemOut = System.out;
    imageView = new ImageViewImpl();
    outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
  }

  @Test
  public void testShowMessageWithEmptyMessage() {
    String message = "";

    imageView.showMessage(message);

    // Verify the output is empty as expected
    String output = outputStream.toString().trim();
    assertEquals("Empty message should result in empty output", message, output);
  }

  @Test
  public void testShowMessageWithNullMessage() {
    String message = null;

    imageView.showMessage(message);

    // Verify the output is "null" as expected
    String output = outputStream.toString().trim();
    assertEquals("Null message should result in 'null' being printed", "", output);
  }

  @After
  public void tearDown() {
    // Restoring original System.out
    System.setOut(originalSystemOut);
  }
}
