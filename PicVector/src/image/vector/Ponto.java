package image.vector;
import image.Vetorial;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Path2D;
public class Ponto extends Ponto2D{
//VETORIAL(PAI)
	private Vetorial vetorial;
		public Vetorial getVetorial(){return vetorial;}
//PRINT
	private Path2D.Double print;
		public Path2D.Double getPrint(){return print;}
//VAR GLOBAIS
	public static Color COR=Color.RED;
//FORM
	private double size=1;
		public double getSize(){return size;}
		public void setSize(double size){this.size=size;}
	public Path2D.Double getForm(){
		final double radius=getSize()/2;
		final Path2D.Double forma=new Path2D.Double();
		forma.moveTo(getX()+radius,getY());
		for(float angle=0;angle<=360;angle+=22.5){
			final Ponto2D ponto=getRelativePoint(angle,radius);
			forma.lineTo(ponto.getX(),ponto.getY());
		}
		return forma;
	}
//MAIN
	public Ponto(){}
	public Ponto(double x,double y){set(x,y);}
	public Ponto(Ponto2D ponto){set(ponto.getX(),ponto.getY());}
//REDRAW
	public void updatePrint(){
		print=getForm();
		getVetorial().updatePrint();
	}
//DRAW
	public void draw(Graphics2D imagemEdit){
		final Color corOld=imagemEdit.getColor();
		final Stroke linha=imagemEdit.getStroke();
		imagemEdit.setColor(Ponto.COR);
		imagemEdit.setStroke(new BasicStroke(1f));
		imagemEdit.draw(getForm());		//FORM
		imagemEdit.setColor(corOld);
		imagemEdit.setStroke(linha);
	}
}