package org.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class MageRepository {
    private Collection<Mage> mages = new ArrayList<>();

    public MageRepository(Collection<Mage> mages) {
        this.mages = mages;
    }

    public Optional<Mage> find(String name){
        return mages.stream()
                .filter(mage -> mage.getName().equals(name))
                .findFirst();
    }

    public void delete(String name){
        var mage = find(name);
        if (mage.isPresent()){
            mages.remove(mage.get());
        }else {
            throw new IllegalArgumentException("Invalid mage name");
        }
    }

    public void save(Mage mage){
        var existingMage = find(mage.getName());
        if (existingMage.isPresent()){
            throw new IllegalArgumentException("Mage already exists");
        }
        mages.add(mage);
    }
}
