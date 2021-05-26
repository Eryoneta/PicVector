package design.display.window;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import design.display.element.Botao;
public class Borda{
//STATE
	private enum Side{
		NONE,
		ALL,
		TOP,
		TOP_RIGHT,
		RIGHT,
		BOTTOM_RIGHT,
		BOTTOM,
		BOTTOM_LEFT,
		LEFT,
		TOP_LEFT;
		private boolean is(Borda.Side... sides){
			for(Borda.Side side:sides)if(this.equals(side))return true;
			return false;
		}
	}
	private Side side=Side.NONE;
		private Side getSide(Point ponto){
			if(getInnerBounds().contains(ponto))return Side.NONE;
			if(getTopBorder().contains(ponto)){
				boolean isInsideButton=false;
				for(Botao botao:botoes)if(botao.getBounds().contains(ponto)){
					isInsideButton=true;
					break;
				}
				if(!isInsideButton){
					if(getRightBorder().contains(ponto))return Side.TOP_RIGHT;
					if(getLeftBorder().contains(ponto))return Side.TOP_LEFT;
					return Side.TOP;
				}
			}
			if(getRightBorder().contains(ponto)){
				if(getBottomBorder().contains(ponto))return Side.BOTTOM_RIGHT;
				return Side.RIGHT;
			}
			if(getBottomBorder().contains(ponto)){
				if(getLeftBorder().contains(ponto))return Side.BOTTOM_LEFT;
				return Side.BOTTOM;
			}
			if(getLeftBorder().contains(ponto)){
				return Side.LEFT;
			}
			if(getTitleBar().contains(ponto)){
				boolean isInsideButton=false;
				for(Botao botao:botoes)if(botao.getBounds().contains(ponto)){
					isInsideButton=true;
					break;
				}
				if(!isInsideButton)return Side.ALL;	
			}
			return Side.NONE;
		}
//JANELA(PAI)
	private Window janela;
//BORDAS
	public static int TOP_WIDTH=30;
	public static int WIDTH=8;
		public int getInnerX(){return WIDTH;}
		public int getInnerY(){return TOP_WIDTH;}
		public int getInnerWidth(){return janela.getWidth()-WIDTH*2;}
		public int getInnerHeight(){return janela.getHeight()-TOP_WIDTH-WIDTH;}
	public Rectangle getInnerBounds(){return new Rectangle(getInnerX(),getInnerY(),getInnerWidth(),getInnerHeight());}
//ÁREAS DAS BORDAS
	private Rectangle getTitleBar(){		return new Rectangle(getInnerX(),0,getInnerWidth(),getInnerY());}
	private Rectangle getTopBorder(){		return new Rectangle(0,0,janela.getWidth(),Borda.WIDTH);}
	private Rectangle getBottomBorder(){	return new Rectangle(0,janela.getHeight()-Borda.WIDTH,janela.getWidth(),Borda.WIDTH);}
	private Rectangle getLeftBorder(){		return new Rectangle(0,0,Borda.WIDTH,janela.getHeight());}
	private Rectangle getRightBorder(){		return new Rectangle(janela.getWidth()-Borda.WIDTH,0,Borda.WIDTH,janela.getHeight());}
//VAR GLOBAIS
	private static Color WINDOW_COLOR=Color.DARK_GRAY;
		public static Color getBordaCor(){return WINDOW_COLOR;}
		public static void setBordaCor(Color bordaCor){WINDOW_COLOR=bordaCor;}
	private static Color WINDOW_DISABLED_COLOR=Color.WHITE;
		public static Color getBordaDisabledCor(){return WINDOW_DISABLED_COLOR;}
		public static void setBordaDisabledCor(Color bordaCor){WINDOW_DISABLED_COLOR=bordaCor;}
	private Rectangle windowSize;
	private Point mousePressed=new Point(0,0);
//BOTÕES
	private List<Botao>botoes=new ArrayList<>();
		public void add(Botao button){
			botoes.add(button);
			int buttonsWidth=TOP_WIDTH*3;		//ALTURA DA BORDA X3 
			for(Botao botao:botoes)buttonsWidth+=botao.getWidth();
			janela.setMinimumSize(new Dimension(WIDTH+buttonsWidth+WIDTH,TOP_WIDTH+WIDTH));
		}
//ÂNCORAS
	private Ancora ancoraTop=new Ancora(this);
		public Ancora getTop(){return ancoraTop;}
	private Ancora ancoraRight=new Ancora(this);
		public Ancora getRight(){return ancoraRight;}
	private Ancora ancoraLeft=new Ancora(this);
		public Ancora getLeft(){return ancoraLeft;}
	private Ancora ancoraBottom=new Ancora(this);
		public Ancora getBottom(){return ancoraBottom;}
	public boolean isAnchored(){
		return (getTop().isAnchored()||getBottom().isAnchored()||getRight().isAnchored()||getLeft().isAnchored());
	}
//ESPAÇOS
	private int spaceExterno;
		public int getSpaceExterno(){return spaceExterno;}
		public void setSpaceExterno(int space){spaceExterno=space;}
	private int spaceTop;
		public int getSpaceTop(){return spaceTop;}
		public void setSpaceTop(int space){spaceTop=space;}
	private int spaceRight;
		public int getSpaceRight(){return spaceRight;}
		public void setSpaceRight(int space){spaceRight=space;}
	private int spaceBottom;
		public int getSpaceBottom(){return spaceBottom;}
		public void setSpaceBottom(int space){spaceBottom=space;}
	private int spaceLeft;
		public int getSpaceLeft(){return spaceLeft;}
		public void setSpaceLeft(int space){spaceLeft=space;}
//MAIN
	public Borda(JDialog janela){
		this.janela=janela;
		windowSize=janela.getBounds();
		janela.setUndecorated(true);
		janela.setBackground(new Color(0,0,0,0));
		setJanela(janela);
	}
	public Borda(JFrame janela){
		this.janela=janela;
		windowSize=janela.getBounds();
		setJanela(janela);
	}
	private void setJanela(Window janela){
		janela.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent m){
				mousePressed.setLocation(m.getXOnScreen(),m.getYOnScreen());
				windowSize=janela.getBounds();
				side=getSide(m.getPoint());	//CASO MOUSE_RELEASE E MOUSE_PRESS SEGUIDOS
			}
			public void mouseReleased(MouseEvent m){
				side=Side.NONE;
			}
		});
		janela.addMouseMotionListener(new MouseAdapter(){
			public void mouseMoved(MouseEvent m){
				side=getSide(m.getPoint());
				setCursor();
			}
			public void mouseDragged(MouseEvent m){
				if(side.is(Side.NONE))return;
				resize(m.getLocationOnScreen());
			}
			private void setCursor(){
				int cursor=0;
				switch(side){
					case NONE:case ALL:default:	cursor=Cursor.DEFAULT_CURSOR;break;
					case TOP:					cursor=Cursor.N_RESIZE_CURSOR;break;
					case TOP_RIGHT:				cursor=Cursor.NE_RESIZE_CURSOR;break;
					case RIGHT:					cursor=Cursor.E_RESIZE_CURSOR;break;
					case BOTTOM_RIGHT:			cursor=Cursor.SE_RESIZE_CURSOR;break;
					case BOTTOM:				cursor=Cursor.S_RESIZE_CURSOR;break;
					case BOTTOM_LEFT:			cursor=Cursor.SW_RESIZE_CURSOR;break;
					case LEFT:					cursor=Cursor.W_RESIZE_CURSOR;break;
					case TOP_LEFT:				cursor=Cursor.NW_RESIZE_CURSOR;break;
				}
				janela.setCursor(Cursor.getPredefinedCursor(cursor));	//INFELIZMENTE O CURSOR NÃO É CUSTOM FORA DA TELA
			}
			private void resize(Point mouseAtual){
				final Dimension diff=new Dimension(mousePressed.x-mouseAtual.x,mousePressed.y-mouseAtual.y);
				final Point newLocal=new Point(windowSize.x-diff.width,windowSize.y-diff.height);
				final Dimension newAreaDimm=new Dimension(windowSize.width-diff.width,windowSize.height-diff.height);
				final Dimension newAreaGrow=new Dimension(windowSize.width+diff.width,windowSize.height+diff.height);
				final Rectangle newWindow=new Rectangle();
				switch(side){
					case ALL:default:	newWindow.setBounds(	newLocal.x,		newLocal.y,		windowSize.width,	windowSize.height	);break;
					case TOP:			newWindow.setBounds(	windowSize.x,	newLocal.y,		windowSize.width,	newAreaGrow.height	);break;
					case TOP_RIGHT:		newWindow.setBounds(	windowSize.x,	newLocal.y,		newAreaDimm.width,	newAreaGrow.height	);break;
					case RIGHT:			newWindow.setBounds(	windowSize.x,	windowSize.y,	newAreaDimm.width,	windowSize.height	);break;
					case BOTTOM_RIGHT:	newWindow.setBounds(	windowSize.x,	windowSize.y,	newAreaDimm.width,	newAreaDimm.height	);break;
					case BOTTOM:		newWindow.setBounds(	windowSize.x,	windowSize.y,	windowSize.width,	newAreaDimm.height	);break;
					case BOTTOM_LEFT:	newWindow.setBounds(	newLocal.x,		windowSize.y,	newAreaGrow.width,	newAreaDimm.height	);break;
					case LEFT:			newWindow.setBounds(	newLocal.x,		windowSize.y,	newAreaGrow.width,	windowSize.height	);break;
					case TOP_LEFT:		newWindow.setBounds(	newLocal.x,		newLocal.y,		newAreaGrow.width,	newAreaGrow.height	);break;
				}
				final Dimension minSize=janela.getMinimumSize();
				if(side.is(Side.TOP_LEFT,Side.LEFT,Side.BOTTOM_LEFT)&&newWindow.width<minSize.width){		//IMPEDE X DE CONTINUAR
					newWindow.x-=minSize.width-newWindow.width;
					newWindow.width=minSize.width;
				}
				if(side.is(Side.TOP_LEFT,Side.TOP,Side.TOP_RIGHT)&&newWindow.height<minSize.height){	//IMPEDE Y DE CONTINUAR
					newWindow.y-=minSize.height-newWindow.height;
					newWindow.height=minSize.height;
				}
				janela.setBounds(newWindow);
			}
		});
	}
	public Rectangle getAnchoredBounds(){
		final Rectangle area=janela.getBounds();
		int x=(getLeft().isAnchored()?getLeft().getLocal():area.x);
		int y=(getTop().isAnchored()?getTop().getLocal():area.y);
		int width=(getRight().isAnchored()?getRight().getLocal()-x:area.width);
		int height=(getBottom().isAnchored()?getBottom().getLocal()-y:area.height);
		if(!getLeft().isAnchored()&&getRight().isAnchored()){	//SE LEFT NÃO ESTÁ ANCORADO 
			x=area.x+width-area.width;
			width=area.width;
		}
		if(!getTop().isAnchored()&&getBottom().isAnchored()){	//SE TOP NÃO ESTÁ ANCORADO
			y=area.y+height-area.height;
			height=area.height;
		}
		return new Rectangle(x,y,width,height);
	}
	public void setAnchoredBounds(){
		janela.setBounds(getAnchoredBounds());
	}
