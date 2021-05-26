package image.vector;
import image.Vetorial;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
public class Linha extends Spline{
//VETORIAL(PAI)
	private Vetorial vetorial;
		public Vetorial getVetorial(){return vetorial;}
//PRINT
	private Path2D.Double print;
		public Path2D.Double getPrint(){return print;}
//COR
	private Color cor=Color.BLACK;
		public Color getCor(){return cor;}
		public void setCor(Color cor){this.cor=cor;}
//FORM
	public List<Ponto>getAllPontos(){
		final List<Ponto>pontos=new ArrayList<Ponto>();
		for(int p=0,size=getControlPontos().size();p<size;p++){
			final Ponto p_1=(Ponto)get(p-1);
			final Ponto p0=(Ponto)get(p+0);
			final Ponto p1=(Ponto)get(p+1);
			final Ponto p2=(Ponto)get(p+2);
			final double widthIni=p0.getSize();
			final double widthFim=p1.getSize();
			final double variancia=widthFim-widthIni;
			final double dist=p0.getDistance(p1);
			final double passos=1/(p==size-1?dist:Math.max(dist,p0.getSize()+p1.getSize()));	//ENTRE 0 E 1, A "DISTÂNCIA" ENTRE OS PONTOS
			for(double passo=0;passo<=1;passo+=passos){
				final Ponto subPonto=new Ponto(catmullRoom(passo,p_1,p0,p1,p2));		//MÁGICA DO CATMULLROOM
				subPonto.setSize(widthIni+(passo/1)*variancia);
				pontos.add(subPonto);
			}
		}
		return pontos;
	}
	public Path2D.Double getForm(){
		final List<Ponto>pontos=getAllPontos();
		final Path2D.Double forma=new Path2D.Double();
		forma.moveTo(getControlPontos().get(0).getX(),getControlPontos().get(0).getY());
		for(int p=0,size=pontos.size();p<size;p++){									//VAI
			final Ponto pontoAnt=pontos.get(p-(p==0?-1:1));							//CASO SEJA O 1º PONTO, SE ORIENTA COM O 2º PONTO
			final Ponto pontoNow=pontos.get(p);
			final double angleAnt=pontoNow.getAngle(pontoAnt);
			final Ponto2D ponto=pontoNow.getRelativePoint(angleAnt-(p==0?-90:90),pontoNow.getSize()/2);
			if(p==0){
				final double anglePasso=90/pontoNow.getSize(); 
				for(double c=pontoNow.getSize();c>0;c--){
					final Ponto2D pontoCurva=pontoNow.getRelativePoint(angleAnt-(anglePasso*c),pontoNow.getSize()/2);
					forma.lineTo(pontoCurva.getX(),pontoCurva.getY());
				}
			}
			forma.lineTo(ponto.getX(),ponto.getY());
			if(p==size-1){
				final double anglePasso=90/pontoNow.getSize(); 
				for(double c=pontoNow.getSize();c>0;c--){
					final Ponto2D pontoCurva=pontoNow.getRelativePoint(angleAnt-(anglePasso*c),pontoNow.getSize()/2);
					forma.lineTo(pontoCurva.getX(),pontoCurva.getY());
				}
			}
		}
		for(int size=pontos.size(),p=size-1;p>=0;p--){								//VOLTA
			final Ponto pontoAnt=pontos.get(p-(p==0?-1:1));							//CASO SEJA O 1º PONTO, SE ORIENTA COM O 2º PONTO
			final Ponto pontoNow=pontos.get(p);
			final double angleAnt=pontoNow.getAngle(pontoAnt);
			final Ponto2D ponto=pontoNow.getRelativePoint(angleAnt+(p==0?-90:90),pontoNow.getSize()/2);
			if(p==size-1){
				final double anglePasso=90/pontoNow.getSize(); 
				for(double c=0;c<pontoNow.getSize();c++){
					final Ponto2D pontoCurva=pontoNow.getRelativePoint(angleAnt+(anglePasso*c),pontoNow.getSize()/2);
					forma.lineTo(pontoCurva.getX(),pontoCurva.getY());
				}
			}
			forma.lineTo(ponto.getX(),ponto.getY());
			if(p==0){
				final double anglePasso=90/pontoNow.getSize(); 
				for(double c=0;c<pontoNow.getSize();c++){
					final Ponto2D pontoCurva=pontoNow.getRelativePoint(angleAnt+(anglePasso*c),pontoNow.getSize()/2);
					forma.lineTo(pontoCurva.getX(),pontoCurva.getY());
				}
			}
		}
		forma.closePath();
		return forma;
	}
//MAIN
	public Linha(){}
//REDRAW
	public void updatePrint(){
		print=getForm();
		getVetorial().updatePrint();
	}
//DRAW
	public void draw(Graphics2D imagemEdit){
		imagemEdit.setColor(getCor());
		imagemEdit.fill(getForm());
		imagemEdit.setColor(Spline.COR);
		imagemEdit.draw(getLine());
		for(Ponto2D p:getControlPontos())((Ponto)p).draw(imagemEdit);
	}
}