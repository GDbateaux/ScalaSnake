class Snake(HeadPosition: Position) {
  var positions: Array[Position] = Array(new Position(HeadPosition.x-2, HeadPosition.y), new Position(HeadPosition.x-1, HeadPosition.y), HeadPosition)

  def move(x: Int, y: Int): Unit = {
    for (i: Int <- positions.indices) {
      if (i < positions.length - 1) {
        positions(i) = positions(i + 1)
      }
      else {
        positions(i) = new Position(positions(i-1).x + x, positions(i-1).y + y)
      }
    }
  }
}
