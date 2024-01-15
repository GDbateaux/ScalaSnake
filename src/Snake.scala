class Snake(gridSideLength: Int) {
  var positions: Array[Position] = new Array(math.pow(gridSideLength, 2).toInt)
  var length: Int = 3
  private var headPos: Position = new Position(gridSideLength/2+1, gridSideLength/2)

  for(i: Int <- 0 until length) {
    positions(i) = new Position(headPos.x - i, headPos.y)
  }

  /**
   * Vérifie si le serpent contient une position sur la grille
   * @param pos Position sur la grille
   * @return true si le serpent contient la position, sinon false
   */
  def contains(pos: Position): Boolean = {
    var contains: Boolean = false

    for (i: Int <- 0 until length){
      if(positions(i).x == pos.x && positions(i).y == pos.y){
        contains = true
      }
    }
    return contains
  }

  /**
   * Test si après un mouvement le serpent va se cogner contre un mur, ou contre lui-même
   * @param movementX Mouvement dans la direction X
   * @param movementY Mouvement dans la direction Y
   * @return True si le serpent va perdre sinnon false
   */
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

  /**
   * Fais avancer le serpent dans une direction
   * @param x La direction en X
   * @param y La direction en Y
   */
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

  /**
   * Fait grandir le serpent
   * @param lastTailPos Position de la queue avant le mouvement
   */
  def grow(lastTailPos: Position): Unit = {
    positions(length) = lastTailPos
    length += 1
  }
}
