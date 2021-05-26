package design.display.window.toolbox;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JFrame;
import design.display.element.Toggle;
import design.display.window.Janela;
@SuppressWarnings("serial")
public class Ferramentas extends Janela{
//ADICIONAR
	private Toggle adicionar=new Toggle(getPainel()){{
		setAction(new Runnable(){
			public void run(){
				
			}
		});
		setBounds(getBorda().getInnerX()+10,getBorda().getInnerY()+10,20,20);
		setLegenda("Adicionar");
	}};
//LÁPIS
	private Toggle lapis=new Toggle(getPainel()){{
		setAction(new Runnable(){
			public void run(){
				
			}
		});
		setBounds(adicionar.getX()+adicionar.getWidth()+10,getBorda().getInnerY()+10,20,20);
		setLegenda("Lápis");
	}};
//MAIN
	public Ferramentas(JFrame janelaPai){
		super(janelaPai);
		setTitle("Ferramentas");
		setSize(100,300);
		setMinimumSize(getSize());
		setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
		getPainel().add(adicionar);
		getPainel().add(lapis);
		addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent c){
				adicionar.setLocation(getBorda().getInnerX()+10,getBorda().getInnerY()+10);
				lapis.setBounds(adicionar.getX()+adicionar.getWidth()+10,getBorda().getInnerY()+10,20,20);
			}
		});
	}
}