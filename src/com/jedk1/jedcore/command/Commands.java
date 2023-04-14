package com.jedk1.jedcore.command;


import com.jedk1.jedcore.scoreboard.BendingBoard;

public class Commands {

	public Commands() {
		if (BendingBoard.enabled) {
			new BoardCommand();
		}
		new JedCoreCommand();
	}
}
