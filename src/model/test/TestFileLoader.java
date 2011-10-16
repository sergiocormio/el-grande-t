package model.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import model.Utils;
import model.dto.Player;
import model.dto.StatisticalInformation;
import model.exceptions.FileBadFormedException;
import model.loaders.PlayersLoader;
import model.loaders.StatsInformationLoader;
import junit.framework.TestCase;

public class TestFileLoader extends TestCase{
	
	public static final String DB_FILENAME = "src/resources/test/LP_TEST.txt";
	public static final String RT_FILENAME = "src/resources/test/resultantTeam.txt";
	
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
		
	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}
	

}
