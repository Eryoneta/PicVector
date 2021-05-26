package image.vector;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Path2D;
public class Ponto2D{
//COR
	public static Color COR=Color.BLUE;
//LOCAL
	private double x=0;
		public double getX(){return x;}
		public void setX(double x){this.x=x;}
	private double y=0;
		public double getY(){return y;}
		public void setY(double y){this.y=y;}
	public void setLocation(double x,double y){
		this.x=x;
		this.y=y;
	}
//FORM
	public Path2D.Double getForm(){
		final Path2D.Double forma=new Path2D.Double();
		forma.moveTo(getX(),getY());
		forma.lineTo(getX(),getY());
		return forma;
	}
//MAIN
	public Ponto2D(){}
	public Ponto2D(double x,double y){set(x,y);}
	public Ponto2D(Ponto2D ponto){set(ponto.getX(),ponto.getY());}
//CÁLCULOS
	public void set(double x,double y){setX(x);setY(y);}												//CONFIGURA
	public void set(Ponto2D ponto){set(ponto.getX(),ponto.getY());}										//CONFIGURA
	public Ponto2D add(Ponto2D ponto){return new Ponto2D(this.getX()+ponto.getX(),this.getY()+ponto.getY());}	//ADICIONA
	public Ponto2D sub(Ponto2D ponto){return new Ponto2D(this.getX()-ponto.getX(),this.getY()-ponto.getY());}	//SUBTRAI
	public Ponto2D scale(double escala){return new Ponto2D(this.getX()*escala,this.getY()*escala);}			//MULTIPLICA
//FUNÇÕES
	public double getDistance(Ponto2D ponto){							//RETORNA DISTÂNCIA ENTRE ESTE E O PONTO DADO
		final double x=Math.abs(ponto.getX()-getX());
		final double y=Math.abs(ponto.getY()-getY());
		return Math.sqrt((x*x)+(y*y));
	}
	public double getAngle(Ponto2D ponto){							//RETORNA ÂNGULO ENTRE ESTE E O PONTO DADO(COMEÇA NA DIREITA, SEGUE ANTICLOCK)
		final double x=getX()-ponto.getX();
		final double y=getY()-ponto.getY();
		return Math.atan2(y,x)*180/Math.PI;
	}
	public Ponto2D getRelativePoint(double angle,double distance){	//RETORNA PONTO COM DISTÂNCIA E ÂNGULO RELATIVO A ESTE(COMEÇA NA DIREITA, SEGUE ANTICLOCK)
		final double x=getX()+Math.cos(Math.toRadians(angle))*distance;
		final double y=getY()+Math.sin(Math.toRadians(angle))*distance;
		return new Ponto(x,y);
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