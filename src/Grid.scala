class Grid(val sideLength: Int, val appleNumber: Int) {
  val snake: Snake = new Snake(sideLength)
  val apples: Apples = new Apples(appleNumber, sideLength)

  override def toString(): String = {
    var grid: String = ""
    var position: Position = new Position(0, 0)
    var addedElement: Boolean = false

    for(y: Int <- 0 until sideLength){
      for(x: Int <- 0 until sideLength){
        position = new Position(x, y)
        addedElement = false
        for(i: Int <- 0 until snake.length) {
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
    return grid
  }


  def getEmptySquares(isApplesGenerated: Boolean): Array[Position] = {
    var numberGeneratedApple = 0
    if(isApplesGenerated){
      numberGeneratedApple = appleNumber
    }
    var emptySquares: Array[Position] = new Array(sideLength^2 - snake.length - numberGeneratedApple)
    var counter: Int = 0

    for (y: Int <- 0 until sideLength) {
      for (x: Int <- 0 until sideLength) {
        var position: Position = new Position(x, y)
        var squareIsEmpty = true

        for (i: Int <- 0 until snake.length) {
          if (snake.positions(i).x == position.x && snake.positions(i).y == position.y) {
            squareIsEmpty = false
          }
        }
        if(isApplesGenerated) {
          for (i: Int <- apples.positions.indices) {
            if (apples.positions(i).x == position.x && apples.positions(i).y == position.y) {
              squareIsEmpty = false
            }
          }
        }

        if (squareIsEmpty) {
          emptySquares(counter) = position;
          counter += 1;
        }
      }
    }

    return emptySquares;
  }

  def snakeWillEatApple(x: Int, y: Int): Boolean = {
    for (i: Int <- apples.positions.indices) {
      if (apples.positions(i).x == snake.positions(snake.positions.length - 1).x + x && apples.positions(i).y == snake.positions(snake.positions.length - 1).y + y) {
        return true
      }
    }
    return false
  }
}