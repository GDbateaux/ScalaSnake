import java.util.Scanner

object SnakeTester extends App {
  var grid: Grid = new Grid(5, 5)
  var movementX: Int = 0
  var movementY: Int = 0
  val scanner = new Scanner(System.in)

  while(true){
    var key: String = scanner.nextLine()
    var lastTailPos: Position = grid.snake.positions(grid.snake.length-1)

    if(key == "w"){
      movementX = 0
      movementY = -1
    }
    else if(key == "d"){
      movementX = 1
      movementY = 0
    }
    else if(key == "s"){
      movementX = 0
      movementY = 1
    }
    else if(key == "a"){
      movementX = -1
      movementY = 0
    }

    var appleIndice = grid.snakeWillEatApple(movementX, movementY)
    if(appleIndice != -1){
      grid.apples.regenerateApple(grid.getEmptySquares(), appleIndice)
      grid.snake.move(movementX, movementY)
      grid.snake.grow(lastTailPos)
    }
    else{
      grid.snake.move(movementX, movementY)
    }
    println(grid.toString())
  }
  scanner.close()
}
