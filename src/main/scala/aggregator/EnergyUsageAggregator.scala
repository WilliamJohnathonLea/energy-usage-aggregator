package aggregator

import model.{MonthlyUsage, DailyUsage}

object EnergyUsageAggregator {

  def aggregateMonthlyUsage(dailySnapshots: Seq[DailyUsage]): Seq[MonthlyUsage] = {
    dailySnapshots.groupBy(u => u.day.getMonth)
      .map {
        case (m, us) =>
          val usage = us.headOption.flatMap { f =>
            us.lastOption.map { g =>
              g.usage - f.usage
            }
          }.getOrElse(0)
          MonthlyUsage(m, usage)
      }.toSeq.sortBy(_.month)
  }

}
