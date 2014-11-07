package fingerprintAnalyzer;

import java.io.File;

import javafx.scene.image.Image;

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
	
}
