import java.awt.Color
import java.awt.event.{KeyEvent, KeyListener, MouseEvent, MouseListener}
import javax.swing.SwingConstants

object Game extends App {
  val SIDELENGTH: Int = 25
  val APPLENUMBER: Int = 5
  val SPEED: Int = 15
  var grid: Grid = new Grid(SIDELENGTH, APPLENUMBER)
  private var gameIsFinish: Boolean = false
  var movementX: Int = 0
  var movementY: Int = 0
  var movementBufferedChar: Char = ' '
  var bufferCounter: Int = 0
  var moveCounter: Int = 0
  var firstLoop: Boolean = true

  val keyListener: KeyListener = new KeyListener {
    override def keyTyped(e: KeyEvent): Unit = {}

    override def keyPressed(e: KeyEvent): Unit = {
      if(e.getKeyChar == ' '){
        if(gameIsFinish){
          grid.restartGame()
          gameIsFinish = false
          firstLoop = true
          movementX = 0
          movementY = 0
          movementBufferedChar = ' '
          bufferCounter = 0
          moveCounter = 0
        }
      }

      if (movementBufferedChar == ' ') {
        if (bufferCounter == 0) {
          if (e.getKeyChar == 'w') {
            movementX = 0
            movementY = -1
            moveCounter += 1
          }
          else if (e.getKeyChar == 'd') {
            movementX = 1
            movementY = 0
            moveCounter += 1
          }
          else if (e.getKeyChar == 's') {
            movementX = 0
            movementY = 1
            moveCounter += 1
          }
          else if (e.getKeyChar == 'a') {
            movementX = -1
            movementY = 0
            moveCounter += 1
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
  newGame();

  def newGame(): Unit = {
    while (true) {
      startGame()
    }
  }

  def startGame(): Unit = {
    if(firstLoop) {
      grid.display.clear()

      while (!gameIsFinish) {
        bufferCounter = 0

        var lastTailPos: Position = grid.snake.positions(grid.snake.length - 1)
        var appleIndice = grid.snakeWillEatApple(movementX, movementY)

        if (grid.snake.willLose(movementX, movementY)) {
          gameIsFinish = true
          grid.display.setColor(Color.white)
          grid.display.drawFillRect(grid.display.width / 2 - 125, 0, 250, 25)
          grid.display.setColor(Color.black)
          grid.display.drawFancyString(grid.display.width / 2 - 105, 15, "You lose, press space to restart", fontSize = 15)
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

        if (grid.snake.length == math.pow(SIDELENGTH, 2)) {
          gameIsFinish = true
          grid.display.setColor(Color.white)
          grid.display.drawFillRect(grid.display.width / 2 - 125, 0, 250, 25)
          grid.display.setColor(Color.black)
          grid.display.drawFancyString(grid.display.width / 2 - 105, 15, "You win, press space to restart", fontSize = 15)
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

          movementBufferedChar = ' '
        }

        if (moveCounter != 0 && !gameIsFinish) {
          grid.updateGrid(SPEED)
        }
        else if (firstLoop) {
          grid.updateGrid(SPEED)
          firstLoop = false
          grid.display.setColor(Color.white)
          grid.display.drawFillRect(grid.display.width / 2 - 125, 0, 250, 25)
          grid.display.setColor(Color.black)
          grid.display.drawFancyString(grid.display.width / 2 - 105, 15, "Press any move key to start", fontSize = 15)
        }

        if (moveCounter == 1) {
          grid.display.setColor(Color.white)
          grid.display.drawFillRect(grid.display.width / 2 - 125, 0, 250, 25)
        }
      }
    }
  }
}
