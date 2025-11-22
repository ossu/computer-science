import tester.Tester;
import javalib.worldimages.*;
import java.awt.Color;
import javalib.funworld.*;

class ExamplesNBullets {
	NBullets world = new NBullets(10, 0, 314, new MtLoEntity(), new MtLoEntity());
	//** Test Entities.java **//

	//** Test IEntity components **//
	IEntity cShip1 = new Ship(250, 150);
	IEntity cBullet1 = new Bullet(250, 150);
	IEntity cBullet2 = new Bullet(200, 150);
	IEntity cBullet3 = new Bullet(300, 150);
	IEntity cBullet4 = new Bullet(250, 100);
	IEntity cBullet5 = new Bullet(250, 200);
	IEntity cBullet6 = new Bullet(250 + 11, 150);
	IEntity cBullet7 = new Bullet(250 - 11, 150);
	IEntity cBullet8 = new Bullet(250, 150 + 11);
	IEntity cBullet9 = new Bullet(250, 150 - 11);
	IEntity cBullet10 = new Bullet(250 + 12, 150);
	IEntity cBullet11 = new Bullet(250 - 12, 150);
	IEntity cBullet12 = new Bullet(250, 150 + 12);
	IEntity cBullet13 = new Bullet(250, 150 - 12);

	boolean testIsCollidedEntities(Tester t) {
		return t.checkExpect(this.cShip1.isCollidedEntities(this.cBullet1), true) &&
						t.checkExpect(this.cShip1.isCollidedEntities(this.cBullet2), false) &&
						t.checkExpect(this.cShip1.isCollidedEntities(this.cBullet3), false) &&
						t.checkExpect(this.cShip1.isCollidedEntities(this.cBullet4), false) &&
						t.checkExpect(this.cShip1.isCollidedEntities(this.cBullet5), false) &&
						t.checkExpect(this.cShip1.isCollidedEntities(this.cBullet6), true) &&
						t.checkExpect(this.cShip1.isCollidedEntities(this.cBullet7), true) &&
						t.checkExpect(this.cShip1.isCollidedEntities(this.cBullet8), true) &&
						t.checkExpect(this.cShip1.isCollidedEntities(this.cBullet9), true) &&
						t.checkExpect(this.cShip1.isCollidedEntities(this.cBullet10), false) &&
						t.checkExpect(this.cShip1.isCollidedEntities(this.cBullet11), false) &&
						t.checkExpect(this.cShip1.isCollidedEntities(this.cBullet12), false) &&
						t.checkExpect(this.cShip1.isCollidedEntities(this.cBullet13), false);
	}

	IEntity gBullet1 = new Bullet(250, 150, 0, 8, 1);
	IEntity gBullet2 = new Bullet(250, 150, 0, 8, 2);
	IEntity gBullet3 = new Bullet(250, 150, 0, 8, 3);
	IEntity gBullet4 = new Bullet(250, 150, 0, 8, 4);
	IEntity gBullet6 = new Bullet(250, 150, 0, 8, 6);
	ILoEntity gLoBullets1 = new ConsLoBullets(new Bullet(250, 150, 0, -8, 2),
			new ConsLoBullets(new Bullet(250, 150, 0, 8, 2), new MtLoEntity()));
	ILoEntity gLoBullets2 = new ConsLoBullets(new Bullet(250, 150, 0, -8, 3),
			new ConsLoBullets(new Bullet(250, 150, 7, 4, 3),
					new ConsLoBullets(new Bullet(250, 150, -7, 4, 3), new MtLoEntity())));
	ILoEntity gLoBullets3 = new ConsLoBullets(new Bullet(250, 150, 0, -8, 4),
			new ConsLoBullets(new Bullet(250, 150, 8, 0, 4),
					new ConsLoBullets(new Bullet(250, 150, 0, 8, 4),
							new ConsLoBullets(new Bullet(250, 150, -8, 0, 4), new MtLoEntity()))));
	ILoEntity gLoBullets6 = new ConsLoBullets(new Bullet(250, 150, 0, -8, 10, 7),
			new ConsLoBullets(new Bullet(250, 150, 6, -5, 10, 7),
					new ConsLoBullets(new Bullet(250, 150, 8, 2, 10, 7),
							new ConsLoBullets(new Bullet(250, 150, 3, 7, 10, 7),
									new ConsLoBullets(new Bullet(250, 150, -3, 7, 10, 7),
											new ConsLoBullets(new Bullet(250, 150, -8, 2, 10, 7),
													new ConsLoBullets(new Bullet(250, 150, -6, -5, 10, 7),
															new MtLoEntity())))))));

