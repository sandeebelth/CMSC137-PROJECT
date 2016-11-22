package game.scene;


import game.components.Character;
import game.components.Map;
import game.network.GameClient;
import game.network.GameServer;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.ShapeRenderer;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainGame extends BasicGame {
    private Map testMap;
    private ArrayList<Rectangle> collisions = new ArrayList<>();
    private float x = 0, y = 0;
    private float centerX, centerY;

    private GameClient gameClient;
    private Character mainPlayer;
    //TODO find way to decouple playerBox
    private Rectangle playerBox;
    private HashMap<String, Character> characterMap = new HashMap<>();
    private UnicodeFont font;
    private TextField textField;
    private LinkedList<String> messages = new LinkedList<>();

    private MainGame(String gameName, InetSocketAddress serverAddress, int clientPort) throws IOException, ClassNotFoundException, SlickException {
        super(gameName);
        gameClient = new GameClient(serverAddress, clientPort, this);
    }


    public void newMessage(String message) {
        messages.addFirst(message);
        if (messages.size() == 6) {
            messages.removeLast();
        }
        return;
    }
    private void moveMap(float x, float y) {
        this.x = centerX - x;
        this.y = centerY - y;
    }

    public void movePlayer(String name, float x, float y) {
        Character ch = characterMap.get(name);
        if (ch != null) {
            ch.setCoordinate(x, y);
            Log.debug(name + " moved");
        } else {
            Log.debug(name + " not found");
        }
    }


    //The loading of the resources should be done in the init() method
    @Override
    public void init(GameContainer gc) throws SlickException {
        testMap = new Map("Assets/Maps/test.tmx");
        collisions.addAll(testMap.getCollisions());
        mainPlayer = new Character(gc.getWidth() / 2f - 16f, gc.getHeight() / 2f - 18f, 32, 36, "Assets/Art/rpgsprites1/warrior_f.png");
        playerBox = mainPlayer.getHitBox();

        centerX = gc.getWidth()/2f;
        centerY = gc.getHeight()/2f;
        moveMap(2*32, 2*32);
        mainPlayer.move(2*32, 2*32);

        Character.setOrigin(centerX, centerY);
        gameClient.getUsers();

        font = new UnicodeFont("arial.ttf", 15, false, false);
        textField = new TextField(gc, font, 0, gc.getHeight() - 30, 300, 50);

        font.addAsciiGlyphs();
        font.getEffects().add(new ColorEffect(java.awt.Color.white));
        gc.setDefaultFont(font);
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        float dX = 0, dY = 0;
        Input input = gc.getInput();
        float playerX = mainPlayer.getX(),
                playerY = mainPlayer.getY();
        if (input.isKeyDown(Input.KEY_UP)) {
            mainPlayer.setMovementDirection(Character.UP);
            mainPlayer.updateDelta(delta);
            // The lower the delta the slowest the sprite will animate.
            dY += delta * 0.1f;
        } else if (input.isKeyDown(Input.KEY_DOWN)) {
            mainPlayer.setMovementDirection(Character.DOWN);
            mainPlayer.updateDelta(delta);
            dY -= delta * 0.1f;
        } else if (input.isKeyDown(Input.KEY_LEFT)) {
            mainPlayer.setMovementDirection(Character.LEFT);
            mainPlayer.updateDelta(delta);
            dX += delta * 0.1f;
        } else if (input.isKeyDown(Input.KEY_RIGHT)) {
            mainPlayer.setMovementDirection(Character.RIGHT);
            mainPlayer.updateDelta(delta);
            dX -= delta * 0.1f;
        } else if (input.isKeyDown(Input.KEY_ENTER)) {
            String toSend = textField.getText();
            if (toSend.length() > 0) {
                gameClient.sendMessage(toSend);
                textField.setText("");
            }
        }
        playerBox.setX(playerX - dX);
        playerBox.setY(playerY - dY);
        boolean isInCollision = false;
        for (Rectangle ret: collisions){
            if (playerBox.intersects(ret)) {
                isInCollision = true;
            }
        }

        if (!isInCollision && ((dX <= -0.1f || dX >= 0.1f) || (dY <= -0.1f || dY >= 0.1f))) {
            //x += dX;
            //y += dY;
            playerX -= dX;
            playerY -= dY;
            //moveMap(playerX, playerY);
            gameClient.move(playerX, playerY);
            mainPlayer.move(-dX, -dY);
            moveMap(mainPlayer.getX(), mainPlayer.getY());
            Character.setOrigin(x, y);
        }

        for (Character ch : characterMap.values()) {
            ch.updateDelta(delta);
        }
        //Log.debug(" X: " + playerX + " Y: " + playerY);

        font.loadGlyphs();
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        g.setFont(font);
        testMap.renderBottom((int) x, (int) y);
        mainPlayer.draw(gc.getWidth() / 2, gc.getHeight() / 2);
        testMap.renderUpper((int)x, (int)y);
        g.setColor(Color.white);
        textField.render(gc, g);
        for(int ii = 0; ii < 5 && ii < messages.size(); ii++) {
            g.drawString(messages.get(ii), 20, gc.getHeight() - ii*30 - 60);
        }

        ShapeRenderer.draw(playerBox);
        g.setColor(Color.blue);
        collisions.forEach(ShapeRenderer::draw);
        g.setColor(Color.red);
        for(Character ch : characterMap.values()) {
            ch.draw();
            ShapeRenderer.draw(ch.getHitBox());
        }

    }

    public void addCharacter(String name, Character character) {
        characterMap.put(name, character);
        Log.debug("added " + name);
    }


    public static void main(String[] args) {
        if (args.length < 1 || (args[0].equals("server") && args.length < 3) ||
                (args[0].equals("client") && args.length < 4)) {
            System.err.println("Usage: java program {server <server port> <client port> | client <server ip> <server port> <client port>");
            return;
        }
        GameServer gameServer;

        InetSocketAddress serverAddress;
        int clientPort;
        if(args[0].equals("server")) {
            gameServer = new GameServer(Integer.parseInt(args[1]));
            serverAddress = new InetSocketAddress("localhost", Integer.parseInt(args[1]));
            clientPort = Integer.parseInt(args[2]);
        } else {
            serverAddress = new InetSocketAddress(args[1], Integer.parseInt(args[2]));
            clientPort = Integer.parseInt(args[3]);
        }

        try {
            AppGameContainer appgc;
            MainGame game = new MainGame("Simple Slick Game", serverAddress, clientPort);
            appgc = new AppGameContainer(game);
            appgc.setDisplayMode(640, 480, false);
            appgc.start();
        } catch (SlickException ex) {
            Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
