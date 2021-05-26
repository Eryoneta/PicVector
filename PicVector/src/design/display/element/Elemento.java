package design.display.element;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import design.display.Painel;
public class Elemento{
//PAINEL(PAI)
	private Painel painel;
		public Painel getPainel(){return painel;}
//LOCAL
	private int x=0;
		public int getX(){return x;}
		public void setX(int x){this.x=painel.getJanela().getInsets().left+x;}
	private int y=0;
		public int getY(){return y;}
		public void setY(int y){this.y=painel.getJanela().getInsets().top+y;}
	public Point getLocation(){return new Point(getX(),getY());}
	public void setLocation(int x,int y){setX(x);setY(y);}
//FORM
	private boolean update=false;
	private int width=0;
		public int getWidth(){return width;}
		public void setWidth(int width){
			this.width=width;
			update=true;
			redraw();
		}
	private int height=0;
		public int getHeight(){return height;}
		public void setHeight(int height){
			this.height=height;
			update=true;
			redraw();
		}
	public Dimension getSize(){return new Dimension(getWidth(),getHeight());}
	public void setSize(int width,int height){setWidth(width);setHeight(height);}
	public Rectangle getBounds(){return new Rectangle(getX(),getY(),getWidth(),getHeight());}
	public void setBounds(int x,int y,int width,int height){setX(x);setY(y);setWidth(width);setHeight(height);}
	public void setBounds(Rectangle r){setBounds(r.x,r.y,r.width,r.height);}
//VISIBLE
	private boolean visible=true;
		public boolean isVisible(){return visible;}
		public void setVisible(boolean visible){this.visible=visible;}
//PRINT
	private Image print=new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
		public Image getPrint(){return print;}
//MAIN
	public Elemento(Painel painel){this.painel=painel;painel.setLayout(null);}
//REDRAW
	public void redraw(){
		if(!isVisible())return;
		if(update){
			if(getWidth()<=0||getHeight()<=0)return;
			print=new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_ARGB);
			update=false;
		}
		draw(print.getGraphics());
		painel.getJanela().repaint();
	}
	public void draw(Graphics imagemEdit){}//TODOS OS ELEMENTOS AQUI DEVEM COMEÇAR PELO 0,0! 
}