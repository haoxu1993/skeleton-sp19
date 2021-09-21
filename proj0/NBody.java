public class NBody{

    // class method:
    public static double readRadius(String fileName){
        In in = new In(fileName);
        int numPlanets = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    public static Body[] readBodies(String fileName){
        In in = new In(fileName);
        int numPlanets = in.readInt();
        double radius = in.readDouble();
        Body[] bodies = new Body[numPlanets];
        // Note: because planets.txt has redundant words in the end, don't use while (!in.isEmpty()){}
        for (int i = 0; i < numPlanets; i += 1){
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String imgFileName = in.readString();
            bodies[i] = new Body(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
        }
        return bodies;
    }

    public static void main (String[] args){

        StdDraw.enableDoubleBuffering();
        int waitTimeMilliseconds = 10;

        // Collecting All Needed Input:
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = NBody.readRadius(filename);
        Body[] bodies = NBody.readBodies(filename);
        // Setting the Background:
        StdDraw.setScale(-radius, radius);
        // Creating an Animation:
        for (double time = 0; time < T; time += dt){
            int numPlanets = bodies.length;
            double[] xForces = new double[numPlanets];
            double[] yForces = new double[numPlanets];
            for (int i = 0; i < numPlanets; i += 1){
                xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
                yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
                bodies[i].update(dt, xForces[i], yForces[i]);
            }
            // Drawing the Background:
            StdDraw.picture(0, 0, "images/starfield.jpg");
            // Drawing Bodies:
            for (Body b : bodies){
                b.draw();
            }
            StdDraw.show();
            StdDraw.pause(waitTimeMilliseconds);
        }
        // Printing the Universe: 
        StdOut.printf("%d\n", bodies.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < bodies.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                        bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                        bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);   
        }
    }
}