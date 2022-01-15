package telran.util;

import java.util.function.Predicate;
import java.util.*;
public class AutoCompletionMapImpl implements AutoCompletion {
HashMap<Character, TreeSet<String>> words = new HashMap<>(); //key - first character of a word;
//value - collection (TreeSet) of words beginning with the given first character
TreeSet<String> collection = new TreeSet<>();
	@Override
	/**
	 * adds word
	 * with applying the method computeIfAbsent
	 */
	public boolean addWord(String str) {
		return words.computeIfAbsent(getKey(str), t -> new TreeSet<>(String.CASE_INSENSITIVE_ORDER)).add(str);
	}

	@Override
	public boolean removeWord(String word) {
		return word.isEmpty() ? false : removeNonEmpty(word);
	}
	private boolean removeNonEmpty(String word) {
		TreeSet<String> tree = words.get(getKey(word));
		return tree==null ? false : tree.remove(word);
	}
	@Override
	public Iterable<String> getCompletionOptions(String prefix) {
		if(prefix.isEmpty()) {
			return  new TreeSet<String>();
		}
		Character character = getKey(prefix);
		collection = words.get(character);
		return collection==null?new TreeSet<String>():collection.subSet(prefix,getPrefixLimit(prefix));
	}

	private Character getKey(String prefix) {
		Character character =prefix.charAt(0);
		return character;
	}
	private String getPrefixLimit(String prefix) {
		char lastChar = prefix.charAt(prefix.length() - 1);
		char limitChar = (char) (lastChar + 1);
		return prefix.substring(0, prefix.length() - 1) + limitChar;

	}

	/**
	 * removes words matching a given predicate
	 * @param predicate
	 * @return count of the removed words
	 */
	public int removeIf(Predicate<String> predicate) {
		int counter = 0;
		Collection<TreeSet<String>> collection = words.values();
		for(TreeSet<String> treeSet : collection ) {
			int originalSize = treeSet.size();
			treeSet.removeIf(predicate);
			counter += (originalSize-treeSet.size());
		}
		return counter;
	}
	public HashMap<Character, TreeSet<String>> getMap() {
		return words;
	}
}
