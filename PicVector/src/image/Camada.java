package image;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
public class Camada{
//IMAGEM(PAI)
	private Imagem imagem;
		public Imagem getImagem(){return imagem;}
//RASCAL
	private Rascal rascal;
		public Rascal getRascal(){return rascal;}
//VETORIAL
	private Vetorial vetorial;
		public Vetorial getVetorial(){return vetorial;}
//PRINT
	private Image print=new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
		public Image getPrint(){return print;}
		public void setSize(int width,int height){
			rascal.setSize(width,height);
			print=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		}
//MAIN
	public Camada(Imagem imagem){
		this.imagem=imagem;
		rascal=new Rascal(this);
		rascal.setSize(getImagem().getWidth(),getImagem().getHeight());
		vetorial=new Vetorial(this);
		vetorial.setSize(getImagem().getWidth(),getImagem().getHeight());
	}
//REDRAW
	public void updatePrint(){
		final Graphics2D imagemEdit=(Graphics2D)print.getGraphics();
		Rascal.clear(imagemEdit,getImagem().getWidth(),getImagem().getHeight());
		rascal.draw(imagemEdit);
		vetorial.draw(imagemEdit);
		imagemEdit.dispose();
		getImagem().updatePrint();
	}
//DRAW
	public void draw(Graphics2D imagemEdit){
		imagemEdit.drawImage(getPrint(),0,0,null);
	}
}