package org.finos.morphir.knowledge.logic.core

import zio.Chunk
import zio.test._
import zio.test.Assertion._

object FluxSpec extends DefaultRunnableSpec {
  override def spec = suite("FluxSpec")(
    testM("An empty flux should produce no values") {
      val sut = Flux.empty[Int]
      for {
        res <- sut.runCollect
      } yield assert(res)(equalTo(Chunk.empty))
    },
    testM("Results are interleaved") {
      val fives = Flux.repeat(5)
      val sixes = Flux.repeat(6)
      val sut   = fives <> sixes
      for {
        res <- sut.runCollectN(10)
      } yield assert(res)(equalTo(Chunk(5, 6, 5, 6, 5, 6, 5, 6, 5, 6)))
    }
  )
}
