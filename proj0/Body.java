public class Body{

    // 6 instance variables:
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    // constant variable:
    static final double G = 6.67e-11;

    // 2 constructors:
    public Body(double xP, double yP, double xV, double yV, double m, String img){
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }
    public Body(Body b) {
        xxPos = b.xxVel;
        yyPos = b.yyPos;
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;
    }
    
    // instance methods
    public double calcDistance(Body b2){
        double dx = b2.xxPos - this.xxPos;
        double dy = b2.yyPos - this.yyPos;
        double dist = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        return dist;
    }
    public double calcForceExertedBy(Body b2){
        double dist = this.calcDistance(b2); // Note: "this."!!
        double force = G * this.mass * b2.mass / Math.pow(dist, 2);
        return force;
    }
    public double calcForceExertedByX(Body b2){
        double dx = b2.xxPos - this.xxPos;
        double r = this.calcDistance(b2);
        double f = this.calcForceExertedBy(b2);
        double fx = f * dx / r;
        return fx;
    }
    public double calcForceExertedByY(Body b2){
        double dy = b2.yyPos - this.yyPos;
        double r = this.calcDistance(b2);
        double f = this.calcForceExertedBy(b2);
        double fy = f * dy / r;
        return fy;
    }
    public double calcNetForceExertedByX(Body[] bodies){
        double fxx = 0;
        for (Body b : bodies){
            if (!this.equals(b)){ // Note: .equals can compare two objects!!!
                fxx += this.calcForceExertedByX(b);
            }
        }
        return fxx;
    }
    public double calcNetForceExertedByY(Body[] bodies){
        double fyy = 0;
        for (Body b : bodies){
            if (!this.equals(b)){ 
                fyy += this.calcForceExertedByY(b);
            }
        }
        return fyy;
    }

    public void update(double dt, double fX, double fY){
        double aX = fX / this.mass;
        double aY = fY / this.mass;
        // Then, update:
        this.xxVel += dt * aX;
        this.yyVel += dt * aY;
        this.xxPos += dt * xxVel;
        this.yyPos += dt * yyVel;
    }

    public void draw(){
        StdDraw.picture(this.xxPos, this.yyPos, "images/"+this.imgFileName);
    }
}