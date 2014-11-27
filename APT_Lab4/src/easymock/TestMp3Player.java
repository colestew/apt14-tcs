package easymock;

/**
 * Excerpted from the book, "Pragmatic Unit Testing"
 * ISBN 0-9745140-1-2
 * Copyright 2003 The Pragmatic Programmers, LLC.  All Rights Reserved.
 * Visit www.PragmaticProgrammer.com
 */

import junit.framework.*;

import java.util.ArrayList;

import static org.easymock.EasyMock.*;

public class TestMp3Player extends TestCase {

	protected Mp3Player mp3;
	protected ArrayList list = new ArrayList();
	protected Mp3Player mockMp3;

	public void setUp() {
		mp3 = new MockMp3Player();
		mockMp3 = createMock(Mp3Player.class);

		list = new ArrayList();
		list.add("Bill Chase -- Open Up Wide");
		list.add("Jethro Tull -- Locomotive Breath");
		list.add("The Boomtown Rats -- Monday");
		list.add("Carl Orff -- O Fortuna");
	}

	/*
	 * EasyMock tests
	 */

	public void testMockPlay() {
		mockMp3.loadSongs(list);
		expect(mockMp3.isPlaying()).andReturn(false);
		mockMp3.play();
		expect(mockMp3.isPlaying()).andReturn(true);
		expect(mockMp3.currentPosition()).andReturn(0.1);
		mockMp3.pause();
		expect(mockMp3.currentPosition()).andReturn(0.1);
		mockMp3.stop();
		expect(mockMp3.currentPosition()).andReturn(0.1);
		replay(mockMp3);
		Mp3Player mp3 = mockMp3;

		mp3.loadSongs(list);
		assertFalse(mp3.isPlaying());
		mp3.play();
		assertTrue(mp3.isPlaying());
		assertTrue(mp3.currentPosition() != 0.0);
		mp3.pause();
		assertTrue(mp3.currentPosition() != 0.0);
		mp3.stop();
		assertEquals(mp3.currentPosition(), 0.0, 0.1);

	}

	public void testMockPlayNoList() {
		expect(mockMp3.isPlaying()).andReturn(false);
		mockMp3.play();
		expect(mockMp3.isPlaying()).andReturn(false);
		expect(mockMp3.currentPosition()).andReturn(0.0);
		mockMp3.pause();
		expect(mockMp3.currentPosition()).andReturn(0.0);
		expect(mockMp3.isPlaying()).andReturn(false);
		mockMp3.stop();
		expect(mockMp3.currentPosition()).andReturn(0.0);
		expect(mockMp3.isPlaying()).andReturn(false);
		replay(mockMp3);
		
		Mp3Player mp3 = mockMp3;
		
		// Don't set the list up
		assertFalse(mp3.isPlaying());
		mp3.play();
		assertFalse(mp3.isPlaying());
		assertEquals(mp3.currentPosition(), 0.0, 0.1);
		mp3.pause();
		assertEquals(mp3.currentPosition(), 0.0, 0.1);
		assertFalse(mp3.isPlaying());
		mp3.stop();
		assertEquals(mp3.currentPosition(), 0.0, 0.1);
		assertFalse(mp3.isPlaying());
	}

	public void testMockAdvance() {
		mockMp3.loadSongs(list);
		mockMp3.play();
		expect(mockMp3.isPlaying()).andReturn(true);
		mockMp3.prev();
		expect(mockMp3.currentSong()).andReturn((String)list.get(0));
		expect(mockMp3.isPlaying()).andReturn(true);
		mockMp3.next();
		expect(mockMp3.currentSong()).andReturn((String)list.get(1));
		mockMp3.next();
		expect(mockMp3.currentSong()).andReturn((String)list.get(2));
		mockMp3.prev();
		mockMp3.next();
		expect(mockMp3.currentSong()).andReturn((String)list.get(1));
		mockMp3.next();
		expect(mockMp3.currentSong()).andReturn((String)list.get(2));
		mockMp3.next();
		expect(mockMp3.currentSong()).andReturn((String)list.get(3));
		mockMp3.next();
		expect(mockMp3.currentSong()).andReturn((String)list.get(3));
		expect(mockMp3.isPlaying()).andReturn(true);
		replay(mockMp3);
		Mp3Player mp3 = mockMp3;

		mp3.loadSongs(list);

		mp3.play();

		assertTrue(mp3.isPlaying());

		mp3.prev();
		assertEquals(mp3.currentSong(), list.get(0));
		assertTrue(mp3.isPlaying());

		mp3.next();
		assertEquals(mp3.currentSong(), list.get(1));
		mp3.next();
		assertEquals(mp3.currentSong(), list.get(2));
		mp3.prev();

		assertEquals(mp3.currentSong(), list.get(1));
		mp3.next();
		assertEquals(mp3.currentSong(), list.get(2));
		mp3.next();
		assertEquals(mp3.currentSong(), list.get(3));
		mp3.next();
		assertEquals(mp3.currentSong(), list.get(3));
		assertTrue(mp3.isPlaying());
	}

	/*
	 * Old tests below from book below
	 */

	public void testPlay() {

		mp3.loadSongs(list);
		assertFalse(mp3.isPlaying());
		mp3.play();
		assertTrue(mp3.isPlaying());
		assertTrue(mp3.currentPosition() != 0.0);
		mp3.pause();
		assertTrue(mp3.currentPosition() != 0.0);
		mp3.stop();
		assertEquals(mp3.currentPosition(), 0.0, 0.1);

	}

	public void testPlayNoList() {

		// Don't set the list up
		assertFalse(mp3.isPlaying());
		mp3.play();
		assertFalse(mp3.isPlaying());
		assertEquals(mp3.currentPosition(), 0.0, 0.1);
		mp3.pause();
		assertEquals(mp3.currentPosition(), 0.0, 0.1);
		assertFalse(mp3.isPlaying());
		mp3.stop();
		assertEquals(mp3.currentPosition(), 0.0, 0.1);
		assertFalse(mp3.isPlaying());
	}

	public void testAdvance() {

		mp3.loadSongs(list);

		mp3.play();

		assertTrue(mp3.isPlaying());

		mp3.prev();
		assertEquals(mp3.currentSong(), list.get(0));
		assertTrue(mp3.isPlaying());

		mp3.next();
		assertEquals(mp3.currentSong(), list.get(1));
		mp3.next();
		assertEquals(mp3.currentSong(), list.get(2));
		mp3.prev();

		assertEquals(mp3.currentSong(), list.get(1));
		mp3.next();
		assertEquals(mp3.currentSong(), list.get(2));
		mp3.next();
		assertEquals(mp3.currentSong(), list.get(3));
		mp3.next();
		assertEquals(mp3.currentSong(), list.get(3));
		assertTrue(mp3.isPlaying());
	}

}