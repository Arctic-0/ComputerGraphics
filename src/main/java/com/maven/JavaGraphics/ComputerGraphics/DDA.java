package com.maven.JavaGraphics.ComputerGraphics;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;

public class DDA extends Application{

	/** 
	*
	* @ClassName : DDA.java
	* @author : Magneto_Wang
	* @date  2018年5月13日 上午11:29:59
	* @Description  画线算法DDA已经完成，封装在BasicShape类中
	* 
	*/
	
	public GraphicsContext gc;
	public Stage stage = new Stage();
    public Canvas canvas;
    public VBox root=new VBox();
    public Scene scene;
	public BasicShape Draw = new BasicShape();
	
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage stage){
//		Canvas sharpCanvas = createCanvasGrid(600, 300, true);
        Canvas blurryCanvas = createCanvasGrid(800, 800, true);
//        VBox root = new VBox(5, sharpCanvas, blurryCanvas);
        canvas=blurryCanvas;
		DDA_LINE();
		DDA_CIRCL();
        root= new VBox(canvas);
        stage.setTitle("DDA直线算法");
        stage.setScene(new Scene(root));
        stage.show();
	}
	/*
	 * 
	 * 画线算法DDA已经完成，封装在BasicShape类中
	 * 
	 */
	public void DDA_LINE(){
        gc = canvas.getGraphicsContext2D() ;
        Draw.setGc(gc);
        double radius=10;
//        Line line = new Line();
//		line.setStartX(0.0f);
//		line.setStartY(0.0f);
//		line.setEndX(100.0f);
//        line.setEndY(100.0f); 
        gc.setStroke(Color.RED);
//        gc.setLineWidth(2.0);
//        gc.strokeLine(0, 0, 100, 100);
//        for()
        DrowPoint(100,400,radius);
        DrowPoint(400,400,radius);
        DrowPoint(600, 100,radius);
        DrowPoint(600, 200,radius);
        DrowPoint(600, 300,radius);
        DrowPoint(600, 400,radius);
        DrowPoint(600, 500,radius);
        DrowPoint(600, 600,radius);
        
        Point2D Begin = new Point2D(0,0);
        Point2D End = new Point2D(800, 800);
        Draw.DrawLine(Begin, End);
//        DDA_LINE_MoreThanOne(Begin,End);
        End = new Point2D(600, 500);
        Draw.DrawLine(Begin, End);
        
        End = new Point2D(600, 400);
        Draw.DrawLine(Begin, End);
//        DDA_LINE_BetweenZeroOne(Begin,End);
        End = new Point2D(600, 300);
        Draw.DrawLine(Begin, End);
        End = new Point2D(600, 200);
        Draw.DrawLine(Begin, End);
//        DDA_LINE_BetweenZeroMinusOne(Begin,End);
        End = new Point2D(600, 100);
        Draw.DrawLine(Begin, End);
        
//        DDA_LINE_LessThanMinusOne(Begin,End);
        
        

        
	}
	
	public void DDA_CIRCL(){
		
	}
	public void DrowPoint(double x, double y, double radius){
		gc.fillOval(x, y, radius, radius);
	}
	private Canvas createCanvasGrid(int width, int height, boolean sharp) {
        canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D() ;
        gc.setLineWidth(1.0);
        for (int x = 0; x < width; x+=10) {
            double x1 ;
            if (sharp) {
                x1 = x + 0.5 ;
            } else {
                x1 = x ;
            }
            gc.moveTo(x1, 0);
            gc.lineTo(x1, height);
            gc.stroke();
        }

        for (int y = 0; y < height; y+=10) {
            double y1 ;
            if (sharp) {
                y1 = y + 0.5 ;
            } else {
                y1 = y ;
            }
            gc.moveTo(0, y1);
            gc.lineTo(width, y1);
            gc.stroke();
        }
        return canvas ;
    }
}
