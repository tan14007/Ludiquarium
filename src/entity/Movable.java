package entity;

public interface Movable {
	public void moveTo(int x, int y);

	public void moveTo(double x, double y, double time);

	public void translate(int x, int y);

	public void translateBy(double x, double y, double time);
}
