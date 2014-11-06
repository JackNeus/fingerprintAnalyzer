package fingerprintAnalyzer;

import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MenuWindow extends Application {
	public void start(final Stage primaryStage){
		final File file1, file2;
		
		primaryStage.setTitle("Select fingerprints");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		//Submit Box
		Button btn = new Button("OK");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 2, 4);
		
		//Title Box
		Text scenetitle = new Text("Choose files:");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		final FileChooser fileChooser = new FileChooser();
		final Text fileName1 = new Text();
		grid.add(fileName1, 1, 1);
		
		Button choose1 = new Button ("Fingerprint 1");
		choose1.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e){
				File file = fileChooser.showOpenDialog(primaryStage);
				if(file != null){
					fileName1.setText(file.getName());
				}
			}
		});
		grid.add(choose1, 0, 1);
	
		final Text fileName2 = new Text();
		grid.add(fileName2, 1, 2);
		
		Button choose2 = new Button ("Fingerprint 2");
		choose2.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e){
				File file = fileChooser.showOpenDialog(primaryStage);
				if(file != null){
					fileName2.setText(file.getName());
				}
			}
		});
		grid.add(choose2, 0, 2);
		
		//Misc. 
		Scene scene = new Scene(grid, 400, 150);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String args[]){
		launch(args);
	}
}
