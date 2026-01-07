import tester.Tester;
import java.util.ArrayList;

class Fibonacci {
	int fib(int n, String type) {
		switch (type) {
		case "recursive":
			return fib_recursive(n);
		case "memo":
			return fib_memo(n);
		case "loop":
			return fib_loop(n);
		default:
			return 0;
		}
	}
	int fib_recursive(int n) {
		if (n == 0) {
			return 1;
		} else if (n == 1) {
			return 1;
		} else {
			return fib_recursive(n - 1) + fib_recursive(n - 2);
		}
	}
	int fib_memo(int n) {
		ArrayList<Integer> answers = new ArrayList<Integer>();
		answers.add(1); // Base cases: fib(0) = 1
		answers.add(1); // fib(1) = 1
		fibAcc(n, answers);
		return answers.get(n);
	}
	int fibAcc(int n, ArrayList<Integer> answers) {
		// Check for redundant computation
		if (answers.size() > n) {
			return answers.get(n);
		}
		// Compute the new things:
		if (n == 0) {
			return 1;
		} else if (n == 1) {
			return 1;
		} else {
			int ans = fibAcc(n - 1, answers) + fibAcc(n - 2, answers);
			answers.add(ans);
			return ans;
		}
	}
	int fib_loop(int n) {
		if (n == 0) {
			return 1;
		} else if (n == 1) {
			return 1;
		} else {
			int prev = 1;
			int cur = 2;
			for (int i = 2; i < n; i += 1) {
				int next = prev + cur;
				prev = cur;
				cur = next;
			}
			return cur;
		}
	}
}

class FibExamples {
	Fibonacci f = new Fibonacci();
	void testFib(Tester t) {
		// 1 1 2 3 5 8 13 21 34
		t.checkExpect(f.fib(5, "recursive"), 8);
		t.checkExpect(f.fib(8, "recursive"), 34);
		t.checkExpect(f.fib(5, "memo"), 8);
		t.checkExpect(f.fib(8, "memo"), 34);
		t.checkExpect(f.fib(5, "loop"), 8);
		t.checkExpect(f.fib(8, "loop"), 34);
	}
}
