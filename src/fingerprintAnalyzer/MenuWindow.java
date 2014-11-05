package fingerprintAnalyzer;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuWindow extends Application {
	public void start(Stage primaryStage){
		primaryStage.setTitle("Select fingerprints");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		Button btn = new Button("OK");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 2, 4);
		
		Scene scene = new Scene(grid, 300, 150);
		primaryStage.setScene(scene);
		
		Text scenetitle = new Text("Choose files:");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);
		
		Label print1 = new Label("Fingerprint 1: ");
		grid.add(print1, 0, 1);
		
		//File Chooser
		
		Label print2 = new Label("Fingerprint 2: ");
		grid.add(print2, 0, 2);
		
		//File Chooser
		
		primaryStage.show();
	}

	public static void main(String args[]){
		launch(args);
	}
}
