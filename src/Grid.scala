import hevs.graphics.FunGraphics
import hevs.graphics.utils.GraphicsBitmap

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class Grid(val sideLength: Int, val appleNumber: Int) {
  val snake: Snake = new Snake(sideLength)
  val apples: Apples = new Apples(appleNumber, getEmptySquares(false))
  private var score: Int = 0
  private val SQUARE_LENGTH: Int = 40
  private val GRAPHICS_WIDTH: Int = SQUARE_LENGTH * sideLength
  private val GRAPHICS_HEIGHT: Int = GRAPHICS_WIDTH
  val display: FunGraphics = new FunGraphics(GRAPHICS_WIDTH, GRAPHICS_HEIGHT, 20, 20, "Hangman", true)

  override def toString: String = {
    var grid: String = ""
    var position: Position = new Position(0, 0)
    var addedElement: Boolean = false

    for(y: Int <- 0 until sideLength){
      for(x: Int <- 0 until sideLength){
        position = new Position(x, y)
        addedElement = false
        for(i: Int <- 0 until snake.length) {
          if(snake.positions(i).x == position.x && snake.positions(i).y == position.y) {
            grid += "▣"
            addedElement = true
          }
        }
        for (i: Int <- apples.positions.indices) {
          if (apples.positions(i).x == position.x && apples.positions(i).y == position.y) {
            grid += "▢"
            addedElement = true
          }
        }
        if(!addedElement) {
          grid += "▦"
        }
      }
      grid += "\n"
    }
    return grid
  }

  def updateGrid(): Unit = {
    var position: Position = new Position(0, 0)
    var addedElement: Boolean = false
    score = snake.length - 3

    for (y: Int <- 0 until sideLength) {
      for (x: Int <- 0 until sideLength) {
        position = new Position(x, y)
        addedElement = false
        for (i: Int <- 0 until snake.length) {
          if (snake.positions(i).x == position.x && snake.positions(i).y == position.y) {
            display.setColor(Color.green)
            if(i == 0){
              val bimg: BufferedImage = ImageIO.read(new File("./img/snake.png"))
              val width = bimg.getWidth
              if(snake.positions(1).x == snake.positions(0).x-1) {
                display.drawTransformedPicture(SQUARE_LENGTH * x + SQUARE_LENGTH / 2, SQUARE_LENGTH * y + SQUARE_LENGTH / 2, math.Pi/2, SQUARE_LENGTH / width, "/img/snake.png")
              }
              else if(snake.positions(1).x == snake.positions(0).x+1){
                display.drawTransformedPicture(SQUARE_LENGTH * x + SQUARE_LENGTH / 2, SQUARE_LENGTH * y + SQUARE_LENGTH / 2, -math.Pi/2, SQUARE_LENGTH / width, "/img/snake.png")
              }
              else if (snake.positions(1).y == snake.positions(0).y - 1) {
                display.drawTransformedPicture(SQUARE_LENGTH * x + SQUARE_LENGTH / 2, SQUARE_LENGTH * y + SQUARE_LENGTH / 2, math.Pi, SQUARE_LENGTH / width, "/img/snake.png")
              }
              else {
                display.drawTransformedPicture(SQUARE_LENGTH * x + SQUARE_LENGTH / 2, SQUARE_LENGTH * y + SQUARE_LENGTH / 2, 0, SQUARE_LENGTH / width, "/img/snake.png")
              }
            }
            else {
              display.drawFillRect(SQUARE_LENGTH * x, SQUARE_LENGTH * y, SQUARE_LENGTH, SQUARE_LENGTH)
            }
            addedElement = true
          }
        }
        for (i: Int <- apples.positions.indices) {
          if (apples.positions(i).x == position.x && apples.positions(i).y == position.y) {
            display.setColor(Color.blue)
            val bimg: BufferedImage = ImageIO.read(new File("./img/apple.png"))
            val width = bimg.getWidth
            display.drawTransformedPicture(SQUARE_LENGTH * x + SQUARE_LENGTH / 2, SQUARE_LENGTH * y + SQUARE_LENGTH / 2, 0, SQUARE_LENGTH / width, "/img/apple.png")
            addedElement = true
          }
        }
        if (!addedElement) {
          display.setColor(Color.white)
          display.drawFillRect(SQUARE_LENGTH*x, SQUARE_LENGTH*y, SQUARE_LENGTH, SQUARE_LENGTH)
        }
      }
    }
    display.setColor(Color.black)
    display.drawString(10, 20, s"Sccore : $score")
  }

  def getEmptySquares(isApplesGenerated: Boolean = true): Array[Position] = {
    var numberGeneratedApple: Int = 0
    if(isApplesGenerated){
      numberGeneratedApple = appleNumber
    }
    var emptySquareLength: Int = math.pow(sideLength, 2).toInt - snake.length - numberGeneratedApple
    if(math.pow(sideLength, 2).toInt - snake.length - numberGeneratedApple <= 0) {
      emptySquareLength = 0
    }
    var emptySquares: Array[Position] = new Array(emptySquareLength)
    var counter: Int = 0

    for (y: Int <- 0 until sideLength) {
      for (x: Int <- 0 until sideLength) {
        var position: Position = new Position(x, y)
        var squareIsEmpty = true

        for (i: Int <- 0 until snake.length) {
          if (snake.positions(i).x == position.x && snake.positions(i).y == position.y) {
            squareIsEmpty = false
          }
        }
        if(isApplesGenerated) {
          for (i: Int <- apples.positions.indices) {
            if (apples.positions(i).x == position.x && apples.positions(i).y == position.y) {
              squareIsEmpty = false
            }
          }
        }

        if (squareIsEmpty && emptySquares.length != 0) {
          emptySquares(counter) = position;
          counter += 1;
        }
      }
    }

    return emptySquares;
  }

  def snakeWillEatApple(x: Int, y: Int): Int = {
    for (i: Int <- apples.positions.indices) {
      if (apples.positions(i).x == snake.positions(0).x + x && apples.positions(i).y == snake.positions(0).y + y) {
        return i
      }
    }
    return -1
  }
}
