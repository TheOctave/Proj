package Data;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

public class ReadImage extends JFrame {

	public ReadImage (String imgStr, Mat m) {
		
		Highgui.imwrite(imgStr, m);
		JFrame frame = new JFrame("My GUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		
		//INserts the image icon
		ImageIcon image = new ImageIcon(imgStr);
		frame.setSize(image.getIconWidth() + 10, image.getIconHeight() + 35);
		
		//Draw the Image data into the BufferedImage	
		JLabel label = new JLabel(" ", image, JLabel.CENTER);
		frame.getContentPane().add(label);
		
		frame.validate();
		frame.setVisible(true);
	}
}
