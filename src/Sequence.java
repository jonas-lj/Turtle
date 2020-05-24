import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Function;
import java.util.stream.Stream;

public class Sequence {

  private static <E> void replace(LinkedList<E> e, Function<E, List<E>> morphism) {

    ListIterator<E> it = e.listIterator();

    while (it.hasNext()) {
      E next = it.next();
      List<E> newValues = morphism.apply(next);
      it.remove();
      for (E value : newValues) {
        it.add(value);
      }
    }
  }

  private static <E> Stream<E> replace(Stream<E> e, Function<E, List<E>> morphism, int k) {

    for (int i = 0; i < k; i++) {
      e = e.flatMap(morphism.andThen(List::stream));
    }

    return e;
  }

  /**
   * Generate a stream consisting of the result of the first <i>k+n</i> iterations of the sequence
   * defined by the initial elements and the given morphisms. The first iteration will have all
   * elements in the init list replaced by what the morphism yields given them as inputs.
   * @param init The initial values.
   * @param morphism The morphism to apply to each individual element repeatedly.
   * @param k The number of iterations to hold in memory.
   * @param n The number of iterations to compute lazily.
   * @param <E>
   * @return
   */
  public static <E> Stream<E> buildSequence(List<E> init, Function<E, List<E>> morphism, int k, int n) {
    LinkedList<E> buffer = new LinkedList<>(init);

    for (int i = 0; i < k; i++) {
      replace(buffer, morphism);
    }

    return replace(buffer.stream(), morphism, n);
  }

}
