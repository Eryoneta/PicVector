package design.display.window;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
public class Ancora{
//SIDE
	public enum Side{
		NONE,
		TOP,
		RIGHT,
		BOTTOM,
		LEFT;
		public boolean is(Ancora.Side... sides){
			for(Ancora.Side side:sides)if(this.equals(side))return true;
			return false;
		}
		public enum Face{
			ALIGNED,
			INTERNAL,
			EXTERNAL;
			public boolean is(Ancora.Side.Face... faces){
				for(Ancora.Side.Face face:faces)if(this.equals(face))return true;
				return false;
			}
		}
	}
//LADO ANCORADO
	private Side side=Side.NONE;
		public Side getSide(){return side;}
	private Side.Face face=Side.Face.ALIGNED;
		public Side.Face getFace(){return face;}
	public void setSide(Side side,Side.Face face){
		this.side=side;
		this.face=face;
		getBorda().setAnchoredBounds();
	}
	public boolean isAnchored(){return (janelaAncorada!=null);}
//BORDA(PAI)
	private Borda borda;
		public Borda getBorda(){return borda;}
//JANELA ANCORADA
	private Window janelaAncorada=null;
		public Window getJanelaAncorada(){return janelaAncorada;}
		public void setJanelaAncorada(Window janelaAncorada,Side side,Side.Face face){
			if(this.janelaAncorada!=null){
				this.janelaAncorada.removeComponentListener(seeker);	//REMOVE DA JANELA ANTERIOR
			}
			if(janelaAncorada!=null){
				janelaAncorada.addComponentListener(seeker);
				setSide(side,face);
			}else setSide(Side.NONE,Side.Face.ALIGNED);
			this.janelaAncorada=janelaAncorada;
			getBorda().setAnchoredBounds();
		}
			private ComponentAdapter seeker=new ComponentAdapter(){
				public void componentResized(ComponentEvent r){
					getBorda().setAnchoredBounds();
				}
				public void componentMoved(ComponentEvent m){
					getBorda().setAnchoredBounds();
				}
			};
//MAIN
	public Ancora(Borda borda){
		this.borda=borda;
	}
	public Ancora(Borda borda,Window janelaAncorada,Side side,Side.Face face){
		this.borda=borda;
		setJanelaAncorada(janelaAncorada,side,face);
	}
//LOCAL DA ÂNCORA
	public int getLocal(){
		final Window janela=getJanelaAncorada();
		final Borda borda=(janela instanceof Janela?((Janela)janela).getBorda():((JanelaMestre)janela).getBorda());
		switch(getSide()){
			case NONE:default:	return -1;
			case TOP:
				switch(getFace()){
					case ALIGNED:default:	return janela.getY();
					case EXTERNAL:			return janela.getY()-borda.getSpaceExterno();
					case INTERNAL:			return janela.getY()+borda.getSpaceTop();
				}
			case BOTTOM:
				switch(getFace()){
					case ALIGNED:default:	return janela.getY()+janela.getHeight();
					case EXTERNAL:			return janela.getY()+janela.getHeight()+borda.getSpaceExterno();
					case INTERNAL:			return janela.getY()+janela.getHeight()-borda.getSpaceBottom();
				}
			case LEFT:
				switch(getFace()){
					case ALIGNED:default:	return janela.getX();
					case EXTERNAL:			return janela.getX()-borda.getSpaceExterno();
					case INTERNAL:			return janela.getX()+borda.getSpaceLeft();
				}
			case RIGHT:
				switch(getFace()){
					case ALIGNED:default:	return janela.getX()+janela.getWidth();
					case EXTERNAL:			return janela.getX()+janela.getWidth()+borda.getSpaceExterno();
					case INTERNAL:			return janela.getX()+janela.getWidth()-borda.getSpaceRight();
				}
		}
	}
}