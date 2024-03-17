package org.example;

import java.util.Comparator;

public class MageComparator implements Comparator<Mage> {

    @Override
    public int compare(Mage mage1, Mage mage2) {
        if (mage1.getLevel() == mage2.getLevel()) {
            if (mage1.getName().compareTo(mage2.getName()) == 0) {
                if (Double.compare(mage1.getPower(), mage2.getPower()) == 0) {
                    return mage2.getApprentices().size() - mage1.getApprentices().size();
                }

                return Double.compare(mage2.getPower(), mage1.getPower());
            }
            return mage2.getName().compareTo(mage1.getName());
        }
        return mage2.getLevel() - mage1.getLevel();
    }
}

