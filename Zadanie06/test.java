
public class test {

	public static void main(String[] args) {
		Compression obj = new Compression();
		obj.addWord("001");
		obj.addWord("001");
		obj.addWord("011");
		obj.addWord("101");
		obj.addWord("001");
		obj.addWord("011");
		obj.compress();
		System.out.println(obj.words);
		System.out.println(obj.countedWords);
		System.out.println(obj.wordsToCompress);
		System.out.println(obj.getHeader());
		System.out.println(obj.compressedWords);
		
		obj = new Compression();
		for(int i = 0 ; i < 9 ; i ++) {
			obj.addWord("001");			
		}
		obj.addWord("000");
		obj.addWord("010");
		obj.addWord("011");
		obj.addWord("110");
		obj.addWord("111");
		obj.compress();
		System.out.println(obj.words);
		System.out.println(obj.countedWords);
		System.out.println(obj.wordsToCompress);
		System.out.println(obj.getHeader());
		System.out.println(obj.compressedWords);
		
		obj = new Compression();
		// 1110 1110 1111 1111 1111 1110 1111 1100 1100 1101 1111 1111 1111 1111 1111 1101 1011 1001
		obj.addWord("1110");
		obj.addWord("1110");
		obj.addWord("1111");
		obj.addWord("1111");
		obj.addWord("1111");
		obj.addWord("1110");
		obj.addWord("1111");
		obj.addWord("1100");
		obj.addWord("1100");
		obj.addWord("1101");
		obj.addWord("1111");
		obj.addWord("1111");
		obj.addWord("1111");
		obj.addWord("1111");
		obj.addWord("1111");
		obj.addWord("1101");
		obj.addWord("1011");
		obj.addWord("1001");
		obj.compress();
		System.out.println(obj.words);
		System.out.println(obj.countedWords);
		System.out.println(obj.wordsToCompress);
		System.out.println(obj.getHeader());
		System.out.println(obj.compressedWords);
		
		obj = new Compression();
		//11 11 11 11 10 11 11 10 11 11 10 11 11 11
		for(int i = 0 ; i< 4; i ++) {
			obj.addWord("11");
		}
		obj.addWord("10");
		for(int i = 0 ; i< 2; i ++) {
			obj.addWord("11");
		}
		obj.addWord("10");
		for(int i = 0 ; i< 2; i ++) {
			obj.addWord("11");
		}
		obj.addWord("10");
		for(int i = 0 ; i< 2; i ++) {
			obj.addWord("11");
		}
		obj.compress();
		System.out.println(obj.words);
		System.out.println(obj.countedWords);
		System.out.println(obj.wordsToCompress);
		System.out.println(obj.getHeader());
		System.out.println(obj.getHeader().size());
		System.out.println(obj.compressedWords);
		
		obj = new Compression();
		//1111 1101 1011 1111 1100 1101 1111 1101 1110 1110 1010 1111 1010 1111
		obj.addWord("1111");
		obj.addWord("1101");
		obj.addWord("1101");
		obj.addWord("1111");
		obj.addWord("1100");
		obj.addWord("1101");
		obj.addWord("1111");
		obj.addWord("1101");
		obj.addWord("1110");
		obj.addWord("1110");
		obj.addWord("1010");
		obj.addWord("1111");
		obj.addWord("1010");
		obj.addWord("1111");
		obj.compress();
		System.out.println(obj.words);
		System.out.println(obj.countedWords);
		System.out.println(obj.wordsToCompress);
		System.out.println(obj.getHeader());
		System.out.println(obj.getHeader().size());
		System.out.println(obj.compressedWords);
		
		obj = new Compression();
		//10010 11111 11100 11111 01111 11011 11111 11100 11100 11101 11110 11010 11111 11100 10011 11111 11101 10010 11011 10001 11101
		obj.addWord("10010");
		obj.addWord("11111");
		obj.addWord("11100");
		obj.addWord("11111");
		obj.addWord("01111");
		obj.addWord("11011");
		obj.addWord("11111");
		obj.addWord("11100");
		obj.addWord("11100");
		obj.addWord("11101");
		obj.addWord("11110");
		obj.addWord("11010");
		obj.addWord("11111");
		obj.addWord("11100");
		obj.addWord("10011");
		obj.addWord("11111");
		obj.addWord("11101");
		obj.addWord("10010");
		obj.addWord("11011");
		obj.addWord("10001");
		obj.addWord("11101");
		obj.compress();
		System.out.println(obj.words);
		System.out.println(obj.countedWords);
		System.out.println(obj.wordsToCompress);
		System.out.println(obj.getHeader());
		System.out.println(obj.getHeader().size());
		System.out.println(obj.compressedWords);
		
		
		obj = new Compression();
		// 11011 11101 11101 11101 10110 11110 11111 11000 11101 10000 11101 11010 11110 11111 11110
		obj.addWord("11011");
		obj.addWord("11101");
		obj.addWord("11101");
		obj.addWord("11101");
		obj.addWord("10110");
		obj.addWord("11110");
		obj.addWord("11111");
		obj.addWord("11000");
		obj.addWord("11101");
		obj.addWord("10000");
		obj.addWord("11101");
		obj.addWord("11010");
		obj.addWord("11110");
		obj.addWord("11111");
		obj.addWord("11110");
		obj.compress();
		System.out.println(obj.words);
		System.out.println(obj.countedWords);
		System.out.println(obj.wordsToCompress);
		System.out.println(obj.getHeader());
		System.out.println(obj.compressedWords);
	}

}
