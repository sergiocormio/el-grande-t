package model.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import model.dto.Player;
import model.dto.ResultantTeam;
import model.dto.Skill;
import model.dto.StatisticalInformation;
import model.dto.UserInputData;
import model.dto.Player.Position;
import model.exceptions.InvalidModelException;
import model.exceptions.SkillAlreadyExistsException;
import model.lpsolve.LPSolveAdapter;

public class TestLPSolveAdapter extends TestCase {

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		
		
		
	}

	public void testLPSolveAdapter() throws InvalidModelException, SkillAlreadyExistsException{
		
		
		List<Player> playersDataBase = createDBMock();
		UserInputData userInputData = createUserInputDataMock();
		
		ResultantTeam resultantTeam = LPSolveAdapter.runModel(userInputData, playersDataBase);
		
		StatisticalInformation information = resultantTeam.getStatisticalInformation();
		
		assertEquals(information.getFinalCost(), 11);
		assertEquals(information.getTotalProfit(), 110);
		
		List<Player> players = resultantTeam.getPlayers();
		
		assertEquals(players.size(), 11);
		int i = 1;
		for (Player player : players) {
			
			assertEquals(player.getSkill("ATAQUE").getValue() , 10 );

		}
		
		assertEquals(players.get(0).getName() , "1" );
		assertEquals(players.get(1).getName() , "101" );
		assertEquals(players.get(2).getName() , "102" );
		assertEquals(players.get(3).getName() , "103" );
		assertEquals(players.get(4).getName() , "104" );
		assertEquals(players.get(5).getName() , "201" );
		assertEquals(players.get(6).getName() , "202" );
		assertEquals(players.get(7).getName() , "203" );
		assertEquals(players.get(8).getName() , "204" );
		assertEquals(players.get(9).getName() , "301" );
		assertEquals(players.get(10).getName() , "302" );
	
	}
	
	
	private UserInputData createUserInputDataMock() {
		UserInputData data = new UserInputData();

		data.setBudget(20);
		data.setFormation("4-4-2");
		data.setNumberOfPlayers(11);
		data.setSkillToMax("ATAQUE");
		
		return data;
	}

	private List<Player> createDBMock() throws SkillAlreadyExistsException {
		List<Player> list = new ArrayList<Player>();
		for (int id = 1; id <= 400; id++) {
			
			Position pos = null;
			int skillvalue = 0;
			
			if(id <= 100){
				pos = Position.ARQ;
				if(id < 2)
					skillvalue = 10;
			}
			else if (id > 100 && id <= 200){
				pos = Position.DEF;
				if(id <= 104)
					skillvalue = 10;
			}
			else if (id > 200 && id <= 300){
				pos = Position.VOL;
				if(id <= 204)
					skillvalue = 10;
			}
			else if (id > 300 && id <= 400){
				pos = Position.DEL;
				if(id <= 302)
					skillvalue = 10;
			}
			
			Player player = createPlayerMock(id, pos, 1, skillvalue);
			list.add(player);
		}
		return list;
	}

	private Player createPlayerMock(int id, Position pos, long price, int skillValue) throws SkillAlreadyExistsException {
		
		Player player = new Player(String.valueOf(id), pos, price);
		
		Skill skill = new Skill();
		skill.setName("ATAQUE");
		skill.setValue(skillValue);
		
		player.addSkill(skill);
		
		return player;
	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}
	
	

}