//DRAW
	public void draw(Graphics imagemEdit){
		drawShadow(imagemEdit);
		drawBorda(imagemEdit);
	}
		private void drawShadow(Graphics imagemEdit){
			final Rectangle area=imagemEdit.getClipBounds();
			final BufferedImage imagem=new BufferedImage(area.width,area.height,BufferedImage.TYPE_INT_ARGB);
			final Graphics2D imagemEdit2D=(Graphics2D)imagem.getGraphics();
			drawShadowBlock(imagemEdit2D,	area.width-WIDTH,	WIDTH,					area.width,			area.height-WIDTH,	Side.RIGHT);
			drawShadowBlock(imagemEdit2D,	WIDTH,				WIDTH,					0,					area.height-WIDTH,	Side.LEFT);
			drawShadowBlock(imagemEdit2D,	WIDTH,				area.height-WIDTH,		area.width-WIDTH,	area.height,		Side.BOTTOM);
			drawShadowBlock(imagemEdit2D,	area.width-WIDTH*2,	area.height-WIDTH*2,	area.width,			area.height,		Side.BOTTOM_RIGHT);
			drawShadowBlock(imagemEdit2D,	WIDTH*2,			area.height-WIDTH*2,	0,					area.height,		Side.BOTTOM_LEFT);
			drawShadowBlock(imagemEdit2D,	WIDTH*2,			WIDTH*2,				0,					0,					Side.TOP_LEFT);
			drawShadowBlock(imagemEdit2D,	area.width-WIDTH*2,	WIDTH*2,				area.width,			0,					Side.TOP_RIGHT);
			imagemEdit.drawImage(imagem,0,0,null);
		}
			private void drawShadowBlock(Graphics2D imagemEdit2D,int x1,int y1,int x2,int y2,Side side){
				final Color sombra=new Color(0,0,0,0.2f);
				final Color transparente=new Color(0,0,0,0);
				int x=Math.min(x1,x2);
				int y=Math.min(y1,y2);
				int width=Math.max(x1,x2)-x;
				int height=Math.max(y1,y2)-y;
				switch(side){
					case RIGHT:case LEFT:	y2=y1;	break;
					case BOTTOM:			x2=x1;	break;
					case BOTTOM_RIGHT:
						width=height/=2;
						x+=width;
						y+=height;
					break;
					case BOTTOM_LEFT:
						width=height/=2;
						y+=height;
					break;
					case TOP_RIGHT:
						width=height/=2;
						x+=width;
					break;
					case TOP_LEFT:
						width=height/=2;
					break;
					default:	break;
				}
				imagemEdit2D.setPaint(new GradientPaint(x1,y1,sombra,x2,y2,transparente));
				imagemEdit2D.fill(new Rectangle2D.Double(x,y,width,height));
			}
		private void drawBorda(Graphics imagemEdit){
			imagemEdit.setColor(janela.getOwner().isFocused()?getBordaCor():getBordaDisabledCor());
			imagemEdit.fillRect(getInnerX()-1,0,getInnerWidth()+2,janela.getHeight()-WIDTH+1);
			imagemEdit.setColor(janela.getOwner().isFocused()?Color.WHITE:new Color(160,143,176));
			imagemEdit.drawString(((Dialog)janela).getTitle(),WIDTH+5,20);
		}
}