package org.example;
import java.util.*;

public class Mage implements Comparable<Mage> {
    private String name;
    private int level;
    private double power;
    private Set<Mage> apprentices;
    public Mage(String name, int level, double power, Set<Mage> apprentices, Comparator<Mage> comparator) { // overloaded
        this.name = name;
        this.level = level;
        this.power = power;

        this.apprentices = new TreeSet<>(comparator);
        this.apprentices.addAll(apprentices);
    }
    public Mage(String name, int level, double power, Set<Mage> apprentices, int isSorted) {
        this.name = name;
        this.level = level;
        this.power = power;

        if (isSorted == -1) { // without sorting
            this.apprentices = new HashSet<>();
            this.apprentices.addAll(apprentices);
        }else if (isSorted == 0){ // with sorting (natural order)
            this.apprentices = new TreeSet<>();
            this.apprentices.addAll(apprentices);
        }
        else {
            this.apprentices = new TreeSet<>(new MageComparator());
            this.apprentices.addAll(apprentices);
        }
    }
    @Override
    public boolean equals(Object obj){
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Mage other = (Mage) obj;
        return Objects.equals(this.name, other.name) && this.level == other.level &&
                Double.compare(this.power, other.power) == 0 && Objects.equals(this.apprentices, other.apprentices);
    }
    @Override
    public int hashCode(){
        return Objects.hash(this.name, this.level, this.power, this.apprentices);
    }
    @Override
    public int compareTo(Mage o) {
        if (this.name.compareTo(o.name) == 0) {
            if (this.level == o.level) {
                if (Double.compare(this.power, o.power) == 0) {
                    return this.apprentices.size() - o.apprentices.size();
                }
                return Double.compare(this.power, o.power);
            }
            return this.level - o.level;
        }
        if (this.name.length() != o.name.length())
            return Integer.compare(this.name.length(), o.name.length());
        return this.name.compareTo(o.name);
    }
    @Override
    public String toString() {
        return "Mage{name='" + this.name + "', level=" + this.level + ", power=" + this.power + "}";
    }
    public String getName(){
        return this.name;
    }
    public int getLevel(){
        return this.level;
    }
    public double getPower(){
        return this.power;
    }
    public Set<Mage> getApprentices(){
        return this.apprentices;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setLevel(int level){
        this.level = level;
    }
    public void setPower(double power){
        this.power = power;
    }
    public void setApprentices(Set<Mage> apprentices) {
        this.apprentices.addAll(apprentices);
    }
}
