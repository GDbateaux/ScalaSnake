import java.awt.event.{KeyEvent, KeyListener}
import java.util.Scanner

object SnakeTester extends App {
  val SIDELENGTH: Int = 10
  val APPLENUMBER: Int = 5
  val SPEED: Int = 150
  var grid: Grid = new Grid(SIDELENGTH, APPLENUMBER)
  private var gameIsFinish: Boolean = false
  var movementX: Int = 0
  var movementY: Int = 0
  var movementBufferedChar: Char = ' '
  var bufferCounter: Int = 0

  val keyListener: KeyListener = new KeyListener {
    override def keyTyped(e: KeyEvent): Unit = {}

    override def keyPressed(e: KeyEvent): Unit = {
      if(movementBufferedChar == ' ') {
        if (bufferCounter == 0) {
          if (e.getKeyChar == 'w') {
            movementX = 0
            movementY = -1
          }
          else if (e.getKeyChar == 'd') {
            movementX = 1
            movementY = 0
          }
          else if (e.getKeyChar == 's') {
            movementX = 0
            movementY = 1
          }
          else if (e.getKeyChar == 'a') {
            movementX = -1
            movementY = 0
          }

          if (grid.snake.positions(0).x + movementX == grid.snake.positions(1).x && grid.snake.positions(0).y + movementY == grid.snake.positions(1).y) {
            movementX = 0 - movementX
            movementY = 0 - movementY
          }
        }
        else {
          movementBufferedChar = e.getKeyChar
        }

        bufferCounter += 1
      }
    }

    override def keyReleased(e: KeyEvent): Unit = {}
  }
  grid.display.setKeyManager(keyListener)

  while(!gameIsFinish){
    bufferCounter = 0

    var lastTailPos: Position = grid.snake.positions(grid.snake.length-1)
    var appleIndice = grid.snakeWillEatApple(movementX, movementY)

    if (grid.snake.willLose(movementX, movementY)) {
      gameIsFinish = true
      println("Lost !!")
    }
    else {
      if (appleIndice != -1) {
        grid.apples.regenerateApple(grid.getEmptySquares(), appleIndice)
        grid.snake.move(movementX, movementY)
        grid.snake.grow(lastTailPos)
      }
      else {
        grid.snake.move(movementX, movementY)
      }
    }

    if(grid.snake.length == math.pow(SIDELENGTH, 2)){
      gameIsFinish = true
      println("Win !!!")
    }

    if (movementBufferedChar != ' ') {
      if (movementBufferedChar == 'w') {
        movementX = 0
        movementY = -1
      }
      else if (movementBufferedChar == 'd') {
        movementX = 1
        movementY = 0
      }
      else if (movementBufferedChar == 's') {
        movementX = 0
        movementY = 1
      }
      else if (movementBufferedChar == 'a') {
        movementX = -1
        movementY = 0
      }

      if (grid.snake.positions(0).x + movementX == grid.snake.positions(1).x && grid.snake.positions(0).y + movementY == grid.snake.positions(1).y) {
        movementX = 0 - movementX
        movementY = 0 - movementY
      }

      movementBufferedChar = ' ';
    }

    grid.updateGrid()
    Thread.sleep(SPEED)
  }
}
