package br.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import br.Game;

public class FPSState implements State {

	private long now, lastTime = System.nanoTime();
	private double timer = 0;
	private int tick = 0;
	private int t;
	
	@Override
	public void init() {
		
	}

	@Override
	public void update() {
		now = System.nanoTime();
		timer += now - lastTime;
		lastTime = now;
		tick+=1;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGTH);
		if(timer >= 1000000000) {
			t=tick;
			tick=0;
			timer=0;
		}
		g.setColor(Color.WHITE);
		Font font = new Font("Serif", Font.PLAIN, 50);
		g.setFont(font);
		String text = "FPS: "+ t;
		int xText = Game.WIDTH/2-g.getFontMetrics().stringWidth(text)/2;
		int yText = Game.HEIGTH/2-g.getFontMetrics(font).getHeight()/2;
		g.drawString(text, xText, yText);
		Font font2 = new Font("Dialog", Font.PLAIN, 25);
		g.setFont(font2);
		String text2 = "Press ESC to return!";
		int xText2 = Game.WIDTH/2-g.getFontMetrics().stringWidth(text2)/2;
		int yText2 = Game.HEIGTH/2+g.getFontMetrics(font2).getHeight()/2;
		g.drawString(text2, xText2, yText2);
	}

	@Override
	public void KeyPress(int cod) {
		System.out.println("Press "+cod);
		
	}

	@Override
	public void KeyReleased(int cod) {
		System.out.println("Release "+cod);
		if(cod == KeyEvent.VK_ESCAPE) {
			StateManager.setStade(StateManager.MENU);
		}
	}

}
