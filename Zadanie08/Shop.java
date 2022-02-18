import java.util.Map;
import java.util.HashMap;

class Shop  implements ShopInterface{
	private Map<String, Integer> stock = new HashMap<>();
	private Map<String, Object> synchro = new HashMap<>();
	private Map<Long, Boolean> synch = new HashMap<>();
	private Map<Long, Boolean> syn = new HashMap<>();
	
	private void addToStock(Map<String, Integer> goods) {
		if(!synch.containsKey(Thread.currentThread().getId())) {
				synch.put(Thread.currentThread().getId(), false);
		}
		for(var good : goods.keySet()) {
			for(var s : stock.keySet()) {
				if(good.equals(s)) {
						stock.replace(s, stock.get(s) + goods.get(good));						
							synch.replace(Thread.currentThread().getId(), true);							
					break;
				}
			}
			if(synch.get(Thread.currentThread().getId()) == false) {
					if(!synchro.containsKey(good)) {
						synchro.put(good, new Object());
				}
					stock.put(good, goods.get(good));					
			}
					synch.replace(Thread.currentThread().getId(), false);					
		}
	}
	
	private synchronized Object func(String s) {
		return synchro.get(s);
	}
	
	@Override
	public synchronized void delivery(Map<String, Integer> goods) {
		addToStock(goods);
		for(var good : goods.keySet()) {
			for(var s : stock.keySet()) {
				if(good.equals(s)) {
					synchronized(func(s)) {
						func(s).notifyAll();
					}
					break;
				}
			}
		}
		
	}

	private synchronized int check(String s) {
		return stock.get(s);
	}
	
	
	@Override
	public boolean purchase(String productName, int quantity) {
		if(!syn.containsKey(Thread.currentThread().getId())) {
			synchronized(this) {
				syn.put(Thread.currentThread().getId(), false);				
			}
		}
		for(var s : stock.keySet()) {
			if(s.equals(productName)) {
				synchronized(this) {
					syn.replace(Thread.currentThread().getId(), true);					
				}
					if(check(s) >= quantity) {
						synchronized(this) {
						stock.replace(s, stock.get(s) - quantity);
						}
						synchronized(this) {
							syn.replace(Thread.currentThread().getId(), false);							
						}
						return true;
					} else {
						try {
							synchronized(func(s)) {
							synchro.get(s).wait();
							}
						} catch (InterruptedException e) {}
					}
						if(check(s) >= quantity) {
							synchronized(this) {
							stock.replace(s, stock.get(s) - quantity);
							}
							synchronized(this) {
								syn.replace(Thread.currentThread().getId(), false);								
							}
							return true;
						}								
				}	
		}
		if(syn.get(Thread.currentThread().getId()) == false) {
			synchronized(this) {
				if(!synchro.containsKey(productName)) {
					synchro.put(productName, new Object());	
				}
				if(!stock.containsKey(productName)) {
					stock.put(productName, 0);									
				}
			}
			synchronized(func(productName)) {
				try {
					func(productName).wait();
				} catch (InterruptedException e) {}			
			}
			synchronized(this) {
				if(check(productName) >= quantity) {
					stock.replace(productName, stock.get(productName) - quantity);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Map<String, Integer> stock() {
		return stock;
	}

}
