class Apples(val appleNumber: Int = 1, emptySquares: Array[Position]) {
  val positions: Array[Position] = getNewPositionsApples(emptySquares, appleNumber)
  private def getNewPositionsApples(emptySquares: Array[Position], numberApples: Int): Array[Position] = {
    val pos: Array[Position] = new Array[Position](numberApples)

    for (i: Int <- 0 until numberApples) {
      var rdm: Int = (math.random() * (emptySquares.length - i)).toInt
      pos(i) = emptySquares(rdm)
      emptySquares(rdm) = emptySquares(emptySquares.length - 1 - i)
    }
    return pos
  }
  def regenerateApple(actualEmptySquares: Array[Position], appleIndice: Int): Unit = {
    if(!actualEmptySquares.isEmpty) {
      var rdm: Int = (math.random() * actualEmptySquares.length).toInt
      positions(appleIndice) = actualEmptySquares(rdm)
    }
    else{
      positions(appleIndice) = new Position(-1, -1)
    }
  }
}
