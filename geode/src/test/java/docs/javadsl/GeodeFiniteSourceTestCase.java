/*
 * Copyright (C) 2016-2018 Lightbend Inc. <http://www.lightbend.com>
 */

package docs.javadsl;

import akka.Done;
import akka.stream.alpakka.geode.javadsl.ReactiveGeode;
import org.junit.Test;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class GeodeFiniteSourceTestCase extends GeodeBaseTestCase {

  @Test
  public void finiteSourceTest() throws ExecutionException, InterruptedException {

    ReactiveGeode reactiveGeode = createReactiveGeode();

    // #query
    CompletionStage<Done> personsDone =
        reactiveGeode
            .query("select * from /persons", new PersonPdxSerializer())
            .runForeach(
                p -> {
                  LOGGER.debug(p.toString());
                },
                materializer);
    // #query

    personsDone.toCompletableFuture().get();

    CompletionStage<Done> animalsDone =
        reactiveGeode
            .query("select * from /animals", new AnimalPdxSerializer())
            .runForeach(
                p -> {
                  LOGGER.debug(p.toString());
                },
                materializer);

    animalsDone.toCompletableFuture().get();
    reactiveGeode.close();
  }
}
