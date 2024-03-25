package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String PERSISTENCE_UNIT_NAME = "Name";
    private static EntityManagerFactory factory;

    public static void main(String[] args) {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();


        Tower tower1 = new Tower("Ivory Tower", 100, null);
        Tower tower2 = new Tower("Mage Tower", 200, null);


        Mage mage1 = new Mage("Gandalf", 100, tower1);

        Mage mage2 = new Mage("Dumbledore", 200, tower2);

        Mage mage3 = new Mage("Merlin", 300, tower1);

        Mage mage4 = new Mage("Saruman", 400, tower2);

        tower1.addMage(mage1);
        tower1.addMage(mage3);
        tower2.addMage(mage2);
        tower2.addMage(mage4);


        em.persist(tower1);
        em.persist(tower2);
        em.persist(mage1);
        em.persist(mage2);
        em.persist(mage3);
        em.persist(mage4);

        em.getTransaction().commit();

        String command;
        var scanner = new java.util.Scanner(System.in);
        do {
            System.out.println("Enter a command: ");
            command = scanner.nextLine();
            switch (command){
                case "add_tower" -> {
                    em.getTransaction().begin();
                    System.out.println("Enter tower name: ");
                    String towerName = scanner.nextLine();
                    System.out.println("Enter tower height: ");
                    int towerHeight = scanner.nextInt();
                    scanner.nextLine();
                    Tower tower = new Tower(towerName, towerHeight, null);
                    System.out.println("Enter number of mages: ");
                    int numberOfMages = scanner.nextInt();
                    scanner.nextLine();
                    for (int i = 0; i < numberOfMages; i++) {
                        System.out.println("Enter mage name: ");
                        Mage mage = em.find(Mage.class, scanner.nextLine());
                        if (mage != null) {
                            if (mage.getTower() != null) {
                                mage.getTower().getMages().remove(mage);
                                em.merge(mage.getTower());
                            }
                            mage.setTower(tower);
                            em.merge(mage);
                            tower.addMage(mage);
                        }else {
                            System.out.println("Mage not found.");
                        }
                    }
                    em.persist(tower);
                    em.getTransaction().commit();
                }
                case "add_mage" -> {
                    em.getTransaction().begin();
                    System.out.println("Enter mage name: ");
                    String mageName = scanner.nextLine();
                    System.out.println("Enter mage level: ");
                    int mageLevel = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter tower name: ");
                    String towerName = scanner.nextLine();
                    Tower tower = em.find(Tower.class, towerName);
                    Mage mage;
                    if (tower == null) {
                        System.out.println("Tower not found. This mage will be created without a tower.");
                        mage = new Mage(mageName, mageLevel, null);
                        em.persist(mage);
                    }else {
                        mage = new Mage(mageName, mageLevel, tower);
                        tower.addMage(mage);
                        em.persist(mage);
                        em.merge(tower);
                    }
                    em.getTransaction().commit();
                }
                case "show_data" -> {
                    em.getTransaction().begin();
                    List<Tower> towers = em.createQuery("SELECT t FROM Tower t", Tower.class).getResultList();
                    for (Tower tower : towers) {
                        System.out.println(tower.getName() + " " + tower.getHeight());
                        for (Mage mage : tower.getMages()) {
                            System.out.println("    " + mage.getName() + " " + mage.getLevel());
                        }
                    }
                    List<Mage> mages = em.createQuery("SELECT m FROM Mage m WHERE m.tower IS NULL", Mage.class).getResultList();
                    if (!mages.isEmpty()){
                        System.out.println("Mages without tower:");
                        for (Mage mage : mages) {
                            System.out.println(mage.getName() + " " + mage.getLevel());
                        }
                    }
                    em.getTransaction().commit();
                }
                case "remove_tower" -> {
                    em.getTransaction().begin();
                    System.out.println("Enter tower name: ");
                    String towerName = scanner.nextLine();
                    Tower tower = em.find(Tower.class, towerName);
                    if (tower == null) {
                        System.out.println("Tower not found.");
                    }else {
                        for (Mage mage : tower.getMages()) {
                            mage.setTower(null);
                        }
                        em.remove(tower);
                    }
                    em.getTransaction().commit();
                }
                case "remove_mage" -> {
                    em.getTransaction().begin();
                    System.out.println("Enter mage name: ");
                    String mageName = scanner.nextLine();
                    Mage mage = em.find(Mage.class, mageName);
                    if (mage == null) {
                        System.out.println("Mage not found.");
                    }else if (mage.getTower() != null) {
                        Tower tower = mage.getTower();
                        tower.getMages().remove(mage);
                        em.remove(mage);
                    }else {
                        em.remove(mage);
                    }
                    em.getTransaction().commit();
                }
                case "Short_towers" -> {
                    em.getTransaction().begin();
                    System.out.println("Enter height: ");
                    int height = scanner.nextInt();
                    scanner.nextLine();
                    List<Tower> towers = em.createQuery("SELECT t FROM Tower t WHERE t.height < :height", Tower.class).setParameter("height", height).getResultList();
                    for (Tower tower : towers) {
                        System.out.println(tower.getName() + " " + tower.getHeight());
                    }
                    em.getTransaction().commit();
                }
                default ->
                    System.out.println("Unknown command");
            }
        } while (!command.equals("exit"));
        em.close();
    }
}