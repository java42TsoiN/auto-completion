package telran.util;
import java.util.*;
public class AutoCompletionImpl implements AutoCompletion {
	private TreeSet<String> words = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
	@Override
	public boolean addWord(String word) {
		
		return words.add(word);
	}

	@Override
	public boolean removeWord(String word) {
		
		return words.remove(word);
	}

	@Override
	public Iterable<String> getCompletionOptions(String prefix) {
		
		return words.subSet(prefix, getPrefixLimit(prefix));
	}
	private String getPrefixLimit(String prefix) {
		char lastChar = prefix.charAt(prefix.length() - 1);
		char limitChar = (char) (lastChar + 1);
		return prefix.substring(0, prefix.length() - 1) + limitChar;

	}

}