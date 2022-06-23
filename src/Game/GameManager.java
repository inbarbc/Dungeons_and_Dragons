package Game;

import Game.Callbacks.MessageCallback;
import Game.Handelers.InputHandler;
import Game.Handelers.MoveHandler;
import Game.Handelers.TargetHandler;
import Game.Tiles.Units.Enemies.Enemy;
import Game.Tiles.Units.Player.Player;
import Game.Tiles.Units.Unit;

import java.io.File;
import java.io.FilenameFilter;
import java.util.*;

public class GameManager {

    private MessageCallback messageCallback;
    Board board;
    List<Enemy> enemies;
    private int tickCount=0;
    private List<File> levelsFiles=new ArrayList<File>();
    public List<Unit> listTurn=new ArrayList<Unit>();


    // contracture
    public GameManager(MessageCallback m){
        this.messageCallback = m;
        DatabaseUnits.BuildDictionary();
        TargetHandler.gameBoard=this.board;
        MoveHandler.gameBoard=this.board;
        Unit.gameManager=this;
    }

    public void Start(String address) {
        Instruction();
        GetPlayerMenu();
        CreateListOfLevel(address);
        for (File level : levelsFiles) {
            Player player = board.GetPlayer();
            if (player.IsDead())
                break;
            LoadGame(level);
            StartLevel();
        }
        if (board.GetPlayer().IsDead())
            messageCallback.Send("Game Over.");
        else
            messageCallback.Send("You Won.");
    }

    public void LoadGame(File file){
        board.BuildBoard(file);
        listTurn.clear();
        listTurn.add(board.GetPlayer());

        for(Unit enemy:board.enemies){
            listTurn.add(enemy);
        }
    }

    public void OnTick(){
        ListIterator<Unit> iter = listTurn.listIterator();
        while(iter.hasNext()&&!board.GetPlayer().IsDead()){
            Unit unit= iter.next();
            if(unit.IsDead()){
                iter.remove();
                continue;
            }
            unit.Turn(tickCount);
        }
    }

    /**
     * create list of files
     * @param address the dir they are locate
     */
    public void CreateListOfLevel(String address){
        File f = new File(address);
        File[] matchingFiles = f.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return  name.endsWith("txt");
            }
        });
        levelsFiles= Arrays.asList(matchingFiles);
        levelsFiles.sort((File f1,File f2)->f1.getName().compareTo(f2.getName()));
    }

    public void Instruction(){
        messageCallback.Send("*!*!*!*!*!*!*!*!*! D&D-Roguelike !*!*!*!*!*!*!*!*!*");
        messageCallback.Send("*** Game instructions:\n");
        messageCallback.Send("* Game Controls:\n");
        messageCallback.Send(
                "-Move up:\tW\n" +
                        "-Move down:\tS\n" +
                        "-Move right:\tD\n" +
                        "-Move left:\tA\n" +
                        "-Wait:\tQ\n" +
                        "-Attack: Steping on an enemy\n" +
                        "-Cast special Attack:\tE\n");
        messageCallback.Send("* Map description:\n");
        messageCallback.Send("-(.):\t Free space\n" +
                "-(#):\t Wall\n" +
                "-(@):\t Your player\n");
        messageCallback.Send("* Enemies list:\n");
        messageCallback.Send("-(s):\t Lannister Solider\n" +
                "-(k):\t Lannister Knight\n" +
                "-(q):\t Queen’s Guard\n" +
                "-(z):\t Wright\n" +
                "-(b):\t Bear-Wright\n" +
                "-(g):\t Giant-Wright\n" +
                "-(w):\t White Walker\n" +
                "^ Traps:\n" +
                "-(B):\t Bonus Trap\n" +
                "-(Q):\t Queen’s Trap\n" +
                "-(D):\t Death Trap\n" +
                "! Bosses:\n" +
                "-(M):\t The Mountain\n" +
                "-(C):\t Queen Cersei\n" +
                "-(N):\t Night’s King\n");
    }

    private void StartLevel() {
        List<String> message=new ArrayList<String>();
        List<String> msg=new ArrayList<String>();
        tickCount=0;
        while(!board.GetPlayer().IsDead()&&board.enemies.size()!=0)
        {
            PrintLevel();
            message.clear();
            tickCount+=1;
            OnTick();
        }if(board.GetPlayer().IsDead())
            message.add("You Lost");
        PrintLevel();
    }


    //public void RemoveEnemy(Enemy e){
    //    board.Remove(e);
    //    enemies.remove(e);
    //}


    /**
     * get the player choose
     * @return the choosen player
     */
    private void GetPlayerMenu(){
        PrintMenu();
        char choose= InputHandler.InputMenu();
        board.SetPlayer((Player) DatabaseUnits.playerPool.get(choose+"").Copy());
        PrintChoosenPlayer();

    }

    public void PrintLevel() {
        if (board.enemies.size() > 0)
            messageCallback.Send(board.ToString());
            messageCallback.Send(board.GetPlayer().Describe());
    }

    public void PrintMenu(){
        messageCallback.Send("Select player:");
        for(Map.Entry<String, Unit> player : DatabaseUnits.playerPool.entrySet())
            messageCallback.Send(player.getValue().ToString()+"."+player.getValue().Describe());
    }

    public void PrintChoosenPlayer(){
        Player player = board.GetPlayer();
        messageCallback.Send(String.format("You have selected: %s", player.GetName()));
    }
}
