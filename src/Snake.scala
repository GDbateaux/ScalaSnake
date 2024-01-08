class Snake(gridSideLength: Int) {
  var positions: Array[Position] = new Array(math.pow(gridSideLength, 2).toInt)
  var length: Int = 3
  private var headPos: Position = new Position(gridSideLength/2+1, gridSideLength/2)

  for(i: Int <- 0 until length) {
    positions(i) = new Position(headPos.x - i, headPos.y)
  }

  def willLose(movementX: Int, movementY: Int): Boolean = {
    val nextPosX: Int = positions(0).x + movementX
    val nextPosY: Int = positions(0).y + movementY

    var isLost: Boolean = false

    if(nextPosX < 0 || nextPosY < 0 || nextPosX > gridSideLength-1 || nextPosY > gridSideLength-1){
      isLost = true
    }

    for (i <- positions.indices){
      if(i != 0 && i < length){
        if(positions(i).x == nextPosX && positions(i).y == nextPosY){
          isLost = true
        }
      }
    }
    return isLost
  }

  def move(x: Int, y: Int): Unit = {
    if(x != 0 || y != 0) {
      for (i: Int <- length - 1 to 0 by -1) {
        if (i > 0) {
          positions(i) = positions(i - 1)
        }
        else {
          positions(i) = new Position(positions(i).x + x, positions(i).y + y)
        }
      }
    }
  }

  def grow(lastTailPos: Position): Unit = {
    positions(length) = lastTailPos
    length += 1
  }
}
