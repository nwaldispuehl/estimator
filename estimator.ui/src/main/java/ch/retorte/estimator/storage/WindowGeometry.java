package ch.retorte.estimator.storage;

/**
 * Holds the current position of the window on the screen.
 */
public class WindowGeometry {

  private double width;
  private double height;

  private double x;
  private double y;

  public WindowGeometry(double width, double height, double x, double y) {
    this.width = width;
    this.height = height;
    this.x = x;
    this.y = y;
  }

  public double getWidth() {
    return width;
  }

  public double getHeight() {
    return height;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }
}
