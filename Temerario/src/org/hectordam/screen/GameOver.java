package org.hectordam.screen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.hectordam.temerario.Juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;

public class GameOver implements Screen{

	Juego juego;
	private Texture fondo1;
	private Texture fondo2;
	
	Connection connection = null;
	List<String> scores;
	
	public GameOver(Juego juego){
		
		this.juego = juego;
		
		fondo1 = new Texture(Gdx.files.internal("fuegos.png"));
		fondo2 = new Texture(Gdx.files.internal("ambulancia.png"));
		
		conectarDatos();
		try {
			consulta();
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		juego.batch.begin();
		if(juego.puntos > 0){
			juego.batch.draw(fondo1, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
		else{
			juego.batch.draw(fondo2, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
		
		juego.font.draw(juego.batch, "Mejores puntuaciones ", 250, 500);
		int pos = 470;
		for(int i = 0; i < scores.size(); i++){
			juego.font.draw(juego.batch, scores.get(i) + " puntos", 250, pos);
			pos -= 20;
		}
		
		juego.font.draw(juego.batch, "Has conseguido " + juego.puntos + " puntos", 30, 70);
		juego.font.draw(juego.batch, "Has conseguido cruzar " + juego.salvados + " veces", 30, 50);
		juego.font.draw(juego.batch, "Te han pillado " + juego.perdidos + " veces", 30, 30);
		juego.font.draw(juego.batch, "Pulsa 'ENTER' para volver a jugar", 350, 60);
		juego.font.draw(juego.batch, "Pulsa 'ESCAPE' para salir", 350, 40);
		juego.batch.end();
		
		handleInput();
	}
	
	private void handleInput() {
		
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			juego.dispose();
			System.exit(0);
		}
		else if (Gdx.input.isKeyPressed(Keys.ENTER)) {
			
			juego.tiempo = 100;
			juego.salvados = 0;
			juego.perdidos = 0;
			juego.puntos = 0;
			juego.setScreen(new PantallaJuego(juego));
		}
	}
	
	private void conectarDatos(){
		
		try {
			Class.forName("org.sqlite.JDBC");
			
			connection = DriverManager.getConnection("jdbc:sqlite:" + Gdx.files.internal("puntuaciones.db"));
		
			Statement statement = connection.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS puntuaciones (puntuacion int)");
			statement.executeUpdate("INSERT INTO puntuaciones (puntuacion) VALUES (" + juego.puntos + ")");
			
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
	}
	
	private void consulta() throws SQLException, ClassNotFoundException{
		
		Class.forName("org.sqlite.JDBC");
		
		connection = DriverManager.getConnection("jdbc:sqlite:" + Gdx.files.internal("puntuaciones.db"));
		
		Statement statement = connection.createStatement();
		ResultSet res = statement.executeQuery("SELECT puntuacion FROM puntuaciones ORDER BY puntuacion DESC LIMIT 5");
		scores = new ArrayList<String>();

		while (res.next()) {
			scores.add(Integer.toString(res.getInt("puntuacion")));
		}
		
		if (statement != null)
			statement.close();
		if (res != null)
			res.close();
		if (connection != null)
			connection.close();
	}
	
	@Override
	public void dispose() {
		
		
	}
	
	@Override
	public void show() {
		
		
	}

	@Override
	public void resize(int width, int height) {		
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
}
