class Apples(val appleNumber: Int = 1, lengthGrid: Int) {
  val positions: Array[Position] = new Array[Position](appleNumber)

  for(i: Int <- 0 until appleNumber){
    positions(i) = new Position((math.random()*lengthGrid).toInt, (math.random()*lengthGrid).toInt)
  }
}
