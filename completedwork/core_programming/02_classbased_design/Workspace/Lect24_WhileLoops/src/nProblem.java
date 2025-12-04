import tester.Tester;
import java.util.ArrayList;
import java.util.Arrays;

class Utils2 {
	boolean getsToOne(int n) {
		ArrayList<Integer> listN = new ArrayList<Integer>();
		listN.add(n);
		while (n > 1) {
			if (n % 2 == 0) {
				n = n / 2;
			} else {
				n = 3 * n + 1;
			}
			if (listN.contains(n)) {
				return false;
			}
			listN.add(n);
		}
		return true;
	}
	ArrayList<Integer> getsToOne_detail(int n) {
		ArrayList<Integer> listN = new ArrayList<Integer>();
		listN.add(n);
		while (n > 1) {
			if (n % 2 == 0) {
				n = n / 2;
			} else {
				n = 3 * n + 1;
			}
			listN.add(n);
		}
		return listN;
	}
}

class ExampleNProblem {
	Utils2 u = new Utils2();

	void testNProblem(Tester t) {
		ArrayList<Integer> n10List = new ArrayList<Integer>(Arrays.asList(
				10, 5, 16, 8, 4, 2, 1));
		t.checkExpect(u.getsToOne(10), true);
		t.checkExpect(u.getsToOne_detail(10), n10List);

	}
}