	boolean testBulletExplode(Tester t) {
		return t.checkExpect(
				gBullet1.explode(gBullet1.getGen() + 1, (2 * Math.PI) / (1 + gBullet1.getGen()),
						new MtLoEntity()),
				this.gLoBullets1) &&
						t.checkExpect(
								gBullet2.explode(gBullet2.getGen() + 1, (2 * Math.PI) / (1 + gBullet2.getGen()),
										new MtLoEntity()),
								this.gLoBullets2) &&
						t.checkExpect(
								gBullet3.explode(gBullet3.getGen() + 1, (2 * Math.PI) / (1 + gBullet3.getGen()),
										new MtLoEntity()),
								this.gLoBullets3) &&
						t.checkExpect(
								gBullet6.explode(gBullet6.getGen() + 1, (2 * Math.PI) / (1 + gBullet6.getGen()),
										new MtLoEntity()),
								this.gLoBullets6);
	}

	//** Test ILoEntity components **//
	IEntity bullet1 = new Bullet(250, 100, 0, -8, 4, 1);
	IEntity bullet2 = new Bullet(250, 150, 0, -8);
	IEntity bullet3 = new Bullet(250, 200);
	IEntity bullet4 = new Bullet(250, 92, 0, -8, 4, 1);
	IEntity bullet5 = new Bullet(250, 142, 0, -8);
	IEntity bullet6 = new Bullet(250, 192);
	IEntity bullet7 = new Bullet(world.WIDTH / 2, world.HEIGHT + 8);
	IEntity bullet8 = new Bullet(100, 0, 0, -8);
	IEntity bullet9 = new Bullet(250, 310);
	IEntity ship1 = new Ship(200, 150, 4, 0);
	IEntity ship2 = new Ship(250, 150);
	IEntity ship3 = new Ship(300, 150);
	IEntity ship4 = new Ship(204, 150, 4, 0);
	IEntity ship5 = new Ship(254, 150);
	IEntity ship6 = new Ship(304, 150);
	IEntity ship7 = new Ship(world.WIDTH, world.HEIGHT / 2);
	IEntity ship8 = new Ship(0, world.HEIGHT / 2, -4, 0);
	IEntity ship9 = new Ship(300, 250);
	ILoEntity loBullets1 = new ConsLoBullets(this.bullet1,
			new ConsLoBullets(this.bullet2,
					new ConsLoBullets(this.bullet3, new MtLoEntity())));
	ILoEntity loBullets2 = new ConsLoBullets(this.bullet4,
			new ConsLoBullets(this.bullet5,
					new ConsLoBullets(this.bullet6, new MtLoEntity())));
	ILoEntity loBullets3 = new ConsLoBullets(this.bullet8,
			new ConsLoBullets(this.bullet7, this.loBullets1));
	ILoEntity loShips1 = new ConsLoShips(this.ship1,
			new ConsLoShips(this.ship2,
					new ConsLoShips(this.ship3, new MtLoEntity())));
	ILoEntity loShips2 = new ConsLoShips(this.ship4,
			new ConsLoShips(this.ship5,
					new ConsLoShips(this.ship6, new MtLoEntity())));
	ILoEntity loShips3 = new ConsLoShips(this.ship8,
			new ConsLoShips(this.ship7, this.loShips1));

	boolean testMove(Tester t) {
		return t.checkExpect(this.loBullets1.move(), this.loBullets2) &&
						t.checkExpect(this.loShips1.move(), this.loShips2);
	}
	boolean testRemoveOffscreen(Tester t) {
		return t.checkExpect(this.loBullets3.removeOffscreen(world.WIDTH, world.HEIGHT),
				this.loBullets1) &&
						t.checkExpect(this.loShips3.removeOffscreen(world.WIDTH, world.HEIGHT),
								this.loShips1);
	}

	ILoEntity loShips4 = new ConsLoShips(this.ship1, new ConsLoShips(this.ship3, new MtLoEntity()));
	ILoEntity loBullets4 = new ConsLoBullets(this.bullet1,
			this.bullet2.explode(this.bullet2.getGen() + 1, (2 * Math.PI) / (1 + this.bullet2.getGen()),
					new ConsLoBullets(this.bullet3, new MtLoEntity())));

