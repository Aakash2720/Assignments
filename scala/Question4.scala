import org.apache.poi.ss.usermodel.{WorkbookFactory, DataFormatter}
import java.io.FileInputStream
import scala.collection.mutable;


case class FruitePurchase(fruite: String, price: Double)

object Question4 extends App {
  type fruiteMap = mutable.HashMap[String, Double]
  val fruitePurchases = mutable.HashMap[String, List[List[fruiteMap]]]()


  val file1 = "src/main/scala/harvest.csv.xlsx" // Replace with your Excel file path
  val file2 ="src/main/scala/prices.csv.xlsx"

  val fileInputStream1 = new FileInputStream(file1)
  val fileInputStream2 = new FileInputStream(file2)

  val workbook1 = WorkbookFactory.create(fileInputStream1)
  val workbook2 = WorkbookFactory.create(fileInputStream2)

  val sheet1 = workbook1.getSheetAt(0)
  val sheet2= workbook2.getSheetAt(0)

  val dataFormatter = new DataFormatter()

  val rowIterator1 = sheet1.rowIterator()
  var count=0;
  while (rowIterator1.hasNext) {
    val row = rowIterator1.next()
    if(count==0){
      count =count+ 1
//      continue
    }
    else{

    // Iterate over cells in the row
    val cellIterator = row.cellIterator()

    if (cellIterator.hasNext) {
      // Fetch cell values in sequence
      val nameCell = dataFormatter.formatCellValue(cellIterator.next())

      if (cellIterator.hasNext) {
        val dateCell = dataFormatter.formatCellValue(cellIterator.next())

         val str = String(dateCell).trim.split("-")
         val month = str(1).toInt

        if (cellIterator.hasNext) {
          val fruitCell = dataFormatter.formatCellValue(cellIterator.next())

          if (cellIterator.hasNext) {
            val priceCell = dataFormatter.formatCellValue(cellIterator.next())
            try {
              val price = priceCell.toDouble //
              addFruitePurchase(nameCell, month, List(FruitePurchase(fruitCell, price)))
            } catch {
              case e: NumberFormatException =>
                println(e.getMessage)
            }
          }
        }
      }
    }}

  }

  // Close the workbook and file input stream when done
  workbook1.close()
  workbook2.close()
  fileInputStream2.close()
  fileInputStream1.close()




  def addFruitePurchase(name: String, monthIndex: Int, purchases: List[FruitePurchase]): Unit = {
    val monthList = fruitePurchases.getOrElseUpdate(name, List.fill(11) ( List.empty[fruiteMap]))
    val monthPurchases =
      if (monthIndex < monthList.length) monthList(monthIndex)
      else List.empty[fruiteMap]
    val fruiteMap = mutable.HashMap.empty[String, Double]
    purchases.foreach { purchase =>
      val currentPrice = fruiteMap.getOrElse(purchase.fruite, 0.0)
      fruiteMap.update(purchase.fruite, currentPrice + purchase.price)
    }
    val updatedMonthPurchases = monthPurchases :+ fruiteMap
    val updatedMonthList =
      if (monthIndex < monthList.length) {
        monthList.updated(monthIndex, updatedMonthPurchases)
      } else {

        monthList ++ List.fill(monthIndex - monthList.length + 1)(List.empty[fruiteMap]) :+ updatedMonthPurchases
      }
    fruitePurchases.update(name, updatedMonthList)
  }

  val rowIterator2 = sheet2.rowIterator()
  var c = 0;
  while (rowIterator1.hasNext) {
    val row = rowIterator1.next()
    if (c == 0) {
      c = c + 1
      //      continue
    }
    else {
      val cellIterator = row.cellIterator()
      if(cellIterator.hasNext){
        val fruitCell = dataFormatter.formatCellValue(cellIterator.next())
        if(cellIterator.hasNext){
          val dateCell = dataFormatter.formatCellValue(cellIterator.next())

          val str = String(dateCell).trim.split("-")
          val month = str(1).toInt
          if(cellIterator.hasNext){
            val priceCell = dataFormatter.formatCellValue(cellIterator.next())
            val price = priceCell.toDouble
            try{
              updateFruitePurchaseMap(fruitCell,month,price);
            }

          }
        }
      }

    }}
    def updateFruitePurchaseMap(fruit:String,date:Int,price:Double):Unit= {

    }
      fruitePurchases.foreach { case (name, months) =>
    println(s"$name's Purchases:")
    months.zipWithIndex.foreach { case (monthList, index) =>
      println(s"Month ${index }:")
      val totalFruiteMap = mutable.HashMap[String, Double]()

      monthList.foreach { fruiteMap =>
        fruiteMap.foreach { case (fruite, price) =>
          if (totalFruiteMap.contains(fruite)) {
            totalFruiteMap.update(fruite, totalFruiteMap(fruite) + price)
          } else {
            totalFruiteMap.put(fruite, price)
          }
        }
      }

      totalFruiteMap.foreach { case (fruite, totalPrice) =>
        println(s"  $fruite: $totalPrice")
      }
    }
    println()


    }
}
