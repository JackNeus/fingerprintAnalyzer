package fingerprintAnalyzer;

import java.io.File;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class Window extends Application {

	private final static int width = 1200;
	private final static int height = 500;
	
	private Fingerprint print[] = new Fingerprint[2];
	
	public Window(File file1, File file2){
		print[0] = new Fingerprint(file1);
		//print[1] = new Fingerprint(file2);
	}
	
	@Override
	public void start(Stage primaryStage){
		primaryStage.setTitle("Fingerprint Analyzer");
	
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		HBox hb = new HBox(50);
		ImageView printView[] = new ImageView[2];
		for(int i = 0; i < 1; i++){
			printView[i] = new ImageView(print[i].getSrcImg());
			ImageView bin = new ImageView(print[i].getBinImg());
			ImageView thin = new ImageView(print[i].getThinImg());
			
			hb.getChildren().add(printView[i]);
			hb.getChildren().add(bin);
			hb.getChildren().add(thin);
		}
		grid.getChildren().add(hb);
		
		Scene scene = new Scene(grid, width, height);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}