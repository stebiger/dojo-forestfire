package main.java.de.oc.dojo.forestfire;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
 
import javax.swing.JFrame;
import javax.swing.JPanel;
 
public class ForestFireSimulator extends JFrame{
	public static final char BURNING = 'w'; //w looks like fire, right?
	public static final char TREE = 'T';
	public static final char EMPTY = '.';
	public static final double F = 0.0001;
	public static final double P = 0.01;
	public static final double TREE_PROB = 0.5;
    
        
        private static ISimulationProcessor simProcessor;
	private List<String> land;
	private final JPanel landPanel;
 
	public ForestFireSimulator(List<String> land){
		this.land = land;
		landPanel = new JPanel(){
			@Override
			public void paint(Graphics g) {
				for(int y = 0; y < ForestFireSimulator.this.land.size();y++){
					String row = ForestFireSimulator.this.land.get(y);
					for(int x = 0; x < row.length();x++){
						switch(row.charAt(x)){
							case BURNING:
								g.setColor(Color.RED);
								break;
							case TREE:
								g.setColor(Color.GREEN);
								break;
							default: //will catch EMPTY
								g.setColor(Color.WHITE);
						}
						g.fillRect(x*3, y*3, 3, 3);
					}
				}
			}
		};
		//each block in the land is a 3x3 square
		landPanel.setSize(this.land.get(0).length() * 3, this.land.size() * 3);
		add(landPanel);
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
 

	public static List<String> populate(int width, int height){
		List<String> land = new LinkedList<>();
		for(;height > 0; height--){//height is just a copy anyway
			StringBuilder line = new StringBuilder(width);
			for(int i = width; i > 0; i--){
				line.append((Math.random() < TREE_PROB) ? TREE : EMPTY);
			}
			land.add(line.toString());
		}
		return land;
	}
 
        @SuppressWarnings("SleepWhileInLoop")
	public void processN(int n) {
		for(int i=0; i< n ; i++){
			land = simProcessor.process(land);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			repaint();
		}
	}
        @SuppressWarnings("SleepWhileInLoop")
	public void processForever() {
		while (true) {
			land = simProcessor.process(land);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			repaint();
		}
	}
 
	public static void main(String[] args){
//		List<String> land = Arrays.asList(".TTT.T.T.TTTT.T",
//				"T.T.T.TT..T.T..",
//				"TT.TTTT...T.TT.",
//				"TTT..TTTTT.T..T",
//				".T.TTT....TT.TT",
//				"...T..TTT.TT.T.",
//				".TT.TT...TT..TT",
//				".TT.T.T..T.T.T.",
//				"..TTT.TT.T..T..",
//				".T....T.....TTT",
//				"T..TTT..T..T...",
//				"TTT....TTTTTT.T",
//				"......TwTTT...T",
//				"..T....TTTTTTTT",
//				".T.T.T....TT...");
//		Fire fire = new Fire(land);
//		fire.processN(5);
                simProcessor = new EmptySimulationProcessor();
		List<String> land = populate(267, 200);
		ForestFireSimulator fire2 = new ForestFireSimulator(land);
//		fire2.processN(10)
                fire2.processForever();
	}
}