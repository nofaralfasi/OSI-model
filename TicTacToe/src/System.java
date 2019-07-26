import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class System {
	private int whoesTurn = 1;
	private BorderPane _controlPane;
	private Button _chooseOButton;
	private Button _chooseXButton;
	private Label playerLabel;
	private Label turnsLabel;
	private BorderPane _root;
	private Game _game;

	public System() {
		whoesTurn = 1;
		_controlPane = new BorderPane();
		_controlPane.setPrefSize(Constants.CONTROLPANE_WIDTH, Constants.CONTROLPANE_HEIGHT);
		_controlPane.setStyle("-fx-background-color: BLACK;");
		this.presentBoard();
		_root = new BorderPane();
		_root.setBottom(this.getControlPane());
		startNewGame();
	}
	
	public System get_controls() {
		return this;
	}
	
	public BorderPane getRoot() {
		return _root;
	}
	
	public void startNewGame() {
		_game = new Game(this);
		_root.setTop(_game.getGamePane());
	}

	public void presentBoard() {
		VBox rightPanel = new VBox(5);
		rightPanel.setPrefSize(Constants.LPANEL_WIDTH, Constants.LPANEL_HEIGHT);
		playerLabel = new Label("Player1: X	    Player2: O");
		playerLabel.setAlignment(Pos.BOTTOM_LEFT);
		playerLabel.setStyle("-fx-font: 17 futura; -fx-base: #0033CC;");
		turnsLabel = new Label("Change Player:  --->");
		turnsLabel.setAlignment(Pos.TOP_LEFT);
		turnsLabel.setStyle("-fx-font: 17 futura; -fx-base: #0033CC;");
		rightPanel.getChildren().addAll(playerLabel, turnsLabel);
		_controlPane.setLeft(rightPanel);
		rightPanel.setAlignment(Pos.CENTER);
		VBox buttonPanel = new VBox(5);
		ButtonHandler buttonHandler = new ButtonHandler();
		buttonPanel.setPrefSize(Constants.BPANEL_WIDTH, Constants.BPANEL_HEIGHT);
		_chooseXButton = new Button(" X ");
		_chooseXButton.setOnAction(buttonHandler);
		_chooseXButton.setAlignment(Pos.TOP_CENTER);
		_chooseXButton.setStyle("-fx-font: 17 futura; -fx-background-color: #ff0000; ");
		_chooseOButton = new Button(" O ");
		_chooseOButton.setOnAction(buttonHandler);
		_chooseOButton.setAlignment(Pos.BOTTOM_CENTER);
		_chooseOButton.setStyle("-fx-font: 17 futura;");
		buttonPanel.getChildren().addAll(_chooseXButton, _chooseOButton);
		_controlPane.setRight(buttonPanel);
		buttonPanel.setAlignment(Pos.CENTER);
	}


	private class ButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (e.getSource() == _chooseXButton) {
				swapPlayers(1);
				_chooseOButton.setStyle("-fx-font: 17 futura;");
				_chooseXButton.setStyle("-fx-font: 17 futura; -fx-background-color: #ff0000; ");
				presentMessage(Constants.WAITING_MESSAGE_X);
			} else if (e.getSource() == _chooseOButton) {
				swapPlayers(2);
				_chooseXButton.setStyle("-fx-font: 17 futura;");
				_chooseOButton.setStyle("-fx-font: 17 futura; -fx-background-color: #ff0000; ");
				presentMessage(Constants.WAITING_MESSAGE_O);
			}
		}

		public void swapPlayers(int i) {
			whoesTurn = i;	
		}
	}
	
	public boolean checkIfRightPlayer() {
		int _playerClickedNum = _game.getStatus(); // from game memory
		int playerNum=whoesTurn;// from controls memory
		return _playerClickedNum == playerNum;
	}

	
/////////***** getters & setters *****//////////

	public void setWhoesTurn(int whoesTurn) {
		this.whoesTurn = whoesTurn;
	}

	public int getWhoesTurn() {
		return whoesTurn;
	}

	public BorderPane getControlPane() {
		return _controlPane;
	}

	public void presentMessage(String str) {
		playerLabel.setText(str);
	}

}