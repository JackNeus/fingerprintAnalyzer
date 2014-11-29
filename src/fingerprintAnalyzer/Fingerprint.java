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
	}

	public Image getSrcImg() {
		return srcImg;
	}
	public Image getBinImg(){
		return binarize(srcImg);
	}
	public Image getThinImg(){
		return thin(binarize(srcImg));
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
		
		final int s = (int)img.getWidth() / 8; //Size of block
		final int t = 15; //Threshold
		//final int s = (int)img.getWidth() / 8; //Size of block
		
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
				int count = (x2 - x1 + 1) * (y2 - y1 + 1);
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
	
	int dx[] = new int[]{0, 1, 1, 1, 0, -1, -1, -1};
	int dy[] = new int[]{1, 1, 0, -1, -1, -1, 0, 1};
	private WritableImage thin(WritableImage img){
		PixelReader imgReader = img.getPixelReader();
		int imgArr[][] = new int[(int)img.getWidth()][(int)img.getHeight()];
		for(int x = 0; x < img.getWidth(); ++x){
			for(int y = 0; y < img.getHeight(); ++y){
				Color c = imgReader.getColor(x, y);
				if((int)c.getRed() == 0){
					imgArr[x][y] = 1;
				}
				else{
					imgArr[x][y] = 0;
				}
			}
		}
		for(int code = 0; code <= 1; code++){
			boolean flag = true;
			while(flag){
				flag = false;
				for(int x = 0; x < img.getWidth(); ++x){
					for(int y = 0; y < img.getHeight(); ++y){
						if(imgArr[x][y] == 0) continue;
						int N = 0, S = 0;
						int P[] = new int[8];
						for(int i = 0; i < 8; i++){
							int nx = x + dx[i];
							int ny = y + dy[i];
							if(nx < 0 || ny < 0 || nx >= img.getWidth() || ny >= img.getHeight()){
								P[i] = -1;
								continue;
							}
							P[i] = imgArr[nx][ny];
							if(P[i] == 1){
								N++;
								if(i > 0 && P[i - 1] == 0) S++;
							}
						}
						if(P[0] == 1 && P[7] == 0) S++;

						if(N >= 2 && N <= 6 && S == 1){
							if(code == 0 && (P[0] == 0 || P[2] == 0 || P[4] == 0) && P[2] * P[4] * P[6] == 0 || code == 1 && P[0] * P[2] * P[6] == 0 && P[0] * P[4] * P[6] == 0){
								flag = true;
								imgArr[x][y] = 0;
							}
						}
					}
				}
			}
		}
		WritableImage thinned = new WritableImage((int)img.getWidth(), (int)img.getHeight());
		PixelWriter imgWriter = thinned.getPixelWriter();
		for(int x = 0; x < img.getWidth(); ++x){
			for(int y = 0; y < img.getHeight(); ++y){
				int c = imgArr[x][y];
				if(c == 0) c = 1;
				else c = 0;
				c *= 0xFF;
				imgWriter.setColor(x, y, Color.rgb(c, c, c));
			}
		}
		return thinned;
	}
	
}
