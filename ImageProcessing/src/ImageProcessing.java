import ecs100.UI;
import ecs100.UIFileChooser;
import java.awt.Color;
import java.io.*;
import java.util.*;


public class ImageProcessing {

	int imgWidth;
	int[] widths = new int[imgWidth];
	int imgHeight;
	int[] heights = new int[imgHeight];
	int colDepth;
	int[][] image = new int[imgHeight][imgWidth];

	public ImageProcessing() {
		UI.initialise();
		UI.addButton("Load file", this::loadFileHandler);
		UI.addButton("Invert image", this::drawInverseImage);
		UI.setMouseMotionListener(this::mouseMotionListener);
		UI.addButton("Save current file", this::saveFile);
	}

	private void loadFileHandler() {
		UI.setColor(new Color(/* red */ 1.0f, /* green */ 1.0f, /* blue */ 1.0f));
		UI.fillRect(0, 0, 1000, 1000);

		try {
			Scanner scan = new Scanner(new File(UIFileChooser.open()));
			scan.nextLine();
			imgWidth = scan.nextInt();
			widths = new int[imgWidth];
			imgHeight = scan.nextInt();
			heights = new int[imgHeight];
			colDepth = scan.nextInt();
			image = new int[imgHeight][imgWidth];
			while (scan.hasNext()) {
				for (int i=0; i<heights.length; i++) {
					for (int j=0; j<widths.length; j++) {
						image[i][j] = scan.nextInt();
					}
				}	
			}
			scan.close();
		} catch (IOException e) {

		}		
		drawImageArrayToScreen();
	}

	private void drawImageArrayToScreen() {
		for (int i=0; i<heights.length; i++) {
			for (int j=0; j<widths.length; j++) {
				float factor = (float) 1/colDepth;
				float color = (float)(image[i][j])*factor;
				UI.setColor(new Color(color, color, color));
				UI.fillRect(j,i,1,1);
			}
		}
	}

	private void drawInverseImage() {
		for (int i=0; i<heights.length; i++) {
			for (int j=0; j<widths.length; j++) {
				image[i][j] = (int) (colDepth - (image[i][j]));
				float factor = (float) 1/colDepth;
				float color = (float)(image[i][j])*factor;
				UI.setColor(new Color(color, color, color));
				UI.fillRect(j,i,1,1);
			}
		}
	}

	private void saveFile() {
			try {
				PrintStream out = new PrintStream(new File(UIFileChooser.save()));
				out.println("P2");
				out.println(imgWidth + " " + imgHeight);
				out.println(colDepth);
				for (int i=0; i<heights.length; i++) {
					for (int j=0; j<widths.length; j++) {
						out.print(image[i][j] + " ");
					}
				}
				out.close();
			} catch (IOException e) {

			}
		} 
	

	public static void main(String[] args) {
		new ImageProcessing();
	}

	private void mouseMotionListener(String action, double x, double y) {
		// You might need to use this for some of the extension. Don't worry about
		// it until then.
		// Documentation is at
		// https://ecs.victoria.ac.nz/foswiki/pub/Main/JavaResources/UI.html#setMouseMotionListener(ecs100.UIMouseListener)
	}
}