import javalib.worldimages.*;
import javalib.funworld.*;
import java.awt.Color;
import java.util.Random;

class NBullets extends World {
	int WIDTH = 500;
	int HEIGHT = 300;
	double TICKRATE = 1.0 / 28.0;
	int SPAWNRATE = 1; // average ticks / spawn
	int SPAWNMAX = 3;
	Color FONTCOLOR = Color.BLACK;
	int FONTSPACING = 10;
	WorldImage CHARACTERIMAGE = new OverlayImage(
			new EquilateralTriangleImage(40, OutlineMode.SOLID, Color.BLACK),
			new RectangleImage(40, 15, OutlineMode.SOLID, Color.DARK_GRAY));

	int ammoAvailable;
	int shipsDestroyed;
	ILoEntity loShips;
	ILoEntity loBullets;
	Random rand;
	// Default Constructor
	NBullets(int ammoAvailabe) {
		this(ammoAvailabe, 0, new Random(), new MtLoEntity(), new MtLoEntity());
	}
	// Testing Constructor
	NBullets(int ammoAvailabe, int shipsDestroyed, int seed, ILoEntity loShips, ILoEntity loBullets) {
		this(ammoAvailabe, shipsDestroyed, new Random(seed), loShips, loBullets);
	}
	NBullets(int ammoAvailabe, int shipsDestroyed, Random rand, ILoEntity loShips,
			ILoEntity loBullets) {
		this.ammoAvailable = ammoAvailabe;
		this.shipsDestroyed = shipsDestroyed;
		this.rand = rand;
		this.loShips = loShips;
		this.loBullets = loBullets;
	}
	public boolean start() {
		return this.bigBang(WIDTH, HEIGHT, TICKRATE);
	}

	@Override
	public WorldScene makeScene() {
		//Make a new scene.
		// ** REMEMBER IMAGEXY COUNTS FROM TOP LEFT **
		WorldScene scene = new WorldScene(this.WIDTH, this.HEIGHT);
		scene = this.addPlayer(scene);
		scene = this.addShips(scene);
		scene = this.addBullets(scene);
		scene = this.addBulletsAvailable(scene);
		scene = this.addShipsDowned(scene);
		return scene;
	}
	public WorldScene addPlayer(WorldScene scene) {
		// returns scene with player art added
		return scene.placeImageXY(this.CHARACTERIMAGE, this.WIDTH / 2, this.HEIGHT);
	}
	public WorldScene addShips(WorldScene scene) {
		// returns scene with all ship art added
		return this.loShips.drawEntities(scene);
	}
	public WorldScene addBullets(WorldScene scene) {
		// returns scene with all bullet art added
		return this.loBullets.drawEntities(scene);
	}
	public WorldScene addBulletsAvailable(WorldScene scene) {
		// returns scene text of bullets remaining
		return scene.placeImageXY(
				new TextImage("Bullets Remaining: " + Integer.toString(this.ammoAvailable), this.FONTCOLOR),
				this.WIDTH / 2, this.FONTSPACING);
	}
	public WorldScene addShipsDowned(WorldScene scene) {
		// returns scene with text of ships downed so far
		return scene.placeImageXY(
				new TextImage("Ships Downed: " + Integer.toString(this.shipsDestroyed), this.FONTCOLOR),
				this.WIDTH / 2, this.FONTSPACING * 2);
	}
	@Override
	public NBullets onTick() {
		return this.moveEntities().explodeEntities().spawnships();
	}
	public NBullets moveEntities() {
		return new NBullets(this.ammoAvailable,
				this.shipsDestroyed,
				this.rand,
				this.loShips.move().removeOffscreen(this.WIDTH, this.HEIGHT),
				this.loBullets.move().removeOffscreen(this.WIDTH, this.HEIGHT));
	}
	public NBullets explodeEntities() {
		return new NBullets(this.ammoAvailable,
				this.loShips.countHits(this.loBullets, this.shipsDestroyed),
				this.rand,
				this.loShips.resolveCollisions(this.loBullets),
				this.loBullets.resolveCollisions(this.loShips));
	}
	public NBullets spawnships() {
		if (this.rand.nextInt(this.SPAWNRATE) == 0) {
			return new NBullets(this.ammoAvailable,
					this.shipsDestroyed,
					this.rand,
					this.loShips.spawnNShips(rand.nextInt(this.SPAWNMAX), this.WIDTH, this.HEIGHT, this.rand),
					this.loBullets);
		} else {
			return this;
		}
	}
	public NBullets onKeyEvent(String key) {
		if (key.equals(" ")) {
			return new NBullets(this.ammoAvailable,
					this.shipsDestroyed,
					this.rand,
					this.loShips,
					this.loBullets.fireBullet());
		} else {
			return this;
		}
	}
	@Override
	public WorldEnd worldEnds() {
		if (this.isGameOver()) {
			return new WorldEnd(true, this.makeEndScene());
		} else {
			return new WorldEnd(false, this.makeEndScene());
		}
	}
	public boolean isGameOver() {
		return this.ammoAvailable <= 0 && this.loShips.isEmpty() &&
						this.loBullets.isEmpty();
	}
	public WorldScene makeEndScene() {
		WorldScene endScene = new WorldScene(this.WIDTH, this.HEIGHT);
		return endScene.placeImageXY(new TextImage("Game Over", this.FONTCOLOR), this.WIDTH / 2,
				this.HEIGHT / 2);
	}
}
