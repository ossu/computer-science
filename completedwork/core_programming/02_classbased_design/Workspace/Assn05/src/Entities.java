import java.awt.Color;
import javalib.worldimages.*;
import javalib.funworld.*;
import java.util.Random;

interface IEntity {
	int getX();
	int getY();
	int getDx();
	int getDy();
	int getSize();
	int getGen();
	int getSpeed();
	boolean isCollided(ILoEntity that);
	boolean isCollidedEntities(IEntity that);
	ILoEntity explode(int i, double theta, ILoEntity rest);
	WorldImage getImage();
}

abstract class AEntity implements IEntity {
	int BULLETSPEED = -8;             // pixels/tick
	int SHIPSPEED = -1 * BULLETSPEED / 2; // pixels/tick
	int XPLAYER = 0;
	int YPLAYER = 0;
	int x;
	int y;
	int dx;
	int dy;
	AEntity(int x, int y, int dx, int dy) {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
	}
	// Constructor needed for creating with default Speed
	AEntity(int x, int y, boolean isBullet) {
		this.x = x;
		this.y = y;
		if (isBullet) {
			this.dx = 0;
			this.dy = BULLETSPEED;
		} else {
			if (x <= 0) {
				this.dx = SHIPSPEED;
				this.dy = 0;
			} else {
				this.dx = SHIPSPEED * -1;
				this.dy = 0;
			}
		}
	}
	// Constructor for player firing bullet
	AEntity(boolean isBullet) {
		this.x = XPLAYER;
		this.y = YPLAYER;
		if (isBullet) {
			this.dx = 0;
			this.dy = BULLETSPEED;
		} else {
			throw new IllegalArgumentException("Only Bullets can be created by default constructor");
		}
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public int getDx() {
		return this.dx;
	}
	public int getDy() {
		return this.dy;
	}
	public int getGen() {
		throw new IllegalArgumentException("Gen only defined for Bullets");
	}
	public boolean isCollided(ILoEntity that) {
		return that.isCollided(this);
	}
	public boolean isCollidedEntities(IEntity that) {
		return Math.abs(this.getX() - that.getX()) < this.getSize() + that.getSize() &&
						Math.abs(this.getY() - that.getY()) < this.getSize() + that.getSize();
	}
	public ILoEntity explode(int i, double theta, ILoEntity rest) {
		return rest;
	}
	public WorldScene drawEntity(WorldScene scene) {
		return scene;
	}
}

class Ship extends AEntity {
	int SHIPRADIUS = 10;
	Color SHIPCOLOR = Color.CYAN;
	WorldImage SHIPIMAGE = new CircleImage(this.SHIPRADIUS, OutlineMode.SOLID, this.SHIPCOLOR);
	Ship(int x, int y, int dx, int dy) {
		super(x, y, dx, dy);
	}
	Ship(int x, int y) {
		super(x, y, false);
	}
	public int getSize() {
		return this.SHIPRADIUS;
	}
	public int getSpeed() {
		return this.SHIPSPEED;
	}
	public WorldImage getImage() {
		return this.SHIPIMAGE;
	}
}

class Bullet extends AEntity {
	int RADIUS = 2;     // pixels
	int MAXRADIUS = 10; // pixels
	int RADIUSINC = 2;  // pixels
	Color COLOR = Color.PINK;
	int size;
	int gen;
	Bullet(int x, int y, int dx, int dy, int size, int gen) {
		super(x, y, dx, dy);
		this.size = size;
		this.gen = gen;
	}
	Bullet(int x, int y, int dx, int dy, int gen) {
		super(x, y, dx, dy);
		this.size = Math.min(gen * this.RADIUSINC, this.MAXRADIUS);
		this.gen = gen;
	}
	Bullet(int x, int y, int dx, int dy) {
		super(x, y, dx, dy);
		this.size = this.RADIUS;
		this.gen = 0;
	}
	Bullet(int x, int y) {
		super(x, y, true);
		this.size = this.RADIUS;
		this.gen = 0;
	}
	Bullet() {
		super(true);
		this.size = this.RADIUS;
		this.gen = 0;
	}
	@Override
	public int getSize() {
		return this.size;
	}
	@Override
	public int getGen() {
		return this.gen;
	}
	public int getSpeed() {
		return this.BULLETSPEED;
	}
	@Override
	public ILoEntity explode(int i, double theta, ILoEntity rest) {
		if (i == 0) {
			return rest;
		} else {
			return new ConsLoBullets(new Bullet(this.x, this.y,
					(int) Math.round(this.getSpeed() * Math.sin(theta * i)),
					(int) Math.round(this.getSpeed() * Math.cos(theta * i)),
					this.gen + 1),
					this.explode(i - 1, theta, rest));
		}
	}
	public WorldImage getImage() {
		return new CircleImage(this.size, OutlineMode.SOLID, this.COLOR);
	}
}

interface ILoEntity {
	ILoEntity move();
	ILoEntity removeOffscreen(int width, int height);
	boolean isCollided(IEntity that);
	int countHits(ILoEntity bullets, int shipsDestroyed);
	ILoEntity resolveCollisions(ILoEntity loEntities);
	ILoEntity fireBullet();
	ILoEntity spawnNShips(int n, int width, int height, Random rand);
	WorldScene drawEntities(WorldScene scene);
	boolean isEmpty();
}

class MtLoEntity implements ILoEntity {
	public ILoEntity move() {
		return this;
	}
	public ILoEntity removeOffscreen(int width, int height) {
		return this;
	}
	public boolean isCollided(IEntity that) {
		return false;
	}
	public int countHits(ILoEntity bullets, int shipsDestroyed) {
		return shipsDestroyed;
	}
	public ILoEntity resolveCollisions(ILoEntity loEntities) {
		return this;
	}
	public ILoEntity fireBullet() {
		return new ConsLoBullets(new Bullet(), this);
	}
	public ILoEntity spawnNShips(int n, int width, int height, Random rand) {
		return this;
	}
	public WorldScene drawEntities(WorldScene scene) {
		return scene;
	}
	public boolean isEmpty() {
		return true;
	}
}

abstract class ALoEntity implements ILoEntity {
	IEntity first;
	ILoEntity rest;
	ALoEntity(IEntity first, ILoEntity rest) {
		this.first = first;
		this.rest = rest;
	}
	public int countHits(ILoEntity bullets, int shipsDestroyed) {
		throw new IllegalArgumentException("Hit only counted for Ships");
	}
	public boolean isCollided(IEntity that) {
		return this.first.isCollidedEntities(that) || this.rest.isCollided(that);
	}
	public ILoEntity fireBullet() {
		throw new IllegalArgumentException("Can only create new bullet with ConsLoBullet class");
	}
	public ILoEntity spawnNShips(int n, int width, int height, Random rand) {
		throw new IllegalArgumentException("Can only create new ship with ConsLoShips class");
	}
	public WorldScene drawEntities(WorldScene scene) {
		return this.rest.drawEntities(scene.placeImageXY(first.getImage(), first.getX(), first.getY()));
	}
	public boolean isEmpty() {
		return false;
	}
}

class ConsLoShips extends ALoEntity {
	ConsLoShips(IEntity first, ILoEntity rest) {
		super(first, rest);
	}
	public ILoEntity move() {
		return new ConsLoShips(
				new Ship(first.getX() + first.getDx(), first.getY() + first.getDy()),
				rest.move());
	}
	public ILoEntity removeOffscreen(int width, int height) {
		int x = this.first.getX();
		int y = this.first.getY();
		if (x > 0 &&	x < width &&
				y > 0 &&
				y < height) {
			return new ConsLoShips(this.first, rest.removeOffscreen(width, height));
		}
		return rest.removeOffscreen(width, height);
	}
	@Override
	public int countHits(ILoEntity bullets, int shipsDestroyed) {
		if (first.isCollided(bullets)) {
			return this.rest.countHits(bullets, shipsDestroyed + 1);
		} else {
			return this.rest.countHits(bullets, shipsDestroyed);
		}
	}
	public ILoEntity resolveCollisions(ILoEntity loEntities) {
		if (this.first.isCollided(loEntities)) {
			return this.rest;
		} else {
			return new ConsLoShips(this.first, this.rest.resolveCollisions(loEntities));
		}
	}
	@Override
	public ILoEntity spawnNShips(int n, int x, int y) {
		if (n <= 0) {
			return this;
		} else {
			return new ConsLoShips(new Ship(x, y), this);
		}
	}
}

class ConsLoBullets extends ALoEntity {
	ConsLoBullets(IEntity first, ILoEntity rest) {
		super(first, rest);
	}
	public ILoEntity move() {
		return new ConsLoBullets(new Bullet(first.getX() + first.getDx(), first.getY() + first.getDy(),
				first.getDx(), first.getDy(),
				first.getSize(), first.getGen()),
				rest.move());
	}
	public ILoEntity removeOffscreen(int width, int height) {
		int x = this.first.getX();
		int y = this.first.getY();
		if (x > 0 &&	x < width &&
				y > 0 &&
				y < height) {
			return new ConsLoBullets(this.first, rest.removeOffscreen(width, height));
		}
		return rest.removeOffscreen(width, height);
	}
	public ILoEntity resolveCollisions(ILoEntity loEntities) {
		if (this.first.isCollided(loEntities)) {
			return this.first.explode(this.first.getGen() + 1,
					(2 * Math.PI) / (1 + this.first.getGen()),
					this.rest);
		} else {
			return new ConsLoBullets(this.first, this.rest.resolveCollisions(loEntities));
		}
	}
	@Override
	public ILoEntity fireBullet() {
		return new ConsLoBullets(new Bullet(), this);
	}
}
