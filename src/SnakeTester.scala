import java.awt.event.{KeyEvent, KeyListener}
import java.util.Scanner

object SnakeTester extends App {
  val SIDELENGTH: Int = 10
  val APPLENUMBER: Int = 5
  var grid: Grid = new Grid(SIDELENGTH, APPLENUMBER)
  var gameIsFinish: Boolean = false
  var movementX: Int = 0
  var movementY: Int = 0
  val keyListener: KeyListener = new KeyListener {
    override def keyTyped(e: KeyEvent): Unit = {}

    override def keyPressed(e: KeyEvent): Unit = {
      if (e.getKeyChar == 'w') {
        if(movementY != 1) {
          movementX = 0
          movementY = -1
        }
      }
      else if (e.getKeyChar == 'd') {
        if(movementX != -1) {
          movementX = 1
          movementY = 0
        }
      }
      else if (e.getKeyChar == 's') {
        if(movementY != -1) {
          movementX = 0
          movementY = 1
        }
      }
      else if (e.getKeyChar == 'a') {
        if(movementX != 1) {
          movementX = -1
          movementY = 0
        }
      }

      if(grid.snake.positions(0).x+movementX == grid.snake.positions(1).x && grid.snake.positions(0).y+movementY == grid.snake.positions(1).y){
        movementX = 0 - movementX
        movementY = 0 - movementY
      }
    }

    override def keyReleased(e: KeyEvent): Unit = {}
  }
  grid.display.setKeyManager(keyListener)

  while(!gameIsFinish){
    var lastTailPos: Position = grid.snake.positions(grid.snake.length-1)
    var appleIndice = grid.snakeWillEatApple(movementX, movementY)

    if(appleIndice != -1){
      grid.apples.regenerateApple(grid.getEmptySquares(), appleIndice)
      grid.snake.move(movementX, movementY)
      grid.snake.grow(lastTailPos)
    }
    else{
      grid.snake.move(movementX, movementY)
    }

    if(grid.snake.isLost){
      gameIsFinish = true
      println("Lost !!")
    }

    if(grid.snake.length == math.pow(SIDELENGTH, 2)){
      gameIsFinish = true
      println("Win !!!")
    }
    grid.updateGrid()
    Thread.sleep(200)
  }
}
