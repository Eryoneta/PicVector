package design.display.element;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import design.display.Painel;
@SuppressWarnings("serial")
public class Botao extends Elemento{
//STATES
	protected enum State{
		INACTIVE,
		HOVERED,
		ACTIVE,
		DRAGGED;
//		public boolean is(Botao.State...states){
//			for(Botao.State state:states)if(this.equals(state))return true;
//			return false;
//		}
	}
	protected State state=State.INACTIVE;
		private void setState(State state){
			this.state=state;
			redraw();
		}
//CORES
	private Color cor=Color.ORANGE;
		public Color getCor(){return cor;}
		public void setCor(Color cor){this.cor=cor;}
	private Color hoverColor=Color.RED;
		public Color getColorOnHover(){return hoverColor;}
		public void setColorOnHover(Color hoverColor){this.hoverColor=hoverColor;}
	private Color activeColor=Color.GREEN;
		public Color getColorOnActive(){return activeColor;}
		public void setColorOnActive(Color activeColor){this.activeColor=activeColor;}
//LEGENDA
	private JPopupMenu popup=null;
		public void setLegenda(String legenda){
			popup=new JPopupMenu(){{
				try{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				}catch(ClassNotFoundException|InstantiationException|IllegalAccessException|UnsupportedLookAndFeelException erro){
					
				}
				add(new JLabel(legenda));
			}};
		}
//ACTION
	private Runnable acao;
		public void setAction(Runnable acao){this.acao=acao;}
		public void click(){
			if(acao!=null)acao.run();
		}
//MAIN
	public Botao(Painel painel){
		super(painel);
		final Window janela=painel.getJanela();
		janela.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent m){
				switch(state){
					case INACTIVE:default:break;
					case HOVERED:
						setState(State.ACTIVE);
					break;
					case ACTIVE:break;
					case DRAGGED:break;
				}
			}
			public void mouseReleased(MouseEvent m){
				switch(state){
					case INACTIVE:default:break;
					case HOVERED:break;
					case ACTIVE:
						setState(State.HOVERED);
						click();
					break;
					case DRAGGED:
						setState(State.INACTIVE);
					break;
				}
			}
		});
		janela.addMouseMotionListener(new MouseAdapter(){
			public void mouseMoved(MouseEvent m){
				switch(state){
					case INACTIVE:default:
						if(getBounds().contains(m.getPoint())){
							setState(State.HOVERED);
							if(popup!=null){
								final Point mouseScreen=MouseInfo.getPointerInfo().getLocation();
								popup.show(painel.getJanela(),mouseScreen.x-painel.getJanela().getX(),mouseScreen.y-painel.getJanela().getY());
							}
						}
					break;
					case HOVERED:
						if(!getBounds().contains(m.getPoint())){
							setState(State.INACTIVE);
							if(popup!=null){
								popup.setVisible(false);
							}
						}
					break;
					case ACTIVE:break;
					case DRAGGED:break;
				}
			}
			public void mouseDragged(MouseEvent m){
				switch(state){
					case INACTIVE:default:break;
					case HOVERED:break;
					case ACTIVE:
						if(!getBounds().contains(m.getPoint())){
							setState(State.DRAGGED);
							if(popup!=null){
								popup.setVisible(false);
							}
						}
					break;
					case DRAGGED:
						if(getBounds().contains(m.getPoint())){
							setState(State.ACTIVE);
							if(popup!=null){
								final Point mouseScreen=MouseInfo.getPointerInfo().getLocation();
								popup.show(painel.getJanela(),mouseScreen.x-painel.getJanela().getX(),mouseScreen.y-painel.getJanela().getY());
							}
						}
					break;
				}
			}
		});
	}
//MAIN DRAW
@Override
	public void draw(Graphics imagemEdit){
		switch(state){
			case INACTIVE:default:		imagemEdit.setColor(getCor());				break;
			case HOVERED:case DRAGGED:	imagemEdit.setColor(getColorOnHover());		break;
			case ACTIVE:				imagemEdit.setColor(getColorOnActive());	break;
		}
		imagemEdit.fillRect(0,0,getWidth(),getHeight());
		imagemEdit.dispose();
	}
}