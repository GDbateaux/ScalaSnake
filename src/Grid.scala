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
  val display: FunGraphics = new FunGraphics(GRAPHICS_WIDTH, GRAPHICS_HEIGHT, 20, 20, "Snake", true)

  var movedUp: Boolean = false;
  var movedDown: Boolean = false;
  var movedRight: Boolean = true;
  var movedLeft: Boolean = false;

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

  def updateGrid(speed: Int): Unit = {
    var position: Position = new Position(0, 0)
    var addedElement: Boolean = false

    score = snake.length - 3
    for(lenght: Int <- 0 to SQUARE_LENGTH) {
      for (y: Int <- 0 until sideLength) {
        for (x: Int <- 0 until sideLength) {
          position = new Position(x, y)
          addedElement = false
          for (i: Int <- 0 until snake.length) {
            if (snake.positions(i).x == position.x && snake.positions(i).y == position.y) {
              display.setColor(Color.green)
              if (i == 0) {
                val bimg: BufferedImage = ImageIO.read(new File("./img/snake.png"))
                val width = bimg.getWidth
                if (snake.positions(1).x == snake.positions(0).x - 1) {
                  display.drawTransformedPicture(SQUARE_LENGTH * x + SQUARE_LENGTH / 2 - (SQUARE_LENGTH - lenght), SQUARE_LENGTH * y + SQUARE_LENGTH / 2, math.Pi / 2, SQUARE_LENGTH / width, "/img/snake.png")
                }
                else if (snake.positions(1).x == snake.positions(0).x + 1) {
                  display.drawTransformedPicture(SQUARE_LENGTH * x + SQUARE_LENGTH / 2 + (SQUARE_LENGTH - lenght), SQUARE_LENGTH * y + SQUARE_LENGTH / 2, -math.Pi / 2, SQUARE_LENGTH / width, "/img/snake.png")
                }
                else if (snake.positions(1).y == snake.positions(0).y - 1) {
                  display.drawTransformedPicture(SQUARE_LENGTH * x + SQUARE_LENGTH / 2, SQUARE_LENGTH * y + SQUARE_LENGTH / 2 - (SQUARE_LENGTH - lenght), math.Pi, SQUARE_LENGTH / width, "/img/snake.png")
                }
                else {
                  display.drawTransformedPicture(SQUARE_LENGTH * x + SQUARE_LENGTH / 2, SQUARE_LENGTH * y + SQUARE_LENGTH / 2 + (SQUARE_LENGTH - lenght), 0, SQUARE_LENGTH / width, "/img/snake.png")
                }
              }
              else if(i == 1) {
                if (snake.positions(i).x == snake.positions(i-1).x - 1) {
                  if (snake.positions(i).y == snake.positions(i + 1).y - 1) {
                    display.drawFillRect(SQUARE_LENGTH * x, SQUARE_LENGTH * y + (SQUARE_LENGTH - lenght), SQUARE_LENGTH, SQUARE_LENGTH)
                  }
                  else if (snake.positions(i).y == snake.positions(i + 1).y + 1) {
                    display.drawFillRect(SQUARE_LENGTH * x, SQUARE_LENGTH * y - (SQUARE_LENGTH - lenght), SQUARE_LENGTH, SQUARE_LENGTH)
                  }
                  else {
                    display.drawFillRect(SQUARE_LENGTH * x - (SQUARE_LENGTH - lenght), SQUARE_LENGTH * y, SQUARE_LENGTH, SQUARE_LENGTH)
                  }
                }
                else if (snake.positions(i).x == snake.positions(i-1).x + 1) {
                  if (snake.positions(i).y == snake.positions(i + 1).y - 1) {
                    display.drawFillRect(SQUARE_LENGTH * x, SQUARE_LENGTH * y + (SQUARE_LENGTH - lenght), SQUARE_LENGTH, SQUARE_LENGTH)
                  }
                  else if(snake.positions(i).y == snake.positions(i + 1).y + 1) {
                    display.drawFillRect(SQUARE_LENGTH * x, SQUARE_LENGTH * y - (SQUARE_LENGTH - lenght), SQUARE_LENGTH, SQUARE_LENGTH)
                  } else {
                    display.drawFillRect(SQUARE_LENGTH * x + (SQUARE_LENGTH - lenght), SQUARE_LENGTH * y, SQUARE_LENGTH, SQUARE_LENGTH)
                  }
                }
                else if (snake.positions(i).y == snake.positions(i-1).y - 1) {
                  if (snake.positions(i).x == snake.positions(i + 1).x - 1) {
                    display.drawFillRect(SQUARE_LENGTH * x + (SQUARE_LENGTH - lenght), SQUARE_LENGTH * y, SQUARE_LENGTH, SQUARE_LENGTH)
                  }
                  else if (snake.positions(i).x == snake.positions(i + 1).x + 1) {
                    display.drawFillRect(SQUARE_LENGTH * x - (SQUARE_LENGTH - lenght), SQUARE_LENGTH * y, SQUARE_LENGTH, SQUARE_LENGTH)
                  }
                  else {
                    display.drawFillRect(SQUARE_LENGTH * x, SQUARE_LENGTH * y - (SQUARE_LENGTH - lenght), SQUARE_LENGTH, SQUARE_LENGTH)
                  }
                }
                else if (snake.positions(i).y == snake.positions(i - 1).y + 1) {
                  if (snake.positions(i).x == snake.positions(i + 1).x - 1) {
                    display.drawFillRect(SQUARE_LENGTH * x + (SQUARE_LENGTH - lenght), SQUARE_LENGTH * y, SQUARE_LENGTH, SQUARE_LENGTH)
                  }
                  else if (snake.positions(i).x == snake.positions(i + 1).x + 1) {
                    display.drawFillRect(SQUARE_LENGTH * x - (SQUARE_LENGTH - lenght), SQUARE_LENGTH * y, SQUARE_LENGTH, SQUARE_LENGTH)
                  }
                  else {
                    display.drawFillRect(SQUARE_LENGTH * x, SQUARE_LENGTH * y + (SQUARE_LENGTH - lenght), SQUARE_LENGTH, SQUARE_LENGTH)
                  }
                }
              }
              else if(i == snake.length -1) {
                display.setColor(Color.white)
                if (snake.positions(i).x == snake.positions(i - 1).x - 1) {
                  if(movedUp) {
                    display.drawFillRect(SQUARE_LENGTH * x, SQUARE_LENGTH * (y + 1) + (SQUARE_LENGTH - lenght), SQUARE_LENGTH, SQUARE_LENGTH)
                  }
                  else if(movedDown) {
                    display.drawFillRect(SQUARE_LENGTH * x, SQUARE_LENGTH * (y - 1) - (SQUARE_LENGTH - lenght), SQUARE_LENGTH, SQUARE_LENGTH)
                  }
                  else if(movedRight) {
                    display.drawFillRect(SQUARE_LENGTH * (x - 1) - (SQUARE_LENGTH - lenght), SQUARE_LENGTH * y, SQUARE_LENGTH, SQUARE_LENGTH)
                  }
                  if(lenght == SQUARE_LENGTH) {
                    movedUp = false;
                    movedDown = false;
                    movedRight = true;
                    movedLeft = false;
                  }
                }
                else if (snake.positions(i).x == snake.positions(i - 1).x + 1) {
                  if (movedUp) {
                    display.drawFillRect(SQUARE_LENGTH * x, SQUARE_LENGTH * (y + 1) + (SQUARE_LENGTH - lenght), SQUARE_LENGTH, SQUARE_LENGTH)
                  }
                  else if (movedDown) {
                    display.drawFillRect(SQUARE_LENGTH * x, SQUARE_LENGTH * (y - 1) - (SQUARE_LENGTH - lenght), SQUARE_LENGTH, SQUARE_LENGTH)
                  }
                  else if (movedLeft) {
                    display.drawFillRect(SQUARE_LENGTH * (x+1) + (SQUARE_LENGTH - lenght), SQUARE_LENGTH * y, SQUARE_LENGTH, SQUARE_LENGTH)
                  }
                  if(lenght == SQUARE_LENGTH) {
                    movedUp = false;
                    movedDown = false;
                    movedRight = false;
                    movedLeft = true;
                  }
                }
                else if (snake.positions(i).y == snake.positions(i - 1).y - 1) {
                  if (movedDown) {
                    display.drawFillRect(SQUARE_LENGTH * x, SQUARE_LENGTH * (y - 1) - (SQUARE_LENGTH - lenght), SQUARE_LENGTH, SQUARE_LENGTH)
                  }
                  else if (movedRight) {
                    display.drawFillRect(SQUARE_LENGTH * (x - 1) - (SQUARE_LENGTH - lenght), SQUARE_LENGTH * y, SQUARE_LENGTH, SQUARE_LENGTH)
                  }
                  else if (movedLeft) {
                    display.drawFillRect(SQUARE_LENGTH * (x + 1) + (SQUARE_LENGTH - lenght), SQUARE_LENGTH * y, SQUARE_LENGTH, SQUARE_LENGTH)
                  }
                  if(lenght == SQUARE_LENGTH) {
                    movedUp = false;
                    movedDown = true;
                    movedRight = false;
                    movedLeft = false;
                  }
                }
                else if (snake.positions(i).y == snake.positions(i - 1).y + 1) {
                  if (movedUp) {
                    display.drawFillRect(SQUARE_LENGTH * x, SQUARE_LENGTH * (y+1) + (SQUARE_LENGTH - lenght), SQUARE_LENGTH, SQUARE_LENGTH)
                  }
                  else if (movedRight) {
                    display.drawFillRect(SQUARE_LENGTH * (x - 1) - (SQUARE_LENGTH - lenght), SQUARE_LENGTH * y, SQUARE_LENGTH, SQUARE_LENGTH)
                  }
                  else if (movedLeft) {
                    display.drawFillRect(SQUARE_LENGTH * (x + 1) + (SQUARE_LENGTH - lenght), SQUARE_LENGTH * y, SQUARE_LENGTH, SQUARE_LENGTH)
                  }
                  if(lenght == SQUARE_LENGTH) {
                    movedUp = true;
                    movedDown = false;
                    movedRight = false;
                    movedLeft = false;
                  }
                }
              }

              addedElement = true
            }
          }



          if(lenght == SQUARE_LENGTH) {
            for (i: Int <- apples.positions.indices) {
              if (apples.positions(i).x == position.x && apples.positions(i).y == position.y) {
                display.setColor(Color.blue)
                val bimg: BufferedImage = ImageIO.read(new File("./img/OJApple.png"))
                val width = bimg.getWidth
                display.drawTransformedPicture(SQUARE_LENGTH * x + SQUARE_LENGTH / 2, SQUARE_LENGTH * y + SQUARE_LENGTH / 2, 0, SQUARE_LENGTH / width, "/img/OJApple.png")
                addedElement = true
              }
            }
            if (!addedElement) {
              display.setColor(Color.white)
              display.drawFillRect(SQUARE_LENGTH * x, SQUARE_LENGTH * y, SQUARE_LENGTH, SQUARE_LENGTH)
            }
          }
        }
      }
      Thread.sleep(speed);
    }

    display.setColor(Color.black)
    display.drawString(10, 20, s"Score : $score")
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
