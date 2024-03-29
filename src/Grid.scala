import hevs.graphics.FunGraphics

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

/**
 * Représente une grille pour le jeu du Snake, comprenant le serpent, les pommes et l'affichage graphique.
 *
 * @param sideLength La longueur du côté de la grille carrée.
 * @param appleNumber Le nombre de pommes initialement placées sur la grille.
 */
class Grid(val sideLength: Int, val appleNumber: Int) {
  var snake: Snake = new Snake(sideLength)
  var apples: Apples = new Apples(appleNumber, getEmptySquares(false))
  private var score: Int = 0
  private val SQUARE_LENGTH: Int = 40
  private val GRAPHICS_WIDTH: Int = SQUARE_LENGTH * sideLength
  private val GRAPHICS_HEIGHT: Int = GRAPHICS_WIDTH
  val display: FunGraphics = new FunGraphics(GRAPHICS_WIDTH, GRAPHICS_HEIGHT, 20, 20, "Snake", true)

  /**
   * Retourne un string qui simule la grille, montrant les positions du serpent, des pommes et des cases vides.
   *
   * @return la grille sous forme de string
   */
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

  /**
   * Met à jour la grille du jeu Snake, y compris le serpent et les pommes.
   * Une animation est réalisée sur le serpent à chaque mise à jour pour qu'il se déplace de pixel en pixel.
   * @param FPS Le nombre d'images par seconde.
   */
  def updateGrid(FPS: Int): Unit = {
    val bimgApple: BufferedImage = ImageIO.read(new File("./img/OJApple.png"))
    val widthApple = bimgApple.getWidth
    val bimgSnake: BufferedImage = ImageIO.read(new File("./img/snake.png"))
    val widthSnake = bimgSnake.getWidth

    score = snake.length - 3

    for (length: Int <- 0 to SQUARE_LENGTH) {
      for (i: Int <- 0 until snake.length) {
        if (i == 0) {
          var headCenterX: Int = SQUARE_LENGTH * snake.positions(i).x + SQUARE_LENGTH / 2
          var headCenterY: Int = SQUARE_LENGTH * snake.positions(i).y + SQUARE_LENGTH / 2
          val scale: Double = SQUARE_LENGTH / widthSnake.toDouble
          val imgName: String = "/img/snake.png"
          var rotation: Double = 0
          if (snake.positions(1).x == snake.positions(0).x - 1) {
            headCenterX -= (SQUARE_LENGTH - length)
            rotation = math.Pi / 2
          }
          else if (snake.positions(1).x == snake.positions(0).x + 1) {
            headCenterX += (SQUARE_LENGTH - length)
            rotation = -math.Pi / 2
          }
          else if (snake.positions(1).y == snake.positions(0).y - 1) {
            headCenterY -= (SQUARE_LENGTH - length)
            rotation = math.Pi
          }
          else{
            headCenterY += (SQUARE_LENGTH - length)
          }
          display.drawTransformedPicture(headCenterX, headCenterY, rotation, scale, imgName)
        }
        else if(i == 1){
          display.setColor(Color.green)
          if(snake.positions(i).x + 1 == snake.positions(0).x){
            display.drawFillRect(SQUARE_LENGTH * snake.positions(i).x, SQUARE_LENGTH * snake.positions(i).y, length, SQUARE_LENGTH)
          }
          else if(snake.positions(i).x - 1 == snake.positions(0).x){
            display.drawFillRect(SQUARE_LENGTH * snake.positions(i).x + SQUARE_LENGTH -length, SQUARE_LENGTH * snake.positions(i).y, length, SQUARE_LENGTH)
          }
          else if (snake.positions(i).y + 1 == snake.positions(0).y) {
            display.drawFillRect(SQUARE_LENGTH * snake.positions(i).x, SQUARE_LENGTH * snake.positions(i).y, SQUARE_LENGTH, length)
          }
          else if (snake.positions(i).y - 1 == snake.positions(0).y) {
            display.drawFillRect(SQUARE_LENGTH * snake.positions(i).x, SQUARE_LENGTH * snake.positions(i).y + SQUARE_LENGTH - length, SQUARE_LENGTH, length)
          }
        }
        else{
          display.setColor(Color.green)
          display.drawFillRect(SQUARE_LENGTH * snake.positions(i).x, SQUARE_LENGTH * snake.positions(i).y, SQUARE_LENGTH, SQUARE_LENGTH)
        }

        if (i == snake.length - 1) {
          display.setColor(Color.white)

          if (!snake.contains(new Position(snake.positions(i).x - 1, snake.positions(i).y)) && !apples.contains(new Position(snake.positions(i).x - 1, snake.positions(i).y))) {
            display.drawFillRect(SQUARE_LENGTH * (snake.positions(i).x - 1), SQUARE_LENGTH * snake.positions(i).y, length, SQUARE_LENGTH)
          }
          if (!snake.contains(new Position(snake.positions(i).x + 1, snake.positions(i).y)) && !apples.contains(new Position(snake.positions(i).x + 1, snake.positions(i).y))) {
            display.drawFillRect(SQUARE_LENGTH * (snake.positions(i).x + 1) + SQUARE_LENGTH - length, SQUARE_LENGTH * snake.positions(i).y, length, SQUARE_LENGTH)
          }
          if (!snake.contains(new Position(snake.positions(i).x, snake.positions(i).y - 1)) && !apples.contains(new Position(snake.positions(i).x, snake.positions(i).y - 1))) {
            display.drawFillRect(SQUARE_LENGTH * snake.positions(i).x, SQUARE_LENGTH * (snake.positions(i).y - 1), SQUARE_LENGTH, length)
          }
          if (!snake.contains(new Position(snake.positions(i).x, snake.positions(i).y + 1)) && !apples.contains(new Position(snake.positions(i).x, snake.positions(i).y + 1))) {
            display.drawFillRect(SQUARE_LENGTH * snake.positions(i).x, SQUARE_LENGTH * (snake.positions(i).y + 1) + SQUARE_LENGTH - length, SQUARE_LENGTH, length)
          }
        }
      }

      if (length == SQUARE_LENGTH) {
        for (i: Int <- apples.positions.indices) {
          display.drawTransformedPicture(SQUARE_LENGTH * apples.positions(i).x + SQUARE_LENGTH / 2, SQUARE_LENGTH * apples.positions(i).y + SQUARE_LENGTH / 2, 0, SQUARE_LENGTH / widthApple, "/img/OJApple.png")
        }
      }
      display.syncGameLogic(FPS)

    }

    display.setColor(Color.white)
    display.drawFillRect(0,0,80,15)
    display.drawFancyString(8, 10, s"Score : $score", fontSize=10)
  }

