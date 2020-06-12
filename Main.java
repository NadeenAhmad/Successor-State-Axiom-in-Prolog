import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

	public static void GenGrid(String grid, String path) throws IOException {
		// The input string grid is split in the ";"
		String data[] = grid.split(";");
		// The first part of the string is the dimensions of the grid
		String dimension[] = data[0].split(",");
		int m = Integer.parseInt(dimension[0]);
		int n = Integer.parseInt(dimension[1]);
		// The second part is the initial location of ironman
		String playerLocation[] = data[1].split(",");
		int playerx = Integer.parseInt(playerLocation[0]);
		int playery = Integer.parseInt(playerLocation[1]);
		Position ironman = new Position(playerx, playery);
		// The third part is the location of Thanos
		String enemyLocation[] = data[2].split(",");
		int enemyx = Integer.parseInt(enemyLocation[0]);
		int enemyy = Integer.parseInt(enemyLocation[1]);
		Position thanos = new Position(enemyx, enemyy);
		// The forth part stones locations
		String stonesLocation[] = data[3].split(",");
		String S = data[3];
		ArrayList<Position> stones = new ArrayList<>();
		for (int i = 0; i < 8; i += 2) {
			int stonex = Integer.parseInt(stonesLocation[i]);
			int stoney = Integer.parseInt(stonesLocation[i + 1]);
			Position stone = new Position(stonex, stoney);
			stones.add(stone);
		}
		// creating a new file in the directory given in the method to fill in
		// it the knowledge bas of the agent
		FileWriter writer = new FileWriter(path);
		// writing the dimensions of the grid in a separate predicate
		writer.write("grid(" + m + "," + n + ").\n");
		// writing a predicate for the location of thanos
		writer.write("thanos(" + thanos.x + "," + thanos.y + ").\n");
		// writing a predicate that will create a list with the location of the
		// 4 stones
		writer.write("stones(X):- \n");
		String list = "X = [ ";
		for (int i = 0; i < stones.size() - 1; i++) {
			list += "(" + stones.get(i).x + "," + stones.get(i).y + "), ";
		}
		list += "(" + stones.get(stones.size() - 1).x + ","
				+ stones.get(stones.size() - 1).y + ") ";
		list += "].";
		writer.write(list + "\n");
		// writing a predicate for the initial situation of ironman
		// containing initial location and initial situation s0
		// an empty list to save the locations of the collected stones
		writer.write("ironman((" + ironman.x + "," + ironman.y + "),[],s0).\n");
		writer.close();
	}

	
	
	public static void main(String[] args) throws IOException {
		GenGrid("5,5;1,2;3,4;1,1,2,1,2,2,3,3", "C:/Users/nadeen/Desktop/KB1.pl");
		GenGrid("5,5;2,2;0,4;2,1,1,1,1,2,1,3", "C:/Users/nadeen/Desktop/KB2.pl");

	}

}
