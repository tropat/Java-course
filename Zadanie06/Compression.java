import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class Compression implements CompressionInterface {
	List<String> words = new ArrayList<String>();
	List<String> compressedWords = new ArrayList<String>();
	List<String> compressedWordsCopy = new ArrayList<String>();
	Map<String, String> header = new HashMap<String, String>();
	Map<String, Integer> countedWords = new HashMap<String, Integer>();
	List<String> sortedCountedWords = new ArrayList<>();
	int wordsToCompress = 0;
	
	@Override
	public void addWord(String word) {
		words.add(word);
		
	}

	private void findCountMap() {
		boolean found = false;
		for(String word : words) {
			if(!countedWords.isEmpty()) {
				for(String key : countedWords.keySet()) {
					if(key.equals(word)) {
						countedWords.replace(key, countedWords.get(key), countedWords.get(key)+1);
						found = true;
						break;
					}
				}
			}
			if(countedWords.isEmpty() || !found) {
				countedWords.put(word, 1);
				wordsToCompress++;
			}
			found = false;
		}
	}
	
	private String findMax(Map<String, Integer> map) {
		String m = "";
		int pom = -1;
		for(String word : map.keySet()) {
			if(map.get(word) > pom /*&& map.get(word) != 1*/) {
				pom = map.get(word);
				m = word;
			}
		}
		if(!m.equals("")) {
			map.remove(m);
		}
		return m;
	}
	
	private void sortCountedWords() {
		Map<String, Integer> countedWordsCopy = new HashMap<>(countedWords);
		while(!countedWordsCopy.isEmpty()) {
			sortedCountedWords.add(findMax(countedWordsCopy));
		}
	}
	
	private void checkHowMany() {
		while(wordsToCompress != 0 && Integer.toBinaryString(wordsToCompress-1).length() >= words.get(0).length()) {
			wordsToCompress--;
		}
		
		int allWordsToCompress = 0;
		for(int i=0; i < wordsToCompress; i++) {
			allWordsToCompress += countedWords.get(sortedCountedWords.get(i));
		}
		
		int one = 1;
		if(wordsToCompress == 1) {
			one = 0;
		}
		
		if(wordsToCompress != 0 && (Integer.toBinaryString(wordsToCompress-1).length()+one)*(wordsToCompress+allWordsToCompress) +  (words.size()-allWordsToCompress)*(words.get(0).length()+1) + wordsToCompress*(words.get(0).length()) >= words.size()*words.get(0).length()) {
			wordsToCompress--;
			checkHowMany();
		}
		else if(wordsToCompress != 0) {
			int wordsToCompressCopy = wordsToCompress;
			int optimal = (Integer.toBinaryString(wordsToCompress-1).length()+one)*(wordsToCompress+allWordsToCompress) +  (words.size()-allWordsToCompress)*(words.get(0).length()+1) + wordsToCompress*(words.get(0).length());
			for(int i = wordsToCompressCopy; i > 0 ; i--) {
				allWordsToCompress = 0;
				for(int j=0; j < i; j++) {
					allWordsToCompress += countedWords.get(sortedCountedWords.get(j));
				}
				one = 1;
				if(i == 1) {
					one = 0;
				}
				int bits = (Integer.toBinaryString(i-1).length()+one)*(i+allWordsToCompress) +  (words.size()-allWordsToCompress)*(words.get(0).length()+1) + i*(words.get(0).length());
				if(bits < optimal) {
					optimal = bits;
					wordsToCompressCopy = i;
				}
			}
			wordsToCompress = wordsToCompressCopy;
		}
	}
	
	private void setHeader() {
		int one = 1;
		if(wordsToCompress == 1) {
			one = 0;
		}
		for(int i = 0 ; i < wordsToCompress; i ++) {
			String key = Integer.toBinaryString(i);
			while(key.length() != Integer.toBinaryString(wordsToCompress-1).length()+one) {
				key = "0" + key;
			}
			header.put(key, sortedCountedWords.get(i));
		}
	}
	
	private void compressFunc() {
		if(wordsToCompress > 0) {
			compressedWords = new ArrayList<>(words);
			
			setHeader();
			
			boolean toCompress = false;
			for(int i = 0 ; i < compressedWords.size(); i++) {
				for(String h : header.values()) {
					if(compressedWords.get(i).equals(h)) {
						for(String key : header.keySet()) {
							if(header.get(key).equals(h)) {
								compressedWords.set(i, key);
								toCompress = true;
								break;
							}
						}
					}
				}
				if(!toCompress) {
					compressedWords.set(i, "1"+compressedWords.get(i));
				}
				toCompress = false;
			}
		}
	}
	
	@Override
	public void compress() {
		findCountMap();
		sortCountedWords();
		checkHowMany();
		compressFunc();
		compressedWordsCopy = new ArrayList<>(compressedWords);
	}

	@Override
	public Map<String, String> getHeader() {
		return header;
	}

	@Override
	public String getWord() {
		String word = "";
		if(!compressedWords.isEmpty()) {
			word = compressedWords.get(0);
			compressedWords.remove(0);			
		}
		return word;
	}

}
