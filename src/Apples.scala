/**
 * Représente une classe gérant les pommes dans le Snake.
 *
 * @param appleNumber Le nombre initial de pommes.
 * @param emptySquares Les carrés vides sur la grille.
 */
class Apples(val appleNumber: Int = 1, emptySquares: Array[Position]) {
  val positions: Array[Position] = getNewPositionsApples(emptySquares, appleNumber)

  /**
   * Génère de nouvelles positions pour les pommes en utilisant les cases vides disponibles.
   *
   * @param emptySquares Les carrés vides sur la grille.
   * @param numberApples Le nombre de pommes à générer.
   * @return Un tableau de nouvelles positions pour les pommes.
   */
  private def getNewPositionsApples(emptySquares: Array[Position], numberApples: Int): Array[Position] = {
    val pos: Array[Position] = new Array[Position](numberApples)

    for (i: Int <- 0 until numberApples) {
      var rdm: Int = (math.random() * (emptySquares.length - i)).toInt
      pos(i) = emptySquares(rdm)
      emptySquares(rdm) = emptySquares(emptySquares.length - 1 - i)
    }
    return pos
  }

  /**
   * Vérifie si une position donnée est occupée par une pomme.
   *
   * @param pos La position à vérifier.
   * @return true si la position contient une pomme, sinon false.
   */
  def contains(pos: Position): Boolean = {
    var contains: Boolean = false

    for (p: Position <- positions) {
      if (p.x == pos.x && p.y == pos.y) {
        contains = true
      }
    }
    return contains
  }

  /**
   * Régénère la position d'une pomme en utilisant les nouvelles cases vides disponibles.
   *
   * @param actualEmptySquares Les carrés vides sur la grille.
   * @param appleIndice        L'indice de la pomme à régénérer.
   */
  def regenerateApple(actualEmptySquares: Array[Position], appleIndice: Int): Unit = {
    if(!actualEmptySquares.isEmpty) {
      var rdm: Int = (math.random() * actualEmptySquares.length).toInt
      positions(appleIndice) = actualEmptySquares(rdm)
    }
    else{
      positions(appleIndice) = new Position(-1, -1)
    }
  }
}
