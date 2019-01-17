import edu.princeton.cs.algs4.SET;
import  edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;


public class KdTree {
    private Node root;

    private static class Node {
                private Point2D point;
                private RectHV rect;
                private Node lb;
                private Node rt;
                private boolean rotation;
                private int size;

                public Node(Point2D p, RectHV rect, boolean rotation, int size){
                    this.point = p;
                    this.rect = rect;
                    this.rotation = rotation;
                    this.size = size;

                }


    }



    public  KdTree(){}// construct an empty set of points


    public  boolean isEmpty(){return size() == 0;}                      // is the set empty?

    public  int size(){return size(root);}

    private int size(Node n){
        if (n == null) return 0;
        return n.size;
    }// number of points in the set

    public void insert(Point2D p){
            if (p == null) throw new IllegalArgumentException("Point should not be null");

            root = insert(root, p, false, 0,0,1,1);
    }
    private Node insert(Node n, Point2D p, boolean rotation, double xmin, double ymin, double xmax, double ymax){
            if (n == null){
                RectHV rect = new RectHV(xmin, ymin, xmax,ymax);
                return new Node(p, rect,!rotation, 1);
            }
            if (n.point.equals(p)) return n;

            if (!n.rotation){
                if (p.y() < n.point.y()) n.lb = insert(n.lb, p, n.rotation,n.rect.xmin(), n.rect.ymin(), n.rect.xmax(),n.point.y());
                else n.rt = insert(n.rt, p, n.rotation, n.rect.xmin(), n.point.y(),n.rect.xmax(),n.rect.ymax());
            }
            else {
                if (p.x() < n.point.x()) n.lb = insert(n.lb, p, n.rotation, n.rect.xmin(), n.rect.ymin(),n.point.x(), n.rect.ymax() );
                else n.rt = insert(n.rt, p, n.rotation,n.point.x(), n.rect.ymin(),n.rect.xmax(), n.rect.ymax());
            }
            n.size = 1+ size(n.rt) +  size(n.lb);
            return n;
    }              // add the point to the set (if it is not already in the set)

    public  boolean contains(Point2D p){
            if (p == null) throw new IllegalArgumentException("Point should not be null");
            return contains(root, p);
    }// does the set contain point p?
    private boolean contains( Node n, Point2D p){
            if (n == null ) return false;
            if (n.point.equals(p)) return true;
            if (!n.rotation){
                if (p.y() < n.point.y()) return contains(n.lb, p);
                else return contains(n.rt, p);
            }
            else {
                if (p.x() < n.point.x()) return contains(n.lb, p);
                else return contains(n.rt, p);

            }

    }

    public void draw(){ }// draw all points to standard draw
    private void draw(Node n) {
        while (n != null) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            n.point.draw();
            if (n.rotation) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius();
                n.rect.draw();
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius();
                n.rect.draw();
            }
            draw(n.lb);
            draw(n.rt);

        }
    }

    public Iterable<Point2D> range(RectHV rect){
        if (rect == null) throw new IllegalArgumentException("Rect should not be null");
             range(rect, root);
             return Points;
    }// all points that are inside the rectangle (or on the boundary)

    private SET<Point2D> Points = new SET<>();
    private void range (RectHV rect, Node n){
            if (n.rect.intersects(rect)){
                if (rect.contains(n.point)) Points.add(n.point);
                if (n.lb != null) range(rect, n.lb);
                if (n.rt != null) range(rect, n.rt);

            }
    }

    public    Point2D nearest(Point2D p){
        if (p == null) throw new IllegalArgumentException();
        return nearest(p, root);
    }             // a nearest neighbor in the set to point p; null if the set is empty
    private Point2D nearest(Point2D p, Node n){

            Point2D nearest;
            double min = n.point.distanceTo(p);
            nearest = n.point;

            if (n.lb !=null && n.lb.rect.distanceTo(p) < min) {
                Point2D lb = nearest(p, n.lb);
                double dist = lb.distanceTo(p);
                if (dist < min) {
                    min = dist;
                    nearest = lb;
                }
            }
            if (n.rt!=null && n.rt.rect.distanceSquaredTo(p) < min) {
                Point2D rt = nearest(p, n.rt);
                double dist = rt.distanceTo(p);
                if (dist < min) {
                    min = dist;
                    nearest = rt;
                }
            }

            return nearest;




    }

    public static void main(String[] args){

    }           // unit testing of the methods (optional)

}
