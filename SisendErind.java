/**
 * Oma erindiklass, mida kasutatakse vale kasutaja sisendi puhul.
 * Pärib RuntimeException-st, nii et seda ei pea kohustuslikult tabama (unchecked).
 */
public class SisendErind extends RuntimeException {
    public SisendErind(String sõnum) {
        super(sõnum);
    }
}
