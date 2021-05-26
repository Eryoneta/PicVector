package design.display.element;
import java.awt.Graphics;
import design.display.Painel;
public class Toggle extends Botao{
//TOGGLE
	private boolean toggled;
		public boolean isToggled(){return toggled;}
		public void setToggle(boolean toggled){this.toggled=toggled;}
//MAIN
	public Toggle(Painel painel){
		super(painel);
	}
//DRAW
@Override
	public void draw(Graphics imagemEdit){
		switch(state){
			case INACTIVE:default:		imagemEdit.setColor(getCor());				break;
			case HOVERED:case DRAGGED:	imagemEdit.setColor(getColorOnHover());		break;
			case ACTIVE:				imagemEdit.setColor(getColorOnActive());	break;
		}
		imagemEdit.fillRect(0,0,getWidth(),getHeight());
		imagemEdit.dispose();
	}
}