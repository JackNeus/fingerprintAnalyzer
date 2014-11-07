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
	private static File file1;
	private static File file2;
	private String[] validExtensions = new String[]{".jpg", ".png", ".bmp", ".gif"};
	
	public final static int width = 400;
	public final static int height = 180;
	
	private final static String spaces = "                                                                                               "; //95 spaces
	private final static String error =  "Please make sure that you have selected two image files.   \nYou may use .jpg, .png, .bmp, and .gif files.";
	
	private Window window;
	
	public void start(final Stage primaryStage){		
		primaryStage.setTitle("Select fingerprints");
				
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER_LEFT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		//Error Message
		final Text errorMsg = new Text(spaces);
		errorMsg.setId("errorMsg");
		grid.add(errorMsg, 0, 3, 2, 4);
		
		//Submit Box
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
	
		Button btn = new Button("OK");
		btn.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e){
				if(imageFile(file1) && imageFile(file2)){
					window = new Window(file1, file2);
					window.start(new Stage());
				}
				else{
					errorMsg.setText(error);
				}
			}
		});
		hbBtn.getChildren().add(btn);
		
		grid.add(hbBtn, 3, 4);
		
		//Title Box
		Text scenetitle = new Text("Choose files:");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 3, 1);

		//File Selection
		final FileChooser fileChooser = new FileChooser();
		
		final Text fileName1 = new Text();
		grid.add(fileName1, 1, 1);
		
		Button choose1 = new Button ("Fingerprint 1");
		choose1.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e){
				File file = fileChooser.showOpenDialog(primaryStage);
				if(file != null){
					fileName1.setText(abbreviateFilename(file.getName()));
					file1 = file;
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
					fileName2.setText(abbreviateFilename(file.getName()));
					file2 = file;
				}
			}
		});
		grid.add(choose2, 0, 2);
		
		//Misc. 
		Scene scene = new Scene(grid, width, height);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		scene.getStylesheets().add(MenuWindow.class.getResource("MenuWindow.css").toExternalForm());
		primaryStage.show();
	}
	
	private static String abbreviateFilename(String fileName){
		if(fileName.length() <= 35){
			return fileName;
		}
		else{
			return fileName.substring(0, 16) + "..." + fileName.substring(fileName.length() - 16, fileName.length());
		}
	}
	
	private boolean imageFile(File f){
		if(f == null) return false;
		String name = f.getName();
		int pos = -1;
		for(int i = name.length() - 1; i >= 0; i--){
			if(name.charAt(i) == '.'){
				pos = i;
				break;
			}
		}
		name = name.substring(pos, name.length()).toLowerCase();
		for(int i = 0; i < validExtensions.length; i++){
			if(name.equals(validExtensions[i])){
				return true;
			}
		}
		return false;
	}
	
	public static void main(String args[]){
		launch(args);
	}
}
