package image.vector;
import image.Vetorial;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
public class Forma{
//VETORIAL(PAI)
	private Vetorial vetorial;
		public Vetorial getVetorial(){return vetorial;}
//PRINT
	private Path2D.Double print;
		public Path2D.Double getPrint(){return print;}
//LINHAS
	private List<Linha>linhas=new ArrayList<>();
		public boolean add(Linha linha){return linhas.add(linha);}
		public boolean del(Linha linha){return linhas.remove(linha);}
		public List<Linha>getLinhas(){return linhas;}
//FORM
	public Path2D.Double getForm(){
		final Path2D.Double forma=new Path2D.Double();
		for(Linha linha:getLinhas())forma.append(linha.getLine(),true);
		return forma;
	}
//MAIN
	public Forma(){}
//REDRAW
	public void updatePrint(){
		print=getForm();
		getVetorial().updatePrint();
	}
//DRAW
	public void draw(Graphics2D imagemEdit){
		imagemEdit.setColor(Color.GREEN);
		imagemEdit.fill(getPrint());
	}
}