import edu.princeton.cs.algs4.SET;
import  edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
    private SET<Point2D> pointset;
    public PointSET() {
         pointset = new SET<Point2D>();
    }                              // construct an empty set of points
    public boolean isEmpty(){
        return pointset.isEmpty();
    }                      // is the set empty?
    public int size() {
        return pointset.size();
    }                        // number of points in the set
    public void insert(Point2D p){
        if(p == null) throw new IllegalArgumentException("Point p is null");
        else
        if (!pointset.contains(p)) pointset.add(p);
    }              // add the point to the set (if it is not already in the set)
    public boolean contains(Point2D p){
        if(p == null) throw new IllegalArgumentException("Point p is null");
        else
        return pointset.contains(p);
    }            // does the set contain point p?
    public void draw(){
        for (Point2D p : pointset){
            p.draw();
        }
    }                         // draw all points to standard draw
    public Iterable<Point2D> range(RectHV rect){
        if(rect == null) throw new IllegalArgumentException("Rect is null");
        else {
            SET<Point2D> rectset  = new SET<Point2D>();
            for (Point2D p : pointset){
               if (rect.contains(p)) rectset.add(p);
            }
            return rectset;
        }

    }             // all points that are inside the rectangle (or on the boundary)
    public Point2D nearest(Point2D p){
        Point2D nearestPoint = new Point2D(p.x(), p.y());
        double mindist = 0;

        for (Point2D q : pointset){
            if (p.distanceTo(q) < mindist) {
                mindist = p.distanceTo(q);
                nearestPoint = q;
            }
        }
        return nearestPoint;

    }           // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args){}                  // unit testing of the methods (optional)
}