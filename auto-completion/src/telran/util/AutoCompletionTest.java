package telran.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class AutoCompletionTest {

	String words[]= {"abcdef","ab123","aaa","ab","ablmn","abbbb",
			"a", "ABd","bbb", "B12"};
	String wordsStartB[] = {"B12", "bbb"};
	String wordsStartAB[] = {"ab","ab123","abbbb","abcdef","ABd","ablmn"};
	String wordsStartABC[] = {"abcdef"};
	AutoCompletion autoCompletion;
	AutoCompletionMapImpl autoCompletionMap;
		@BeforeEach
		void setUp() throws Exception {
			autoCompletion = new AutoCompletionImpl();
			autoCompletionMap = new AutoCompletionMapImpl();
			for(String word: words) {
				autoCompletion.addWord(word);
				autoCompletionMap.addWord(word);
			}
		}

		@Test
		void test() {
			assertIterableEquals(Arrays.asList(wordsStartABC),
					autoCompletion.getCompletionOptions("abc"));
			assertIterableEquals(Arrays.asList(wordsStartB), autoCompletion.getCompletionOptions("B"));
			assertIterableEquals(Arrays.asList(wordsStartAB), autoCompletion.getCompletionOptions("ab"));
			
		}
		@Test
		void testRemoveIf_1() {
			assertEquals(4, ((AutoCompletionMapImpl) autoCompletionMap).removeIf(p -> p.length() >= 5));
			printMap("testRemoveIf_1");
		}
		@Test
		void testRemoveIf_2() {
			assertEquals(6, ((AutoCompletionMapImpl) autoCompletionMap)
					.removeIf(p -> p.startsWith("ab") || p.startsWith("AB")));
			printMap("testRemoveIf_2");
		}
		@Test
		void testRemoveIf_3() {
			assertEquals(0, ((AutoCompletionMapImpl) autoCompletionMap).removeIf(p -> p.endsWith("hh")));
			printMap("testRemoveIf_3");
		}
		void printMap(String prefix) {
			System.out.println("==============="+prefix+"=========================");
			HashMap<Character, TreeSet<String>> map = ((AutoCompletionMapImpl)autoCompletionMap).getMap();
			Set<Character> keys = map.keySet();
			System.out.println("Map length="+keys.size());
			for(Character ch : keys) {
				TreeSet<String> tree = map.get(ch);
				System.out.println("key="+ch+ "  len="+tree.size());
				for(String word : tree) {
					System.out.println(word);
				}
			}
		}
		void printSet(String prefix, Iterable<String> iterable) {
			System.out.println("===========" + prefix + "====================");
			printIterableSize(iterable);
			Iterator itr = iterable.iterator();
			while(itr.hasNext()) {
				System.out.println(itr.next());
				itr.remove();
			}
			printIterableSize(iterable);
		}
		int size = 0;
		void printIterableSize(Iterable<String> iterable) {
			size = 0;
			iterable.forEach(t->size++); 
			System.out.println("size="+size);
		}
}