  /**
   * Redémarre le jeu en réinitialisant le serpent, les pommes et le score.
   */
  def restartGame(): Unit = {
    snake = new Snake(sideLength)
    apples = new Apples(appleNumber, getEmptySquares(false))
    score = 0
  }

  /**
   * Récupère les positions des cases vides sur la grille, en excluant les positions occupées par le serpent et les pommes.
   *
   * @param isApplesGenerated Indique si les positions des pommes sont déjà générées.
   * @return Un tableau de positions représentant les cases vides.
   */
  def getEmptySquares(isApplesGenerated: Boolean = true): Array[Position] = {
    var numberGeneratedApple: Int = 0
    if(isApplesGenerated){
      numberGeneratedApple = appleNumber
    }
    var emptySquareLength: Int = math.pow(sideLength, 2).toInt - snake.length - numberGeneratedApple
    if(math.pow(sideLength, 2).toInt - snake.length - numberGeneratedApple <= 0) {
      emptySquareLength = 0
    }
    val emptySquares: Array[Position] = new Array(emptySquareLength)
    var counter: Int = 0

    for (y: Int <- 0 until sideLength) {
      for (x: Int <- 0 until sideLength) {
        val position: Position = new Position(x, y)
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
          emptySquares(counter) = position
          counter += 1
        }
      }
    }
    return emptySquares
  }

  /**
   * Met à jour la tête du serpent en affichant son nouveau positionnement.
   *
   * @param x La nouvelle position horizontale de la tête du serpent.
   * @param y La nouvelle position verticale de la tête du serpent.
   */
  def updateHead(x: Int, y: Int): Unit = {
    val bimgSnake: BufferedImage = ImageIO.read(new File("./img/snake.png"))
    val widthSnake = bimgSnake.getWidth
    val imgName: String = "/img/snake.png"
    var rotation: Double = 0
    val headCenterX: Int = SQUARE_LENGTH * snake.positions(0).x + SQUARE_LENGTH / 2
    val headCenterY: Int = SQUARE_LENGTH * snake.positions(0).y + SQUARE_LENGTH / 2
    val scale: Double = SQUARE_LENGTH / widthSnake.toDouble

    if(x == 0 && y == 1) {
      rotation = math.Pi
    } else if (x == 1 && y == 0) {
      rotation = math.Pi / 2
    } else if (x == -1 && y == 0) {
      rotation = -math.Pi / 2
    }
    display.drawTransformedPicture(headCenterX, headCenterY, rotation, scale, imgName)
  }

  /**
   * Vérifie si la tête du serpent va manger une pomme à la nouvelle position spécifiée.
   *
   * @param x La prochaine position horizontale du serpent.
   * @param y La prochaine position verticale du serpent.
   * @return L'indice de la pomme que le serpent va manger, ou -1 s'il n'y a pas de pomme à cette position.
   */
  def snakeWillEatApple(x: Int, y: Int): Int = {
    for (i: Int <- apples.positions.indices) {
      if (apples.positions(i).x == snake.positions(0).x + x && apples.positions(i).y == snake.positions(0).y + y) {
        return i
      }
    }
    return -1
  }
}
