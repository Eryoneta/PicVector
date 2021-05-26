package image.vector;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
public class Spline{
//COR
	public static Color COR=Color.BLUE;
//PONTOS
	private List<Ponto2D>pontos=new ArrayList<>();
		public List<Ponto2D>getControlPontos(){return pontos;}
		public boolean add(Ponto2D ponto){return pontos.add(ponto);}
		public boolean del(Ponto2D ponto){return pontos.remove(ponto);}
		public Ponto2D get(int index){
			index=Math.max(index,0);
			index=Math.min(index,pontos.size()-1);
			return pontos.get(index);
		}
//FORM
	public List<Ponto2D>getAllPontos2D(){
		final List<Ponto2D>pontos=new ArrayList<Ponto2D>();
		for(int p=0,size=getControlPontos().size();p<size;p++){
			final Ponto2D p_1=get(p-1);
			final Ponto2D p0=get(p+0);
			final Ponto2D p1=get(p+1);
			final Ponto2D p2=get(p+2);
			final double dist=p0.getDistance(p1);
			final double passos=1/dist;		//ENTRE 0 E 1, A "DISTÂNCIA" ENTRE OS PONTOS
			for(double passo=0;passo<=1;passo+=passos){
				final Ponto2D subPonto=new Ponto2D(catmullRoom(passo,p_1,p0,p1,p2));		//MÁGICA DO CATMULLROOM
				pontos.add(subPonto);
			}
		}
		return pontos;
	}
		protected Ponto2D catmullRoom(double t,Ponto2D p0,Ponto2D p1,Ponto2D p2,Ponto2D p3){
			//q(t)=((2*P1)+(((-P0)+P2)*t)+(((2*P0)-(5*P1)+(4*P2)-P3)*(t^2))+(((-P0)+(3*P1)-(3*P2)+P3)*(t^3)))*0.5
			return (p1.scale(2).add(p0.scale(-1).add(p2).scale(t)).add(p0.scale(2).sub(p1.scale(5)).add(p2.scale(4)).sub(p3).scale(t*t))
					.add(p0.scale(-1).add(p1.scale(3)).sub(p2.scale(3)).add(p3).scale(t*t*t)).scale(0.5));
		}
	public Path2D.Double getLine(){
		final Path2D.Double linha=new Path2D.Double();
		linha.moveTo(get(0).getX(),get(0).getY());
		for(Ponto2D ponto:getAllPontos2D())linha.lineTo(ponto.getX(),ponto.getY());
		return linha;
	}
//MAIN
	public Spline(){}
//DRAW
	public void draw(Graphics2D imagemEdit){
		imagemEdit.setColor(COR);
		imagemEdit.draw(getLine());
	}
}