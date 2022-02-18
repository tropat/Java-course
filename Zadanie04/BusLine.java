import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.HashMap;

class BusLine implements BusLineInterface{
	private Map<String, List<Position>> busLines = new HashMap<String, List<Position>>();
	private Map<String, List<LineSegment>> linesSegments = new HashMap<String, List<LineSegment>>();
	private Map<String, List<Position>> lineIntersections = new HashMap<String, List<Position>>();
	private Map<String, List<Position>> lineIntersectionsSorted = new HashMap<String, List<Position>>();
	private Map<String, List<Position>> linePositions = new HashMap<String, List<Position>>();
	private Map<String, List<String>> lineIntersectionLinesSorted = new HashMap<String, List<String>>();
	private Map<BusLineInterface.LinesPair, Set<Position>> linePairIntersections = new HashMap<BusLineInterface.LinesPair, Set<Position>>();
	
	public class LinesPair implements BusLineInterface.LinesPair {
		private String firstLineName = "";
		private String secondLineName = "";
		
		public LinesPair(String first, String second) {
			firstLineName = first;
			secondLineName = second;
		}
		
		@Override
		public String getFirstLineName() {
			return firstLineName;
		}

		@Override
		public String getSecondLineName() {
			return secondLineName;
		}
		
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			LinesPair other = (LinesPair) obj;
			return firstLineName == other.getFirstLineName() && secondLineName == other.getSecondLineName();
		}
	}
	
	
	@Override
	public void addBusLine(String busLineName, Position firstPoint, Position lastPoint) {
		List<Position> line = new ArrayList<Position>();
		line.add(firstPoint);
		line.add(lastPoint);
		busLines.put(busLineName, line);	
	}

	@Override
	public void addLineSegment(String busLineName, LineSegment lineSegment) {
		if(linesSegments.containsKey(busLineName)) {
			linesSegments.get(busLineName).add(lineSegment);
		}
		else {
			List<LineSegment> segment = new ArrayList<LineSegment>();
			segment.add(lineSegment);
			linesSegments.put(busLineName, segment);
		}		
	}

	private void sort() {
		for(String key : linesSegments.keySet()) {
			Position i = busLines.get(key).get(0);
			for(int j = 0; j < linesSegments.get(key).size(); j++) {
				if(!linesSegments.get(key).get(j).getFirstPosition().equals(i)) {	
					for(int k = j; k < linesSegments.get(key).size(); k++) {
						if(linesSegments.get(key).get(k).getFirstPosition().equals(i)) {
							Collections.swap(linesSegments.get(key), k, j);
							break;
						}
					}
				}
				 // SCALANIE SCIEZEK
				i = new Position2D(linesSegments.get(key).get(j).getLastPosition().getCol(), linesSegments.get(key).get(j).getLastPosition().getRow());
				if(j > 0) {
					if(linesSegments.get(key).get(j).getFirstPosition().getCol() == linesSegments.get(key).get(j).getLastPosition().getCol() && linesSegments.get(key).get(j-1).getFirstPosition().getCol() == linesSegments.get(key).get(j-1).getLastPosition().getCol() && linesSegments.get(key).get(j).getLastPosition().getCol() == linesSegments.get(key).get(j-1).getLastPosition().getCol()) {
						linesSegments.get(key).set(j-1, new LineSegment(linesSegments.get(key).get(j-1).getFirstPosition(), linesSegments.get(key).get(j).getLastPosition()));
						linesSegments.get(key).remove(j);
						j--;
					}
					else if(linesSegments.get(key).get(j).getFirstPosition().getRow() == linesSegments.get(key).get(j).getLastPosition().getRow() && linesSegments.get(key).get(j-1).getFirstPosition().getRow() == linesSegments.get(key).get(j-1).getLastPosition().getRow() && linesSegments.get(key).get(j).getLastPosition().getRow() == linesSegments.get(key).get(j-1).getLastPosition().getRow()) {
						linesSegments.get(key).set(j-1, new LineSegment(linesSegments.get(key).get(j-1).getFirstPosition(), linesSegments.get(key).get(j).getLastPosition()));
						linesSegments.get(key).remove(j);
						j--;
					}
					else if(linesSegments.get(key).get(j).getFirstPosition().getCol() != linesSegments.get(key).get(j).getLastPosition().getCol() && linesSegments.get(key).get(j).getFirstPosition().getRow() != linesSegments.get(key).get(j).getLastPosition().getRow() && linesSegments.get(key).get(j-1).getFirstPosition().getCol() != linesSegments.get(key).get(j-1).getLastPosition().getCol() && linesSegments.get(key).get(j-1).getFirstPosition().getRow() != linesSegments.get(key).get(j-1).getLastPosition().getRow()) {
						if(linesSegments.get(key).get(j).getFirstPosition().getCol() < linesSegments.get(key).get(j).getLastPosition().getCol() && linesSegments.get(key).get(j).getFirstPosition().getRow() < linesSegments.get(key).get(j).getLastPosition().getRow() && linesSegments.get(key).get(j-1).getFirstPosition().getCol() < linesSegments.get(key).get(j-1).getLastPosition().getCol() && linesSegments.get(key).get(j-1).getFirstPosition().getRow() < linesSegments.get(key).get(j-1).getLastPosition().getRow()) {
							linesSegments.get(key).set(j-1, new LineSegment(linesSegments.get(key).get(j-1).getFirstPosition(), linesSegments.get(key).get(j).getLastPosition()));
							linesSegments.get(key).remove(j);
							j--;
						}
						else if(linesSegments.get(key).get(j).getFirstPosition().getCol() > linesSegments.get(key).get(j).getLastPosition().getCol() && linesSegments.get(key).get(j).getFirstPosition().getRow() < linesSegments.get(key).get(j).getLastPosition().getRow() && linesSegments.get(key).get(j-1).getFirstPosition().getCol() > linesSegments.get(key).get(j-1).getLastPosition().getCol() && linesSegments.get(key).get(j-1).getFirstPosition().getRow() > linesSegments.get(key).get(j-1).getLastPosition().getRow()) {
							linesSegments.get(key).set(j-1, new LineSegment(linesSegments.get(key).get(j-1).getFirstPosition(), linesSegments.get(key).get(j).getLastPosition()));
							linesSegments.get(key).remove(j);
							j--;
						}
						else if(linesSegments.get(key).get(j).getFirstPosition().getCol() < linesSegments.get(key).get(j).getLastPosition().getCol() && linesSegments.get(key).get(j).getFirstPosition().getRow() > linesSegments.get(key).get(j).getLastPosition().getRow() && linesSegments.get(key).get(j-1).getFirstPosition().getCol() < linesSegments.get(key).get(j-1).getLastPosition().getCol() && linesSegments.get(key).get(j-1).getFirstPosition().getRow() > linesSegments.get(key).get(j-1).getLastPosition().getRow()) {
							linesSegments.get(key).set(j-1, new LineSegment(linesSegments.get(key).get(j-1).getFirstPosition(), linesSegments.get(key).get(j).getLastPosition()));
							linesSegments.get(key).remove(j);
							j--;
						}
						if(linesSegments.get(key).get(j).getFirstPosition().getCol() > linesSegments.get(key).get(j).getLastPosition().getCol() && linesSegments.get(key).get(j).getFirstPosition().getRow() < linesSegments.get(key).get(j).getLastPosition().getRow() && linesSegments.get(key).get(j-1).getFirstPosition().getCol() > linesSegments.get(key).get(j-1).getLastPosition().getCol() && linesSegments.get(key).get(j-1).getFirstPosition().getRow() < linesSegments.get(key).get(j-1).getLastPosition().getRow()) {
							linesSegments.get(key).set(j-1, new LineSegment(linesSegments.get(key).get(j-1).getFirstPosition(), linesSegments.get(key).get(j).getLastPosition()));
							linesSegments.get(key).remove(j);
							j--;
						}
					}
				}
			}
		}
	}
	
	private void mapaPunktow() {
		for(String key : linesSegments.keySet()) {
			if(lineIntersections.keySet().contains(key)) {
			for(LineSegment segment : linesSegments.get(key)) {
				if(segment.getFirstPosition().getCol() == segment.getLastPosition().getCol()) {
						for(int i = segment.getFirstPosition().getRow(), k = -1; ;k++) {
							if(linePositions.containsKey(key)) {
								k = linePositions.get(key).size()-1;
								Position pos = new Position2D(segment.getFirstPosition().getCol(), i);
								if(!(linePositions.get(key).get(k).getCol() == segment.getFirstPosition().getCol() && linePositions.get(key).get(k).getRow() == i)) {
									linePositions.get(key).add(pos);
								}
								else {
									k--;
								}
							}
							else {
								List<Position> list = new ArrayList<Position>();
								list.add(new Position2D(segment.getFirstPosition().getCol(), i));
								linePositions.put(key, list);
							}
							
							if(i == segment.getLastPosition().getRow()) {
								break;
							}
							
							if(segment.getFirstPosition().getRow() < segment.getLastPosition().getRow()) {
								i++;
							}
							else {
								i--;
							}
						}
				}
				else if(segment.getFirstPosition().getRow() == segment.getLastPosition().getRow()) {
					for(int i = segment.getFirstPosition().getCol(), k = -1; ;k++) {
						if(linePositions.containsKey(key)) {
							k = linePositions.get(key).size()-1;
							Position pos = new Position2D(i, segment.getFirstPosition().getRow());
							if(!(linePositions.get(key).get(k).getRow() == segment.getFirstPosition().getRow() && linePositions.get(key).get(k).getCol() == i)) {
								linePositions.get(key).add(pos);
							}
							else {
								k--;
							}
						}
						else {
							List<Position> list = new ArrayList<Position>();
							list.add(new Position2D(i, segment.getFirstPosition().getRow()));
							linePositions.put(key, list);
						}
						
						if(i == segment.getLastPosition().getCol()) {
							break;
						}
						
						if(segment.getFirstPosition().getCol() < segment.getLastPosition().getCol()) {
							i++;
						}
						else {
							i--;
						}
					}
				}
				else {
						for(int i = segment.getFirstPosition().getCol(), j = segment.getFirstPosition().getRow(), k = -1;;k++) {
							if(linePositions.containsKey(key)) {
								k = linePositions.get(key).size()-1;
								Position pos = new Position2D(i, j);
								if(!(linePositions.get(key).get(k).getCol() == i && linePositions.get(key).get(k).getRow() == j)) {
									linePositions.get(key).add(pos);									
								}
								else {
									k--;
								}
							}
							else {
								List<Position> list = new ArrayList<Position>();
								list.add(new Position2D(i, j));
								linePositions.put(key, list);
							}
							
							if(i == segment.getLastPosition().getCol()) {
								break;
							}
							
							if(segment.getFirstPosition().getCol() < segment.getLastPosition().getCol()) {
								i++;
							}
							else {
								i--;
							}
							if(segment.getFirstPosition().getRow() < segment.getLastPosition().getRow()) {
								j++;
							}
							else {
								j--;
							}
					}
				}
			}
			}
		}
	}
	
	private void skrzyzowania() {
		for(String key1 : linesSegments.keySet()) {
			for(LineSegment segment1 : linesSegments.get(key1)) {
				for(String key2 : linesSegments.keySet()) {
					for(LineSegment segment2 : linesSegments.get(key2)) {
						int seg1x1 = segment1.getFirstPosition().getCol();
						int seg1y1 = segment1.getFirstPosition().getRow();
						int seg1x2 = segment1.getLastPosition().getCol();
						int seg1y2 = segment1.getLastPosition().getRow();
						int seg2x1 = segment2.getFirstPosition().getCol();
						int seg2y1 = segment2.getFirstPosition().getRow();
						int seg2x2 = segment2.getLastPosition().getCol();
						int seg2y2 = segment2.getLastPosition().getRow();
						
						// DO MAPY PAR //
						boolean contains = false;
						LinesPair pair = new LinesPair(key1, key2);
						if(!linePairIntersections.isEmpty()) {
							for(var p : linePairIntersections.keySet()) {
								if(pair.equals(p)) {
									contains = true;
								}
							}
							if(!contains) {
								Set<Position> set = new HashSet<Position>();
								linePairIntersections.put(pair, set);
							}
						} 
						else {
							Set<Position> set = new HashSet<Position>();
							linePairIntersections.put(pair, set);
						}
						// DO MAPY PAR //
						
						if(segment1 != segment2) {
							if(seg1x1 == seg1x2 && seg2y1 == seg2y2) {
								// pionowe, poziome
								if(seg1x1 > Math.min(seg2x1, seg2x2) && seg1x1 < Math.max(seg2x1, seg2x2)) {
									if(seg2y1 > Math.min(seg1y1, seg1y2) && seg2y1 < Math.max(seg1y1, seg1y2)) {
										
										// MAPA LINIA-SKRZYZOWANIA
										if(lineIntersections.containsKey(key1)) {
											lineIntersections.get(key1).add(new Position2D(seg1x1,seg2y1));
										}
										else {
											List<Position> list = new ArrayList<Position>();
											list.add(new Position2D(seg1x1,seg2y1));
											lineIntersections.put(key1, list);
										}
										
										if(lineIntersections.containsKey(key2) && key1 != key2) {
											lineIntersections.get(key2).add(new Position2D(seg1x1,seg2y1));
										}
										else if(key1 != key2){
											List<Position> list = new ArrayList<Position>();
											list.add(new Position2D(seg1x1,seg2y1));
											lineIntersections.put(key2, list);
										}									
										
										// PARA LINII
										Position pos = new Position2D(seg1x1,seg2y1);
										contains = false;
											for(var p : linePairIntersections.keySet()) {
												if(pair.equals(p)) {
													for(var posit : linePairIntersections.get(p)) {
														if(posit.equals(pos)) {
															linePairIntersections.get(p).remove(posit);
														}
													}
													linePairIntersections.get(p).add(pos);
													contains = true;
												}
											}											
											
											contains = false;
											pair = new LinesPair(key2, key1);
												for(var p : linePairIntersections.keySet()) {
													if(pair.equals(p)) {
														for(var posit : linePairIntersections.get(p)) {
															if(posit.equals(pos)) {
																linePairIntersections.get(p).remove(posit);
															}
														}
														linePairIntersections.get(p).add(pos);
														contains = true;
													}
												}
												
												if(!contains) {
													Set<Position> set = new HashSet<Position>();
													set.add(pos);
													linePairIntersections.put(pair, set);
												}									
									}
								}
								
							}
							else if(seg1x1 != seg1x2 && seg1y1 != seg1y2 && seg2x1 != seg2x2 && seg2y1 != seg2y2) { 
								// po skosie
								if(((seg1x1 < seg1x2 && seg1y1 < seg1y2) || (seg1x1 > seg1x2 && seg1y1 > seg1y2)) && ((seg2x1 < seg2x2 && seg2y1 > seg2y2) || (seg2x1 > seg2x2 && seg2y1 < seg2y2))) {
									if(Math.min(seg1x1, seg1x2) < Math.max(seg2x1, seg2x2) && Math.min(seg1y1, seg2y2) < Math.max(seg2y1, seg2y2)) {
										int x = ((seg2y1 - ((seg2y1-seg2y2)/(seg2x1 - seg2x2))*seg2x1) - (seg1y1 - ((seg1y1-seg1y2)/(seg1x1 - seg1x2))*seg1x1))/((seg1y1-seg1y2)/(seg1x1-seg1x2)-(seg2y1-seg2y2)/(seg2x1-seg2x2));
										int y = ((seg1y1-seg1y2)/(seg1x1-seg1x2))*x + seg1y1 - ((seg1y1-seg1y2)/(seg1x1-seg1x2))*seg1x1;
										
										// MAPA LINIA-SKRZYZOWANIA
										if(lineIntersections.containsKey(key1)) {
											lineIntersections.get(key1).add(new Position2D(x,y));
										}
										else {
											List<Position> list = new ArrayList<Position>();
											list.add(new Position2D(x,y));
											lineIntersections.put(key1, list);
										}
										
										if(lineIntersections.containsKey(key2) && key1 != key2) {
											lineIntersections.get(key2).add(new Position2D(x,y));
										}
										else if(key1 != key2){
											List<Position> list = new ArrayList<Position>();
											list.add(new Position2D(x,y));
											lineIntersections.put(key2, list);
										}
										
										// PARA LINII
										Position pos = new Position2D(x,y);
										contains = false;
											for(var p : linePairIntersections.keySet()) {
												if(pair.equals(p)) {
													for(var posit : linePairIntersections.get(p)) {
														if(posit.equals(pos)) {
															linePairIntersections.get(p).remove(posit);
														}
													}
													linePairIntersections.get(p).add(pos);
													contains = true;
												}
											}

											contains = false;
											pair = new LinesPair(key2, key1);
												for(var p : linePairIntersections.keySet()) {
													if(pair.equals(p)) {
														for(var posit : linePairIntersections.get(p)) {
															if(posit.equals(pos)) {
																linePairIntersections.get(p).remove(posit);
															}
														}
														linePairIntersections.get(p).add(pos);
														contains = true;
													}
												}
												
												if(!contains) {
													Set<Position> set = new HashSet<Position>();
													set.add(pos);
													linePairIntersections.put(pair, set);
												}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	private void skrzyzowaniaLinie() {
		for(String line1 : busLines.keySet()) {
			if(linePositions.containsKey(line1)) {
			for(Position pos1 : linePositions.get(line1)) {
				for(String line2 : busLines.keySet()) {
					if(linePositions.containsKey(line2)) {
					for(Position pos2 : linePositions.get(line2)) {
							if(pos1.equals(pos2) && pos1 != pos2 && lineIntersections.containsKey(line1)) {
								for(Position pos : lineIntersections.get(line1)) {
									if(pos1.equals(pos)) {
										
										boolean valid = false;
										LinesPair pair = new LinesPair(line1, line2);
										for(var p : linePairIntersections.keySet()) {
											if(p.equals(pair)) {
												for(var posit : linePairIntersections.get(p)) {
													if(posit.equals(pos1)) {
														valid = true;
													}
												}
											}
										}
										if(valid) {
											if(lineIntersectionLinesSorted.containsKey(line1)) {
												lineIntersectionLinesSorted.get(line1).add(line2);
											}
											else {
												List<String> list = new ArrayList<>();
												list.add(line2);
												lineIntersectionLinesSorted.put(line1, list);
											}						
										}
										
										break;											
									}
								}
							}
					}
					}
				}
			}
			}
		}
	}
	
	private void skrzyzowaniaSort() {
		for(String line1 : busLines.keySet()) {
			if(linePositions.containsKey(line1)) {
			for(Position pos1 : linePositions.get(line1)) {
				if(lineIntersections.containsKey(line1)) {
					for(Position pos : lineIntersections.get(line1)) {
						if(pos1.equals(pos)) {
							if(lineIntersectionsSorted.containsKey(line1)) {
									lineIntersectionsSorted.get(line1).add(pos1);
							}
							else {
								List<Position> list = new ArrayList<>();
								list.add(pos1);
								lineIntersectionsSorted.put(line1, list);
							}
						}
					}
				}
			}
			}
		}
	}
	
	@Override
	public void findIntersections() {
		// SORTOWANIE
		sort();
		
		// SKRZYZOWANIA
		skrzyzowania();
		
		// MAPA PUNKTOW
		mapaPunktow();
		
		// SORTOWANIE SKRZYZOWANIA
		skrzyzowaniaLinie();
		skrzyzowaniaSort();
		
	}

	@Override
	public Map<String, List<Position>> getLines() {
		return linePositions;
	}

	@Override
	public Map<String, List<Position>> getIntersectionPositions() {
		return lineIntersectionsSorted;
	}

	@Override
	public Map<String, List<String>> getIntersectionsWithLines() {
		return lineIntersectionLinesSorted;
	}

	@Override
	public Map<BusLineInterface.LinesPair, Set<Position>> getIntersectionOfLinesPair() {
		return linePairIntersections;
	}

}
