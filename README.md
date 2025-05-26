# Tetris (JavaFX)

This is a simple Tetris game implementation written in Java using JavaFX.  
It focuses on clean separation of concerns and was designed for learning purposes and architecture improvement practice.

## 🕹 Features

- Classic falling-block Tetris gameplay
- Five tetromino shapes
- Line clearing with automatic shifting
- Game over detection
- Restart functionality
- Keyboard-based control

## 🎮 Controls

- **← / →** — Move figure left / right
- **Space** — Rotate figure 90°
- **↓** — Speed up falling
- **Enter** — Restart the game after game over

## 📁 Code Structure

- `Render` — Handles all drawing operations and figure templates
- `Grid` — Stores game field state and cell colors
- `Logic` — Contains static logic: collision checks, game-over check
- `Move` — Movement and rotation operations
- `ControlUnits` — Core controller managing game flow and input

## 📅 Development Timeline

- **Initial version**: March 2025
- **Refactoring completed**: May 2025

## 🔧 How to Run

The project uses JavaFX, which is automatically managed via Maven.

- Install JavaFX
- Run via Maven with the `javafx:run` parameter