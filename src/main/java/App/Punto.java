package App;

public class Punto {

    private double x,y;
    private int id;

    public Punto(double x, double y, int id){
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public Punto(){
    }

    public double getX() { return this.x; }

    public double getY() { return this.y; }

    public void setX(double x) { this.x=x; }

    public void setY(double y) { this.y = y; }

    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }

    @Override
    public String toString(){
        return new String(this.id + "(" + this.getX() + ", " + this.getY() + ")");
    }
}
