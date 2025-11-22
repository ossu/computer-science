import tester.Tester;

class EmbroideryPiece {
	String name;
	ILoMotif motif;
	EmbroideryPiece(String name, ILoMotif motif) {
		this.name = name;
		this.motif = motif;
	}
	public double averageDifficulty() {
		return this.motif.averageDifficulty();
	}
	public String embroideryInfo() {
		return this.motif.embroideryInfo(this.name + ":");
	}
}

interface IMotif {
	double getDifficulty();
	String getDescription();
	String getType();
}

abstract class AMotif implements IMotif {
	String description;
	double difficulty;
	AMotif(String description, double difficulty) {
		this.description = description;
		this.difficulty = difficulty;
	}
	public String getDescription() {
		return this.description;
	}
	public double getDifficulty() {
		return this.difficulty;
	}
}

class CrossStitch extends AMotif {
	CrossStitch(String description, double difficulty) {
		super(description, difficulty);
	}
	public String getType() {
		return "cross stitch";
	}
}

class ChainStitch extends AMotif {
	ChainStitch(String description, double difficulty) {
		super(description, difficulty);
	}
	public String getType() {
		return "chain stitch";
	}
}

interface ILoMotif {
	//returns a double of the average difficulty of all of the cross-stitch and 
	// chain-stitch motifs in an EmbroideryPiece
	double averageDifficulty();
	double averageDifficultyHelp(double sum, int cnt);
	//returns one String that has in it all names of cross-stitch and chain-stitch
	//motifs in an EmbroideryPiece, their stitch types in parentheses, and each motif 
	//separated by comma and space.
	String embroideryInfo(String prefix);
	String embroideryInfoHelp(String prefix);
}

class MtLoMotif implements ILoMotif {
	MtLoMotif() {}
	public double averageDifficulty() {
		return 0;
	}
	public double averageDifficultyHelp(double sum, int cnt) {
		return sum / cnt;
	}
	public String embroideryInfo(String prefix) {
		return prefix + " empty."; //should only be called on an empty motif in EmbroideryPiece
	}
	public String embroideryInfoHelp(String prefix) {
		return prefix + ".";
	}
}

//Group Motif
class ConsLoMotif implements ILoMotif {
	IMotif first;
	ILoMotif rest;
	ConsLoMotif(IMotif first, ILoMotif rest) {
		this.first = first;
		this.rest = rest;
	}
	public double averageDifficulty() {
		return this.averageDifficultyHelp(0, 0);
	}
	public double averageDifficultyHelp(double sum, int cnt) {
		return this.rest.averageDifficultyHelp(sum + first.getDifficulty(), cnt + 1);
	}
	public String embroideryInfo(String prefix) {
		return rest.embroideryInfoHelp(
				prefix + " " + this.first.getDescription() + " (" + this.first.getType() + ")");
	}
	public String embroideryInfoHelp(String prefix) {
		return rest.embroideryInfoHelp(
				prefix + ", " + this.first.getDescription() + " (" + this.first.getType() + ")");
	}
}

class ExamplesEmbroidery {
	IMotif rose = new CrossStitch("rose", 5.0);
	IMotif poppy = new ChainStitch("poppy", 4.75);
	IMotif daisy = new CrossStitch("daisy", 3.2);
	ILoMotif flowers = new ConsLoMotif(this.rose, new ConsLoMotif(this.poppy,
			new ConsLoMotif(this.daisy, new MtLoMotif())));
	IMotif bird = new CrossStitch("bird", 4.5);
	IMotif tree = new ChainStitch("tree", 3.0);
	ILoMotif nature = new ConsLoMotif(this.bird, new ConsLoMotif(this.tree, this.flowers));
	EmbroideryPiece pillow = new EmbroideryPiece("Pillow Cover", this.nature);

	boolean testAverageDifficulty(Tester t) {
		return t.checkInexact(this.flowers.averageDifficulty(), (5.0 + 4.75 + 3.2) / 3, 0.01) &&
						t.checkInexact(this.pillow.averageDifficulty(), (5 + 4.74 + 3.2 + 4.5 + 3) / 5, 0.01);
	}
	boolean testEmbroideryInfo(Tester t) {
		return t.checkExpect(pillow.embroideryInfo(),
				"Pillow Cover: bird (cross stitch), tree (chain stitch), rose (cross stitch), poppy (chain stitch), daisy (cross stitch).");
	}
}
