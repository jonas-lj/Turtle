import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class RosetteMovie {

  public static void main(String[] arguments) throws IOException {

    Stream<Integer> sequence = Sequence.buildSequence(
        List.of(1), e -> e == 0 ? List.of(0, 1, 1) : List.of(0), 6, 8);

    Turtle.generateMovie(new Dimension(800, 800),
        new Point2D.Double(420, 400), 14, sequence, i -> {
          if (i == 0) {
            return new Turtle.Step(true, 7 * Math.PI / 9);
          } else {
            return new Turtle.Step(true, -2 * Math.PI / 9);
          }
        }, true, new File("rosette.mp4"));

  }

}
