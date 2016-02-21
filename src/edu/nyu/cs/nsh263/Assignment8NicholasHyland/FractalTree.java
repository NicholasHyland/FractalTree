package edu.nyu.cs.nsh263.Assignment8NicholasHyland;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.JFrame;
import javax.swing.JPanel;


/** This program extends JPanel which is used to draw a Fractal Tree. One of the key features that this program uses is recursion which is used to draw multiple branches which extends from a parent branch
 * 	The program also uses 2D graphics to actually display the contents of the JFrame
 */
public class FractalTree extends JPanel {
		public Graphics2D g1; //creates 2D graphics, contained in JPanel, called g1
		
		/**
		 * maxAngle: This is the maximum number of degrees that an angle can have. The angles are ultimately used to calculate the angle that each branch extends from the previous branch
		 * startX: This is the starting x-coordinate for the first branch of the tree
		 * startY: This is the starting y-coordinate for the first branch of the tree
		 * numOfRecursions: This is the number of times we want the Fractals to recurse
		 * startAngle: This is the starting angle for the first branch of the tree, which is 0 meaning that it is vertical
		 * treeSize: This value is used to create the size of the tree, or the length of the branches of the tree
		 * Detail: This sets the limit for the number of times the Fractal Tree recurses, for when the number of recursions reaches this number, the recursion will stop
		 * randFact: This value gives a variance for the degree of each branch
		 * constFact: This array has two negative and two positive values which will be used to create the starting angle of each new branch
		 */
		
	   	public static final int maxAngle = 360;
	    public static final int startX = 600;
	    public static final int startY = 800;
	    public static final int numOfRecursions = 9;
	    public static final int startAngle = 0;
	    public static final double treeSize = 2;
	    public static final int Detail = 2;
	    public static final int randFact = 30;
	    public static final int[] constFact = {-60, 05, -50, 45};
	    
