package Peptides;

import java.util.*;

public class Peptide {

    public static final int DEFAULT_PEPTIDE_SIZE = 8;

    private String protein;
    private int peptideSize;

    public HashMap<String, List<Integer>> kmers = new LinkedHashMap<>();

    private List<String> library;

    Map<Long, List<Integer>> longPeptides = new HashMap<>();

    private long[] peptideLongArray;

    public Peptide(int peptideSize, String protein, List<String> library) {
        this.peptideSize = peptideSize;
        this.protein = protein;
        this.library = library;
        // Approach 1: Generate k-mers from the long protein and store them in a dictionary
        createKMersDictionary();

        // Approach 3: Convert peptides to long integers and store them in a Map
        convertPeptidesToLong();

        // Approach 4: Convert peptides to long integers and store them in a sorted array
        convertPeptidesToLongArray(library);
    }

    // Approach 3: Convert peptides from the library to long integers
    void convertPeptidesToLong() {
        for (String peptide : library) {
            long peptideAsLong = convertToLong(peptide);
            longPeptides.put(peptideAsLong, new ArrayList<>());
        }
    }

    // Approach 1: Generate k-mers from the protein and store them in a map
    void createKMersDictionary() {
        for (int i = 0; i < protein.length() - peptideSize + 1; i++) {
            String kmer = protein.substring(i, i + peptideSize);
            List<Integer> positions = kmers.get(kmer);
            if (positions == null) {
                positions = new ArrayList<>();
                kmers.put(kmer, positions);
            }
            positions.add(i);
        }
    }

    // Approach 1: Search for peptides in the protein using the k-mer dictionary
    public Map<String, List<Integer>> searchLibrary() {
        LinkedHashMap<String, List<Integer>> existingPeptides = new LinkedHashMap<String, List<Integer>>();
        for (String peptide : library) {
            List<Integer> positions = kmers.get(peptide);
            if (null == positions)
                continue;
            existingPeptides.put(peptide, positions);
        }
        return existingPeptides;
    }

    // Approach 2: Reverse search by taking k-mers from the protein and checking them in the map of long integers
    void reverseSearch() {
        boolean found = false;

        for (int i = 0; i <= protein.length() - peptideSize; i++) {
            String kmer = protein.substring(i, i + peptideSize);
            long kmerAsLong = convertToLong(kmer);

            if (longPeptides.containsKey(kmerAsLong)) {
                longPeptides.get(kmerAsLong).add(i);  // Add position to list
                System.out.println("Found: " + kmer + " at index " + i);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No peptides from the library were found in the protein.");
        }
    }

    // Approach 4: Convert peptides to long integers and store them in a sorted array
    void convertPeptidesToLongArray(List<String> library) {
        peptideLongArray = new long[library.size()];

        for (int i = 0; i < library.size(); i++) {
            peptideLongArray[i] = convertToLong(library.get(i));
        }

        Arrays.sort(peptideLongArray);
    }

    // Approach 4: Perform binary search for k-mers in the sorted array of long integers
    void binarySearchForKmers() {
        boolean found = false;

        for (int i = 0; i <= protein.length() - peptideSize; i++) {
            String kmer = protein.substring(i, i + peptideSize);
            long kmerAsLong = convertToLong(kmer);

            if (binarySearch(kmerAsLong)) {
                System.out.println("Found: " + kmer + " at index " + i);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No peptides from the library were found in the protein.");
        }
    }

    boolean binarySearch(long kmerAsLong) {
        return Arrays.binarySearch(peptideLongArray, kmerAsLong) >= 0;
    }

    // Helper method to convert peptide strings to long integers (base-26 encoding)
    long convertToLong(String peptide) {
        long value = 0;
        for (char c : peptide.toCharArray()) {
            value = value * 26 + (c - 'A');
        }
        return value;
    }
}




