package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Tower {
    @Id
    private String name;
    private int height;
    @OneToMany
    private List<Mage> mages;

    public Tower(String name, int height, List<Mage> mages) {
        this.name = name;
        this.height = height;
        if (mages == null) {
            this.mages = new ArrayList<>();
        } else {
            this.mages = new ArrayList<>(mages);
        }
    }
    public String toString() {
        return "|" +
                "name='" + name + '\'' +
                ", height=" + height +
                ", mages=" + mages +
                '|';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    public void setMages(List<Mage> mages) {
        this.mages = mages;
    }
    public void addMage(Mage mage) {
        this.mages.add(mage);
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public List<Mage> getMages() {
        return mages;
    }
}