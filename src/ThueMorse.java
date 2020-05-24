import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class ThueMorse{

  public static void main(String[] arguments) throws IOException {

    Stream<Integer> sequence = Sequence.buildSequence(
        List.of(0), e -> e == 0 ? List.of(0, 1) : List.of(1, 0), 8, 4);

    Turtle.generateImage(new Dimension( 500, 1000), new Point2D.Double(400, 1000), 3, sequence, i -> {
      if (i == 0) {
        return new Turtle.Step(false, Math.PI);
      } else {
        return new Turtle.Step(true, Math.PI / 3);
      }
    }, new File("koch.png"));


  }
}
