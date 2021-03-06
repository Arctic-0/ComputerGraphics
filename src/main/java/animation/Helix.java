package animation;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.effect.Bloom;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @see . stackoverflow.com/a/37370840/230513
 */
public class Helix extends Application {

    private static final double SIZE = 300;
    private final Content content = Content.create(SIZE);

    public void play() {
        content.animation.play();
    }

    private static final class Content {

        private static final Duration DURATION = Duration.seconds(4);
        private static final Color COLOR = Color.AQUA;
        private static final double WIDTH = 3;
        private final Group group = new Group();
        private final Rotate rx = new Rotate(0, Rotate.X_AXIS);
        private final Rotate ry = new Rotate(0, Rotate.Y_AXIS);
        private final Rotate rz = new Rotate(0, Rotate.Z_AXIS);
        private final Box xAxis;
        private final Box yAxis;
        private final Box zAxis;
        private final Shape circle;
        private final Shape arrow;
        private final Animation animation;

        private static Content create(double size) {
            Content c = new Content(size);
            c.group.getChildren().addAll(c.arrow, c.circle,
                    c.xAxis, c.yAxis, c.zAxis);
            c.group.getTransforms().addAll(c.rz, c.ry, c.rx);
            c.group.setTranslateX(-size / 2);
            c.group.setTranslateY(-size / 2);
            c.group.setTranslateZ(size / 2);
            c.rx.setAngle(35);
            c.ry.setAngle(-45);
            return c;
        }

        private Content(double size) {
            xAxis = createBox(size, WIDTH, WIDTH);
            yAxis = createBox(WIDTH, size, WIDTH);
            zAxis = createBox(WIDTH, WIDTH, size);
            circle = createCircle(size);
            arrow = createShape();
            animation = new ParallelTransition(
                    createTransition(circle, arrow),
                    createTimeline(size / 2));
        }

        private Circle createCircle(double size) {
            Circle c = new Circle(size / 4);
            c.setFill(Color.TRANSPARENT);
            c.setStroke(COLOR);
            return c;
        }

        private Box createBox(double w, double h, double d) {
            Box b = new Box(w, h, d);
            b.setMaterial(new PhongMaterial(COLOR));
            return b;
        }

        private Shape createShape() {
            Shape s = new Polygon(0, 0, -10, -10, 10, 0, -10, 10);
            s.setStrokeWidth(WIDTH);
            s.setStrokeLineCap(StrokeLineCap.ROUND);
            s.setStroke(COLOR);
            s.setEffect(new Bloom());
            return s;
        }

        private Transition createTransition(Shape path, Shape node) {
            PathTransition t = new PathTransition(DURATION, path, node);
            t.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
            t.setCycleCount(Timeline.INDEFINITE);
            t.setInterpolator(Interpolator.LINEAR);
            return t;
        }

        private Timeline createTimeline(double size) {
            Timeline t = new Timeline();
            t.setCycleCount(Timeline.INDEFINITE);
            t.setAutoReverse(true);
            KeyValue keyX = new KeyValue(group.translateXProperty(), size);
            KeyValue keyY = new KeyValue(group.translateYProperty(), size);
            KeyValue keyZ = new KeyValue(group.translateZProperty(), -size);
            KeyFrame keyFrame = new KeyFrame(DURATION.divide(2), keyX, keyY, keyZ);
            t.getKeyFrames().add(keyFrame);
            return t;
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("JavaFX 3D");
        Scene scene = new Scene(content.group, SIZE * 2, SIZE * 2, true);
        primaryStage.setScene(scene);
        scene.setFill(Color.BLACK);
        scene.setOnMouseMoved((final MouseEvent e) -> {
            content.rx.setAngle(e.getSceneY() * 360 / scene.getHeight());
            content.ry.setAngle(e.getSceneX() * 360 / scene.getWidth());
        });
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setFarClip(SIZE * 6);
        camera.setTranslateZ(-3 * SIZE);
        scene.setCamera(camera);
        scene.setOnScroll((final ScrollEvent e) -> {
            camera.setTranslateZ(camera.getTranslateZ() + e.getDeltaY());
        });
        primaryStage.show();
        play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
