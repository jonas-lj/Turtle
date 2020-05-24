import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class NørgaardI {

  public static void main(String[] arguments) throws IOException {

      Stream<Integer> sequence = Sequence.buildSequence(
          List.of(0), a -> List.of(-a, a + 1), 8, 16).map(i -> Math.floorMod(i, 4));
    int[] n = new int[] {1, 3, 1, 0};
    int[] d = new int[] {1, 7, 1, 1};
    boolean[] move = new boolean[] {false, true, false, true};

      Turtle.generateImage(new Dimension(5000, 5000), new Point2D.Double(1000, 2000),
          1.2, sequence, i ->
          new Turtle.Step(move[i], n[i] * Math.PI / d[i]), false, new File("nørgaardI.png"));

  }

}
