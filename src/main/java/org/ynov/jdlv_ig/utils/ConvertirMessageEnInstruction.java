package org.ynov.jdlv_ig.utils;

public class ConvertirMessageEnInstruction {

    private static final String REGLES_REGEX = "->r:[";
    private static final String REGLES_PROPOSEES = "Règles proposées:\n";
    private static final String TAILLE_GRILLE = "taille grille: ";
    private static final String REPRODUCTION = "\nReproduction: ";
    private static final String SURPOP = "\nSur-Population: ";
    private static final String SOUSPOP = "\nSous-Population: ";

    public static String convertirMessageEnInstruction(String message) {
        String regles = null;
        String regleRegex = REGLES_REGEX;
        if (message.contains(regleRegex)) {
            int indexRegex = message.indexOf(regleRegex);
            regles = (String) message.subSequence(indexRegex + 5, message.length() - 1);
            return regles;
        }
        return regles;
    }

    public static String convertirReglesRowEnProposition(String regles) {
        String[] reglesTab = regles.split(",");
        String reglesProposees = REGLES_PROPOSEES +
                TAILLE_GRILLE + reglesTab[0] +
                REPRODUCTION + reglesTab[1] +
                SURPOP + reglesTab[2] +
                SOUSPOP + reglesTab[3] +
                "\n";

        return reglesProposees;
    }

}
