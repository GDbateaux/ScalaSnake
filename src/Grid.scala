class Grid(val sideLength: Int, val appleNumber: Int) {
  val snake: Snake = new Snake(new Position(sideLength/2+1, sideLength/2))
  val apples: Apples = new Apples(appleNumber, sideLength)

  def updateGrid(): Unit = {
    var grid: String = ""
    var position: Position = new Position(0, 0)
    var addedElement: Boolean = false

    for(y: Int <- 0 until sideLength){
      for(x: Int <- 0 until sideLength){
        position = new Position(x, y)
        addedElement = false
        for(i: Int <- snake.positions.indices) {
          if(snake.positions(i).x == position.x && snake.positions(i).y == position.y) {
            grid += "▣"
            addedElement = true
          }
        }
        for (i: Int <- apples.positions.indices) {
          if (apples.positions(i).x == position.x && apples.positions(i).y == position.y) {
            grid += "▢"
            addedElement = true
          }
        }
        if(!addedElement) {
          grid += "▦"
        }
      }
      grid += "\n"
    }
    println(snake.positions.mkString(","))
    print(grid)
  }
}
