package org.example;
import org.example.Mage;
import org.example.MageComparator;

import java.util.*;

import static java.util.Collections.emptySet;

public class Main {
    public static void main(String[] args) {

        // -1 - without sorting
        // 0 - with sorting (natural order)
        // 1 - with sorting2 (custom order)

        System.out.println(Integer.parseInt(args[0]));

        Mage mage10 = new Mage("Mage10", 1, 4.0, emptySet(), 0);
        Mage mage9 = new Mage("Mage9", 2, 5.0, emptySet(), 0);
        Mage mage8 = new Mage("Mage8", 3, 6.0, emptySet(), 0);
        Mage mage7 = new Mage("Mage7", 4, 7.0, emptySet(), 0);
        Mage mage6 = new Mage("Mage6", 5, 8.0, new HashSet<>(List.of(mage10)), 0);
        Mage mage5 = new Mage("Mage5", 6, 9.0, emptySet(), 0);
        Mage mage4 = new Mage("Mage4", 7, 10.0, new HashSet<>(List.of(mage9)), 0);
        Mage mage3 = new Mage("Mage3", 30, 300.0, new HashSet<>(Arrays.asList(mage7, mage8)), 0);
        Mage mage2 = new Mage("Mage2", 20, 200.0, new HashSet<>(List.of(mage6)), 0);
        Mage mage1 = new Mage("Mage1", 10, 100.0, new HashSet<>(Arrays.asList(mage4, mage5)), 0);


        /*Mage mage10 = new Mage("Mage10", 1, 4.0, emptySet(), 0);
        Mage mage9 = new Mage("Mage9", 2, 5.0, emptySet(), 0);
        Mage mage8 = new Mage("Mage8", 3, 6.0, emptySet(), 0);
        Mage mage7 = new Mage("Mage7", 4, 7.0, emptySet(), 0);
        Mage mage6 = new Mage("Mage6", 5, 8.0, new HashSet<>(List.of(mage10)), 0);
        Mage mage5 = new Mage("Mage5", 6, 9.0, emptySet(), 0);
        Mage mage4 = new Mage("Mage4", 7, 10.0, new HashSet<>(List.of(mage8, mage9)), 0);
        Mage mage3 = new Mage("Mage3", 30, 300.0, emptySet(), 0);
        Mage mage2 = new Mage("Mage2", 20, 200.0, new HashSet<>(List.of(mage7, mage6, mage9, mage10)), 0);
        Mage mage1 = new Mage("Mage1", 10, 100.0, new HashSet<>(Arrays.asList(mage10, mage5, mage4, mage3)), 0);
        */

        Set<Mage> mages;

        if (Objects.equals(args[0], "-1")) {
            mages = new HashSet<>();
            System.out.println("without sorting: ");
        }
        else if (Objects.equals(args[0], "0")){
            mages = new TreeSet<>();
            System.out.println("sorted (natural order): ");
        }
        else if (Objects.equals(args[0], "1")){
            mages = new TreeSet<>(new MageComparator());
            System.out.println("sorted (custom order): ");
        }
        else {
            mages = new HashSet<>();
            System.out.println("without sorting: " + mages);
        }
        mages.add(mage1);
        mages.add(mage2);
        mages.add(mage3);
        mages.add(mage4);
        mages.add(mage5);
        mages.add(mage6);
        mages.add(mage7);
        mages.add(mage8);
        mages.add(mage9);
        mages.add(mage10);


        List<Mage> withoutSupervisor = new ArrayList<>(mages);
        for (Mage mage : mages) {
            withoutSupervisor.removeAll(mage.getApprentices());
        }
        for (Mage mage : withoutSupervisor) {
            printHierarchy(mage, 1);
        }

        if (Objects.equals(args[0], "-1")) {
            generateStatistics(mages, false);
        }
        else {
            generateStatistics(mages, true);
        }
    }
    public static void printHierarchy(Mage mage, int depth) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append("-");
        }
        sb.append(mage).append("\n");
        System.out.print(sb.toString());
        for (Mage apprentice : mage.getApprentices()) {
            printHierarchy(apprentice, depth + 1);
        }
    }
    public static  Map<Mage, Integer> generateStatistics(Set<Mage> mages, boolean isSorted) {
        Map<Mage, Integer> statisticsMap;

        if (!isSorted) {
            statisticsMap = new HashMap<>();
        } else {
            statisticsMap = new TreeMap<>();
        }

        for (Mage mage : mages) {
            Set<Mage> countedApprentices = new HashSet<>();
            int descendantsCount = countDescendants(mage, countedApprentices);
            statisticsMap.put(mage, descendantsCount);
            System.out.println(mage.getName() + " has " + descendantsCount + " descendants");
        }
        return statisticsMap;
    }
    private static int countDescendants(Mage mage, Set<Mage> countedApprentices) {
        int descendantsCount = 0;
        for (Mage apprentice : mage.getApprentices()) {
            if (!countedApprentices.contains(apprentice)) {
                countedApprentices.add(apprentice);
                descendantsCount++;
                descendantsCount += countDescendants(apprentice, countedApprentices);
            }
        }
        return descendantsCount;
    }

}