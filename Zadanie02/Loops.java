import java.util.List;
import java.util.ArrayList;

class Loops implements GeneralLoops {

	private List<Integer> lowerLimits = new ArrayList<Integer>();
	private List<Integer> upperLimits = new ArrayList<Integer>();
	private List<Integer> iterationsList;
	private List<List<Integer>> result;
	private int loops = 1;
	private int currentLoop = 1;
	
	@Override
	public void setLowerLimits(List<Integer> limits) {
		lowerLimits = limits;
		loops = limits.size();
	}

	@Override
	public void setUpperLimits(List<Integer> limits) {
		upperLimits = limits;
		loops = limits.size();
	}

	@Override
	public List<List<Integer>> getResult() {
		if(lowerLimits.size() < loops) {
			while(lowerLimits.size() < loops) {
				lowerLimits.add(0);
			}
		}
		if(upperLimits.size() < loops) {
			while(upperLimits.size() < loops) {
				upperLimits.add(0);
			}
		}
		
		if(currentLoop == 1) {
			result = new ArrayList<List<Integer>>();
			iterationsList = new ArrayList<Integer>();
		}
		
		for(int i = lowerLimits.get(currentLoop-1); i <= upperLimits.get(currentLoop-1); i++) {
			if(iterationsList.size() < currentLoop) {
				iterationsList.add(currentLoop-1,i);				
			}
			else {
				iterationsList.set(currentLoop-1,i);
			}
			if(currentLoop == loops) {
				result.add(iterationsList);
				iterationsList = new ArrayList(iterationsList);
			}
			else {
				currentLoop++;
				getResult();				
			}
		}
		
		if(currentLoop != 1) {
			currentLoop--;						
		}
		
		return result;
	}
	
}
