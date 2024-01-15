import java.awt.Color
import java.awt.event.{ KeyEvent, KeyListener }

object Game extends App {
  val SIDELENGTH: Int = 15
  val APPLENUMBER: Int = 5
  val SPEED: Int = 400
  var grid: Grid = new Grid(SIDELENGTH, APPLENUMBER)
  private var gameIsFinish: Boolean = false
  var movementX: Int = 0
  var movementY: Int = 0
  var movementBufferedChar: Char = ' '
  var bufferCounter: Int = 0
  var moveCounter: Int = 0
  var firstLoop: Boolean = true

  // Ecoute les touches du clavier
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
          if (e.getKeyChar == 'w' || e.getKeyChar == 'W') {
            movementX = 0
            movementY = -1
            moveCounter += 1
          }
          else if (e.getKeyChar == 'd' || e.getKeyChar == 'D') {
            movementX = 1
            movementY = 0
            moveCounter += 1
          }
          else if (e.getKeyChar == 's' || e.getKeyChar == 'S') {
            movementX = 0
            movementY = 1
            moveCounter += 1
          }
          else if (e.getKeyChar == 'a' || e.getKeyChar == 'A') {
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

  /**
   * Crée la partie
   */
  def newGame(): Unit = {
    while (true) {
      startGame()
    }
  }

  /**
   * Logique de jeu
   */
  def startGame(): Unit = {
    if(firstLoop) {
      grid.display.clear()

      while (!gameIsFinish) {
        bufferCounter = 0

        val lastTailPos: Position = grid.snake.positions(grid.snake.length - 1)
        val appleIndice = grid.snakeWillEatApple(movementX, movementY)

        if (grid.snake.willLose(movementX, movementY)) {
          gameIsFinish = true
          grid.updateHead(movementX, movementY)
          grid.display.setColor(Color.white)
          grid.display.drawFillRect(grid.display.width / 2 - 135, 0, 270, 25)
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

        if (grid.snake.length == math.pow(SIDELENGTH, 2)) {
          gameIsFinish = true
          grid.display.setColor(Color.white)
          grid.display.drawFillRect(grid.display.width / 2 - 135, 0, 270, 25)
          grid.display.setColor(Color.black)
          grid.display.drawFancyString(grid.display.width / 2 - 105, 15, "You win, press space to restart", fontSize = 15)
        }

        if (moveCounter == 1) {
          grid.display.setColor(Color.white)
          grid.display.drawFillRect(grid.display.width / 2 - 125, 0, 250, 25)
        }
      }
    }
  }
}
