package model.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.List;

import model.Utils;
import model.dto.Player;
import model.dto.PlayersDataBase;
import model.dto.ResultantTeam;
import model.dto.StatisticalInformation;
import model.exceptions.FileBadFormedException;
import model.loaders.PlayersLoader;
import model.loaders.StatsInformationLoader;
import junit.framework.TestCase;

public class TestFileLoader extends TestCase{
	
	public static final String DB_FILENAME = "src/resources/test/LP_TEST.txt";
	public static final String RT_FILENAME = "src/resources/test/resultantTeam.txt";
	public static final String RT_FILENAME_TO_SAVE = "src/resources/test/resultantTeam_TO_SAVE.txt";
	private static final String DB_FILENAME_TO_SAVE = "src/resources/test/LP_TEST_TO_SAVE.txt";
	
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		
		
		
	}

	public void testHeader() throws FileBadFormedException, IOException {
		
		BufferedReader bufferedReader = Utils.getBufferedReader(DB_FILENAME);
		List<String> headers = PlayersLoader.loadHeaders(bufferedReader);
		
		assertEquals(headers.size(), 5);
		
		for (String string : headers) {
			System.out.println(string);
		}
		
	}
	
	public void testData() throws FileBadFormedException, IOException {
		
		BufferedReader bufferedReader = Utils.getBufferedReader(DB_FILENAME);
		List<String> headers = PlayersLoader.loadHeaders(bufferedReader);
		List<Player> players = PlayersLoader.loadData(headers, bufferedReader);
		
		assertEquals(400, players.size());
		
		for (Player player: players) {
			System.out.println(player.getName());
		}
		
	}
	
	
	public void testLoadPlayers() throws FileBadFormedException, IOException {
		
		List<Player> players = PlayersLoader.loadDataBase(DB_FILENAME);
		
		assertEquals(400, players.size());
		
		for (Player player: players) {
			System.out.println(player.getName());
		}
		
	}
	
	public void testSavePlayersDataBase() throws FileBadFormedException, IOException {
		
		BufferedReader bufferedReader = Utils.getBufferedReader(DB_FILENAME);
		List<String> headers = PlayersLoader.loadHeaders(bufferedReader);
		List<Player> players = PlayersLoader.loadData(headers, bufferedReader);
		
		assertEquals(400, players.size());
		
		for (Player player: players) {
			System.out.println(player.getName());
		}
		
		PlayersDataBase dataBase = new PlayersDataBase(headers, players, new File(DB_FILENAME_TO_SAVE));
		
		dataBase.saveToFile();
		
	}

	public void testLoadResultantTeam() throws FileBadFormedException, IOException {
		
		BufferedReader bufferedReader = Utils.getBufferedReader(RT_FILENAME);
		StatisticalInformation information = StatsInformationLoader.load(bufferedReader);
		
		List<String> headers = PlayersLoader.loadHeaders(bufferedReader);
		List<Player> players = PlayersLoader.loadData(headers, bufferedReader);
		
		assertNotNull(information);
		
		assertEquals(11, players.size());
		
		for (Player player: players) {
			System.out.println(player.getName());
		}
		
		bufferedReader.close();
	}
	
	
	public void testSaveResultantTeam() throws FileBadFormedException, IOException {
		
		BufferedReader bufferedReader = Utils.getBufferedReader(RT_FILENAME);
		StatisticalInformation information = StatsInformationLoader.load(bufferedReader);
		
		List<String> headers = PlayersLoader.loadHeaders(bufferedReader);
		List<Player> players = PlayersLoader.loadData(headers, bufferedReader);
		
		assertNotNull(information);
		
		assertEquals(11, players.size());
		
		for (Player player: players) {
			System.out.println(player.getName());
		}
		
		PlayersDataBase dataBase = new PlayersDataBase(headers, players, new File(RT_FILENAME_TO_SAVE));;
		information.getUserInputData().setPlayersDataBase(dataBase);
		
		ResultantTeam team = new ResultantTeam(new File(RT_FILENAME_TO_SAVE), players, information);;
		
		team.saveToFile();
	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}
	

}
