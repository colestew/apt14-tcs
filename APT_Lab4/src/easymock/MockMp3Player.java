package easymock;

import java.util.ArrayList;
import java.util.List;

public class MockMp3Player implements Mp3Player {
	private boolean isPlaying = false;
	private double position = 0.0;
	private List<String> songs;
	private int currSong = 0;

	@Override
	public void play() {
		if (songs != null) {
			isPlaying = true;
			position = 0.1;
		}
	}

	@Override
	public void pause() {
		isPlaying = false;
	}

	@Override
	public void stop() {
		songs = null;
		isPlaying = false;
		position = 0.0;
	}

	@Override
	public double currentPosition() {
		return position;
	}

	@Override
	public String currentSong() {
		return songs.get(currSong);
	}

	@Override
	public void next() {
		position = 0.0;
		currSong += 1;
		if (currSong == songs.size())
			--currSong;
	}

	@Override
	public void prev() {
		if (position == 0.0) {
			currSong -= 1;
			if (currSong == -1)
				++currSong;
		} else {
			position = 0.0;
		}
	}

	@Override
	public boolean isPlaying() {
		return isPlaying;
	}

	@Override
	public void loadSongs(ArrayList names) {
		songs = names;
		currSong = 0;
	}

}
