package fingerprintAnalyzer;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Fingerprint{
	private Image srcImg;
	
	private final int goalSize = 400;
	
	public Fingerprint(File file){
		System.out.println(file);
		srcImg = new Image(file.toURI().toString(), goalSize, goalSize, true, true);
		setup();
	}

	public Image getSrcImg() {
		return binarize(srcImg);
	}
		
	public void setup(){
		/*PixelReader p = srcImg.getPixelReader();
		img = new WritableImage((int)srcImg.getWidth(), (int)srcImg.getHeight());
		PixelWriter pixelWriter = img.getPixelWriter();
		
		for(int y = 0; y < srcImg.getHeight(); y++){
			for(int x = 0; x < srcImg.getWidth(); x++){
				Color color = p.getColor(x, y);
				pixelWriter.setColor(x, y, color);
			}
		}*/
		
		
		
		//http://java-buddy.blogspot.com/2014/06/pixelreader-pixelwriter-and.html
	}
	
	private WritableImage grayscale(Image img){
		WritableImage gray = new WritableImage((int)img.getWidth(), (int)img.getHeight());
		PixelReader imgReader = img.getPixelReader();
		PixelWriter imgWriter = gray.getPixelWriter();
		for(int y = 0; y < img.getHeight(); ++y){
			for(int x = 0; x < img.getWidth(); ++x){
				Color c = imgReader.getColor(x, y);
				int mx = (int) (0xFF * Math.max(c.getRed(), Math.max(c.getGreen(),  c.getBlue())));
				int mn = (int) (0xFF * Math.min(c.getRed(), Math.min(c.getGreen(),  c.getBlue())));
				int g = (mx + mn) / 2;
				Color nc = Color.rgb(g, g, g);
				imgWriter.setColor(x, y, nc);
			}
		}
		return gray;
	}
	
	//http://people.scs.carleton.ca/~roth/iit-publications-iti/docs/gerh-50002.pdf
	private WritableImage binarize(Image img){
		final Color white = Color.rgb(0, 0, 0);
		final Color black = Color.rgb(255, 255, 255);
		
		WritableImage ans = new WritableImage((int)img.getWidth(), (int)img.getHeight());
		PixelWriter imgWriter = ans.getPixelWriter();
		
		PixelReader imgReader = img.getPixelReader();
		int intImg[][] = new int[(int)img.getWidth()][(int)img.getHeight()];
		final int s = 16; //Size of block
		final int t = 15; //Threshold
		for(int x = 0; x < img.getWidth(); ++x){
			int sum = 0;
			for(int y = 0; y < img.getHeight(); ++y){
				sum += 0xFF * imgReader.getColor(x, y).getRed();
				if(x == 0){
					intImg[x][y] = sum;
				}
				else{
					intImg[x][y] = intImg[x - 1][y] + sum;
				}
			}
		}
		for(int x = 0; x < img.getWidth(); ++x){
			for(int y = 0; y < img.getHeight(); ++y){
				int x1 = Math.max(0, x - s / 2);
				int x2 = Math.min((int)img.getWidth() - 1, x + s / 2);
				int y1 = Math.max(0, y - s / 2);
				int y2 = Math.min((int)img.getHeight() - 1, y + s / 2);
				int count = (x2 - x1) * (y2 - y1);
				int sum = intImg[x2][y2] - (y1 == 0 ? 0 : intImg[x2][y1 - 1]) - (x1 == 0 ? 0 : intImg[x1 - 1][y2]) + (y1 == 0 || x1 == 0 ? 0 : intImg[x1 - 1][y1 - 1]);
				int v = (int)(imgReader.getColor(x, y).getRed() * 0xFF);
				if(v * count <= sum * (100 - t) / 100){
					imgWriter.setColor(x, y, white);
				}
				else{
					imgWriter.setColor(x, y, black);
				}
			}
		}
		return ans;
	}
	
}
