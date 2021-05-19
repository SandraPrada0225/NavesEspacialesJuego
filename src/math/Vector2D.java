package math;

public class Vector2D {
	
	private double x,y;
	
	public Vector2D(double x,double y) {
		this.x=x;
		this.y=y;
		
	}
	
	public Vector2D() {
		x=0;
		y=0;
	}
	
	public Vector2D add(Vector2D v) {
		return new Vector2D(x+v.getX(),y+v.getY());
	}
	
	public Vector2D subtract(Vector2D v) {
		return new Vector2D(x-v.getX(),y-v.getY());
	}
	
	public Vector2D limit(double value) {
		
		   if(getMagnitude() > value)
           {
            return this.normalizar().scale(value);
           }
           
           return this;
		
	}
	
	public Vector2D normalizar() {
		double magnitud= getMagnitude();
		return new Vector2D(x/magnitud,y/magnitud);
	}
	
	//modificamos la magnitud del vector direccion
	public Vector2D scale(double value) {
		
		return new Vector2D(x*value, y*value);
	}
	
	public double getMagnitude() {
		return Math.sqrt(x*x+y*y);
	}
	
	public Vector2D setDirection(double angle)
	{
		double magnitud= getMagnitude();
		return new Vector2D(Math.cos(angle)*magnitud,Math.sin(angle)*magnitud);
	}
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public double getAngle() {
		return Math.asin(y/getMagnitude());
	}
	
	
	
}
