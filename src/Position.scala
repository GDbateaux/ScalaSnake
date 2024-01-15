class Position(val x: Int, val y: Int) {
  /**
   * Méthode qui remplace le toString de la classe
   * @return La classe en format chaîne de caractère
   */
  override def toString: String = {
    return s"X: $x, Y: $y"
  }
}
