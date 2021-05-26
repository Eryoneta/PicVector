package image;
import image.vector.Ponto;
import image.vector.Spline;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
public class Rascal{
//CAMADA(PAI)
	private Camada camada;
		public Camada getCamada(){return camada;}
//IMAGEM
	private Image imagem;
		public Image getImagem(){return imagem;}
//FORM
	public void setSize(int width,int height){
		imagem=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
	}
//MAIN
	public Rascal(Camada camada){
		this.camada=camada;
		imagem=new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
	}
//CLEAR
	public static void clear(Graphics2D imagemEdit,int width,int height){
		imagemEdit.setComposite(AlphaComposite.Clear);
		imagemEdit.fillRect(0,0,width,height);
		imagemEdit.setComposite(AlphaComposite.SrcOver);
	}
//FUNÇÕES
	public void drawImage(Image imagem){
		final Graphics2D imagemEdit=(Graphics2D)getImagem().getGraphics();
		imagemEdit.drawImage(imagem,0,0,null);
		updatePrint();
	}
	public void drawPixel(int x,int y){
		updatePrint();
	}
	public void fillArea(int x,int y){
		updatePrint();
	}
	public void drawSpline(Spline spline,Ponto ponto){
		updatePrint();
	}
//REDRAW
	public void updatePrint(){
		getCamada().updatePrint();
	}
//DRAW
	public void draw(Graphics2D imagemEdit){
		imagemEdit.drawImage(getImagem(),0,0,null);
	}
}