package main;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
public class Cursor{
//STATES
	public final static int NORMAL=1;			//[0-9]
	public final static int SELECT=2;			//[0-9]
	public final static int MOVE=10;			//[0-9]0
	public final static int CREATE=20;			//[0-9]0
	public final static int DELETE=30;			//[0-9]0
	public final static int SON=40;				//[0-9]0
	public final static int PAI=50;				//[0-9]0
	public final static int EDIT_TITLE=60;		//[0-9]0
	public final static int AREA_SELECT=100;	//[0-9]00
	public final static int AREA_CREATE=200;	//[0-9]00
	public final static int AREA_DELETE=300;	//[0-9]00
	public final static int AREA_SON=400;		//[0-9]00
	public final static int AREA_PAI=500;		//[0-9]00
	public final static int DRAG=1000;			//[0-9]000
	public final static int AUTODRAG=2000;		//[0-9]000
	private int state=NORMAL;
		public int getState(){return state;}
//VAR GLOBAIS
	private JFrame janela;
	public final static int CURSOR_SIZE=32;
//MAIN
	public Cursor(JFrame janela){
		this.janela=janela;
	}
	public void set(int state){
		final int camada1=(state%1000%100%10);
		final int camada2=(state%1000%100)-camada1;
		final int camada3=(state%1000)-camada1-camada2;
		final int camada4=(state)-camada1-camada2-camada3;
		final BufferedImage cursor=new BufferedImage(CURSOR_SIZE,CURSOR_SIZE,BufferedImage.TYPE_INT_ARGB);
		final Graphics editCursor=cursor.getGraphics();
		switch(camada1){
			case NORMAL:		editCursor.drawImage(getCursor("NORMAL"),0,0,null);			break;
			case SELECT:		editCursor.drawImage(getCursor("SELECT"),0,0,null);			break;
		}
		switch(camada2){
			case MOVE:			editCursor.drawImage(getCursor("MOVE"),0,0,null);			break;
			case CREATE:		editCursor.drawImage(getCursor("CREATE"),0,0,null);			break;
			case DELETE:		editCursor.drawImage(getCursor("DELETE"),0,0,null);			break;
			case SON:			editCursor.drawImage(getCursor("SON"),0,0,null);			break;
			case PAI:			editCursor.drawImage(getCursor("PAI"),0,0,null);			break;
			case EDIT_TITLE:	editCursor.drawImage(getCursor("EDIT_TITLE"),0,0,null);		break;
		}
		switch(camada3){
			case AREA_SELECT:	editCursor.drawImage(getCursor("AREA_SELECT"),0,0,null);	break;
			case AREA_CREATE:	editCursor.drawImage(getCursor("AREA_CREATE"),0,0,null);	break;
			case AREA_DELETE:	editCursor.drawImage(getCursor("AREA_DELETE"),0,0,null);	break;
			case AREA_SON:		editCursor.drawImage(getCursor("AREA_SON"),0,0,null);		break;
			case AREA_PAI:		editCursor.drawImage(getCursor("AREA_PAI"),0,0,null);		break;
		}
		switch(camada4){
			case DRAG:			editCursor.drawImage(getCursor("DRAG"),0,0,null);			break;
			case AUTODRAG:		editCursor.drawImage(getCursor("AUTODRAG"),0,0,null);		break;
		}
		Point ponto=new Point(0,0);
		if(camada4==AUTODRAG)ponto=new Point(16,16);
		editCursor.dispose();
		janela.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(cursor,ponto,""));
	}
	private Image getCursor(String nome){
//		Image cursor=Toolkit.getDefaultToolkit().getImage(Cursor.class.getResource("/element/tree/cursores/"+nome+".png"));	//PRECISA DE UM LISTENER, ESPERANDO CARREGAR???
		final Image cursor=new ImageIcon(Cursor.class.getResource("/element/tree/cursores/"+nome+".png")).getImage();
		return cursor;
	}
//COMANDOS
	public final static int NOTHING=0;
	public final static int LEFT=MouseEvent.BUTTON1_DOWN_MASK;
	public final static int MIDDLE=MouseEvent.BUTTON2_DOWN_MASK;
	public final static int RIGHT=MouseEvent.BUTTON3_DOWN_MASK;
	public final static int SHIFT=MouseEvent.SHIFT_DOWN_MASK;
	public final static int CTRL=MouseEvent.CTRL_DOWN_MASK;
	public static boolean match(MouseEvent m,int...keys){
		boolean result=true;
		boolean multiPress=(m.getButton()==MouseEvent.NOBUTTON);
		for(int key1:keys){
			switch(key1){
				case LEFT:case MIDDLE:case RIGHT:
					for(int key2:keys){
						if(key2!=key1)switch(key2){
							case LEFT:case MIDDLE:case RIGHT:
								multiPress=true;
							break;
						}
					}
				break;
			}
		}
		for(int k=0;k<keys.length&&result;k++){
			if(multiPress)switch(keys[k]){
				case LEFT:		result=SwingUtilities.isLeftMouseButton(m);		break;
				case MIDDLE:	result=SwingUtilities.isMiddleMouseButton(m);	break;
				case RIGHT:		result=SwingUtilities.isRightMouseButton(m);	break;
			}else switch(keys[k]){
				case LEFT:		result=(m.getButton()==MouseEvent.BUTTON1);		break;
				case MIDDLE:	result=(m.getButton()==MouseEvent.BUTTON2);		break;
				case RIGHT:		result=(m.getButton()==MouseEvent.BUTTON3);		break;
			}
			switch(keys[k]){
				case SHIFT:		result=m.isShiftDown();							break;
				case CTRL:		result=m.isControlDown();						break;
			}
		}
		return result;
	}
}