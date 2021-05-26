package main;
import image.Imagem;
import image.vector.Linha;
import image.vector.Ponto;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import design.display.window.toolbox.Ferramentas;
import design.display.window.toolbox.Camadas;
import design.display.window.toolbox.Cores;
import design.display.window.toolbox.Historico;
import design.ImagemEditor;
import design.display.Painel;
import design.display.window.Ancora;
import design.display.window.Janela;
import design.display.window.JanelaMestre;
@SuppressWarnings("serial")
public class PicVector{
//EXECUTOR(MAIN)
	public static void main(String[]vars){new PicVector();}
//IMAGENS
	private List<Imagem>imagens=new ArrayList<Imagem>();
//JANELA
	private JanelaMestre janela=new JanelaMestre(){{
		setTitle("PicVector");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(618,618);
		setBackground(Color.WHITE);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Icone.png")));
		add(new Painel(this){{
			add(editor=new ImagemEditor(this){{
				
			}});
		}});
		setVisible(true);
		addWindowListener(new WindowListener(){
			public void windowOpened(WindowEvent w){}
			public void windowIconified(WindowEvent w){}
			public void windowDeiconified(WindowEvent w){}
			public void windowClosing(WindowEvent w){}
			public void windowClosed(WindowEvent w){}
			public void windowActivated(WindowEvent w){repaintInterfaces();}
			public void windowDeactivated(WindowEvent w){repaintInterfaces();}
			private void repaintInterfaces(){
				if(ferramentas!=null)repaint(ferramentas);
				if(cores!=null)repaint(cores);
				if(camadas!=null)repaint(camadas);
				if(historico!=null)repaint(historico);
			}
			private void repaint(Janela janela){
				janela.dispose();			//NECESSÁRIO PARA IMPEDIR QUE FIQUE INVISÍVEL AO DEMINIMIZAR
				janela.setVisible(true);
				janela.repaint();
			}
		});
		addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent r){
				if(editor==null)return;
				editor.setSize(janela.getWidth()-janela.getInsets().left-janela.getInsets().right,
						janela.getHeight()-janela.getInsets().top-janela.getInsets().bottom);
				editor.redraw();
			}
		});
		getBorda().setSpaceExterno(5);
		getBorda().setSpaceTop(80);
		getBorda().setSpaceRight(5);
		getBorda().setSpaceLeft(5);
		getBorda().setSpaceBottom(5);

		addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent m){
				if(editor==null)return;
				if(ponto==null)return;
				ponto.set(editor.getGridPosition(m.getPoint()));
				imagens.get(0).getCamadas().get(0).getVetorial().updatePrint();
				editor.redraw();
			}
		});
		
	}};
//EDITOR DE IMAGENS
	private ImagemEditor editor;
//JANELA DE FERRAMENTAS
	private Ferramentas ferramentas=new Ferramentas(janela){{
		getBorda().getTop().setJanelaAncorada(janela,Ancora.Side.TOP,Ancora.Side.Face.INTERNAL);
		getBorda().getLeft().setJanelaAncorada(janela,Ancora.Side.LEFT,Ancora.Side.Face.INTERNAL);
		setVisible(true);
	}};
//JANELA DE CORES
	private Cores cores=new Cores(janela){{
		getBorda().getLeft().setJanelaAncorada(janela,Ancora.Side.LEFT,Ancora.Side.Face.INTERNAL);
		getBorda().getBottom().setJanelaAncorada(janela,Ancora.Side.BOTTOM,Ancora.Side.Face.INTERNAL);
		setVisible(true);
	}};
//JANELA DE CAMADAS
	private Camadas camadas=new Camadas(janela){{
		getBorda().getRight().setJanelaAncorada(janela,Ancora.Side.RIGHT,Ancora.Side.Face.INTERNAL);
		getBorda().getTop().setJanelaAncorada(janela,Ancora.Side.TOP,Ancora.Side.Face.INTERNAL);
		getBorda().getBottom().setJanelaAncorada(janela,Ancora.Side.BOTTOM,Ancora.Side.Face.INTERNAL);
		setVisible(true);
	}};
//JANELA DE HISTÓRICO
	private Historico historico=new Historico(janela){{
		getBorda().getTop().setJanelaAncorada(camadas,Ancora.Side.TOP,Ancora.Side.Face.ALIGNED);
		getBorda().getRight().setJanelaAncorada(camadas,Ancora.Side.LEFT,Ancora.Side.Face.ALIGNED);
		setVisible(true);
	}};
//MAIN
	private PicVector(){
		abrir(new File(""));
		janela.setLocation(janela.getLocation());
		janela.requestFocus();
		editor.redraw();
	}
	Ponto ponto;
//ABRIR
	private void abrir(File link){
		imagens.add(new Imagem(link));
		editor.setImagem(imagens.get(0));
		final Linha l=new Linha(){{
			add(new Ponto(100,100){{setSize(6);}});
			add(new Ponto(100,300){{setSize(10);}});
			add(new Ponto(300,100){{setSize(6);}});
		}};
		imagens.get(0).getCamadas().get(0).getVetorial().addLinha(l);
		final double x=100.500;
		final double y=100.500;
		final int size=350;
		final int meio=size/2;
		final int offset=size/5;
		final  Linha c=new Linha(){{
			add(ponto=new Ponto(	x,				y				){{setSize(0);}});//1
			add(new Ponto(	x+meio,			y-offset		){{setSize(16);}});//1-2
			add(new Ponto(	x+size,			y				){{setSize(16);}});//2
			add(new Ponto(	x+size+offset,	y+meio			){{setSize(16);}});//2-3
			add(new Ponto(	x+size,			y+size			){{setSize(16);}});//3
			add(new Ponto(	x+meio,			y+size+offset	){{setSize(16);}});//3-4
			add(new Ponto(	x,				y+size			){{setSize(16);}});//4
			add(new Ponto(	x-offset,		y+meio			){{setSize(16);}});//4-1
			add(new Ponto(	x,				y				){{setSize(16);}});//1
			add(new Ponto(	x+meio,			y-offset		){{setSize(16);}});//1-2
			add(new Ponto(	x+size,			y				){{setSize(0);}});//2
		}};
		imagens.get(0).getCamadas().get(0).getVetorial().addLinha(c);
	}
}


/*
	GENERALPATH É IMPRECISO, FLOAT. USAR PATH2D.DOUBLE.
	MATH.POW É LENTO, CHEIO DE CASOS ESPECÍFICOS. USAR MULT.
	
*/