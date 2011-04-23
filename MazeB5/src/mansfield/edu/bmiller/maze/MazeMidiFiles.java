package mansfield.edu.bmiller.maze;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * MVC Model: retrieves a midi music filename.
 * 
 * License: CC BY-SA 3.0 http://creativecommons.org/licenses/by-sa/3.0/
 * 
 * @author John Phillips
 */
public class MazeMidiFiles {
	private ArrayList<String> midiFileList;
	private int index;
	private static Random gen = new Random();

	public MazeMidiFiles() {
		midiFileList = new ArrayList<String>();
		createFileList();
		midiFileList.trimToSize();
		//randomSong();
		index=0;
	}

	private void createFileList() {
		Scanner sc = new Scanner(
				MazeMidiFiles.class.getResourceAsStream("/midilist.txt"));
		while (sc.hasNextLine())
			midiFileList.add(sc.nextLine());
	}

	public ArrayList<String> getFileList() {
		return midiFileList;
	}

	public String getMidiFileName() {
		return midiFileList.get(index);
	}

	public void randomSong() {
		index = gen.nextInt(midiFileList.size());
	}

	public void nextSong() {
		if (index < midiFileList.size() - 1)
			++index;
	}

	public void lastSong() {
		if (index > 0)
			--index;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int i) {
		index = i;
	}

	@Override
	public String toString() {
		return "midi song: " + getMidiFileName();
	}
}
