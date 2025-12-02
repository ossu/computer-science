import java.util.*;
import tester.Tester;
class ExamplesPerms {
	ArrayList<Character> alphabet;
	ArrayList<Character> testCode1;
	ArrayList<Character> testCode2;
	PermutationCode pCode1;
	PermutationCode pCode2;
	void init() {
		alphabet = new ArrayList<Character>(Arrays.asList(
				'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
				'u', 'v', 'w', 'x', 'y', 'z'));
		testCode1 = new ArrayList<Character>(Arrays.asList(
				'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
				'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
				'U', 'V', 'W', 'X', 'Y', 'Z'));
		testCode2 = new ArrayList<Character>(Arrays.asList(
				't', 'k', 'j', 'i', 'l', 'v', 'm', 'd', 'c', 'x',
				'w', 'q', 'u', 'f', 'b', 'y', 'o', 'a', 'z', 'h',
				'p', 'n', 's', 'r', 'g', 'e'));
		pCode1 = new PermutationCode(this.testCode1);
		pCode2 = new PermutationCode(314);

	}

	void testConstructor(Tester t) {
		init();
		t.checkExpect(pCode1.code, testCode1);
		t.checkExpect(pCode2.code, testCode2);
	}
	void testEncode(Tester t) {
		init();
		t.checkExpect(pCode1.encode("hello world"), "HELLO WORLD");
		t.checkExpect(pCode2.alphabet, this.alphabet);
		t.checkExpect(pCode2.code, this.testCode2);
		t.checkExpect(pCode2.encode("hello world"), "dlqqb sbaqi");

		t.checkException(
				new RuntimeException("Could not encode given character: !"),
				pCode1,
				"encode",
				"!");
	}
	void testDecode(Tester t) {
		init();
		t.checkExpect(pCode1.decode("HELLO WORLD"), "hello world");
		t.checkExpect(pCode2.decode("dlqqb sbaqi"), "hello world");

		t.checkException(
				new RuntimeException("Could not decode given character: !"),
				pCode1,
				"decode",
				"!");

	}
}