	    /**
	     * These arrays stores integers from 0 - 255 so that they can be used to generate different colors using values for red, green, and blue
	     */
	    public static int[] red =   {0, 0, 0, 0, 7, 15, 23, 31, 39, 47, 55, 43};
	    public static int[] green = {171, 159, 147, 135, 123, 111, 99, 87, 75, 63, 51, 43};
	    public static int[] blue =  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};   
	     
	    
	    /** This method accepts as its parameter a degree as an integer, and converts it into a radian using the formula: degree * (PI /180) and returns that value
	     */
	    public static double degToRad(int deg) {
	        return deg * Math.PI / 180;
	    }
	    
	    /** This is the part of the program that creates the branches of the tree. It implements 5 different parameters:
	     * 	Graphics2D: 
	     * 	int x: starting x-coordinate location of each branch
	     * 	int y: starting y-coordinate location of each branch
	     * 	int n: the number of times drawFractal is going to recurse
	     * 	int angle: angle at which each branch extends from its parent branch
	     * 	It uses these parameters to create the branches of the tree and calls itself four times so that each branch has four child branches, and this continues until the number of recursions reaches a set limit
	     */
	    public static void drawFractal(Graphics2D g1, int x, int y, int n, int angle) {
	    	/**
	    	 * This is basically a flag, where when the number of recursions reaches Detail, which is 3, drawFractal will return and therefore return back to where it was called. The value for number of recursions is decremented once for each drawFractal which is called below
	    	 * Since drawFractal calls itself with a value of n which is n-1, this number will eventually reach Detail. Since n starts from 9, the number of total recursions is 6 meaning there are a total of 6 different types of branch
	    	 */
	        if (n == Detail) return;
	        int len = (int) Math.round(Math.pow(treeSize, n - 1)); //Generates an integer which is 2 to the power of n - 1, and since n is decrementing by 1 during each recursion, the length of each branch will be halved each time
	         
	        /**
	         * xn1 and yn1 are integers which are used in the creation of the x and y-coordinates of the starting point of each branch below
	         * The values are created and adjusted based on the current angle (in radians) of the current branch which is going to extend four more branches
	         * The values are also a ratio of the current length of the parent branch
	         */
	        int xn1 = (int) Math.round(x - (2 * len * Math.sin(degToRad(angle))));
	        int yn1 = (int) Math.round(y - (2 * len * Math.cos(degToRad(angle))));
	        
	        /**
	         * These values determine the x and y-coordinates of the starting point of the four new branches
	         * The x and y-coordinates of each new branch is based on the coordinates of the previous branches so that their starting points are not the same
	         * The x-y position of each branch is a ratio of the length of each parent branch
	         */
	        int mid1x = (x + xn1) / 2;
	        int mid1y = (y + yn1) / 2;
	        int mid2x = (mid1x + xn1) / 2;
	        int mid2y = (mid1y + yn1) / 2;
	        int mid3x = (x + mid1x) / 2;
	        int mid3y = (y + mid1y) / 2;
	        int mid4x = (mid3x + mid1x) / 2;
	        int mid4y = (mid3y + mid1y) / 2;
	        
	        java.util.Random r = new java.util.Random(); //creates a random floating point between 0 and 1 excluding 1
	        
	        /** 
	         * drawFractal is called 4 times, which suggests that each branch of the Fractal tree will have 4 other branches attached to it
	         * The 1st argument, g1 is a Graphics2D which is later used 
	         * Each of the new recursions accepts different values for it's startX and startY (x-y location) as well as the startAngle
	         * 		The x-y coordinate locations are different for each of the four branches which is calculated above
	         * The 4th argument, number of recursions, becomes n - 1 so that each time drawFractal is recursed, the number of recursions is decremented by 1.
	         * The last argument, start angle, generates a different starting angle for each Fractal recursion. 
	         * 		For the first and third recursion, it uses constFact[0] and constFact[2] which are negative numbers -60 and -50, which is added to r.nextInt(randFact) (a number between 0 and 30)
	         * 		This gives a degree variance of -60 to -30 degrees, and -50 to -20 degrees for the first and third branch recursion respectively. This means that these two branches will be on the left side of its parent branch
	         * 		For the second and fourth recursion, constFact[1] and constFact[3] are positive values, and gives a degree variance of 5 to 35 degrees and 45 to 75 degrees respectively. This means that these two branches will be on the right side of its parent branch
	         * 		The angles are also dependent on its parents current angle, denoted 'angle', so that recursive branches are adjusted for it's angle such that there will always be two branches on the left and two branches on the right
	         * 		Also, the angles are not beyond -90 and 90 degrees so that the branches are always pointing upwards respectively to its parent branch
	         * 		The startAngle parameter also % maxAngle so that it doesn't return a value greater than the maxAngle which is 360
	         */
	        drawFractal(g1, mid1x, mid1y, n - 1, (angle + r.nextInt(randFact) + constFact[0]) % maxAngle);
	        drawFractal(g1, mid2x, mid2y, n - 1, (angle + r.nextInt(randFact) + constFact[1]) % maxAngle);
	        drawFractal(g1, mid3x, mid3y, n - 1, (angle + r.nextInt(randFact) + constFact[2]) % maxAngle);
	        drawFractal(g1, mid4x, mid4y, n - 1, (angle + r.nextInt(randFact) + constFact[3]) % maxAngle);
	        
	        /** Color c creates a new color for each new branch, where each new branch becomes consecutively more green than the previous
	         *  r.nextInt() % 3 gets a number from 0 - 2, and + n for the first recursion will give you the last three elements of the red[], green[], and blue[], which is 9, 10, and 11
	         *  because n is being decreased by 1 during each recursion, the second recursion will give you the last four elements minus the last one, so elements 8, 9, and 10 for red[], green[], and blue[]
	         *  This continues until the position of the arrays are 0, 1, and 2, and since green[] has decreasing values in it's array, the latter recursions for each branch are more green than the previous
	         */ 
	        Color c = new Color(red[(r.nextInt() % 3) + n], green[(r.nextInt() % 3) + n], blue[(r.nextInt() % 3) + n]);
	        g1.setColor(c); //uses the value generated above to set the color of g1, which is the 2D graphics used to draw the line below
	        
	        /**
	         * Line2D L1 creates a new 2D line and accepts as its parameter a starting x and y location (denoted x and y) for the branch, as well as its ending x and y location (denoted xn1, yn1)
	         */
	        Line2D L1 = new Line2D.Double(x, y, xn1, yn1);
	        g1.draw(L1); // Draws to the screen the line which is created above
	        return; //ends the method
	    }
	    
	    /**
	     * This class basically prints the illustrations to the screen by using Graphics2D and calling drawFractal(), refreshing the screen constantly so that a 2D image appears
	     * paint takes as its argument a Graphics g which allows it to create graphics
	     */
	    public void paint(final Graphics g) {
	        g1 = (Graphics2D) g; //the graphics g is converted into 2D graphics by casting (Graphics2D), and is assigned to g1 which is used as one of the arguments of drawFractal
	        drawFractal(g1, startX, startY, numOfRecursions, startAngle);
	    }
	    
	    public static void main(String args[]) {
	        JFrame FF = new JFrame("Drawing a recursive tree"); //creates a JFrame which is basically the window which displays the Fractal Tree. This JFrame is titled "Drawing a recursive tree"
	        FF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closes the JFrame when the user clicks the exit button
	        FractalTree F = new FractalTree(); //Creates a new Fractal Tree
	        FF.setBackground(Color.BLACK); //Sets the background color of the display screen to black
	        FF.add(F); //The JFrame implements the FractalTree F into its interface
	        FF.pack();
	        FF.setVisible(true); //the boolean true is assigned to make the JFrame visible so that the window actually appears
	        FF.setSize(1200, 1000); //Sets the size of the screen to 1200 x 1000
	    }
	}