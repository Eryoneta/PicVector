package image;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
public class Imagem{
//LOCAL
	private int x=0;
		public int getX(){return x;}
		public void setX(int x){this.x=x;}
	private int y=0;
		public int getY(){return y;}
		public void setY(int y){this.y=y;}
	public void setLocation(int x,int y){setX(x);setY(y);}
//FORM
	private int width=DEFAULT_WIDTH;
		public int getWidth(){return width;}
		public int getScaledWidth(){return (int)(getWidth()*getZoom());}
	private int height=DEFAULT_HEIGHT;
		public int getHeight(){return height;}
		public int getScaledHeight(){return (int)(getHeight()*getZoom());}
	public void setSize(int width,int height){
		this.width=width;
		this.height=height;
		print=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		for(Camada camada:getCamadas())camada.setSize(width,height);
	}
//ZOOM
	private double zoom=1;
		public double getZoom(){return zoom;}
		public void setZoom(double zoom){
			zoom=Math.max(0.1d,Math.min(20d,zoom));
			this.zoom=zoom;
		}
//CAMADAS
	private List<Camada>camadas=new ArrayList<Camada>();
		public List<Camada>getCamadas(){return camadas;}
		public boolean addCamada(Camada camada){return camadas.add(camada);}
		public boolean delCamada(Camada camada){return camadas.remove(camada);}
//LINK
	private File link;
		public File getLink(){return link;}
		public void setLink(File link){this.link=link;}
//PRINT
	private Image print=new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
		public Image getPrint(){return print;}
//VAR GLOBAIS
	public static int DEFAULT_WIDTH=800;
	public static int DEFAULT_HEIGHT=600;
//MAIN
	public Imagem(File link){
		setLink(link);
		addCamada(new Camada(this));
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		if(link!=null&&link.exists()){
			try{
				final Image imagem=ImageIO.read(link);
				setSize(((BufferedImage)imagem).getWidth(),((BufferedImage)imagem).getHeight());
				getCamadas().get(0).getRascal().drawImage(imagem);
			}catch(IOException erro){}
		}
	}
//REDRAW
	public void updatePrint(){
		final Graphics2D imagemEdit=(Graphics2D)print.getGraphics();
		Rascal.clear(imagemEdit,getWidth(),getHeight());
		for(Camada camada:getCamadas())imagemEdit.drawImage(camada.getPrint(),0,0,null);
		imagemEdit.dispose();
	}
//DRAW
	public void draw(Graphics2D imagemEdit){
		imagemEdit.drawImage(getPrint(),0,0,null);
	}
}