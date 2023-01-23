package lt.tomexas.sbfarming;

import java.util.HashMap;
import java.util.Map;

public class Dictionary {

    Map<String, String> letters = new HashMap<>();

    public Dictionary () {
        letters.put("A", "\uE172");
        letters.put("Ą", "\uE173");
        letters.put("B", "\uE174");
        letters.put("C", "\uE175");
        letters.put("Č", "\uE176");
        letters.put("D", "\uE177");
        letters.put("E", "\uE178");
        letters.put("Ę", "\uE179");
        letters.put("Ė", "\uE180");
        letters.put("F", "\uE181");
        letters.put("G", "\uE182");
        letters.put("H", "\uE183");
        letters.put("I", "\uE184");
        letters.put("Į", "\uE185");
        letters.put("Y", "\uE186");
        letters.put("J", "\uE187");
        letters.put("K", "\uE188");
        letters.put("L", "\uE189");
        letters.put("M", "\uE190");
        letters.put("N", "\uE191");
        letters.put("O", "\uE192");
        letters.put("P", "\uE193");
        letters.put("R", "\uE194");
        letters.put("S", "\uE195");
        letters.put("Š", "\uE196");
        letters.put("T", "\uE197");
        letters.put("U", "\uE198");
        letters.put("Ų", "\uE199");
        letters.put("Ū", "\uE200");
        letters.put("V", "\uE201");
        letters.put("Z", "\uE202");
        letters.put("Ž", "\uE203");

        letters.put("0", "\uE204");
        letters.put("1", "\uE205");
        letters.put("2", "\uE206");
        letters.put("3", "\uE207");
        letters.put("4", "\uE208");
        letters.put("5", "\uE209");
        letters.put("6", "\uE210");
        letters.put("7", "\uE211");
        letters.put("8", "\uE212");
        letters.put("9", "\uE213");

        letters.put(" ", "\uF823");
        letters.put(".", "\uE214");
        letters.put(":", "\uE216");
        letters.put("[", "\uE217");
        letters.put("]", "\uE218");
        letters.put("|", "\uE263");
        letters.put("•", "\uE264");

        letters.put("a", "\uE219");
        letters.put("ą", "\uE220");
        letters.put("b", "\uE221");
        letters.put("c", "\uE222");
        letters.put("č", "\uE223");
        letters.put("d", "\uE224");
        letters.put("e", "\uE225");
        letters.put("ę", "\uE226");
        letters.put("ė", "\uE227");
        letters.put("f", "\uE228");
        letters.put("g", "\uE229");
        letters.put("h", "\uE230");
        letters.put("i", "\uE231");
        letters.put("į", "\uE232");
        letters.put("y", "\uE233");
        letters.put("j", "\uE234");
        letters.put("k", "\uE235");
        letters.put("l", "\uE236");
        letters.put("m", "\uE237");
        letters.put("n", "\uE238");
        letters.put("o", "\uE239");
        letters.put("p", "\uE240");
        letters.put("r", "\uE241");
        letters.put("s", "\uE242");
        letters.put("š", "\uE243");
        letters.put("t", "\uE244");
        letters.put("u", "\uE245");
        letters.put("ų", "\uE246");
        letters.put("ū", "\uE247");
        letters.put("v", "\uE248");
        letters.put("z", "\uE249");
        letters.put("ž", "\uE250");

        letters.put("X", "\uE215");
        letters.put("x", "\uE251");

    }

    public String getLetter(String letter) {
        return letters.get(letter);
    }
}
