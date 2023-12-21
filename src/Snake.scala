class Snake(gridSideLength: Int) {
  var positions: Array[Position] = new Array(gridSideLength)
  var length: Int = 3;
  private var headPos: Position = new Position(gridSideLength/2+1, gridSideLength/2)

  for(i: Int <- 0 until length) {
    positions(i) = new Position(headPos.x - i, headPos.y)
  }

  def move(x: Int, y: Int): Unit = {
    for (i: Int <- length-1 to 0 by -1) {
      if (i > 0) {
        print(i)
        positions(i) = positions(i - 1)
      }
      else {
        positions(i) = new Position(positions(i).x + x, positions(i).y + y)
      }
    }
  }

  def grow(lastTailPos: Position): Unit = {
    positions(length) = lastTailPos
    length += 1
  }
}
