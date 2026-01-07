//import tester.Tester;
//import java.util.ArrayList;
//import java.util.Arrays;
//
//class Shoppers {
//	int bestScoreRecursive(ArrayList<Integer> scores, ArrayList<Integer> costs,
//			int curItemIndex, int remainingBudget) {
//		// Base case: no more items
//		if (curItemIndex >= scores.size()) {
//			return 0;
//		} else {
//			// Recursive case: take the better of...
//			return Math.max(
//					// this item
//					scores.get(curItemIndex) +
//					// score of the best item(s) below with remaining budget
//							bestScoreRecursive(scores, costs,
//									curItemIndex + 1,
//									remainingBudget - costs.get(curItemIndex)),
//					// or best item(s) below
//					bestScoreRecursive(scores, costs, curItemIndex + 1, remainingBudget));
//		}
//	}
//	int bestScoreMemo(ArrayList<Integer> scores, ArrayList<Integer> costs, int budget) {
//		ArrayList<ArrayList<Integer>> memos = new ArrayList<ArrayList<Integer>>();
//		// It's a bit easier to pre-fill the array with placeholders,
//		// than to try to dynamicalaly fill it during the algorithm itself.
//		for (int idx = 0; idx < scores.size(); idx += 1) {
//			ArrayList<Integer> vals = new ArrayList<Integer>();
//			for (int b = 0; b < budget; b += 1) {
//				vals.add(Integer.MAX_VALUE); // Placeholder value to mark invalid answers
//			}
//			memos.add(vals);
//		}
//		bestScoreMemoHelp(memos, scores, costs, 0, budget);
//		return memos.get(0).get(budget);
//	}
//	int bestScoreMemoHelp(ArrayList<ArrayList<Integer>> memos,
//			ArrayList<Integer> scores, ArrayList<Integer> costs,
//			int curItemIndex, int remainingBudget) {
//		// Lookup memoized answer:
//		if (memos.get(curItemIndex).get(remainingBudget) != Integer.MAX_VALUE) {
//			return memos.get(curItemIndex).get(remainingBudget);
//		}
//		// Base case: no more items
//		if (curItemIndex >= scores.size()) {
//			return 0;
//		} else {
//			// Recursive case: take the better of...
//			int ans = Math.max(
//					// Try buying this item
//					scores.get(curItemIndex) + bestScoreRecursive(memos, scores, costs,
//							curItemIndex + 1,
//							remainingBudget - costs.get(curItemIndex)),
//					// Skip buying this item
//					bestScoreRecursive(memos, scores, costs, curItemIndex + 1, remainingBudget));
//			memos.get(curItemIndex).set(remainingBudget, ans);
//			return ans;
//		}
//	}
//}
//
//class Examples {
//	Shoppers s;
//	ArrayList<Integer> scores0;
//	ArrayList<Integer> costs0;
//
//	void init() {
//		s = new Shoppers();
//		scores0 = new ArrayList<Integer>(Arrays.asList(
//				0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
//		costs0 = new ArrayList<Integer>(Arrays.asList(
//				10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0));
//		// 10  0  10  10
//		//  9  1  19   9
//		//  8  2  27   7
//		//  7  3  34   4
//		//  6  4  40   0
//		//  5  5
//		//  4  6
//		//  3  7
//		//  2  8
//		//  1  9
//		//  0 10
//	}
//	void testBestScore(Tester t) {
//		init();
//		t.checkExpect(s.bestScoreRecursive(scores0, costs0, 0, 10), 40);
//		t.checkExpect(s.bestScoreMemo(scores0, costs0, 10), 40);
//	}
//}
