package org.ynov.jdlv_ig.utils;

public class ConvertirMessageEnInstruction {

    public static String convertirMessageEnInstruction(String message) {
        String regles = null;
        String regleRegex = "->r:[";
        if (message.contains(regleRegex)) {
            int indexRegex = message.indexOf(regleRegex);
            regles = (String) message.subSequence(indexRegex + 5, message.length() - 1);
            return regles;
        }
        return regles;
    }

    public static String convertirReglesRowEnProposition(String regles) {
        String[] reglesTab = regles.split(",");
        for (int i = 0; i < reglesTab.length; i++) {
            System.out.println("Regles " + i + ": " + reglesTab[i]);
        }
        String reglesProposees = "Règles proposées:\n" +
                "taille grille: " + reglesTab[0] +
                "\nReproduction: " + reglesTab[1] +
                "\nSur-Population: " + reglesTab[2] +
                "\nSous-Population: " + reglesTab[3] +
                "\n";

        return reglesProposees;
    }

}
