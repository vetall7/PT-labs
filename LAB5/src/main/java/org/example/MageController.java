package org.example;

public class MageController {
    private MageRepository mageRepository;

    public MageController(MageRepository mageRepository) {
        this.mageRepository = mageRepository;
    }
    public String findMage(String name){
        var mage = mageRepository.find(name);
        if (mage.isPresent()){
            return mage.get().getName() + " " + mage.get().getLevel();
        }else {
            return "not found";
        }
    }
    public String deleteMage(String name){
        try {
            mageRepository.delete(name);
            return "done";
        }
        catch (IllegalArgumentException e){
            return "not found";
        }
    }
    public String saveMage(String name, String level){
        try {
            mageRepository.save(new Mage(name, Integer.parseInt(level)));
            return "done";
        }
        catch (IllegalArgumentException e){
            return "bad request";
        }
    }
}
