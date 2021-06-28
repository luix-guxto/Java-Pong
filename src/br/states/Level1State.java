package br.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;

import br.Game;
import br.audio.AudioPlayer;
import br.input.KeyManager;

public class Level1State implements State {

	private Rectangle ball = new Rectangle(Game.WIDTH / 2 - 5, Game.HEIGTH / 2 - 5, 10, 10);
	
	private Rectangle p1 = new Rectangle(0, 0, 10, 50);
	private Rectangle p2 = new Rectangle(Game.WIDTH, 0, 10, 50);
	
	private Rectangle rede = new Rectangle(Game.WIDTH / 2 - 2, 0, 4, Game.HEIGTH);
	
	private int movex = 2, movey = 2;
	private int pt1 = 0, pt2 = 0;
	private AudioPlayer pong, music;
	private Font font;
	
	public Level1State() {
		pong = new AudioPlayer("/audio/pong.mp3");
		music = new AudioPlayer("/audio/theblackframe.mp3");
	}
	
	@Override
	public void init() {
		font = new Font("Dialog", Font.PLAIN, 40);
		start();
		music.start();
	}
	public void start() {
		ball.x = Game.WIDTH/2-ball.width/2;
		ball.y = Game.HEIGTH/2-ball.height/2;
		Random r = new Random();
		int rm = (r.nextInt(2)==0)? 1 : -1;
		movex *= rm;
	}

	@Override
	public void update() {
		ball.x += movex;
		ball.y += movey;
		
		if(KeyManager.w) {
			p1.y -= 8;
		}
		if(KeyManager.s) {
			p1.y += 8;
		}
		if(KeyManager.up) {
			p2.y -= 8;
		}
		if(KeyManager.down) {
			p2.y += 8;
		}
		
		limitsBall();
		limitsPlayers();
	}
	private void limitsBall() {
		if (ball.x - ball.width > Game.WIDTH) {
			movex *= -1;
			pt1++;
			start();
		}
		if (ball.x < 0) {
			movex *= -1;
			pt2++;
			start();
		}
		if(ball.y + ball.height > Game.HEIGTH) {
			movey *= -1;
			
			pong.start();
		}
		if(ball.y < 0) {
			movey *= -1;
			pong.start();
		}
		
		if(p1.intersects(ball) || p2.intersects(ball)) {
			movex *= -1;
			pong.start();
		}
	}
	
	private void limitsPlayers() {
		if(p1.y < 0) {
			p1.y = 0;
		}
		if(p1.y > Game.HEIGTH-p1.height) {
			p1.y = Game.HEIGTH-p1.height;
		}
		if(p2.y < 0) {
			p2.y = 0;
		}
		if(p2.y > Game.HEIGTH-p2.height) {
			p2.y =Game.HEIGTH-p2.height;
		}
	}

	@Override
	public void render(Graphics g) {
		
		// Fundo
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGTH);
		
		// Rede
		g.setColor(Color.WHITE);
		g.fillRect(rede.x, rede.y, rede.width, rede.height);
		
		// Bola
		g.setColor(Color.CYAN);
		g.fillRect(ball.x, ball.y, ball.width, ball.height);
		
		// Jogador 1
		g.setColor(Color.YELLOW);
		g.fillRect(p1.x, p1.y, p1.width, p1.height);
		
		// Jogador 2
		g.setColor(Color.RED);
		g.fillRect(p2.x-p2.width, p2.y, p2.width, p2.height);
		
		// Pontuação Jogador 1
		g.setColor(Color.WHITE);
		g.setFont(font);
		String text = ""+pt1;
		int lText = g.getFontMetrics().stringWidth(text);
		int gText = g.getFontMetrics(font).getHeight();
		g.drawString(text, Game.WIDTH/2-lText*2, 0 + gText + 2);
		
		// Pontuação Jogador 2
				g.setColor(Color.WHITE);
				g.setFont(font);
				String text2 = ""+pt2;
				int lText2 = g.getFontMetrics().stringWidth(text2);
				int gText2 = g.getFontMetrics(font).getHeight();
				g.drawString(text2, Game.WIDTH/2+lText2, 0 + gText2 + 2);
	}

	@Override
	public void KeyPress(int cod) {
		// TODO Auto-generated method stub

	}

	@Override
	public void KeyReleased(int cod) {
		if(cod == KeyEvent.VK_ESCAPE) {
			music.stop();
			pong.stop();
			StateManager.setStade(StateManager.MENU);
		}
	}

}
