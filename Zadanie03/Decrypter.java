import java.util.Map;
import java.util.HashMap;

class Decrypter implements DecrypterInterface {
	private String inputText = "";
	private String[] inputTextArray = new String[] {};
	private String[] text = new String[] {"Wydział", "Fizyki,", "Astronomii", "i", "Informatyki", "Stosowanej"};
	private Map<Character, Character> code = new HashMap<Character, Character>();
	private Map<Character, Character> decode = new HashMap<Character, Character>();
	private int licznik = 0;
	private int position = -1;
	private int m = 0;
	
	private void searchForPosition() {
		licznik = 0;
		position = -1;
		if(inputText != null) {
			for(; m < inputTextArray.length; m++) {
				if(licznik != text.length) {
					if(inputTextArray[m].length() == text[licznik].length()) {
						licznik++;
						
						if(licznik == text.length) {
							position = m - text.length + 1;
							break;
						}
						
					}
					else if(licznik != 0){
						m = m - licznik;
						licznik = 0;
					}
				}
			}				
		}
	}
	
	@Override
	public void setInputText(String encryptedDocument) {
		licznik = 0;
		position = -1;
		inputText = encryptedDocument;
		if(inputText != null) {
			inputTextArray = inputText.split("\\s+");		
		}
	}

	@Override
	public Map<Character, Character> getCode() {
		code.clear();
		m = 0;
		searchForPosition();
			for(int i = 0; i < text.length; i++) {
				if(licznik == text.length) {
					for(int j = 0; j < text[i].length(); j++) {

							if((!code.containsKey(text[i].charAt(j)) && !code.containsValue(inputTextArray[position + i].charAt(j)) ) || (code.containsKey(text[i].charAt(j)) && code.get(text[i].charAt(j)) == inputTextArray[position + i].charAt(j)) ) {
								code.put(text[i].charAt(j), inputTextArray[position + i].charAt(j));							
							}
							else {
								code.clear();
								searchForPosition();
								i = -1;
								break;
							}
						
					}					
				}
				else {
					break;
				}
			}
			code.remove(',');
		m = 0;
		return code;
	}

	@Override
	public Map<Character, Character> getDecode() {
		decode.clear();
		m = 0;
		searchForPosition();
		for(int i = 0; i < text.length; i++) {
			if(licznik == text.length) {
				for(int j = 0; j < text[i].length(); j++) {

						if((!decode.containsKey(inputTextArray[position + i].charAt(j)) && !decode.containsValue(text[i].charAt(j)) ) || (decode.containsKey(inputTextArray[position + i].charAt(j)) && decode.get(inputTextArray[position + i].charAt(j)) == text[i].charAt(j) )) {
							decode.put(inputTextArray[position + i].charAt(j), text[i].charAt(j));							
						}
						else {
							decode.clear();
							searchForPosition();
							i = -1;
							break;
						}
					
				}					
			}
			else {
				break;
			}
		}
		decode.remove(',');
	m = 0;
	return decode;
	}

}
