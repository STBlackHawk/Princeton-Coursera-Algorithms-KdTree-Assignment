import edu.princeton.cs.algs4.SET;
import  edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import org.w3c.dom.css.Rect;

import java.util.TreeSet;

public class KdTree {
        private TreeSet<Node> Tree;


        private static class Node{
                private Point2D point;
                private RectHV rect;
                private Node lb;
                private Node rt;
                private boolean rotation;
                private int size;

                public Node(Point2D p, RectHV rect, boolean rotation){
                    this.point = p;
                    this.rect = rect;
                    this.rotation = rotation;

                }


        }

        private Node root;


        public  KdTree(){
        }// construct an empty set of points


    public  boolean isEmpty(){return size() == 0;}                      // is the set empty?

    public  int size(){return root.size;}                         // number of points in the set

    public void insert(Point2D p) {
            if (p == null) throw new IllegalArgumentException("Point should not be null");

            root = insert(root, p, root.rotation, 0,0,1,1);

        }
        private Node insert(Node n, Point2D p, boolean rotation, double xmin, double ymin, double xmax, double ymax){
            if (n == null){
                RectHV rect = new RectHV(xmin, ymin, xmax,ymax);
                return new Node(p, rect,!rotation);
            }

            if (!n.rotation){
                if (p.y() < n.point.y()) n.lb = insert(n.lb, p, n.rotation,n.rect.xmin(), n.rect.ymin(), n.rect.xmax(),n.point.y());
                else n.rt = insert(n.rt, p, n.rotation, n.rect.xmin(), n.point.y(),n.rect.xmax(),n.rect.ymax());
            }
            else {
                if (p.x() < n.point.x()) n.lb = insert(n.lb, p, n.rotation, n.rect.xmin(), n.rect.ymin(),n.point.x(), n.rect.ymax() );
                else n.rt = insert(n.rt, p, n.rotation,n.point.x(), n.rect.ymin(),n.rect.xmax(), n.rect.ymax());

            }
            n.size = 1+ n.rt.size + n.lb.size;
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
                if (p.y() < n.point.y()) contains(n.lb, p);
                else contains(n.rt, p);
            }
            else {
                if (p.x() < n.point.x()) contains(n.lb, p);
                else contains(n.rt, p);

            }
            return false;
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
            return range(rect, root);
    }// all points that are inside the rectangle (or on the boundary)
    private Iterable<Point2D> range (RectHV rect, Node n){
            SET<Point2D> Points = new SET<Point2D>();
            if (n.rect.intersects(rect)){
                if (rect.contains(n.point)) Points.add(n.point);
                range(rect, n.lb);
                range(rect, n.rt);

            }
            return Points;

    }

    public    Point2D nearest(Point2D p){
            return nearest(p, root);
    }             // a nearest neighbor in the set to point p; null if the set is empty
    private Point2D nearest(Point2D p, Node n){
            Point2D nearest;
            double min = n.point.distanceSquaredTo(p);
            nearest = n.point;
            if(root.lb.rect.distanceSquaredTo(p) < min){
                 Point2D lb =  nearest(p, n.lb);
                 double dist = lb.distanceSquaredTo(p);
                 if (dist < min){
                     min = dist;
                     nearest = lb;
                 }
            }
            if(root.rt.rect.distanceSquaredTo(p) < min){
            Point2D rt =  nearest(p, n.rt);
                double dist = rt.distanceSquaredTo(p);
                if (dist < min ) {
                    min = dist;
                    nearest = rt;
                }
            }

            return nearest;


    }

    public static void main(String[] args) {}           // unit testing of the methods (optional)

}
