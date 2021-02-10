package agh.cs.project.elements;

import java.util.Objects;

public class Vector2d{
    public int x;
    public int y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return ("(" + this.x + "," + this.y + ")");
    }

    public boolean precedes(Vector2d other){
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other){
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d upperRight(Vector2d other){
        int x,y;
        x = Math.max(this.x, other.x);
        y = Math.max(this.y, other.y);

        return new Vector2d(x,y);
    }

    public Vector2d lowerLeft(Vector2d other){
        int x,y;
        x = Math.min(this.x, other.x);
        y = Math.min(this.y, other.y);

        return new Vector2d(x,y);
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(other.x + this.x, other.y + this.y);
    }

    public Vector2d subtract(Vector2d other){
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public boolean equals(Object other){
        if (other == this) return true;
        if (other == null) return false;
        if (getClass()!=other.getClass()) return false;
        return this.hashCode() == other.hashCode();
    }

    public Vector2d repair(Vector2d lowerLeft, Vector2d upperRight){
        int x = this.x;
        int y = this.y;

        if(this.x < lowerLeft.x){
            x = upperRight.x;
        }
        else if(this.x > upperRight.x){
            x = lowerLeft.x;
        }

        if(this.y < lowerLeft.y){
            y = upperRight.y;
        }
        else if(this.y > upperRight.y){
            y = lowerLeft.y;
        }
        return new Vector2d(x,y);
    }

    public Vector2d opposite(){
        return new Vector2d(-this.x, -this.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }
}