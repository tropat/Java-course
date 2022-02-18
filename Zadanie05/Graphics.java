import java.util.Objects;
import java.util.List;
import java.util.ArrayList;

class Graphics implements GraphicsInterface{
	public class Position2D implements Position {

		private final int col;
		private final int row;

		public Position2D(int col, int row) {
			this.col = col;
			this.row = row;
		}

		@Override
		public int getRow() {
			return row;
		}

		@Override
		public int getCol() {
			return col;
		}

		@Override
		public String toString() {
			return "Position2D [col=" + col + ", row=" + row + "]";
		}

		@Override
		public int hashCode() {
			return Objects.hash(col, row);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Position2D other = (Position2D) obj;
			return col == other.col && row == other.row;
		}
	}

	private CanvasInterface canv = null;
	
	@Override
	public void setCanvas(CanvasInterface canvas) {
		canv = canvas;
		
	}

	private List<Position> painted = new ArrayList<Position>();
	
	private void fillWithColorR(Position startingPosition, Color color) {
		try {
			if(!painted.contains(startingPosition)) {
				canv.setColor(startingPosition, color);
				painted.add(startingPosition);
				fillWithColorR(new Position2D(startingPosition.getCol()+1, startingPosition.getRow()), color);
				fillWithColorR(new Position2D(startingPosition.getCol()-1, startingPosition.getRow()), color);
				fillWithColorR(new Position2D(startingPosition.getCol(), startingPosition.getRow()+1), color);
				fillWithColorR(new Position2D(startingPosition.getCol(), startingPosition.getRow()-1), color);				
			}
		} catch (CanvasInterface.CanvasBorderException e) {} 
		catch (CanvasInterface.BorderColorException e) {
			try {
				canv.setColor(startingPosition, e.previousColor);				
			} catch (Exception e1) {}
		}
	}
	
	@Override
	public void fillWithColor(Position startingPosition, Color color)
			throws GraphicsInterface.WrongStartingPosition, GraphicsInterface.NoCanvasException {
		painted.clear();
		Position position = new Position2D(startingPosition.getCol(), startingPosition.getRow());
		
		if(canv == null) {
			throw new GraphicsInterface.NoCanvasException();
		}
		
		try {
			canv.setColor(startingPosition, color);
		} catch (CanvasInterface.CanvasBorderException e) {
			throw new GraphicsInterface.WrongStartingPosition();
		} catch (CanvasInterface.BorderColorException e) {
			try {
				canv.setColor(position, e.previousColor);				
			} catch (Exception e1) {}
			throw new GraphicsInterface.WrongStartingPosition();
		}
		
		fillWithColorR(startingPosition, color);
	}
}
