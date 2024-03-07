package org.ynov.jdlv_ig.utils;

/**
 * Classe qui permet de convertir le message en Instruction/Règles.
 */
public class ConvertirMessageEnInstruction {
    /**
     * REGEX qui permet de reconnaître qu'il s'agit de règles.
     */
    private static final String REGLES_REGEX = "->r:[";
    /**
     * Constante pour formater le message au niveau des règles proposées.
     */
    private static final String REGLES_PROPOSEES = "Règles proposées:\n";
    /**
     * Constante pour formater le message au niveau dela taille de la grille.
     */
    private static final String TAILLE_GRILLE = "taille grille: ";
    /**
     * Constante pour formater le message au niveau de la reproduction.
     */
    private static final String REPRODUCTION = "\nReproduction: ";
    /**
     * Constante pour formater le message au niveau de la Sur-population.
     */
    private static final String SURPOP = "\nSur-Population: ";
    /**
     * Constante pour formater le message au niveau de la Sous-population.
     */
    private static final String SOUSPOP = "\nSous-Population: ";

    /**
     * Méthode pour convertir un message en règles sous forme de String.
     * @param message
     * @return
     */
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

    /**
     * Méthode pour convertir les règles sous formes de String en message formaté.
     * @param regles
     * @return
     */
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
