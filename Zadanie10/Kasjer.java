import java.util.List;
import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.Collections;

class Kasjer implements KasjerInterface {
	private List<Pieniadz> kasa = new ArrayList<>();
	private RozmieniaczInterface rozmieniacz;
	private List<Pieniadz> klienta = new ArrayList<>();
	private List<Pieniadz> reszta = new ArrayList<>();
	private List<Pieniadz> zKasy = new ArrayList<>();
	
	Pieniadz pieniadzDoRozmienienia = null;
	
	private int kwota;
	private int doRozmienienia;
	
	private Pieniadz ost = null;
	
	private void sortowanie() {
		Collections.sort(kasa, (a, b) -> {
			return a.wartosc() - b.wartosc();
		});
		
		Collections.sort(klienta, (a, b) -> {
			return a.wartosc() - b.wartosc();
		});
	}
	
	private int liczenieKwoty() {
		int kwota = 0;
		
		for(var pieniadz : klienta) {
			kwota += pieniadz.wartosc();
		}
		
		return kwota;
	}
	
	private void szukaniePieniedzyKlienta() {
		for(var pieniadz : klienta) {
			if(pieniadz.wartosc() > doRozmienienia) {
				break;
			}
			else {
				doRozmienienia -= pieniadz.wartosc();
				reszta.add(pieniadz);
			}
		}
	}
	
	private void czyszczenie(List<Pieniadz> lista, List<Pieniadz> doCzyszczenia) {
		for(var r : lista) {
			if(doCzyszczenia.contains(r)) {
				doCzyszczenia.remove(r);
			}
		}
	}
	
	private void uzupelnianieKasy() {
		for(var k : klienta) {
			kasa.add(k);
		}
	}
	
	private void szukaniePieniadzaDoRozmienienia() {
		if(!klienta.isEmpty()) {
			pieniadzDoRozmienienia = klienta.get(0);					
		} else {
			pieniadzDoRozmienienia = null;
		}
		
		for(var pieniadz : klienta) {
			if(pieniadz.czyMozeBycRozmieniony() == true && pieniadz.wartosc() > doRozmienienia) {
				pieniadzDoRozmienienia = pieniadz;
				break;
			}
		}
	}
	
	private void wydawanieZKasy() {
		zKasy.clear();
		
		for(var pieniadz : kasa) {
			if(pieniadz.wartosc() > doRozmienienia && doRozmienienia != 0 && pieniadz.czyMozeBycRozmieniony()) {
				kasa.addAll(rozmieniacz.rozmien(pieniadz));
				zKasy.add(pieniadz);
				break;
			} else if(pieniadz.wartosc() > doRozmienienia && doRozmienienia == 0) {
				break;
			}
			else if(pieniadz.wartosc() <= doRozmienienia && doRozmienienia != 0 && pieniadz.czyMozeBycRozmieniony()) {
				doRozmienienia -= pieniadz.wartosc();
				reszta.add(pieniadz);
				zKasy.add(pieniadz);
			} else if(pieniadz.wartosc() <= doRozmienienia && doRozmienienia != 0 && !pieniadz.czyMozeBycRozmieniony()) {
				if(ost == null || (ost != null && !ost.equals(pieniadz))) {
					ost = pieniadz;					
				}
			} else if(pieniadz.wartosc() <= doRozmienienia && doRozmienienia == 0) {
				break;
			}
		}
		
		czyszczenie(zKasy, kasa);		
		
	}
	
	private void dodajNRzKasydoReszty() {
		if(doRozmienienia != 0 && zKasy.isEmpty() && ost != null) {
			reszta.add(ost);
			doRozmienienia -= ost.wartosc();
			kasa.remove(ost);
			sortowanie();
			ost = null;
		}
	}
	
	@Override
	public List<Pieniadz> rozlicz(int cena, List<Pieniadz> pieniadze) {
		klienta = new ArrayList<>();
		klienta.addAll(pieniadze);
		reszta = new ArrayList<>();
		pieniadzDoRozmienienia = null;
		ost = null;
		
		kwota = liczenieKwoty();
		doRozmienienia = kwota - cena;
		
		while(doRozmienienia != 0) {
			sortowanie();
			
			szukaniePieniedzyKlienta();
			
			czyszczenie(reszta, klienta);
			
			if(doRozmienienia != 0) {
				szukaniePieniadzaDoRozmienienia();
				
				if(pieniadzDoRozmienienia != null && pieniadzDoRozmienienia.czyMozeBycRozmieniony()) {
					klienta.addAll(rozmieniacz.rozmien(pieniadzDoRozmienienia));
					klienta.remove(pieniadzDoRozmienienia);
				} else {
					if(pieniadzDoRozmienienia != null) {
						klienta.remove(pieniadzDoRozmienienia);
						reszta.add(pieniadzDoRozmienienia);						
					}
					
					while(doRozmienienia != 0) {
						wydawanieZKasy();
						sortowanie();
						
						dodajNRzKasydoReszty();
					}
				}
			}
			
		}
		
		uzupelnianieKasy();
		
		return reszta;
	}

	@Override
	public List<Pieniadz> stanKasy() {
		return kasa;
	}

	@Override
	public void dostępDoRozmieniacza(RozmieniaczInterface rozmieniacz) {
		this.rozmieniacz = rozmieniacz;
		
	}

	@Override
	public void dostępDoPoczątkowegoStanuKasy(Supplier<Pieniadz> dostawca) {
		Pieniadz p = dostawca.get();
		while(p != null) {
			kasa.add(p);
			p = dostawca.get();
		}
		
	}

}
