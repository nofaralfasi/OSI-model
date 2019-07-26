
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell {
	private Pane _gamePane;
	private Rectangle _cell;
	private int _state;

	public Cell(Pane gamePane, int x, int y) {
		_state=0;
		_gamePane = gamePane;
		_cell = new Rectangle(x, y, Constants.CELL_SIZE, Constants.CELL_SIZE);
		_cell.setFill(Color.WHITE);
		_gamePane.getChildren().add(_cell);
	}
	
	public Rectangle getRect(){
		return _cell;
	}
	
	public void setState(int s) {
		_state=s;
	}
	
	public int getState() {
		return _state;
	}
	
}
