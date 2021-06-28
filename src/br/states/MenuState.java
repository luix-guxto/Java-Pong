package br.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Random;

import br.Game;

public class MenuState implements State {

	Random random = new Random();
	private String[] options = { "START", "HELP", "EXIT" };
	private Font font1;
	private Font font2;
	private int choice = 0;
	private int x = 0, y = 0, movex = random.nextInt(9)+1, movey = random.nextInt(9)+1;
	
	@Override
	public void init() {
		font1= new Font("Dialog", Font.PLAIN, 50);
		font2= new Font("Dialog", Font.PLAIN, 25);
	}
	
	private void limits() {
		if (x+15 > Game.WIDTH) {
			movex *= -1;
		}
		if (x < 0) {
			movex *= -1;
		}
		if(y+15 > Game.HEIGTH) {
			movey *= -1;
		}
		if(y < 0) {
			movey *= -1;
		}
		
		
	}

	@Override
	public void update() {
		x += movex;
		y += movey;
		limits();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGTH);
		
		g.setColor(Color.WHITE);
		g.setFont(font1);
		g.drawString("PONG", 
				Game.WIDTH/2 - g.getFontMetrics().stringWidth("PONG")/2, 
				Game.HEIGTH * 1/4
				);
		g.setFont(font2);
		for(int i = 0; i < options.length; i++) {
			g.setColor(Color.white);
			if(i==choice) {
				g.setColor(Color.YELLOW);
			}
			g.drawString(options[i], 
					Game.WIDTH/2 - g.getFontMetrics().stringWidth(options[i])/2, 
					Game.HEIGTH*3/4+g.getFontMetrics(font2).getHeight()*i);
			
		}
		
		g.setColor(Color.WHITE);
		g.fillRect(x, y, 10, 10);
	}

	@Override
	public void KeyPress(int cod) {
		// TODO Auto-generated method stub

	}

	@Override
	public void KeyReleased(int cod) {
		if(cod == KeyEvent.VK_W || cod == KeyEvent.VK_UP) {
			choice--;
			if(choice<0) {
				choice= options.length-1;
			}
		}if(cod == KeyEvent.VK_S || cod == KeyEvent.VK_DOWN) {
			choice++;
			if(choice>options.length-1) {
				choice = 0;
			}
		}if(cod == KeyEvent.VK_ENTER) {
			select();
		}if(cod == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
		
	}

	private void select() {
		switch (choice) {
		
		case 0:
			StateManager.setStade(StateManager.LEVEL1);
			break;
			
		case 1:
			StateManager.setStade(StateManager.FPS);
			break;
			
		case 2:
			System.exit(0);
			break;
			
			
		default:
			break;
		}
	}

}
