import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.IntFunction;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.util.FastMath;
import org.jcodec.api.awt.AWTSequenceEncoder;

public class Turtle {

  public static class Step {

    private final boolean move;
    private final double deltaAngle;

    public Step(double deltaAngle) {
      this(true, deltaAngle);
    }

    public Step(boolean move, double deltaAngle) {
      this.move = move;
      this.deltaAngle = deltaAngle;
    }

  }

  public static void generateImage(Dimension dimensions, Point2D init, double unit,
      Stream<Integer> sequence, IntFunction<Step> rule, File file) throws IOException {
    generateImage(dimensions, init, unit, sequence, rule, true, file);
  }

  public static void generateImage(Dimension dimensions, Point2D init, double unit,
      Stream<Integer> sequence, IntFunction<Step> rule, boolean rotateFirst, File file) throws IOException {

    BufferedImage bi = new BufferedImage(dimensions.width, dimensions.height, BufferedImage.TYPE_INT_ARGB);
    Graphics graphics = bi.createGraphics();
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, dimensions.width, dimensions.height);

    graphics.setColor(Color.BLACK);

    AtomicReference<Point2D> p = new AtomicReference<>(init);
    AtomicReference<Double> angle = new AtomicReference<>(0.0);

    sequence.map(rule::apply).forEach(state -> {

      if (rotateFirst) {
        angle.updateAndGet(v -> v + state.deltaAngle);
      }

      if (state.move) {
        Point2D q = move(p.get(), unit, angle.get());
        graphics.drawLine((int) p.get().getX(), (int) p.get().getY(),
            (int) q.getX(), (int) q.getY());
        p.set(q);
      }

      if (!rotateFirst) {
        angle.updateAndGet(v -> v + state.deltaAngle);
      }

    });

    ImageIO.write(bi, "PNG", file);
  }

  public static void generateMovie(Dimension dimensions, Point2D init, double unit,
      Stream<Integer> sequence, IntFunction<Step> rule, boolean rotateFirst, File file) throws IOException {

    AWTSequenceEncoder enc = AWTSequenceEncoder.create25Fps(file);

    BufferedImage bi = new BufferedImage(dimensions.width, dimensions.height, BufferedImage.TYPE_INT_ARGB);
    Graphics graphics = bi.createGraphics();
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, dimensions.width, dimensions.height);
    graphics.setColor(Color.BLACK);

    AtomicReference<Point2D> p = new AtomicReference<>(init);
    AtomicReference<Double> angle = new AtomicReference<>(0.0);

    sequence.map(rule::apply).forEach(state -> {

      if (rotateFirst) {
        angle.updateAndGet(v -> v + state.deltaAngle);
      }

      if (state.move) {
        Point2D q = move(p.get(), unit, angle.get());
        graphics.setColor(Color.red);
        graphics.drawLine((int) p.get().getX(), (int) p.get().getY(),
            (int) q.getX(), (int) q.getY());

        try {
          enc.encodeImage(bi);
          System.out.print(".");
        } catch (IOException e) {
          e.printStackTrace();
        }

        graphics.setColor(Color.black);
        graphics.drawLine((int) p.get().getX(), (int) p.get().getY(),
            (int) q.getX(), (int) q.getY());

        p.set(q);
      }

      if (!rotateFirst) {
        angle.updateAndGet(v -> v + state.deltaAngle);
      }
    });

    enc.finish();
  }

  private static Point2D move(Point2D p, double l, double angle) {
    return new Point2D.Double(
        p.getX() + l * FastMath.cos(angle),
        p.getY() + l * FastMath.sin(angle));
  }

}
