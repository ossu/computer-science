import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

class Util {
	public void draw(WorldScene scene, String lastKeyPress, int size, Color c) {
		if (lastKeyPress.equals("up")) {
			c = Color.ORANGE;
		} else if (lastKeyPress.equals("down")) {
			c = Color.BLUE;
		} else if (lastKeyPress.equals("left")) {
			c = Color.GREEN;
		} else if (lastKeyPress.equals("right")) {
			c = Color.RED;
		}
		scene.placeImageXY(
				new TextImage(lastKeyPress, c),
				size / 2,
				size / 2);
	}
}

class TestWorld extends World {
	// constants
	static int BOARD_SIZE = 100;

	Util u = new Util();

	String lastKeyPress = "None";

	public WorldScene makeScene() {
		WorldScene scene = new WorldScene(
				TestWorld.BOARD_SIZE,
				TestWorld.BOARD_SIZE);
		u.draw(scene, lastKeyPress, BOARD_SIZE, Color.black);
		return scene;
	}
	public void onKeyEvent(String k) {
		this.lastKeyPress = k;
	}
}

class Examples {
	void testWorld(Tester t) {
		World testWorld = new TestWorld();
		testWorld.bigBang(100, 100);
	}
}
