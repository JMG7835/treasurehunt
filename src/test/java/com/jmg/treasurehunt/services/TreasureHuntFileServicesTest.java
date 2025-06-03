package com.jmg.treasurehunt.services;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.jmg.treasurehunt.utils.FilesUtils.VOID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@SpringBatchTest
public class TreasureHuntFileServicesTest {
    public static final String Line_C_OK = "C - 3 - 4";
    public static final String Line_M_OK = "M - 1 - 0";
    public static final String Line_M_OK_2 = "M - 2 - 1";
    public static final String Line_T_OK = "T - 0 - 3 - 2";
    public static final String Line_T_OK_2 = "T - 1 - 3 - 3";
    public static final String Line_A_OK = "A - Lara - 1 - 1 - S - AADADAGGA";

    @InjectMocks
    private TreasureHuntFileServices treasureHuntFileServices;


    @Test
    void controleLineTest_Line_ko() {
        EtatLineModel result = treasureHuntFileServices.controleLine("P - 3 -");
        assertFalse(result.isOk());
        assertThat(result.line()).isEqualTo("Type line P not found");
    }

    @Test
    void controleLineTest_LineC_OK() {
        EtatLineModel result = treasureHuntFileServices.controleLine(Line_C_OK);
        assertTrue(result.isOk());
        assertThat(result.line()).isEqualTo(VOID);
    }

    @Test
    void controleLineTest_LineC_KO_incorect_number() {
        EtatLineModel result = treasureHuntFileServices.controleLine("C - 3 -");
        assertFalse(result.isOk());
        assertThat(result.line()).isEqualTo("Line type C has an incorrect number of fields");
    }

    @Test
    void controleLineTest_LineC_KO_not_number() {
        EtatLineModel result = treasureHuntFileServices.controleLine("C - B - A");
        assertFalse(result.isOk());
        assertThat(result.line()).isEqualTo("Line type C: field at index 1 is not a number (value: B) and field at index 2 is not a number (value: A)");
    }

    @Test
    void controleLineTest_LineM_OK() {
        EtatLineModel result = treasureHuntFileServices.controleLine(Line_M_OK);
        assertTrue(result.isOk());
        assertThat(result.line()).isEqualTo(VOID);
    }

    @Test
    void controleLineTest_LineM_KO_incorect_number() {
        EtatLineModel result = treasureHuntFileServices.controleLine("M - 3 -");
        assertFalse(result.isOk());
        assertThat(result.line()).isEqualTo("Line type M has an incorrect number of fields");
    }

    @Test
    void controleLineTest_LineM_KO_not_number() {
        EtatLineModel result = treasureHuntFileServices.controleLine("M - B - A");
        assertFalse(result.isOk());
        assertThat(result.line()).isEqualTo("Line type M: field at index 1 is not a number (value: B) and field at index 2 is not a number (value: A)");
    }

    @Test
    void controleLineTest_LineT_OK() {
        EtatLineModel result = treasureHuntFileServices.controleLine(Line_T_OK);
        assertTrue(result.isOk());
        assertThat(result.line()).isEqualTo(VOID);
    }

    @Test
    void controleLineTest_LineT_KO_incorect_number() {
        EtatLineModel result = treasureHuntFileServices.controleLine("T - 3 -");
        assertFalse(result.isOk());
        assertThat(result.line()).isEqualTo("Line type T has an incorrect number of fields");
    }

    @Test
    void controleLineTest_LineT_KO_not_number() {
        EtatLineModel result = treasureHuntFileServices.controleLine("T - B - A - 4");
        assertFalse(result.isOk());
        assertThat(result.line()).isEqualTo("Line type T: field at index 1 is not a number (value: B) and field at index 2 is not a number (value: A)");
    }

    @Test
    void controleLineTest_LineA_OK() {
        EtatLineModel result = treasureHuntFileServices.controleLine(Line_A_OK);
        assertTrue(result.isOk());
        assertThat(result.line()).isEqualTo(VOID);
    }

    @Test
    void controleLineTest_LineA_KO_incorect_number() {
        EtatLineModel result = treasureHuntFileServices.controleLine("A - Lara - 1 - S - AADADAGGA");
        assertFalse(result.isOk());
        assertThat(result.line()).isEqualTo("Line type A has an incorrect number of fields");
    }

    @Test
    void controleLineTest_LineA_KO_All_Error() {
        EtatLineModel result = treasureHuntFileServices.controleLine("A - Lara - A - B - F - AAKADAPGA");
        assertFalse(result.isOk());
        assertThat(result.line()).isEqualTo("Line type A: field at index 2 is not a number (value: A) and field at index 3 is not a number (value: B) and field at index 4 is Invalid cardinal point (value: F) and field at index 5 is Invalid move point (value: K) and field at index 5 is Invalid move point (value: P)");
    }

    @Test
    void controleLineTest_LineA_KO_Direction() {
        EtatLineModel result = treasureHuntFileServices.controleLine("A - Lara - 1 - 1 - F - AADADAGGA");
        assertFalse(result.isOk());
        assertThat(result.line()).isEqualTo("Line type A: field at index 4 is Invalid cardinal point (value: F)");
    }


    @Test
    void controleLineTest_LineA_KO_Move() {
        EtatLineModel result = treasureHuntFileServices.controleLine("A - Lara - 1 - 1 - S - AADKDAGGA");
        assertFalse(result.isOk());
        assertThat(result.line()).isEqualTo("Line type A: field at index 5 is Invalid move point (value: K)");
    }

    @Test
    void playTest_OK() {
        List<String> waiting = new ArrayList<>();
        waiting.add("C-3-4");
        waiting.add("M-1-0");
        waiting.add("M-2-1");
        waiting.add("T-1-3-2");
        waiting.add("A-Lara-0-3-S-3");

        List<String> result = treasureHuntFileServices.play(List.of(Line_A_OK, Line_M_OK, Line_M_OK_2, Line_T_OK_2, Line_C_OK, Line_T_OK));
        assertNotNull(result);
        assertThat(result).containsExactlyElementsOf(waiting);

    }
}
