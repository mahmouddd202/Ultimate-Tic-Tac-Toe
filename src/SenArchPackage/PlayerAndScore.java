package SenArchPackage;

public class PlayerAndScore {

	private String name;
	private int score;

	public PlayerAndScore (){	   
	      name = "";
	      score   = 0;
	   }

	public PlayerAndScore (String name, int score)
	   throws PlayerAndScoreException{
		
	      // Remove elading and trailing spaces, tabs.
	      name = name.trim ();

	      if (name.length () == 0){	      
	         //JOptionPane.showMessageDialog (null, "Error: name cannot be blank.");
	         throw new PlayerAndScoreException ("Error: name cannot be blank.");
	      }

	      else{
	         this.name = name;
	         this.score   = score;
	      }
	   }

    public String getName (){
	   
	      return name;
	   }

	public int getscore (){
	   
	      return score;
	   }

	public void setName (String name){
	   
	      this.name = name;
	   }

	public void setscore (int score) {
	   
	      this.score= score;
	   }

	   @Override
	public String toString (){
	   
	      return score + "\t" + name + "\t"  ;
	   }

}
