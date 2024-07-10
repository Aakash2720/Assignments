import scala.io.Source
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import scala.util.Using
case class HarvestRecord(date: LocalDate, gatherer: String, fruit: String, amount: Double)
case class PriceRecord(date: LocalDate, fruit: String, price: Double)
object Que4 extends App{
  val harvestData = loadHarvestData("src/main/scala/harvest.csv")
  val priceData = loadPriceData("src/main/scala/prices.csv")

  val gathererMonthlyAmounts = harvestData.groupBy(r => (r.gatherer, r.date.getYear, r.date.getMonth))
    .view.mapValues(value => value.map (a=>a.amount).sum)
// gathererMonthlyAmounts.foreach(println)
  val bestGatherer = gathererMonthlyAmounts.maxBy(pair => pair._2)
  println(s"Best gatherer: ${bestGatherer._1._1} with ${bestGatherer._2} units gathered")

  val gathererFruitAmounts = harvestData.groupBy(r => (r.gatherer, r.fruit))
    .view.mapValues(value=>value.map(a=>a.amount).sum)
//    gathererFruitAmounts.foreach(println)


  val bestGatherersForFruit = gathererFruitAmounts.groupBy(pair=>pair._1._2)
    .view.mapValues(person=>person.maxBy(_._2))

  bestGatherersForFruit.foreach {
    case (fruit, ((gatherer, _), amount)) => println(s"Best gatherer for $fruit: $gatherer with $amount units gathered")
  }

  val fruitMonthlyEarnings = harvestData.flatMap { harvest =>
    priceData.find(p => p.date.getMonth  == harvest.date.getMonth && p.date.getDayOfMonth-1==harvest.date.getDayOfMonth && p.fruit == harvest.fruit)
      .map(price => ((harvest.date.getYear, harvest.date.getMonth, harvest.fruit), harvest.amount * price.price))
  }.groupBy(_._1).view.mapValues(_.map(_._2).sum)
  val fruitOverallEarnings = fruitMonthlyEarnings.groupBy(_._1._3)
    .view.mapValues(_.map(_._2).sum)
//  fruitMonthlyEarnings.foreach(println)

  val bestEarningFruit = fruitOverallEarnings.maxBy(_._2)
  val leastProfitableFruit = fruitOverallEarnings.minBy(_._2)
  println(s"Best earning fruit overall: ${bestEarningFruit._1} with ${bestEarningFruit._2} income")
  println(s"Least profitable fruit overall: ${leastProfitableFruit._1} with ${leastProfitableFruit._2} income")

  // Find best and least profitable fruits by month
  val bestEarningFruitByMonth = fruitMonthlyEarnings.maxBy(_._2)
  val leastProfitableFruitByMonth = fruitMonthlyEarnings.minBy(_._2)
  println(s"Best earning fruit by month: ${bestEarningFruitByMonth._1._3} in ${bestEarningFruitByMonth._1._2} ${bestEarningFruitByMonth._1._1} with ${bestEarningFruitByMonth._2} income")
  println(s"Least profitable fruit by month: ${leastProfitableFruitByMonth._1._3} in ${leastProfitableFruitByMonth._1._2} ${leastProfitableFruitByMonth._1._1} with ${leastProfitableFruitByMonth._2} income")

  val gathererEarnings = harvestData.flatMap { harvest =>
    priceData.find(p => p.date.getMonth == harvest.date.getMonth && harvest.date.getDayOfMonth ==p.date.getDayOfMonth-1  && p.fruit == harvest.fruit)
      .map(price => ((harvest.date.getYear, harvest.date.getMonth, harvest.gatherer), harvest.amount * price.price))
  }.groupBy(_._1).view.mapValues(_.map(_._2).sum)


  val gathererOverallEarnings = gathererEarnings.groupBy(_._1._3)
    .view.mapValues(_.map(_._2).sum)
//  gathererOverallEarnings.foreach(println)

  val bestEarningGatherer = gathererOverallEarnings.maxBy(_._2)
  println(s"Gatherer contributing most to income overall: ${bestEarningGatherer._1} with ${bestEarningGatherer._2} income")

  val bestEarningGathererByMonth = gathererEarnings.maxBy(_._2)
  println(s"Gatherer contributing most to income by month: ${bestEarningGathererByMonth._1._3} in ${bestEarningGathererByMonth._1._2} ${bestEarningGathererByMonth._1._1} with ${bestEarningGathererByMonth._2} income")

  def parseDate(dateStr: String): LocalDate = {
    LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
  }
  def loadHarvestData(filePath: String): Seq[HarvestRecord] = {
    Using.resource(Source.fromFile(filePath)) { source =>
      source.getLines().drop(1).map { line =>
        val cols = line.split(",").map(_.trim)
        HarvestRecord(parseDate(cols(1)), cols(0), cols(2), cols(3).toDouble)
      }.toSeq
    }
  }
  def loadPriceData(filePath: String): Seq[PriceRecord] = {
    Using.resource(Source.fromFile(filePath)) { source =>
      source.getLines().drop(1).map { line =>
        val cols = line.split(",").map(_.trim)
        PriceRecord(parseDate(cols(1)), cols(0), cols(2).toDouble)
      }.toSeq
    }
  }

}
