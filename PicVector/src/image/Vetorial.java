package image;
import image.vector.Forma;
import image.vector.Linha;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
public class Vetorial{
//CAMADA(PAI)
	private Camada camada;
		public Camada getCamada(){return camada;}
//PRINT
	private Image print;
		public Image getPrint(){return print;}
		public void setSize(int width,int height){
			print=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		}
//LINHAS
	private List<Linha>linhas=new ArrayList<Linha>();
		public List<Linha>getLinhas(){return linhas;}
		public boolean addLinha(Linha linha){
			final boolean result=linhas.add(linha);
			if(result)updatePrint();
			return result;
		}
		public boolean delLinha(Linha linha){
			final boolean result=linhas.remove(linha);
			if(result)updatePrint();
			return result;
		}
//FORMAS
	private List<Forma>formas=new ArrayList<Forma>();
		public List<Forma>getFormas(){return formas;}
		public boolean addForma(Forma forma){
			final boolean result=formas.add(forma);
			if(result)updatePrint();
			return result;
		}
		public boolean delForma(Forma forma){
			final boolean result=formas.remove(forma);
			if(result)updatePrint();
			return result;
		}
//MAIN
	public Vetorial(Camada camada){
		this.camada=camada;
		print=new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
	}
//REDRAW
	public void updatePrint(){
		final Graphics2D imagemEdit=(Graphics2D)print.getGraphics();
		Rascal.clear(imagemEdit,getCamada().getImagem().getWidth(),getCamada().getImagem().getHeight());
		imagemEdit.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		for(Forma forma:getFormas())forma.draw(imagemEdit);
		for(Linha linha:getLinhas())linha.draw(imagemEdit);
		imagemEdit.dispose();
		getCamada().updatePrint();
	}
//DRAW
	public void draw(Graphics2D imagemEdit){
		imagemEdit.drawImage(getPrint(),0,0,null);
	}
}