import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Start {
	JFrame frame = new JFrame("Graph"); 
	JButton loadFileButton = new JButton("Load graph");
	GraphPanel graph  = new GraphPanel();
	List<List<Integer>> points = new ArrayList<>();
	List<List<Integer>> drawPoints = new ArrayList<>();
	List<List<Integer>> lines = new ArrayList<>();
	int maxX,maxY,minX,minY;
	
	public static void main(String[] argv) {
		Start s = new Start();
		s.set();
	}
	
	private void clear() {
		points.clear();
		drawPoints.clear();
		lines.clear();
	}
	
	private void findMinMax() {
		maxX = points.get(0).get(0);
		maxY = points.get(0).get(1);
		minX = points.get(0).get(0);
		minY = points.get(0).get(1);
		
		for(int i = 1; i < points.size(); i++) {
			if(points.get(i).get(0)>maxX) {
				maxX = points.get(i).get(0);
			}
			if(points.get(i).get(1)>maxY) {
				maxY = points.get(i).get(1);
			}
		}
		for(int i = 1; i < points.size(); i++) {
			if(points.get(i).get(0)<minX) {
				minX = points.get(i).get(0);
			}
			if(points.get(i).get(1)<minY) {
				minY = points.get(i).get(1);
			}
		}
	}
	
	private class LoadFile implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			clear();
			
			JFileChooser chooser = new JFileChooser("Load file");
			
			if(chooser.showOpenDialog(chooser) == JFileChooser.APPROVE_OPTION) {
				try {
					File file = chooser.getSelectedFile();
					List<Integer> list = new ArrayList<>();
					String[] l = new String[] {};
					Scanner myReader = new Scanner(file);
					String read;
					
					int n = Integer.parseInt(myReader.nextLine());
					for(int i = 0; i < n; i++) {
						read = myReader.nextLine();
						list = new ArrayList<>();
						l = new String[] {};
						l = read.split(" ");
						list.add(Integer.parseInt(l[0]));
						list.add(Integer.parseInt(l[1]));
						points.add(list);
						drawPoints.add(list);
					}
					
					n = Integer.parseInt(myReader.nextLine());
					for(int i = 0; i < n; i++) {
						read = myReader.nextLine();
						l = new String[] {};
						list = new ArrayList<>();
						l = read.split(" ");
						list.add(Integer.parseInt(l[0]));
						list.add(Integer.parseInt(l[1]));
						list.add(Integer.parseInt(l[2]));
						lines.add(list);
					}
					
					findMinMax();
					frame.getContentPane().repaint();
					
				} catch (FileNotFoundException error) {}
			}
		}	
	}
	
	
	class GraphPanel extends JPanel {
		int distanceX, distanceY;
		
		private void drawPoints(Graphics g) {
			int x,y;
			   int size;
			   List<Integer> l = new ArrayList<>();
			   
			   
			   for (int i = 0; i < points.size(); i++) {
				   l = new ArrayList<>();
				   x = getWidth()-(maxX-points.get(i).get(0))*getWidth()/distanceX - getWidth()/distanceX/2;
				   y = (maxY-points.get(i).get(1))*(getHeight())/distanceY + getHeight()/distanceY/2;
				   
				   l.add(x);
				   l.add(y);
				   drawPoints.set(i, l);
				   
				   if(getWidth()<getHeight()) {
					   size = getWidth()/distanceX/12+10;
				   } else {
					   size = getHeight()/distanceY/12+10;
				   }
				   
				   g.fillOval(x-size/2,y-size/2,size,size);
			   }
		}
			
		private void drawLines(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			   g2.setStroke(new BasicStroke(2));
			   
			   int x1, x2, y1, y2;
			   int maxWeight = 0;
			   if(!lines.isEmpty()) {
				   maxWeight = lines.get(0).get(2);				   
			   }
			   int weight = 2;
			   for(int i = 1; i < lines.size(); i++) {
				   if(maxWeight < lines.get(i).get(2)) {
					   maxWeight = lines.get(i).get(2);
				   }
			   }
			   for(int i = 0; i < lines.size(); i++) {
				   if(lines.get(i).get(2) <= 0.25*maxWeight) {
					   weight = 1;
				   } else if(lines.get(i).get(2) <= 0.5*maxWeight) {
					   weight = 4;
				   } else if(lines.get(i).get(2) <= 0.75*maxWeight) {
					   weight = 7;
				   } else {
					   weight = 10;
				   }
				   
				   g2.setStroke(new BasicStroke(weight));
				   x1 = drawPoints.get(lines.get(i).get(0)-1).get(0);
				   x2 = drawPoints.get(lines.get(i).get(1)-1).get(0);
				   y1 = drawPoints.get(lines.get(i).get(0)-1).get(1);
				   y2 = drawPoints.get(lines.get(i).get(1)-1).get(1);
				   
				   g2.draw(new Line2D.Float(x1,y1,x2,y2));
			   }
		   }
		
		
		   public void paintComponent( Graphics g ) {
			   super.paintComponent(g);
			   
			   distanceX = maxX - minX + 1;
			   distanceY = maxY - minY + 1;
			   
			   g.setColor(Color.black);
			   
			   drawPoints(g);
			   drawLines(g);
		   }
			   
		}
	
	private void  set(){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);
		
		loadFileButton.addActionListener(new LoadFile());
		
		frame.getContentPane().add(BorderLayout.PAGE_START,loadFileButton);
		frame.getContentPane().add(graph);
		
		frame.setVisible(true);
	}
}
