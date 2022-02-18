
class Decode extends DecoderInterface {
	private String inputCode = "";
	private String outputCode = "";
	
	@Override
	public void input(int bit) {
		inputCode += Integer.toString(bit);
	}

	@Override
	public String output() {
		int counter = -1;
		int ones = -1;
		char bit = ' ';
		
		for(int i = 0; i < inputCode.length(); i++) {
			bit = inputCode.charAt(i);
			if(ones == -1 && bit == '0' && counter != -1) {
				ones = counter+1;
			}
			
			if(bit == '0' && counter != -1) {
				outputCode += Integer.toString(counter / ones);
				counter = -1;
				bit = ' ';
			}
			else if(bit == '1'){
				counter++;
			}
		}
		
		return outputCode;
	}

	@Override
	public void reset() {
		inputCode = "";
		outputCode = "";
	}
	
}
