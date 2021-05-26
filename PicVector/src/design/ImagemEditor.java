package design;
import image.Imagem;
import image.vector.Ponto2D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import main.Cursor;
import design.display.Painel;
import design.display.element.Elemento;
public class ImagemEditor extends Elemento{
//IMAGEM
	private Imagem imagem=null;
		public Imagem getImagem(){return imagem;}
		public void setImagem(Imagem imagem){this.imagem=imagem;}
//VAR GLOBAIS
	private static Color COR_FUNDO=new Color(240,240,240);
//	private Point mousePressed=new Point();
	private Point mouseDragged=new Point();
	private Point mouseMoved=new Point();
//MAIN
	public ImagemEditor(Painel painel){
		super(painel);
		painel.getJanela().addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent m){
//				final Point mouse=getGridPosition(m.getPoint());
//				mousePressed=mouse;
				mouseDragged=m.getPoint();
			}
			public void mouseReleased(MouseEvent m){
				
			}
			public void mouseClicked(MouseEvent m){
				
			}
		});
		painel.getJanela().addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent m){
				mouseMoved=m.getPoint();
			}
			public void mouseDragged(MouseEvent m){
				if(imagem==null)return;
				if(Cursor.match(m,Cursor.MIDDLE)){
					final Point mouseDraggedAtual=m.getPoint();
					imagem.setLocation(imagem.getX()+(mouseDraggedAtual.x-mouseDragged.x),imagem.getY()+(mouseDraggedAtual.y-mouseDragged.y));
					mouseDragged=mouseDraggedAtual;
					redraw();
				}
			}
		});
		painel.getJanela().addMouseWheelListener(new MouseWheelListener(){
			public void mouseWheelMoved(MouseWheelEvent w){
				final float zoom=(float)(w.getWheelRotation())/10;
				final Rectangle sizeIni=new Rectangle(mouseMoved.x-imagem.getX(),mouseMoved.y-imagem.getY(),imagem.getScaledWidth(),imagem.getScaledHeight());
				imagem.setZoom(imagem.getZoom()-zoom*imagem.getZoom());
				final Rectangle sizeFim=new Rectangle(
						(sizeIni.x*imagem.getScaledWidth())/sizeIni.width,
						(sizeIni.y*imagem.getScaledHeight())/sizeIni.height,
						imagem.getScaledWidth(),
						imagem.getScaledHeight()
				);
				imagem.setLocation(imagem.getX()-(sizeFim.x-sizeIni.x),imagem.getY()-(sizeFim.y-sizeIni.y));
				redraw();
			}
		});
	}
	private Point setPosition(Point mouse){
		return new Point(mouse.x-getX()-getPainel().getJanela().getInsets().left,mouse.y-getY()-getPainel().getJanela().getInsets().top);
	}
	public Ponto2D getGridPosition(Point mouse){
		if(imagem==null)return null;
		final Point mouseNew=setPosition(mouse);
		final Double x=getRounded((mouseNew.getX()-imagem.getX())/imagem.getZoom());
		final Double y=getRounded((mouseNew.getY()-imagem.getY())/imagem.getZoom());
		return new Ponto2D(x,y);
	}
		private double getRounded(double valor){
			return (double)Math.round(valor*1000d)/1000d;
		}
//DRAW
@Override
	public void draw(Graphics imagemEdit){
		if(imagem==null)return;
		drawFundo(imagemEdit);
		drawImage(imagemEdit);

//	//RENDER DE FONTE
//		final FontRenderContext renderer=((Graphics2D)imagemEdit).getFontRenderContext();
//		final Font fonte=new Font("Stanford_Pines",Font.BOLD,250);
//		final GlyphVector fontVector=fonte.createGlyphVector(renderer,"Gravity Falls");
//		Rectangle2D box=fontVector.getVisualBounds();
//		final int xOff=25-(int)box.getX();
//		final int yOff=80-(int)box.getY();
//		final Shape shape=fontVector.getOutline(xOff,yOff);
//		final List<double[]>pontos=new ArrayList<double[]>();
//		final double[]coords=new double[6];
//		for(PathIterator path=shape.getPathIterator(null);!path.isDone();path.next()) {
//			final int tipo=path.currentSegment(coords); //PODE SER: SEG_MOVETO, SEG_LINETO, SEG_QUADTO, SEG_CUBICTO, OU SEG_CLOSE
//			final double[]ponto={tipo,coords[0],coords[1]};
//			pontos.add(ponto);
//		}
//		for(double[]pt:pontos){
//			imagemEdit.setColor(Color.RED);
//			imagemEdit.fillRect((int)pt[1],(int)pt[2],1,1);
//		}
		
		imagemEdit.dispose();
	}
		private void drawFundo(Graphics imagemEdit){
			imagemEdit.setColor(COR_FUNDO);
			imagemEdit.fillRect(0,0,getPainel().getJanela().getWidth(),getPainel().getJanela().getHeight());
		}
		private void drawImage(Graphics imagemEdit){
			imagemEdit.drawImage(imagem.getPrint(),
					imagem.getX(),
					imagem.getY(),
					(int)(imagem.getWidth()*imagem.getZoom()),
					(int)(imagem.getHeight()*imagem.getZoom())
			,null);
		}
}