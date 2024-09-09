package Peptides;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class PeptideTest {

    private Peptide peptide;

    @Before
    public void setUp() {
        String protein = "ABCDEFGHIJKL";
        List<String> library = Arrays.asList("ABCDEFGH", "IJKLMNOP");
        peptide = new Peptide(Peptide.DEFAULT_PEPTIDE_SIZE, protein, library);
    }

    @Test
    public void testCreateKMersDictionary() {
        peptide.createKMersDictionary();
        assertEquals(5, peptide.kmers.size());

        List<Integer> positions = peptide.kmers.get("ABCDEFGH");
        assertNotNull(positions);
        assertTrue(positions.contains(0));
    }

    @Test
    public void testConvertToLong() {
        String peptideString = "ABCDEFGH";
        long expectedLong = peptide.convertToLong(peptideString);

        long calculatedValue = 0;
        for (char c : peptideString.toCharArray()) {
            calculatedValue = calculatedValue * 26 + (c - 'A');
        }
        assertEquals(calculatedValue, expectedLong);
    }

    @Test
    public void testConvertPeptidesToLong() {
        peptide.convertPeptidesToLong();
        long expectedLong1 = peptide.convertToLong("ABCDEFGH");
        long expectedLong2 = peptide.convertToLong("IJKLMNOP");

        assertTrue(peptide.longPeptides.containsKey(expectedLong1));
        assertTrue(peptide.longPeptides.containsKey(expectedLong2));
    }

    @Test
    public void testReverseSearch() {
        peptide.convertPeptidesToLong();

        peptide.reverseSearch();
        assertTrue(peptide.longPeptides.get(peptide.convertToLong("ABCDEFGH")).contains(0));
    }

    @Test
    public void testBinarySearch() {
        List<String> library = Arrays.asList("ABCDEFGH", "IJKLMNOP");
        Peptide peptide = new Peptide(Peptide.DEFAULT_PEPTIDE_SIZE, "ABCDEFGHIJKL", library);

        long peptideLong = peptide.convertToLong("ABCDEFGH");
        assertTrue(peptide.binarySearch(peptideLong));

        long nonExistentPeptideLong = peptide.convertToLong("ZZZZZZZZ");
        assertFalse(peptide.binarySearch(nonExistentPeptideLong));
    }

    @Test
    public void testBinarySearchForKmers() {
        List<String> library = Arrays.asList("ABCDEFGH", "IJKLMNOP");
        Peptide peptide = new Peptide(Peptide.DEFAULT_PEPTIDE_SIZE, "ABCDEFGHIJKL", library);

        peptide.binarySearchForKmers();
    }

}
