package utils;

public class Vector {
    // Recreation of Vector2D
    public double x, y;

    public Vector() {
        this.x = 0;
        this.y = 0;
    }

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector(Vector v) {
        this.x = v.x;
        this.y = v.y;
    }

    public Vector set(Vector v) {
        this.x = v.x;
        this.y = v.y;
        return this;
    }

    public Vector set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector addScaled(Vector vector, double factor) {
        this.x += factor * vector.x;
        this.y += factor * vector.y;
        return this;
    }

    public Vector subtract(Vector vector) {
        this.x -= vector.x;
        this.y -= vector.y;
        return this;
    }

    public Vector subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Vector multiply(double factor) {
        this.x *= factor;
        this.y *= factor;
        return this;
    }

    public boolean equals(Object object) {
        if (object instanceof Vector) {
            Vector v = (Vector) object;
            return x == v.x && y == v.y;
        } else
            return false;
    }

    public String toString() {
        return "{" + x + "," + y + "}";
    }

    public double magnitude() {
        return Math.hypot(x, y);
    }

    public double angle() {
        return Math.atan2(y, x);
    }

    public double angle(Vector other) {
        double result = other.angle() - this.angle();
        if (result > Math.PI) result -= 2 * Math.PI;
        else if (result < -Math.PI) result += 2 * Math.PI;
        return result;
    }

    public Vector add(Vector v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    public Vector add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector rotate(double angle) {
        double newX = this.x * Math.cos(angle) - this.y * Math.sin(angle);
        this.y = this.x * Math.sin(angle) + this.y * Math.cos(angle);
        this.x = newX;
        return this;
    }

    public double dot(Vector v) {
        return this.x * v.x + this.y * v.y;
    }

    public double distance(Vector v) {
        return Math.hypot(this.x - v.x, this.y - v.y);
    }

    public Vector normalise() {
        if (magnitude() != 0) {
            double m = magnitude();
            this.x /= m;
            this.y /= m;
        }
        return this;

    }

    public Vector wrapAround(double w, double h) {
        if (x>w) {
            x -= w;
        }
        else if (x<0) {
            x+= w;
        }
        if (y>h) {
            y -= h;
        }
        else if (y<0) {
            y += h;
        }

        return this;
    }

    public static Vector polar(double angle, double magnitude) {
        return new Vector(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
    }

}
