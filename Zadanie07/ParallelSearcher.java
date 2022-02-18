import java.util.List;
import java.util.ArrayList;

class ParallelSearcher implements ParallelSearcherInterface{
	private static double totalValueOfPreviousObject = 0.0;
	HidingPlaceSupplier hps = null;
	
	private void checkAndTakeValue(HidingPlaceSupplier.HidingPlace hp) {
		if(hp.isPresent()) {
			synchronized(this) {
				totalValueOfPreviousObject += hp.openAndGetValue();				
			}
		}
	}
	public class Searcher extends Thread {
		public void run() {
			HidingPlaceSupplier.HidingPlace hp = null;
			do {
					hp = hps.get();
					if(hp != null) {
						checkAndTakeValue(hp);
					}
				
			} while(hp != null);
		}
	}
	
	@Override
	public void set(HidingPlaceSupplierSupplier supplier) {
		List<Thread> l = new ArrayList<>();
		Thread s = new Searcher();
		do {
			hps = supplier.get(totalValueOfPreviousObject);
			l = new ArrayList<>();
			if(hps != null) {
				totalValueOfPreviousObject = 0.0;
				for(int i=0;i<hps.threads();i++) {
					s = new Searcher();
					l.add(s);
					s.start();
				}
				for(int i = 0; i < l.size(); i ++) {
					try {
						l.get(i).join();
					} catch (InterruptedException e) {}
				}
			}
			
		} while (hps != null);
		
		totalValueOfPreviousObject = 0.0;
		
	}

}
