package design.display;
import java.awt.Graphics;
import java.awt.Window;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import design.display.element.Elemento;
@SuppressWarnings("serial")
public class Painel extends JPanel{
//JANELA(PAI)
	private Window janela;
		public Window getJanela(){return janela;}
//ELEMENTOS
	private List<Elemento>elementos=new ArrayList<Elemento>();
		public List<Elemento>getElementos(){return elementos;}
		public void add(Elemento e){elementos.add(e);}
		public void del(Elemento e){elementos.remove(e);}
//MAIN
	public Painel(Window j){janela=j;}
//MAIN DRAW
@Override
	protected void paintComponent(Graphics imagemEdit){
		for(Elemento e:getElementos())imagemEdit.drawImage(e.getPrint(),e.getX(),e.getY(),e.getWidth(),e.getHeight(),null);
	}
}