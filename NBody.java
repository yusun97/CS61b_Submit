public class NBody{

	public static void main(String[] args){
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String fileName = args[2];
		Planet[] bodies = readBodies(fileName);
		double radius = readRadius(fileName);
		StdDraw.setScale(-radius, radius);
		StdDraw.clear();
		StdDraw.picture(0, 0, "./images/starfield.jpg");

		for(int i = 0; i < bodies.length; i++){
			bodies[i].draw();
		}

		StdDraw.enableDoubleBuffering();


		double curT = 0;
		double[] fX = new double[bodies.length];
		double[] fY = new double[bodies.length];
		while(curT < T){
			StdDraw.clear();
			StdDraw.picture(0, 0, "./images/starfield.jpg");
			for(int i = 0; i < bodies.length; i++){
				fX[i] = bodies[i].calcNetForceExertedByX(bodies);
				fY[i] = bodies[i].calcNetForceExertedByY(bodies);
			}

			//don’t call bodies[0].update() 
			//until after the entire xForces and yForces arrays are done
			for(int i = 0; i < bodies.length; i++){
				bodies[i].update(dt, fX[i], fY[i]);
				bodies[i].draw();
			}

			StdDraw.show();
			StdDraw.pause(10);
			curT += dt;
		}

		StdOut.printf("%d\n", bodies.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < bodies.length; i++) {
		    StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
		                  bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
		                  bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);   
		}
	}


	public static double readRadius(String fileName){
		In in = new In(fileName);
		in.readInt();
		return in.readDouble();
	}

	public static Planet[] readBodies(String fileName){
		In in = new In(fileName);
		int num = in.readInt();
		double rd = in.readDouble();

		Planet[] bodies = new Planet[num];

		/*
		 The first two values are the x- and y-coordinates of the initial position; 
		 the next pair of values are the x- and y-components of the initial velocity; 
		 the fifth value is the mass; 
		 the last value is a String that is the name of an image file used to display the planets.
		*/

		for(int i = 0; i < num && !in.isEmpty(); i++){
			double xxPos = in.readDouble();
			double yyPos = in.readDouble();
			double xxVel = in.readDouble();
			double yyVel = in.readDouble();
			double mass = in.readDouble();
			String imgFileName = in.readString();
			bodies[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);

		}
		return bodies;
	}


}

