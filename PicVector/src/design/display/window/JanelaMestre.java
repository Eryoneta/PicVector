package design.display.window;
import javax.swing.JFrame;
@SuppressWarnings("serial")
public class JanelaMestre extends JFrame{
//BORDA
	private Borda borda=new Borda(this);
		public Borda getBorda(){return borda;}
//MAIN
	public JanelaMestre(){}
}