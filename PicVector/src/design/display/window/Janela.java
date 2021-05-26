package design.display.window;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import design.display.Painel;
import design.display.element.Botao;
@SuppressWarnings("serial")
public class Janela extends JDialog{
//PAINEL
	private Painel painel=new Painel(this);
		public Painel getPainel(){return painel;}
//BORDA
	private Borda borda=new Borda(this);
		public Borda getBorda(){return borda;}
//BOTÃO X
	private Botao X=new Botao(painel){{
		setAction(new Runnable(){
			public void run(){
				dispose();
			}
		});
		final int size=Borda.TOP_WIDTH-Borda.WIDTH;
		setSize(size,size);
	}};
//VAR GLOBAIS
	public static float TRANSPARENCIA=0.8f;
//MAIN
	public Janela(JFrame janelaPai){
		super(janelaPai);
		setFocusableWindowState(false);		//NÃO ROUBA FOCUS
		setFont(janelaPai.getFont());
		setOpacity(TRANSPARENCIA);
		getBorda().add(X);
		painel.add(X);
		add(painel);
		addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent c){
				X.setLocation(getBorda().getInnerX()+getBorda().getInnerWidth()-X.getWidth()-4,4);
			}
		});
		addMouseListener(new MouseListener(){
			public void mouseReleased(MouseEvent arg0){
				getOwner().requestFocus();
			}
			public void mousePressed(MouseEvent arg0){
				getOwner().requestFocus();
			}
			public void mouseExited(MouseEvent arg0){}
			public void mouseEntered(MouseEvent arg0){}
			public void mouseClicked(MouseEvent arg0){
				getOwner().requestFocus();
			}
		});
		getBorda().setSpaceExterno(5);
	}
//MAIN FORM
@Override
	public void setSize(int width,int height){
		final Dimension sizeLimitado=getSizeLimit(width,height);
		super.setSize(sizeLimitado.width,sizeLimitado.height);
		X.setLocation(getBorda().getInnerX()+getBorda().getInnerWidth()-X.getWidth()-4,4);
	}
@Override
	public void setBounds(int x,int y,int width,int height){
		final Dimension sizeLimitado=getSizeLimit(width,height);
		super.setBounds(x,y,sizeLimitado.width,sizeLimitado.height);
		X.setLocation(getBorda().getInnerX()+getBorda().getInnerWidth()-X.getWidth()-4,4);
	}
	private Dimension getSizeLimit(int width,int height){
		final Dimension min=new Dimension(Borda.WIDTH+(X.getWidth()*3)+Borda.WIDTH,Borda.TOP_WIDTH+Borda.WIDTH);
		final Dimension max=Toolkit.getDefaultToolkit().getScreenSize();
		return new Dimension(
				Math.min(Math.max(width,min.width),max.width),
				Math.min(Math.max(height,min.height),max.height)
		);
	}
//MAIN DRAW
@Override
	public void paint(Graphics imagemEdit){
		super.paint(imagemEdit);
		getBorda().draw(imagemEdit);
		imagemEdit.setColor(Color.WHITE);
		final Rectangle innerArea=getBorda().getInnerBounds();
		imagemEdit.fillRect(innerArea.x,innerArea.y,innerArea.width,innerArea.height);
		super.paintComponents(imagemEdit);
		imagemEdit.dispose();
	}
}