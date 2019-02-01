import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.entity.components.PositionComponent;
import com.almasb.fxgl.entity.components.RotationComponent;
import com.almasb.fxgl.entity.components.ViewComponent;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

public class BulletController extends Component {
    private PositionComponent position;
    private BoundingBoxComponent bbox;
    private ViewComponent view;
    private RotationComponent rotation;

    private MoveDirection moveDir;
    private Entity bullet;
    private Point2D velocity;

    public BulletController(MoveDirection moveDir) {
        super();
        this.moveDir = moveDir;
        switch (moveDir) {
            case UP:
                velocity = new Point2D(0,-7);
                break;
            case DOWN:
                velocity = new Point2D(0,7);
                break;
            case LEFT:
                velocity = new Point2D(-7,0);
                break;
            case RIGHT:
                velocity = new Point2D(7,0);
                break;
        }
    }

    @Override
    public void onUpdate(double tpf) {
        position.translate(velocity);
    }

    public Rectangle2D getExplodingRect() {
        switch (moveDir) {
            case UP:
                return new Rectangle2D(
                        position.getX() - 21,
                        position.getY(),
                        50,
                        6);
            case DOWN:
                return new Rectangle2D(
                        position.getX() - 21,
                        position.getY() + bbox.getHeight(),
                        50,
                        6);
            case LEFT:
                return new Rectangle2D(
                        position.getX(),
                        position.getY() - 21,
                        6,
                        50);
            case RIGHT:
                return new Rectangle2D(
                        position.getX() + bbox.getWidth(),
                        position.getY() - 21,
                        6,
                        50);
        }
        System.out.println("move dir: " + moveDir);
        return null;
    }
}
