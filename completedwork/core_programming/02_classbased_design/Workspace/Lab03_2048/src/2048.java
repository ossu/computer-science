import tester.Tester;

interface IGamePiece {
	// returns the value of the current piece
	int getValue();
	// returns a MergeTile which combines the two pieces (p1 this, p2 other)
	IGamePiece merge(IGamePiece other);
	// returns true if the two pieces have the same value (required to merge)
	boolean isValid(IGamePiece other);
}

class BaseTile implements IGamePiece {
	int value;
	BaseTile(int value) {
		this.value = value;
	}
	public int getValue() {
		return this.value;
	}
	public IGamePiece merge(IGamePiece other) {
		return new MergeTile(this, other);
	}
	public boolean isValid(IGamePiece other) {
		return this.getValue() == other.getValue();
	}
}

class MergeTile implements IGamePiece {
	IGamePiece piece1, piece2;
	MergeTile(IGamePiece piece1, IGamePiece piece2) {
		this.piece1 = piece1;
		this.piece2 = piece2;
	}
	public int getValue() {
		return this.piece1.getValue() + this.piece2.getValue();
	}
	public IGamePiece merge(IGamePiece other) {
		return new MergeTile(this, other);
	}
	public boolean isValid(IGamePiece other) {
		return this.getValue() == other.getValue();
	}
}

class ExamplesGamePiece {
	IGamePiece four = new MergeTile(new BaseTile(2), new BaseTile(2));
	boolean testGetValue(Tester t) {
		return t.checkExpect(four.getValue(), 4);
	}
}
