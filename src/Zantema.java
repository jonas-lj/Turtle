import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

// From "Turtle graphics of morphic sequences" by Hans Zantema
public class Zantema {

  public static void main(String[] arguments) throws IOException {

    Stream<Integer> sequence = Sequence.buildSequence(List.of(0),
        e -> e == 0 ? List.of(0, 0, 1, 1, 0, 0) : List.of(0, 0, 1, 1, 0, 1), 4, 6);

    Turtle.generateImage(new Dimension( 1200, 1200), new Point2D.Double(1200, 500), 1.2, sequence, i -> {
      if (i == 0) {
        return new Turtle.Step(7*Math.PI / 18);
      } else {
        return new Turtle.Step(-7*Math.PI / 12);
      }
    }, new File("zantema.png"));


  }

}
