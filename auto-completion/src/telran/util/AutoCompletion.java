package telran.util;

public interface AutoCompletion {
	/**
	 * adds word
	 * @param str
	 * @return true if added
	 */
	boolean addWord(String str);
	/**
	 * removes word
	 * @param string
	 * @return true if removed
	 */
	boolean removeWord(String string);
	/**
	 * 
	 * @param prefix
	 * @return any Iterable of words beginning from a given prefix
	 */
	Iterable<String> getCompletionOptions(String prefix);
}
