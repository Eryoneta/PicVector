package design.display.window.toolbox;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JFrame;
import design.display.element.Botao;
import design.display.window.Janela;
@SuppressWarnings("serial")
public class Camadas extends Janela{
	private Botao adicionar;
	public Camadas(JFrame janelaPai){
		super(janelaPai);
		setTitle("Camadas");
		setSize(200,200);
		setMinimumSize(getSize());
		setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
		getPainel().add(adicionar=new Botao(getPainel()){{
			setAction(new Runnable(){
				public void run(){
					
				}
			});
			setBounds(getBorda().getInnerX()+10,getBorda().getInnerY()+10,20,20);
		}});
		addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent c){
				adicionar.setLocation(getBorda().getInnerX()+10,getBorda().getInnerY()+10);
			}
		});
	}
}