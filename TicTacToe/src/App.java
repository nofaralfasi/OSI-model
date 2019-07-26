import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class App extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		System system = new System();
		Scene scene = new Scene(system.getRoot(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
		stage.setScene(scene);
		stage.setTitle("TicTacToe");
		stage.show();

	}

	public static void main(String[] args) {
		App.launch((String[])args);
	}

}
