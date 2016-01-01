package br.com.joaoparana;

import java.util.ArrayList;
import java.util.List;

public class DuckType {
	static interface Action {
		public int act();
		public String describe();
	}

	public void example() {
		List<Action> actionList = new ArrayList<>();
		String example = "example";

		actionList.add(new Action() {
			public int act() {
				return example.length();
			}

			public String describe() {
				return "Action: " + example;
			}
		});

		int qtd = forEachAction(actionList);
		System.out.println("actionList size = " + qtd);
	}

	public int forEachAction(List<Action> actionList) {
		int total = 0;
		for (Action a : actionList) {
			System.out.println(a.describe());
			total += a.act();
		}
		return total;
	}
}
