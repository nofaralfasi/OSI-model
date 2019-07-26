import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class Game {
	private Pane _gamePane;// GUI
	private System system;
	private int status;
	private Cell[][] _cellArray;// from Board
	private Player[][] _pieceArray;// GUI

	public Game(System s) {
		status = 1;
		//board = new Board(this);
		_gamePane = new Pane();
		_gamePane.setPrefSize(Constants.GAMEPANE_WIDTH, Constants.GAMEPANE_HEIGHT);
		setUpCells();
		setUpLines();
		_pieceArray = new Player[3][3];
		_gamePane.addEventHandler(MouseEvent.ANY, new MouseHandler());
		system = s;
	}

	public void setUpCells() { // REDO
		_cellArray = new Cell[3][3];
		int x = 0, y = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Cell cell = new Cell(_gamePane, x, y);
				_cellArray[i][j] = cell;
				x = x + 100;
			}
			x = 0;
			y = y + 100;
		}
	}

	public void setUpLines() {
		Lines lines = new Lines(_gamePane);
		lines.createVerticalLines(100, 25);
		lines.createHorizontalLines(25, 100);
	}

	public void clear() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (_cellArray[i][j].getState() != 0) {
					_pieceArray[i][j].remove();
					_pieceArray[i][j] = null;
					_cellArray[i][j].setState(0);
				}
			}
		}
	}

	public int getStatus() {
		return status;
	}

	public Pane getGamePane() {
		return _gamePane;
	}

	public Cell[][] get_cellArray() {
		return _cellArray;
	}

	public Player[][] get_pieceArray() {
		return _pieceArray;
	}
	
	public boolean checkWinning(int playerNum) {
		for (int i = 0; i < 3; i++)
			if (_cellArray[i][0].getState() == playerNum && _cellArray[i][1].getState() == playerNum
					&& _cellArray[i][2].getState() == playerNum)
				return true;
		for (int j = 0; j < 3; j++)
			if (_cellArray[0][j].getState() == playerNum && _cellArray[1][j].getState() == playerNum
					&& _cellArray[2][j].getState() == playerNum)
				return true;
		if (_cellArray[0][0].getState() == playerNum && _cellArray[1][1].getState() == playerNum
				&& _cellArray[2][2].getState() == playerNum)
			return true;
		if (_cellArray[0][2].getState() == playerNum && _cellArray[1][1].getState() == playerNum
				&& _cellArray[2][0].getState() == playerNum)
			return true;
		return false;
	}
	
	private class MouseHandler implements EventHandler<MouseEvent> {
		double xClick, yClick;
		int playerNum;// from controls memory
		
		public void handle(MouseEvent e) {
			if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
				playerNum = system.getWhoesTurn();
				xClick = e.getX();
				yClick = e.getY();
				check();
			}
		}

		public void check() {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (_cellArray[i][j].getRect().contains(xClick, yClick) && (_cellArray[i][j].getState() == 0)) {
						if (system.checkIfRightPlayer()) {
							system.presentMessage(Constants.MIDDLE_GAME_MESSAGE);
							if (status == 1) { // X
								double lineX = _cellArray[i][j].getRect().getX();
								double lineY = _cellArray[i][j].getRect().getY();
								XPiece xpiece = new XPiece(_gamePane, lineX, lineY);
								_pieceArray[i][j] = xpiece;
								_cellArray[i][j].setState(1);
								status = 2;
								if(checkWinning(playerNum)) {
									system.presentMessage(Constants.GAME_OVER_X);
									clear();
								}
							} else { // O
								double circleX = _cellArray[i][j].getRect().getX() + 50;
								double circleY = _cellArray[i][j].getRect().getY() + 50;
								OPiece opiece = new OPiece(_gamePane, circleX, circleY);
								_pieceArray[i][j] = opiece;
								_cellArray[i][j].setState(2);
								status = 1;
								if(checkWinning(playerNum)) {
									system.presentMessage(Constants.GAME_OVER_O);
									clear();
								}
							}
							if(checkWinning(playerNum)) {
								system.presentMessage(Constants.GAME_OVER_O);
								clear();
							}
						} else {
							system.presentMessage(Constants.WRONG_PLAYER_MESSAGE);
						}
					}
				}
			}
		}
	}
}