	boolean testIsCollidedILoEntity(Tester t) {
		return t.checkExpect(this.loShips1.isCollided(this.bullet2), true) &&
						t.checkExpect(this.loBullets1.isCollided(this.ship2), true) &&
						t.checkExpect(this.loShips1.isCollided(this.bullet9), false) &&
						t.checkExpect(this.loBullets1.isCollided(this.ship9), false);
	}
	boolean testCounthits(Tester t) {
		return t.checkExpect(new MtLoEntity().countHits(loBullets1, 0), 0) &&
						t.checkExpect(this.loShips1.countHits(loBullets1, 0), 1) &&
						t.checkExpect(this.loShips1.countHits(loBullets1, 1), 2) &&
						t.checkExpect(this.loShips4.countHits(loBullets4, 0), 0) &&
						t.checkExpect(this.loShips4.countHits(loBullets4, 1), 1);
	}
	boolean testResolveCollision(Tester t) {
		return t.checkExpect(this.loBullets1.resolveCollisions(this.loShips1), this.loBullets4) &&
						t.checkExpect(this.loShips1.resolveCollisions(this.loBullets1), this.loShips4) &&
						t.checkExpect(this.loBullets1.resolveCollisions(this.loShips4), this.loBullets1) &&
						t.checkExpect(this.loShips4.resolveCollisions(this.loBullets1), this.loShips4);
	}
	boolean testFireBullet(Tester t) {
		return t.checkExpect(new MtLoEntity().fireBullet(),
				new ConsLoBullets(new Bullet(), new MtLoEntity())) &&
						t.checkExpect(loBullets1.fireBullet(), new ConsLoBullets(new Bullet(), loBullets1));
	}
	//	boolean testSpawnShip(Tester t) {
	//		return t.checkExpect(new MtLoEntity().spawnShip(this.world.rand),
	//				new ConsLoShips(new Ship(0, 0), new MtLoEntity())) &&
	//						t.checkExpect(this.loShips1.spawnShip(this.world.rand),
	//								new ConsLoShips(new Ship(0, 0), this.loShips1));
	//		// !!! need a new constructor? 
	//		// how do i make multiple ships and randomize start loc and dir?
	//	}

	//** Test NBullets.java **//

	//** Test makeScene Components **//
	NBullets world0_noEntities = new NBullets(10, 0, 314, new MtLoEntity(), new MtLoEntity());
	NBullets world1_baseTest = new NBullets(10, 0, 314, this.loShips1, this.loBullets1);

	WorldScene scene0_blank = new WorldScene(this.world.WIDTH, this.world.HEIGHT);
	WorldScene scene0_playerOnly = this.scene0_blank.placeImageXY(world.CHARACTERIMAGE,
			this.world.WIDTH / 2, this.world.HEIGHT);

	boolean testAddPlayer(Tester t) {
		return t.checkExpect(this.world0_noEntities.addPlayer(scene0_blank), this.scene0_playerOnly);
	}

	//** Test onTick Components **//
	NBullets world1_postMove = new NBullets(10, 0, 314, this.loShips2, this.loBullets2); // World1 post move
	NBullets world1_wOffscreen = new NBullets(10, 0, 314, this.loShips3, this.loBullets3); // World1 /w offscreen entities
	NBullets world1_postHit = new NBullets(10, 1, 314, this.loShips4, this.loBullets4); // World1 post hit
	NBullets world2_noHits = new NBullets(10, 0, 314, this.loShips4, this.loBullets4); // no hits
	NBullets world3_1bullet = new NBullets(10, 0, 314, new MtLoEntity(),
			new ConsLoBullets(new Bullet(), new MtLoEntity()));
	NBullets world1_postFire = new NBullets(10, 0, 314, this.loShips1,
			new ConsLoBullets(new Bullet(), this.loBullets1));

	boolean testMoveEntities(Tester t) {
		return t.checkExpect(this.world0_noEntities.moveEntities(), this.world0_noEntities) &&
						t.checkExpect(this.world1_baseTest.moveEntities(), this.world1_postMove) &&
						t.checkExpect(this.world1_wOffscreen.moveEntities(), this.world1_postMove);
	}
	boolean testExplodeEntities(Tester t) {
		return t.checkExpect(this.world0_noEntities.explodeEntities(), this.world0_noEntities) &&
						t.checkExpect(this.world1_baseTest.explodeEntities(), this.world1_postHit) &&
						t.checkExpect(this.world2_noHits.explodeEntities(), this.world2_noHits);
	}

	//** Test onKeyEvents Components **//
	boolean testOnKeyEvents(Tester t) {
		return t.checkExpect(this.world0_noEntities.onKeyEvent(" "), this.world3_1bullet) &&
						t.checkExpect(this.world1_baseTest.onKeyEvent(" "), this.world1_postFire);
	}

	boolean testBigBang(Tester t) {
		//		NBullets world = new NBullets(10, 0, 314, new MtLoEntity(), new MtLoEntity());
		return this.world1_baseTest.start();
	}
}
