import java.util.ArrayList;
import java.util.HashMap;

class Util {
	Util() {}

	boolean isOneTree(ArrayList<Room> vertices, HashMap<String, String> setMap) {
		// this seems wildly inefficient but it works
		for (Room v0 : vertices) {
			for (Room v1 : vertices) {
				if (!this.sameTree(v0.name, v1.name, setMap)) {
					return false;
				}
			}
		}
		return true;
	}
	boolean sameTree(String k1, String k2, HashMap<String, String> setMap) {
		return this.getStump(k1, setMap).equals(this.getStump(k2, setMap));
		//		if (setMap.get(k1).equals(setMap.get(k2))) {
		//			// easy answer
		//			return true;
		//		} else if (setMap.get(k1).equals(k1) &&
		//								setMap.get(k2).equals(k2)) {
		//									// if both nodes are part of their own sets they cannot be part
		//									// of the same set
		//									return false;
		//								} else {
		//									// run up the tree to see if it ends in the same tree
		//									// i can climb both sides here because when we get to a stump
		//									// the self reference will by definition create a loop that will
		//									// be broken by conditional above
		//									return sameTree(setMap.get(k1), setMap.get(k2), setMap);
		//								}
	}
	String getStump(String nodeName, HashMap<String, String> setMap) {
		String nodeSet = setMap.get(nodeName);
		if (nodeName.equals(nodeSet)) {
			return nodeSet;
		} else {
			return getStump(nodeSet, setMap);
		}
	}
	void union(HashMap<String, String> setMap, String k1, String k2) {
		if (setMap.get(k2).equals(k2)) {
			// if k2 is stump of tree change to k1
			setMap.put(k2, this.getStump(k1, setMap));
		} else {
			// go up the tree to change stump's set to k1
			this.union(setMap, k1, setMap.get(k2));
		}
	}
}
