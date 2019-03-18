import java.util.*;
/********************************************************************************
Name: Calvin H
Date: 2/11/16
javac-introcs NBody.java
java-introcs NBody

*Compilation and tests are succesful. Results as expected from the FAQs of the assignment

Test-Script for planets.txt:
    NBodyTestScript.sh

TestScript Output
    NBodyTestOutput.txt

Expected Answers for Output taken from the assignment's FAQ:
    planetsExpectedOutput.txt
    * No differences when compared to NBodyTestOutput.txt

Not included in the Test-Script:
    Running the program using .txt files for the other simulations within nbody directory

    *Tested each one, animation and expected simulation runs correctly.
*//////////////////////////////////////////////////////////////////////////////////
public class NBody{
	private static void Animation(double [] x, double[] y, double[] vx, double[] vy, double[] mass, String[] Gif, double radius, double dTime, double Time){
		double FX[]=new double[x.length]; //X-Netforce
		double FY[]=new double[x.length]; //Y-Netforce
		double accelx[]=new double[x.length]; //X-Acceleration
		double accely[]=new double[x.length]; //Y-Acceleration
		double r;
		double G= 6.67e-11; //Gravitational constant; Given

		//Initial Drawing; Drawing the initial positions of the bodies
		StdDraw.setScale(-radius, radius);
		StdAudio.play("2001.mid");
		//StdAudio.play("Sprach.wav"); Fun with TA Josh


		StdDraw.picture(0, 0, "starfield.jpg");
			for(int i=0; i<x.length; i++){
				StdDraw.picture(x[i], y[i], Gif[i]);
			}
			StdDraw.show(20);

		//Calculations at T
		for(double d=0.0; d<Time; d+=dTime){
		    //The principle of superposition
		    //Netforces
		    for(int i=0; i<FY.length; i++){ //FY.length used to implement N outside of main method
			    for(int j=0; j<FY.length; j++){
			    	if (j==i) //To avoid comparing a body to itself, will skip to next iteration
			    		continue;
			        r = Math.sqrt((Math.pow((x[j]-x[i]), 2) + Math.pow((y[j]-y[i]),2)));
			        FX[i]=FX[i]+((G*(mass[i]*mass[j])/(Math.pow(r,2))*((x[j]-x[i])/r)));
			        FY[i]=FY[i]+(G*(mass[i]*mass[j])/(Math.pow(r,2))*((y[j]-y[i])/r));
			    }
			}

			//Accelerations: Acceleration= Netforce x Mass
		    for(int i=0; i<FY.length; i++){
		    	accelx[i]=FX[i]/(mass[i]);
		    	accely[i]=FY[i]/(mass[i]);
		    }

		    //Velocities: New Velocity = Old Velocity + (Delta Time * Acceleration)
		    for(int i=0; i<FY.length; i++){
			    vx[i]=vx[i]+(dTime*accelx[i]);
			    vy[i]=vy[i]+(dTime*accely[i]);
		    }

		    //New Positions: New Position(x or y) = Previous Position + (deltaTime x Velocity)
			for(int i=0; i<FY.length; i++){
			    x[i]=x[i]+(dTime*vx[i]);
			    y[i]=y[i]+(dTime*vy[i]);
		    }

			//ReDraw, Flip-book style (Will draw, then clear, then redraw)
			StdDraw.clear();
			StdDraw.picture(0, 0, "starfield.jpg");
			for(int i=0; i<x.length; i++){
				StdDraw.picture(x[i], y[i], Gif[i]);
			}
			StdDraw.show(20);

			//Resetting Netforces to 0
			for(int i=0; i<FY.length; i++){
			    FX[i]=0;
			    FY[i]=0;
			}
		}

		//Final Positions / Output for debugging
		StdOut.printf("%d\n", x.length);
		StdOut.printf("%.2e\n", radius);
		for (int i=0; i<x.length; i++){
			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n", x[i], y[i], vx[i], vy[i], mass[i], 				Gif[i]);
		}

	}

	public static void main(String[] args){
		double Time = Double.parseDouble(args[0]); //Time
		double dTime = Double.parseDouble(args[1]); //delta time *Must be < Time

		int N = StdIn.readInt(); //# of bodies
		double Radius=StdIn.readDouble(); //Radius of universe
		double x[]=new double[N]; //x-position
		double y[]=new double[N]; //y-position
		double vx[]=new double[N]; //initial x-velocity
		double vy[]=new double[N]; //initial y-velocity
		double mass[]=new double[N]; //Mass
		String Gif[] =new String[N]; //Gif File

		//Parallel Arrays for storing the values; will read to the end of the input file
		for (int a=0; a<N; a++){
			x[a]=StdIn.readDouble();
			y[a]=StdIn.readDouble();
			vx[a]=StdIn.readDouble();
			vy[a]=StdIn.readDouble();
			mass[a]=StdIn.readDouble();
			Gif[a]=StdIn.readString();
		}
		//StdOut.printf("%.2e\n", Time);	//Test for Time proper reading
		//StdOut.printf("%.2e\n", dTime); //Test for dTime proper reading

		/*Trace Code for Debugging StdIn
		StdOut.printf("%d\n", N);
		StdOut.printf("%.2e\n", Radius);
		for (int i=0; i<N; i++){
			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n", x[i], y[i], vx[i], vy[i], mass[i], 				Gif[i]);
		}*/

        //Helper method for modularity
		Animation(x, y, vx, vy, mass, Gif, Radius, dTime, Time);
	}
}//End of File
