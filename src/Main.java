import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {

		InputStreamReader is = new InputStreamReader(System.in);
		BufferedReader bis = new BufferedReader(is);

		ChordCalculator calc = new ChordCalculator();

		System.out.println("usage: 'Chord' or 'Chord+n'");
		System.out.println("example: 'C#m+3'");
		String line = "";
		while ((line = bis.readLine()) != null) {
			line = line.trim();
			String chordMatch = "[CDEFGAB](#|b)?([a-zA-Z#]*[0-9]*)*";
			try {
				if (line.matches((chordMatch + "( *)\\+( *)[0-9]+"))) {
					// SUM
					String[] parts = line.split("\\+");
					String chord = parts[0];
					int n = Integer.parseInt(parts[1]);
					String newchord = calc.addSemitones(chord, n);
					System.out.println(newchord + "\n");
				} else if (line.matches((chordMatch + "( *)-( *)?[0-9]+"))) {
					// SUBTRACT
					String[] parts = line.split("-");
					String chord = parts[0];
					int n = Integer.parseInt(parts[1]);
					String newchord = calc.subtractSemitones(chord, n);
					System.out.println(newchord + "\n");
				} else if (line.matches("([CDEFGAB](#|b)?) ([CDEFGAB](#|b)? ?)+")) {
					// CHORD FROM NOTES
					List<String> notes = Arrays.asList(line.split(" "));
					System.out.println(calc.getChordFromComposition(notes) + "\n");
				} else { // if (line.matches((chordMatch))) {
					/// COMPOSITION
					String chord = line;
					List<String> list = null;
					list = calc.getComposition(chord);

					// for debugging
					System.out.println("Root = " + calc.getRootNote(chord));
					System.out.println("Symbols = " + calc.getSymbols(chord, true));

					String composition = "";
					for (String note : list)
						composition += note + " ";
					System.out.println("= " + composition + "\n");
				}
			} catch (UnknownNoteException e) {
				System.out.println(e.getMessage() + "\n");
			}
		}
	}
}
