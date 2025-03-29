package SenArchPackage;
import java.util.ArrayList;

public class Checker1v1 {

	public static ArrayList<Marker1v1> checkWin(Marker1v1[][] markers){
		ArrayList<Marker1v1> match;
		
		for (int i = 0; i < Main1v1.SIZE; i++) {
			int x = i % Main1v1.ROWS;
			int y = i / Main1v1.ROWS;
			
			// diagonal bottom left, top right
			match = checkMatch(x, y, 1, -1, i, markers);
			
			// diagonal top left, bottom right
			if(match == null) {
				match = checkMatch(x, y, 1, 1, i, markers);
			}

			// horizontal
			if(match == null) {
				match = checkMatch(x, y, 1, 0, i, markers);
			}

			// vertical
			if(match == null) {
				match = checkMatch(x, y, 0, 1, i, markers);
			}
			
			if(match != null) {
				return match;
			}
			
		}
		
		return null;
	}
	
	private static ArrayList<Marker1v1> checkMatch(int x, int y, int dX, int dY, int index, Marker1v1[][] markers){
		
		ArrayList<Marker1v1> match = new ArrayList<Marker1v1>(Main1v1.MATCH);
		int type = -1;
		int checkCount = 0;
		
		while(checkCount < Main1v1.ROWS && index < Main1v1.SIZE && x >= 0 && x <= Main1v1.ROWS -1 &&
				y >= 0 && y <= Main1v1.ROWS -1) {
			
			boolean found = false;
			Marker1v1 marker = markers[x][y];
			if(marker != null) {
				if(type == -1) {
					type = marker.getType();
				}
				
				if(marker.getType() == type) {
					match.add(marker);
					found = true;
				}
			}
			
			if(!found && match.size() < Main1v1.MATCH) {
				match.clear();
				type = -1;
			}
			
			x += dX;
			y += dY;
			index ++;
			checkCount ++;
		}
		
		return match.size() >= Main1v1.MATCH ? match : null;
	}

	public static int getWinType(Marker1v1[][] markers) {

		ArrayList<Marker1v1> match = checkWin(markers);
		
		return match == null ? -1 : match.get(0).getType();
				
	}
	 
}
