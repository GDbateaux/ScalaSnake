object SnakeTester extends App {
  var grid: Grid = new Grid(20, 1)

  grid.updateGrid()
  grid.snake.move(1,0)
  grid.snake.move(1,0)
  grid.snake.move(0,1)
  grid.snake.move(0,1)
  grid.snake.move(1,0)
  grid.updateGrid()
}
