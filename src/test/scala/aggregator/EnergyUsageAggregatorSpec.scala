package aggregator

import java.time.{LocalDate, Month}

import model.{MonthlyUsage, DailyUsage}
import org.scalatest.{Matchers, WordSpec}

class EnergyUsageAggregatorSpec extends WordSpec with Matchers {

  "The aggregator" when {

    "given a series of daily snapshots for one month" should {

      "collect them into one monthly usage" in {
        val input = Seq(
          DailyUsage(LocalDate.of(2017, 1, 1), 100),
          DailyUsage(LocalDate.of(2017, 1, 2), 200),
          DailyUsage(LocalDate.of(2017, 1, 3), 400)
        )

        val expected = Seq(
          MonthlyUsage(Month.JANUARY, 300)
        )

        val result = EnergyUsageAggregator.aggregateMonthlyUsage(input)

        result shouldBe expected
      }
    }

    "given a series of daily snapshots for two months" should {

      "collect them into two monthly usages" in {
        val input = Seq(
          DailyUsage(LocalDate.of(2017, 1, 1), 100),
          DailyUsage(LocalDate.of(2017, 1, 2), 200),
          DailyUsage(LocalDate.of(2017, 1, 3), 400),
          DailyUsage(LocalDate.of(2017, 2, 1), 500),
          DailyUsage(LocalDate.of(2017, 2, 2), 600),
          DailyUsage(LocalDate.of(2017, 2, 3), 800)
        )

        val expected = Seq(
          MonthlyUsage(Month.JANUARY, 300),
          MonthlyUsage(Month.FEBRUARY, 300)
        )

        val result = EnergyUsageAggregator.aggregateMonthlyUsage(input)

        result shouldBe expected
      }
    }

  }

}
