import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class NørgaardII {

  public static void main(String[] arguments) throws IOException {

    Stream<Integer> sequence = Sequence.buildSequence(
        List.of(0), a -> List.of(-a, a + 1), 8, 16).map(i -> Math.floorMod(i, 4));

    int[] n = new int[]{1, 2, 1, 0};
    int[] d = new int[]{1, 5, 1, 1};
    boolean[] move = new boolean[]{false, true, false, true};

    Turtle.generateImage(new Dimension(5050, 6000),
        new Point2D.Double(5050, 800), 0.5, sequence,
        i -> new Turtle.Step(move[i], n[i] * Math.PI / d[i]),
        false, new File("nørgaardII.png"));

  }

}
