
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import edu.princeton.cs.algs4.SET;
import  edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class test {

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("test3");
        Scanner sc = new Scanner(file);
        KdTree tree = new KdTree();
        PointSET set = new PointSET();
       RectHV rect1 = new RectHV(0.46, 0.07,0.7, 0.74);
        while(sc.hasNextLine()){
            Point2D p = new Point2D(sc.nextDouble(), sc.nextDouble());
            tree.insert(p);
//           System.out.println(tree.size());
            set.insert(p);
        }
//        Point2D p = new Point2D(0.19, 0.56);
//        System.out.println(tree.contains(p));

//        Point2D test = tree.nearest(p);
//        System.out.println(test);
//        System.out.println(tree.contains(p));
       Iterable<Point2D> testset = tree.range(rect1);
//
        for (Point2D p : testset){

            System.out.println(p);
        }
//        Point2D p = new Point2D(0.144, 0.179);
//        System.out.println(tree.contains(p));
//        System.out.println(set.contains(p));

//        for(Point2D p : testset1){
//            System.out.println(p);
//        }
    }

}
